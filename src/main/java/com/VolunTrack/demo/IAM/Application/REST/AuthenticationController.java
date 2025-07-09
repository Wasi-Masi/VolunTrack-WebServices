package com.VolunTrack.demo.IAM.Application.REST;

import com.VolunTrack.demo.IAM.Application.REST.Resources.AuthenticationResponseResource;
import com.VolunTrack.demo.IAM.Application.REST.Resources.SignInResource;
import com.VolunTrack.demo.IAM.Application.REST.Resources.SignUpResource;
import com.VolunTrack.demo.IAM.Domain.Model.Aggregates.User;
import com.VolunTrack.demo.IAM.Infrastructure.Repositories.UserRepository;
import com.VolunTrack.demo.IAM.Infrastructure.Tokens.JWT.JwtService;

import com.VolunTrack.demo.VolunteerRegistration.Application.Internal.CommandServices.OrganizationCommandService;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.CreateOrganizationCommand; // Necesitarás este comando

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

    public AuthenticationController(UserRepository userRepository,
                                    PasswordEncoder passwordEncoder,
                                    JwtService jwtService,
                                    AuthenticationManager authenticationManager,
                                    UserDetailsService userDetailsService,
                                    OrganizationCommandService organizationCommandService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.organizationCommandService = organizationCommandService;
    }

    @Operation(summary = "Register a user", description = "Creates a new user in the system.")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpResource signUpResource) {
        if (userRepository.existsByUsername(signUpResource.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpResource.getEmail())) {
            return new ResponseEntity<>("Email is already in use!", HttpStatus.BAD_REQUEST);
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

        userRepository.save(user);

        try {
            CreateOrganizationCommand createOrganizationCommand = new CreateOrganizationCommand(
                    signUpResource.getUsername(),     // Nombre de la organización
                    signUpResource.getDescription(),  // Descripción de la organización
                    signUpResource.getEmail(),        // Email de la organización
                    signUpResource.getPlan()          // Plan de la organización
            );
            organizationCommandService.handle(createOrganizationCommand);
            System.out.println("Organization created successfully for user: " + signUpResource.getUsername());

        } catch (IllegalArgumentException e) {
            System.err.println("Error creating organization for user " + signUpResource.getUsername() + ": " + e.getMessage());
            userRepository.delete(user);
            return new ResponseEntity<>("Failed to create organization: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String jwt = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponseResource(jwt));
    }


    @Operation(summary = "Sign in with an existing user", description = "Validates access tokens for an existing user and gives access to other endpoints.")
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInResource signInResource) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInResource.getUsername(), signInResource.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(signInResource.getUsername());

            String jwt = jwtService.generateToken(userDetails);

            return ResponseEntity.ok(new AuthenticationResponseResource(jwt));

        } catch (Exception e) {
            return new ResponseEntity<>("Invalid username or password!", HttpStatus.UNAUTHORIZED);
        }
    }
}