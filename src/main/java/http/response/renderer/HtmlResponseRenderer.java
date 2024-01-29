package http.response.renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileUtils;
public class HtmlResponseRenderer {
    private static final Logger logger = LoggerFactory.getLogger(HtmlResponseRenderer.class);
    public static String renderHtmlWithUserName(String filePath, String userName) {
        try {
            String htmlContent = FileUtils.readFileAsString(filePath);

            // 로그인과 회원가입 버튼을 포함하는 HTML 부분을 찾아서 사용자 인사말로 교체
            String userGreeting = "<li class=\"nav-item\">안녕하세요, " + userName + "님!</li>";

            // 기존 로그인/회원가입 버튼을 찾아 교체
            htmlContent = htmlContent.replaceFirst(
                    "<ul class=\"nav navbar-nav navbar-right\">\\s*<li class=\"active\"><a href=\"index.html\">Posts</a></li>",
                    "<ul class=\"nav navbar-nav navbar-right\">" +
                            "<li class=\"active\"><a href=\"index.html\">Posts</a></li>" + userGreeting
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
