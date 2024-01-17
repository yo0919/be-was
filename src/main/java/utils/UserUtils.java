package utils;

import model.User;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UserUtils {

    public static User createUserFromRequest(String queryString) {
        Map<String, String> paramMap = new HashMap<>();

        if (queryString != null && !queryString.isEmpty()) {
            String[] params = queryString.split("&");

            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                    paramMap.put(key, value);
                }
            }
        }

        return new User(
                paramMap.getOrDefault("userId", ""),
                paramMap.getOrDefault("password", ""),
                paramMap.getOrDefault("name", ""),
                paramMap.getOrDefault("email", "")
        );
    }
}
