package utils;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

public class UserUtils {
    private static final Logger logger = LoggerFactory.getLogger(UserUtils.class);

    public static User createUserFromRequest(Map<String, String> paramMap) {
        String userId = paramMap.getOrDefault("userId", "");
        String password = paramMap.getOrDefault("password", "");
        String name = paramMap.getOrDefault("name", "");
        String email = paramMap.getOrDefault("email", "");

        User user = new User(userId, password, name, email);
        logger.info("User created: {}", user); // User 객체 생성 로그

        return user;
    }
}