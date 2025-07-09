package com.VolunTrack.demo.IAM.Application.REST;

import com.VolunTrack.demo.IAM.Application.REST.Resources.AuthenticationResponseResource;
import com.VolunTrack.demo.IAM.Application.REST.Resources.SignInResource;
import com.VolunTrack.demo.IAM.Application.REST.Resources.SignUpResource;
import com.VolunTrack.demo.IAM.Domain.Model.Aggregates.User;
import com.VolunTrack.demo.IAM.Infrastructure.Repositories.UserRepository;

import com.VolunTrack.demo.IAM.Infrastructure.Tokens.JWT.JwtService;
import com.VolunTrack.demo.VolunteerRegistration.Application.Internal.CommandServices.OrganizationCommandService;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.CreateOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Organization;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authorization", description = "Authorization and JWT Management Endpoints")
public class AuthenticationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final OrganizationCommandService organizationCommandService;
    private final MessageSource messageSource;


    public AuthenticationController(UserRepository userRepository,
                                    PasswordEncoder passwordEncoder,
                                    JwtService jwtService,
                                    AuthenticationManager authenticationManager,
                                    UserDetailsService userDetailsService,
                                    OrganizationCommandService organizationCommandService,
                                    MessageSource messageSource) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.organizationCommandService = organizationCommandService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Register a user", description = "Creates a new user in the system.")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpResource signUpResource) {
        if (userRepository.existsByUsername(signUpResource.getUsername())) {
            String message = messageSource.getMessage("auth.usernameTaken", null, LocaleContextHolder.getLocale());
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpResource.getEmail())) {
            String message = messageSource.getMessage("auth.emailTaken", null, LocaleContextHolder.getLocale());
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }


        User user = new User();
        user.setUsername(signUpResource.getUsername());
        user.setPassword(passwordEncoder.encode(signUpResource.getPassword()));
        user.setEmail(signUpResource.getEmail());
        user.setPhoneNumber(signUpResource.getPhoneNumber());
        user.setPlan(signUpResource.getPlan());
        user.setDescription(signUpResource.getDescription());
        user.setProfilePictureUrl(signUpResource.getProfilePictureUrl());
        user.setBannerPictureUrl(signUpResource.getBannerPictureUrl());


        try {
            CreateOrganizationCommand createOrganizationCommand = new CreateOrganizationCommand(
                    signUpResource.getUsername(),
                    signUpResource.getDescription(),
                    signUpResource.getEmail(),
                    signUpResource.getPlan()
            );

            Organization createdOrganization = organizationCommandService.handle(createOrganizationCommand)
                    .orElseThrow(() -> new IllegalStateException(
                            messageSource.getMessage("auth.orgCreationFailedState", new Object[]{signUpResource.getUsername()}, LocaleContextHolder.getLocale())
                    ));
            user.setOrganizationId(createdOrganization.getId());
            userRepository.save(user);

            String successLog = messageSource.getMessage("auth.orgCreationSuccessLog",
                    new Object[]{signUpResource.getUsername(), createdOrganization.getId()}, LocaleContextHolder.getLocale());
            System.out.println(successLog);

        } catch (IllegalArgumentException e) {
            String errorLog = messageSource.getMessage(
                    "auth.orgCreationErrorLog", new Object[]{signUpResource.getUsername(), e.getMessage()}, LocaleContextHolder.getLocale());
            System.err.println(errorLog);
            if (user.getId() != null) {
                userRepository.delete(user);
            }
            String message = messageSource.getMessage("auth.orgCreationFailed", new Object[]{e.getMessage()}, LocaleContextHolder.getLocale());
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            String unexpectedLog = messageSource.getMessage(
                    "auth.orgCreationUnexpectedLog", new Object[]{signUpResource.getUsername(), e.getMessage()}, LocaleContextHolder.getLocale());
            System.err.println(unexpectedLog);

            String message = messageSource.getMessage("auth.orgCreationUnexpected", new Object[]{e.getMessage()}, LocaleContextHolder.getLocale());
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String jwt = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponseResource(jwt, user.getOrganizationId()));
    }


    @Operation(summary = "Sign in with an existing user", description = "Validates access tokens for an existing user and gives access to other endpoints.")
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInResource signInResource) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInResource.getUsername(), signInResource.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(signInResource.getUsername());
            User authenticatedUser = (User) userDetails;

            String jwt = jwtService.generateToken(userDetails);

            return ResponseEntity.ok(new AuthenticationResponseResource(jwt, authenticatedUser.getOrganizationId()));

        } catch (Exception e) {
            e.printStackTrace();
            String message = messageSource.getMessage("auth.invalidCredentials", null, LocaleContextHolder.getLocale());
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }
    }
}