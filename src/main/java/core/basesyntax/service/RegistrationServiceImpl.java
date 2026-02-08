package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        validateAge(user.getAge());
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login "
                    + user.getLogin() + " already exists");
        }
        return storageDao.add(user);
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Age cannot be null");
        }
        if (age < 0) {
            throw new RegistrationException("Age cannot be negative: " + age);
        }
        if (age < MIN_AGE) {
            throw new RegistrationException("User is too young. Min age: "
                    + MIN_AGE + ", actual: " + age);
        }
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Login cannot be null");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login is too short. Min length: "
                    + MIN_LOGIN_LENGTH + ", actual: " + login.length());
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new RegistrationException("Password cannot be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password is too short. Min length: "
                    + MIN_PASSWORD_LENGTH + ", actual: " + password.length());
        }
    }
}
