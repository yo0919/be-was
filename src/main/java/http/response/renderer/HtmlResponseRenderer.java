package http.response.renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileUtils;
public class HtmlResponseRenderer {
    private static final Logger logger = LoggerFactory.getLogger(HtmlResponseRenderer.class);
    public static String renderHtmlWithUserName(String filePath, String userName) {
        try {
            String htmlContent = FileUtils.readFileAsString(filePath);

            // 환영 문구를 먼저 추가
            String userGreeting = "<li class=\"nav-item welcome-message\">안녕하세요, 전용호님!</li>\n";

            // "Posts" 버튼과 환영 문구의 순서를 변경
            htmlContent = htmlContent.replaceFirst(
                    "<ul class=\"nav navbar-nav navbar-right\">\\s*<li class=\"active\"><a href=\"index.html\">Posts</a></li>",
                    "<ul class=\"nav navbar-nav navbar-right\">" +
                            userGreeting + "<li class=\"active\"><a href=\"index.html\">Posts</a></li>"
            );

            // 로그인/회원가입 버튼을 삭제
            htmlContent = htmlContent.replaceAll(
                            "<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>", "")
                    .replaceAll("<li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>", "");


            // 로그 출력
            logger.debug("Transformed HTML: {}", htmlContent);
            return htmlContent;
        } catch (Exception e) {
            logger.error("Error rendering HTML with user name", e);
            return "Error loading page";
        }
    }
}
