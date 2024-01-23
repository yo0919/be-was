package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class HttpUtils {
    public static String readHttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder requestBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            requestBuilder.append(line).append("\n");
        }
        return requestBuilder.toString();
    }
}
