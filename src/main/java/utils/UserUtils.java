package utils;

import model.User;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class UserUtils {

    public static User createUserFromRequest(String url) {
        String queryString = url.split("\\?")[1];
        String[] params = queryString.split("&");

        String userId = "";
        String password = "";
        String name = "";
        String email = "";

        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length != 2) {
                continue;
            }

            String key = keyValue[0];
            String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);

            if ("userId".equals(key)) {
                userId = value;
            } else if ("password".equals(key)) {
                password = value;
            } else if ("name".equals(key)) {
                name = value;
            } else if ("email".equals(key)) {
                email = value;
            }
        }

        return new User(userId, password, name, email);
    }
}

