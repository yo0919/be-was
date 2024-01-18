package utils;

import model.User;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UserUtilsTest {

    @Test
    void createUserFromRequestTest() {
        String queryString = "userId=test&password=1234&name=TestUser&email=test@example.com";
        User user = UserUtils.createUserFromRequest(queryString);

        assertThat(user.getUserId()).isEqualTo("test");
        assertThat(user.getPassword()).isEqualTo("1234");
        assertThat(user.getName()).isEqualTo("TestUser");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
    }
}
