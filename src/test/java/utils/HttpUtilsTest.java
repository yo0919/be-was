package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpUtilsTest {
    @Test
    void extractUrl_ValidRequest_ReturnsCorrectUrl() {
        String httpRequest = "GET /index.html HTTP/1.1\nHost: localhost\n\n";
        String expectedUrl = "/index.html";

        String actualUrl = HttpUtils.extractUrl(httpRequest);

        assertEquals(expectedUrl, actualUrl);
    }
}