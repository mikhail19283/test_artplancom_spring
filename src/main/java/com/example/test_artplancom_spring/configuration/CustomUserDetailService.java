package com.example.test_artplancom_spring.configuration;

import com.example.test_artplancom_spring.entity.Owner;
import com.example.test_artplancom_spring.repository.OwnerRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Component
public class CustomUserDetailService implements UserDetailsService {

    private final OwnerRepository ownerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final Map<String, AuthenticationTimeout> authenticationInfo;

    public CustomUserDetailService(OwnerRepository ownerRepository, BCryptPasswordEncoder passwordEncoder) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationInfo = new HashMap<>();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Owner> ownerOpt = ownerRepository.findByName(username);
        if (ownerOpt.isPresent()
                && passwordEncoder.matches(extractPassword(), ownerOpt.get().getPassword())
                && !isUserBlocked(username)) {
            Owner owner = ownerOpt.get();
            return new User(owner.getName(), owner.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("user"));
        } else {
            addUserBlock(username);

            throw new UsernameNotFoundException("Пользователь не найден");
        }
    }

    private void addUserBlock(String username) {
        AuthenticationTimeout timeout = authenticationInfo.get(username);
        if (timeout == null) {
            authenticationInfo.put(username, new AuthenticationTimeout(1, LocalDateTime.now()));
        } else {
            if (tryToRemoveBlockedUser(timeout)) {
                authenticationInfo.remove(username);
            } else {
                if(timeout.getCount() < 10) {
                    authenticationInfo.put(username, new AuthenticationTimeout(timeout.getCount() + 1, LocalDateTime.now()));
                }
            }
        }
    }

    private boolean tryToRemoveBlockedUser(AuthenticationTimeout timeout) {
        Duration duration = Duration.between(timeout.getLastTryTime(), LocalDateTime.now());

        if (timeout.count == 10) {
            if (duration.toSeconds() > 30) {
                return true;
            }
        }

        return false;
    }

    private boolean isUserBlocked(String username) {
        AuthenticationTimeout timeout = authenticationInfo.get(username);
        if (timeout != null) {
            long durationBetweenLastTrySeconds = Duration.between(timeout.getLastTryTime(), LocalDateTime.now()).toSeconds();
            boolean notBlocked = timeout.count == 10 && durationBetweenLastTrySeconds > 30;
            if(notBlocked) {
                authenticationInfo.remove(username);
            }

            return !notBlocked;
        }

        return false;
    }

    private CharSequence extractPassword() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String authorizationHeader = request.getHeader("Authorization");
        String loginPasswordEncoded = authorizationHeader.substring(authorizationHeader.indexOf(" ") + 1);
        String loginPasswordDecoded = new String(Base64.getDecoder().decode(loginPasswordEncoded));

        return loginPasswordDecoded.substring(loginPasswordDecoded.indexOf(":") + 1);
    }

    private static class AuthenticationTimeout {
        private final Integer count;
        private final LocalDateTime lastTryTime;

        public AuthenticationTimeout(Integer count, LocalDateTime lastTryTime) {
            this.count = count;
            this.lastTryTime = lastTryTime;
        }

        public Integer getCount() {
            return count;
        }

        public LocalDateTime getLastTryTime() {
            return lastTryTime;
        }
    }

}
