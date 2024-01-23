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
        int contentLength = 0;
        boolean headerEnd = false;

        // 헤더 읽기
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                // 헤더의 끝을 나타내며, 여기서 빈 줄 추가
                if (!headerEnd) {
                    requestBuilder.append("\r\n");
                    headerEnd = true;
                }
                break;
            } else {
                requestBuilder.append(line).append("\r\n");
            }

            // Content-Length 헤더 찾기
            if (line.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(line.substring("Content-Length:".length()).trim());
            }
        }

        // 본문 읽기
        if (contentLength > 0) {
            char[] body = new char[contentLength];
            br.read(body, 0, contentLength);
            requestBuilder.append(new String(body));
        }

        return requestBuilder.toString();
    }
}
