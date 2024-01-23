package http.request.parser;

import http.request.HttpRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class RequestBodyParser {
    public static void parse(HttpRequest request, String body) {
        if (!"POST".equalsIgnoreCase(request.getMethod()) || !request.getHeaders().containsKey("Content-Type")) {
            return;
        }

        String contentType = request.getHeaders().get("Content-Type");
        if ("application/x-www-form-urlencoded".equalsIgnoreCase(contentType)) {
            parseUrlEncodedBody(request, body);
        }
    }

    private static void parseUrlEncodedBody(HttpRequest request, String body) {
        String[] pairs = body.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                try {
                    String key = keyValue[0];
                    String value = URLDecoder.decode(keyValue[1], "UTF-8");
                    request.getBodyParams().put(key, value);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
