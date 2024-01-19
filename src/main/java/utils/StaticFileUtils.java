package utils;

import http.HttpResponse;
import http.Mime;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(StaticFileUtils.class);
    private static final String TEMPLATE_ROOT = "src/main/resources/templates";
    private static final String STATIC_ROOT = "src/main/resources/static";

    public static void handleStaticFile(String url, HttpResponse response) throws IOException {
        String filePath = determineFilePath(url);
        logger.debug("Requested file path: {}", filePath);
        String fileExtension = getFileExtension(filePath);
        byte[] body = FileUtils.readFile(filePath);

        if (body == null) {
            response.setStatusCode(404); // 파일이 없을 경우 404 응답
        } else {
            response.setStatusCode(200); // 200 OK 응답
            String mimeType = Mime.convertMime(fileExtension).getMimeType();
            response.setHeader("Content-Type", mimeType); // MIME 타입 설정
            response.setBody(body);
        }
    }

    private static String determineFilePath(String url) {
        if (url.equals("/")) {
            return TEMPLATE_ROOT + "/index.html"; // 루트 URL 요청 시 index.html로 변경
        }
        if (url.startsWith("/css/") || url.startsWith("/js/")) {
            String resourcePath = url.startsWith("/static/") ? url.substring(7) : url;
            return STATIC_ROOT + resourcePath; // '/css/', '/js/' 경로로 시작하는 경우
        }
        return TEMPLATE_ROOT + url; // 그 외의 경우는 'templates' 경로
    }

    private static String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot == -1) {
            return ""; // 확장자가 없는 경우
        }
        return fileName.substring(lastIndexOfDot + 1);
    }
}
