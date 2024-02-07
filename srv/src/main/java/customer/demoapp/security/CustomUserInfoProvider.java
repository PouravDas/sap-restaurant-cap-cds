package customer.demoapp.security;

import com.sap.cds.services.request.UserInfo;
import com.sap.cds.services.runtime.UserInfoProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomUserInfoProvider implements UserInfoProvider {

    private UserInfoProvider defaultProvider;

    @Override
    public UserInfo get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            return null;
        else return new UserInfoImpl(authentication);
    }

    @Override
    public void setPrevious(UserInfoProvider previous) {
        defaultProvider = previous;
    }

    private static class UserInfoImpl implements UserInfo {

        private Authentication authentication;

        private UserInfoImpl(Authentication authentication) {
            this.authentication = authentication;
        }

        @Override
        public String getName() {
            return authentication.getName();
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public Set<String> getRoles() {
            Set<String> roles = new HashSet<>();
            authentication.getAuthorities().forEach(a -> roles.add(a.getAuthority()));
            return roles;
        }
    }
}
