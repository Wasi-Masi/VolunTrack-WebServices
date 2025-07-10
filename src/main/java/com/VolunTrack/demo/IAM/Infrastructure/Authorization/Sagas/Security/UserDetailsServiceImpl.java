package com.VolunTrack.demo.IAM.Infrastructure.Authorization.Sagas.Security;

import com.VolunTrack.demo.IAM.Infrastructure.Repositories.UserRepository;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.context.MessageSource;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public UserDetailsServiceImpl(UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String message = messageSource.getMessage("auth.userNotFound", new Object[]{username}, LocaleContextHolder.getLocale());
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(message));
    }

}