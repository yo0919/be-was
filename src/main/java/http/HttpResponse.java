package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private int statusCode;
    private Map<String, String> headers;
    private byte[] body;
    public Map<String, String> getHeaders() {
        return headers;
    }

    // 특정 키에 해당하는 헤더 값을 반환하는 getHeader 메서드
    public String getHeader(String key) {
        return headers.get(key);
    }
    public HttpResponse() {
        this.headers = new HashMap<>();
    }
    //getter
    public int getStatusCode() {
        return statusCode;
    }
    //setter
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }
    public void setContentType(String fileExtension) {
        String mimeType = Mime.convertMime(fileExtension).getMimeType(); // MIME 타입을 가져오는 부분 수정
        setHeader("Content-Type", mimeType);
    }
    public void setBody(byte[] body) {
        this.body = body;
        if (body != null) {
            this.headers.put("Content-Length", String.valueOf(body.length));
        }
    }

    public void send(DataOutputStream dos) throws IOException {
        dos.writeBytes("HTTP/1.1 " + statusCode + " \r\n");
        // Ensure Content-Type is set
        headers.putIfAbsent("Content-Type", "text/plain");
        for (Map.Entry<String, String> header : headers.entrySet()) {
            dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
        if (body != null) {
            dos.write(body, 0, body.length);
        }
        dos.flush();
    }
}