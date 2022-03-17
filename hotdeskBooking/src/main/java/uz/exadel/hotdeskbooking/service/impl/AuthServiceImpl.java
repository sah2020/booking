package uz.exadel.hotdeskbooking.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.exadel.hotdeskbooking.domain.User;
import uz.exadel.hotdeskbooking.dto.request.LoginDTO;
import uz.exadel.hotdeskbooking.dto.response.UserBasicResTO;
import uz.exadel.hotdeskbooking.exception.ForbiddenException;
import uz.exadel.hotdeskbooking.repository.UserRepository;
import uz.exadel.hotdeskbooking.response.success.OkResponse;
import uz.exadel.hotdeskbooking.security.JWTProvider;
import uz.exadel.hotdeskbooking.service.AuthService;

import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService, UserDetailsService {
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findFirstByTelegramIdAndEnabledTrue(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("");
        }
        return user.get();
    }

    @Override
    @Transactional
    public OkResponse login(LoginDTO loginDTO) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword()
            ));
            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateTokenAdmin(user.getUsername());
            UserBasicResTO userBasicResTO = user.toBasic();
            userBasicResTO.setToken(token);
            return new OkResponse(userBasicResTO);
        } catch (BadCredentialsException ex) {
            log.info("Unauthorized user: " + loginDTO.getUsername());
            throw new BadCredentialsException("");
        }
    }

    @Transactional
    @Override
    public OkResponse getCurrentUser() throws AuthenticationException {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) throw new ForbiddenException("");

        Authentication authentication = context.getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new ForbiddenException("");
        }
        if (authentication.getPrincipal() instanceof User) {
            return new OkResponse(((User) authentication.getPrincipal()).toBasic());
        }
        return new OkResponse();
    }

    public User getCurrentUserDetails() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) return null;

        Authentication authentication = context.getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        if (authentication.getPrincipal() instanceof User) {
            return ((User) authentication.getPrincipal());
        }
        return null;
    }
}
