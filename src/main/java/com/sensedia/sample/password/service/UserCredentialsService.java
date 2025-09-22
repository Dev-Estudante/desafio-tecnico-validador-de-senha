package com.sensedia.sample.password.service;


import com.sensedia.sample.password.domain.UserCredentials;
import com.sensedia.sample.password.domain.dto.UserCredentialsDTO;
import com.sensedia.sample.password.repository.UserCredentialsRepository;
import com.sensedia.sample.password.utils.PasswordValidate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCredentialsService {

    private final UserCredentialsRepository repository;

    public UserCredentials save(UserCredentialsDTO request) {

        PasswordValidate.validate(request.getUser(), request.getPassword(), request.getConfirmPassword());
        UserCredentials userCredentials = UserCredentialsDTO.toUserCredentials(request);

        var savedUser = findByUser(userCredentials.getUser());

        if (savedUser.getUser() != null) {
            List<String> passwords = PasswordValidate.checkPasswordReuse(request.getPassword(), savedUser.getPasswords());
            savedUser.setPasswords(passwords);
            return repository.save(savedUser);
        }

        return repository.save(userCredentials);
    }

    public UserCredentials findByUser(String user) {
        return repository.findByUser(user).orElse(new UserCredentials());
    }

}
