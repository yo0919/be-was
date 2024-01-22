package http;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HttpRequestTest {
    public static void main(String[] args) {
        // 테스트를 위한 POST 요청 데이터 예제
        String testRequestData = "POST /some/path HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: 32\r\n" +
                "\r\n" +
                "userId=testUser&password=1234";

        // HttpRequest 객체 생성 및 본문 파싱
        HttpRequest httpRequest = new HttpRequest(testRequestData);

        // 파싱 결과 검증
        Map<String, String> bodyParams = httpRequest.getBodyParams();
        System.out.println("Parsed body parameters: " + bodyParams);
    }
}
