package com.sensedia.sample.password.domain.dto;

import com.sensedia.sample.password.domain.UserCredentials;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialsDTO {

    private String user;
    private String password;
    private String confirmPassword;


    public static UserCredentials toUserCredentials(UserCredentialsDTO request) {
        return UserCredentials.builder()
                .user(request.getUser())
                .passwords(List.of(request.getPassword()))
                .build();
    }

}
