package http;

import http.request.HttpRequest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Map;

public class HttpRequestTest {
    @Test
    public void testParseRequest() {
        String requestText =
                "POST /user/create HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Content-Length: 85\n" +
                        "Cache-Control: max-age=0\n" +
                        "sec-ch-ua: \"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"\n" +
                        "sec-ch-ua-mobile: ?0\n" +
                        "sec-ch-ua-platform: \"macOS\"\n" +
                        "Upgrade-Insecure-Requests: 1\n" +
                        "Origin: http://localhost:8080\n" +
                        "Content-Type: application/x-www-form-urlencoded\n" +
                        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36\n" +
                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\n" +
                        "Sec-Fetch-Site: same-origin\n" +
                        "Sec-Fetch-Mode: navigate\n" +
                        "Sec-Fetch-User: ?1\n" +
                        "Sec-Fetch-Dest: document\n" +
                        "Referer: http://localhost:8080/user/form.html\n" +
                        "Accept-Encoding: gzip, deflate, br\n" +
                        "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n" +
                        "\n" +
                        "userId=yo0919&password=1234&name=%EC%A0%84%EC%9A%A9%ED%98%B8&email=0919yo%40naver.com\n";

        HttpRequest httpRequest = new HttpRequest(requestText);

        // 헤더 검증
        assertThat(httpRequest.getHeaders()).containsEntry("Host", "localhost:8080")
                .containsEntry("Content-Length", "81");

        // 본문 검증
        String expectedBody = "userId=test&password=24&name=%EC%A0%84%EC%9A%A9%ED%98%B8&email=0919yo%40naver.com";
        assertThat(httpRequest.getBody()).isEqualTo(expectedBody);

        // 본문 파라미터 검증
        Map<String, String> bodyParams = httpRequest.getBodyParams();
        assertThat(bodyParams).containsEntry("userId", "test")
                .containsEntry("password", "24")
                .containsEntry("name", "전용호")
                .containsEntry("email", "0919yo@naver.com");
    }
    @Test
    public void testParseUrlEncodedBody() {
        String testRequestData = "POST /some/path HTTP/1.1\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: 32\r\n\r\n" +
                "userId=testUser&password=1234";

        HttpRequest httpRequest = new HttpRequest(testRequestData);

        // 본문 파싱 후, bodyParams 맵 검증
        Map<String, String> bodyParams = httpRequest.getBodyParams();
        assertThat(bodyParams)
                .isNotEmpty()
                .containsEntry("userId", "testUser")
                .containsEntry("password", "1234");
    }
}
