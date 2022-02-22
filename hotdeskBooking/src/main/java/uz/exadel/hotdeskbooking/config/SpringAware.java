package uz.exadel.hotdeskbooking.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.exadel.hotdeskbooking.domain.UserDomain;

import java.util.Optional;

public class SpringAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())){
            UserDomain user = (UserDomain) authentication.getPrincipal();
            return Optional.of(user.getId());
        }
        return Optional.empty();
    }
}
