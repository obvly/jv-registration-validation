package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "valid_login";
    private static final String VALID_PASSWORD = "valid_password";
    private static final int VALID_AGE = 20;
    private static final int ADULT_AGE = 18;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_Ok() {
        User user = createValidUser();
        User result = registrationService.register(user);

        assertNotNull(result);
        assertEquals(user, result);
        assertEquals(user, Storage.people.get(0));
    }

    @Test
    void register_age18_ok() {
        User user = createValidUser();
        user.setAge(ADULT_AGE);
        User result = registrationService.register(user);

        assertNotNull(result);
        assertEquals(user, result);
        assertEquals(user, Storage.people.get(0));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user1 = createValidUser();
        Storage.people.add(user1);

        User user2 = createValidUser();
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_nullAge_notOk() {
        User user = createValidUser();
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = createValidUser();
        user.setAge(-1);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_underage_notOk() {
        User user = createValidUser();
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = createValidUser();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = createValidUser();
        user.setLogin("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = createValidUser();
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = createValidUser();
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_fiveCharPassword_notOk() {
        User user = createValidUser();
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_sixCharPassword_Ok() {
        User user = createValidUser();
        user.setPassword("123456");
        User result = registrationService.register(user);

        assertNotNull(result);
        assertEquals(user, result);
        assertEquals(user, Storage.people.get(0));
    }

    @Test
    void register_eightCharPassword_Ok() {
        User user = createValidUser();
        user.setPassword("12345678");
        User result = registrationService.register(user);

        assertNotNull(result);
        assertEquals(user, result);
        assertEquals(user, Storage.people.get(0));
    }

    private User createValidUser() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        return user;
    }
}
