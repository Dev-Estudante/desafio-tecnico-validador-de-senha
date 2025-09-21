package com.sensedia.sample.password.repository;

import com.sensedia.sample.password.domain.UserCredentials;
import org.springframework.data.repository.CrudRepository;

public interface UserCredentialsRepository extends CrudRepository<UserCredentials, String> {
}
