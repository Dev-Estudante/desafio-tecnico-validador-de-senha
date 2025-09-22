package com.sensedia.sample.password.utils;

import com.sensedia.sample.password.exception.PasswordInvalidException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PasswordValidate {

    private static final Logger log = LoggerFactory.getLogger(PasswordValidate.class);

    public static void validate(String user, String password, String confirmPassword) {
        log.info("Iniciando validação da senha para o usuário '{}'", user);

        checkPasswordsMatch(password, confirmPassword);
        checkMinLength(password);
        checkUppercase(password);
        checkLowercase(password);
        checkDigit(password);
        checkSpecialCharacter(password);
        checkContainsUsername(user, password);

        log.info("Senha do usuário '{}' validada com sucesso", user);
    }


    private static void checkPasswordsMatch(String password, String confirmPassword) {
        log.info("Validando se as senhas coincidem");
        if (!password.equals(confirmPassword)) {
            log.warn("Falha na validação: senhas não coincidem");
            throw new PasswordInvalidException("As senhas não coincidem.");
        }
    }

    private static void checkMinLength(String password) {
        log.info("Validando tamanho mínimo da senha");
        if (password.length() < 8) {
            log.warn("Falha na validação: senha com menos de 8 caracteres");
            throw new PasswordInvalidException("A senha deve conter pelo menos 8 caracteres.");
        }
    }

    private static void checkUppercase(String password) {
        log.info("Validando presença de letra maiúscula");
        if (!PasswordRegexValidator.hasUppercase(password)) {
            log.warn("Falha na validação: não contém letra maiúscula");
            throw new PasswordInvalidException("A senha deve conter pelo menos uma letra maiúscula.");
        }
    }

    private static void checkLowercase(String password) {
        log.info("Validando presença de letra minúscula");
        if (!PasswordRegexValidator.hasLowercase(password)) {
            log.warn("Falha na validação: não contém letra minúscula");
            throw new PasswordInvalidException("A senha deve conter pelo menos uma letra minúscula.");
        }
    }

    private static void checkDigit(String password) {
        log.info("Validando presença de número");
        if (!PasswordRegexValidator.hasDigits(password)) {
            log.warn("Falha na validação: não contém número");
            throw new PasswordInvalidException("A senha deve conter pelo menos um número.");
        }
    }

    private static void checkSpecialCharacter(String password) {
        log.info("Validando presença de caractere especial");
        if (!PasswordRegexValidator.hasSpecialCharacters(password)) {
            log.warn("Falha na validação: não contém caractere especial");
            throw new PasswordInvalidException("A senha deve conter pelo menos um caractere especial.");
        }
    }

    private static void checkContainsUsername(String user, String password) {
        log.info("Validando se a senha contém o nome de usuário");
        if (password.toLowerCase().contains(user.toLowerCase())) {
            log.warn("Falha na validação: senha contém o nome do usuário '{}'", user);
            throw new PasswordInvalidException("A senha não pode conter o nome do usuário.");
        }
    }

    public static List<String> checkPasswordReuse(String password, List<String> passwordList) {

        log.info("Validando se a senha já foi usada recentemente");
        if (passwordList.contains(password)) {
            log.warn("Falha na validação: senha já utilizada");
            throw new PasswordInvalidException("A senha já foi usada recentemente.");
        } else if (passwordList.size() == 5) {
            passwordList.removeFirst();
        }
        passwordList.add(password);
        return passwordList;
    }

}
