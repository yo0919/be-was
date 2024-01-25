package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
    public static String readHttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder requestBuilder = new StringBuilder();
        Map<String, String> headers = new HashMap<>();
        String line;
        int contentLength = 0;

        // 헤더 읽기
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            requestBuilder.append(line).append("\r\n");

            int index = line.indexOf(":");
            if (index > 0) {
                String headerName = line.substring(0, index).trim();
                String headerValue = line.substring(index + 1).trim();
                headers.put(headerName, headerValue);

                if ("Content-Length".equalsIgnoreCase(headerName)) {
                    contentLength = Integer.parseInt(headerValue);
                }
            }
        }
        requestBuilder.append("\r\n"); // 헤더의 끝

        // 본문 읽기
        if (contentLength > 0) {
            char[] body = new char[contentLength];
            br.read(body, 0, contentLength);
            requestBuilder.append(body);
        }

        return requestBuilder.toString();
    }
}
