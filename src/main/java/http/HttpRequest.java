package http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequest {
    private String method;
    private String path;
    private Map<String, String> headers;
    private String body;
    private Map<String, String> bodyParams; // POST 요청의 본문 파라미터를 저장할 Map

    public HttpRequest(String requestText) {
        this.headers = new HashMap<>();
        this.bodyParams = new HashMap<>();
        parseRequest(requestText);
    }

    private void parseRequest(String requestText) {
        StringTokenizer tokenizer = new StringTokenizer(requestText, "\n");
        parseRequestLine(tokenizer.nextToken());

        while (tokenizer.hasMoreTokens()) {
            String line = tokenizer.nextToken();
            if (line.equals("\r")) break; // 헤더의 끝을 만났을 때
            parseHeader(line);
        }

        if (tokenizer.hasMoreTokens()) {
            this.body = tokenizer.nextToken();
            parseBody(); // POST 요청의 본문 파싱
        }
    }

    private void parseRequestLine(String requestLine) {
        StringTokenizer tokenizer = new StringTokenizer(requestLine);
        this.method = tokenizer.nextToken();
        this.path = tokenizer.nextToken();
    }

    private void parseHeader(String headerLine) {
        int idx = headerLine.indexOf(":");
        if (idx != -1) {
            String key = headerLine.substring(0, idx).trim();
            String value = headerLine.substring(idx + 1).trim();
            this.headers.put(key, value);
        }
    }

    // POST 요청 본문 파싱
    private void parseBody() {
        if ("POST".equalsIgnoreCase(this.method) && this.headers.containsKey("Content-Type")) {
            String contentType = this.headers.get("Content-Type");
            if ("application/x-www-form-urlencoded".equalsIgnoreCase(contentType)) {
                parseUrlEncodedBody();
            }
        }
    }

    // URL 인코딩된 본문 파싱
// URL 인코딩된 본문 파싱
    private void parseUrlEncodedBody() {
        if (this.body != null && !this.body.isEmpty()) {
            String[] pairs = this.body.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    try {
                        String value = URLDecoder.decode(keyValue[1], "UTF-8");
                        this.bodyParams.put(key, value); // 본문 파라미터 저장
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    // Getter
    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getBodyParams() {
        return bodyParams; // 본문 파라미터 반환
    }
}