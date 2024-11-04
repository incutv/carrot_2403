package carrotmoa.carrotmoa.model.response;

import carrotmoa.carrotmoa.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDto {
    private long userId;
    private String nickname;
    private String picUrl;
    private long addressId;
    private String phone_number;
    private String birthday;
    private String defaultProfileImageUrl;

    public UserLoginResponseDto(UserProfile userProfile) {
        this.userId = userProfile.getUserId();
        this.nickname = userProfile.getNickname();

        if (userProfile.getPicUrl() != null) {
            this.picUrl = userProfile.getPicUrl();
        }

        if (userProfile.getAddressId() != null) {
            this.addressId = userProfile.getAddressId();
        }

        if (userProfile.getPhoneNumber() != null) {
            this.phone_number = userProfile.getPhoneNumber();
        }

        if (userProfile.getBirthday() != null) {
            this.birthday = userProfile.getBirthday().toString();
        }
    }
}
