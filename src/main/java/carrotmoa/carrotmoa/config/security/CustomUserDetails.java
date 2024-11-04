package carrotmoa.carrotmoa.config.security;

import carrotmoa.carrotmoa.entity.User;
import carrotmoa.carrotmoa.enums.AuthorityCode;
import carrotmoa.carrotmoa.model.response.UserLoginResponseDto;
import carrotmoa.carrotmoa.repository.UserProfileRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class CustomUserDetails implements UserDetails {
    private final User user;
    private final UserProfileRepository userprofileRepository;
    private final String defaultProfileImageUrl;

    public CustomUserDetails(User user, UserProfileRepository userprofileRepository, String defaultProfileImageUrl) {
        this.user = user;
        this.userprofileRepository = userprofileRepository;
        this.defaultProfileImageUrl = defaultProfileImageUrl;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(AuthorityCode.getAuthorityCodeName(user.getAuthorityId())));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public UserLoginResponseDto getUserProfile() {
        UserLoginResponseDto userLoginResponseDto
                = new UserLoginResponseDto(Objects.requireNonNull(userprofileRepository.findByUserId(user.getId())));

        if (userLoginResponseDto.getPicUrl() == null) {
            userLoginResponseDto.setPicUrl(defaultProfileImageUrl);
        }
        return userLoginResponseDto;
    }
}
