package utils;

import model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserUtilsTest {

    @Test
    void createUserFromRequestTest() {
        String testUrl = "/user/create?userId=test&password=1234&name=TestUser&email=test@example.com";
        User user = UserUtils.createUserFromRequest(testUrl);

        assertEquals("test", user.getUserId());
        assertEquals("1234", user.getPassword());
        assertEquals("TestUser", user.getName());
        assertEquals("test@example.com", user.getEmail());
    }
}
