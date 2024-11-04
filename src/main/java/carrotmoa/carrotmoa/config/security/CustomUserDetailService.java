package carrotmoa.carrotmoa.config.security;

import carrotmoa.carrotmoa.entity.User;
import carrotmoa.carrotmoa.repository.UserProfileRepository;
import carrotmoa.carrotmoa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    @Value("${spring.user.profile.default-image}")
    private String defaultProfileImageUrl;

    public CustomUserDetailService(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new CustomUserDetails(user, userProfileRepository, defaultProfileImageUrl);
    }
}
