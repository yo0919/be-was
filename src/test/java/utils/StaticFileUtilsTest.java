package utils;

import http.response.HttpResponse;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;

class StaticFileUtilsTest {

    @Test
    void testHandleStaticFile() throws IOException {
        String url = "/static/css/styles.css"; // 실제 존재하는 파일 URL
        HttpResponse response = new HttpResponse();

        StaticFileUtils.handleStaticFile(url, response);

        assertThat(response.getStatusCode()).isEqualTo(200); // 상태 코드 검증
        assertThat(response.getHeaders().get("Content-Type")).contains("text/css"); // MIME 타입 검증
    }
}
