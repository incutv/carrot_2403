package carrotmoa.carrotmoa;

import carrotmoa.carrotmoa.enums.AuthorityCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class test {
    public static void main(String[] args) {
        System.out.println("Id : "+AuthorityCode.USER.getId());
        System.out.println("Id : "+AuthorityCode.USER);
        System.out.println("Id : "+AuthorityCode.USER.name());
        System.out.println("Id : "+AuthorityCode.USER.toString());
        System.out.println("Id : "+AuthorityCode.USER.ordinal());
        System.out.println("Id : "+ AuthorityCode.getAuthorityCodeName(3L));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("1234"));
    }
}
