package com.sensedia.sample.password.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document("userCredentials")
@Builder
public class UserCredentials {

    @MongoId
    private String id;
    private String user;
    private List<String> passwords;

}
