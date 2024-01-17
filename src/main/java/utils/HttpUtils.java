package utils;

public class HttpUtils {

    public static String extractUrl(String httpRequest) {
        String requestLine = httpRequest.split("\n")[0];
        return requestLine.split(" ")[1];
    }
}