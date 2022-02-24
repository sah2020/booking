package uz.exadel.hotdeskbooking.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.xml.registry.infomodel.User;
import java.util.Optional;

public class AuditingConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())){
            User user = (User) authentication.getPrincipal();
            return Optional.of("field");
        }
        return Optional.empty();
    }
}
