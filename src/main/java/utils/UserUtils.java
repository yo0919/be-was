package utils;

import model.User;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UserUtils {

    public static User createUserFromRequest(String url) {
        String queryString = url.split("\\?")[1];
        String[] params = queryString.split("&");

        Map<String, String> paramMap = new HashMap<>();

        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length != 2) {
                continue;
            }

            String key = keyValue[0];
            String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
            paramMap.put(key, value);
        }

        return new User(
                paramMap.getOrDefault("userId", ""),
                paramMap.getOrDefault("password", ""),
                paramMap.getOrDefault("name", ""),
                paramMap.getOrDefault("email", "")
        );
    }
}