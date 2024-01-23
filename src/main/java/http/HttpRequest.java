package http;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequest {
    private String method;
    private String path;
    private Map<String, String> headers;
    private String body;

    public HttpRequest(String requestText) {
        this.headers = new HashMap<>();
        parseRequest(requestText);
    }

    private void parseRequest(String requestText) {
        StringTokenizer tokenizer = new StringTokenizer(requestText, "\r\n");
        parseRequestLine(tokenizer.nextToken());

        while (tokenizer.hasMoreTokens()) {
            String line = tokenizer.nextToken();
            if (line.isEmpty()) break; // 헤더의 끝을 만났을 때
            parseHeader(line);
        }

        if (tokenizer.hasMoreTokens()) {
            this.body = tokenizer.nextToken();
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

    public String getQueryString() {
        int queryStartIndex = path.indexOf("?");
        if (queryStartIndex != -1) {
            return path.substring(queryStartIndex + 1);
        }
        return "";
    }
}
