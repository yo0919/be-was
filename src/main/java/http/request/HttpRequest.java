package http.request;

import http.enums.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String path;
    private Map<String, String> headers;
    private Map<String, String> bodyParams;

    public HttpRequest(String requestText) {
        this.headers = new HashMap<>();
        this.bodyParams = new HashMap<>();
        HttpRequestParser.parse(this, requestText);
    }

    // Getter
    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getBodyParams() {
        return bodyParams;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpMethod getHttpMethod() {
        return HttpMethod.valueOf(this.method.toUpperCase());
    }

    // Setter
    public void setMethod(String method) {
        this.method = method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setBodyParams(Map<String, String> bodyParams) {
        this.bodyParams = bodyParams;
    }
}
