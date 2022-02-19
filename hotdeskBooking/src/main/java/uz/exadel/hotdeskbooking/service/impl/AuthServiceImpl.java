package uz.exadel.hotdeskbooking.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.config.security.JWTProvider;
import uz.exadel.hotdeskbooking.domain.User;
import uz.exadel.hotdeskbooking.dto.LoginDTO;
import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.dto.UserBasicResTO;
import uz.exadel.hotdeskbooking.exception.RestException;
import uz.exadel.hotdeskbooking.repository.UserRepository;
import uz.exadel.hotdeskbooking.service.AuthService;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private final ResponseItem unauthorizedResponse = new ResponseItem("User not found. Please contact the administration.", HttpStatus.UNAUTHORIZED.value());

    private final JWTProvider jwtProvider;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(JWTProvider jwtProvider, UserRepository userRepository, @Lazy AuthenticationManager authenticationManager) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByTelegramIdAndEnabledTrue(username);
        if (user.isEmpty()) {
            throw new RestException("User not found. Please contact the administration.", HttpStatus.UNAUTHORIZED);
        }
        return user.get();
    }

    @Override
    public ResponseItem login(LoginDTO loginDTO) {
        loginDTO.setPassword("password"); //only check telegram id, default password is "password"
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword()
            ));
            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateTokenAdmin(user.getUsername());
            UserBasicResTO userBasicResTO = user.toBasic();
            userBasicResTO.setToken(token);
            return new ResponseItem(userBasicResTO);
        } catch (BadCredentialsException badCredentialsException) {
            return unauthorizedResponse;
        }
    }

    @Override
    public ResponseItem getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return unauthorizedResponse;
        }

        Authentication authentication = context.getAuthentication();
        if (authentication == null || authentication.getDetails() == null || authentication instanceof AnonymousAuthenticationToken) {
            return unauthorizedResponse;
        }
        authentication.getDetails();
        if (authentication.getDetails() instanceof User) {
            return new ResponseItem(((User) authentication.getDetails()).toBasic());
        }
        return unauthorizedResponse;

    }

}
