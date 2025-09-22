package com.sensedia.sample.password.repository;

import com.sensedia.sample.password.domain.UserCredentials;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserCredentialsRepository extends MongoRepository<UserCredentials, String> {

    Optional<UserCredentials> findByUser(String user);

}
