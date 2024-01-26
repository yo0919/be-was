package http.response.renderer;

import model.User;

public class HtmlResponseRenderer {

    public static byte[] renderWelcomePage(User user) {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<html><body>");
        if (user != null) {
            htmlContent.append("안녕하세요, ").append(user.getName()).append("님!");
        } else {
            htmlContent.append("<a href='/login.html'>Login</a>");
        }
        htmlContent.append("</body></html>");
        return htmlContent.toString().getBytes();
    }
}
