package http.response.handler;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.SessionHandler;
import model.User;
import http.response.renderer.HtmlResponseRenderer;

public class LoginResponseHandler {

    private final SessionHandler sessionHandler;

    public LoginResponseHandler() {
        this.sessionHandler = new SessionHandler();
    }

    public void handle(HttpRequest request, HttpResponse response) {
        User loggedInUser = sessionHandler.checkSession(request);
        if (request.getPath().equals("/index.html") || request.getPath().equals("/")) {
            byte[] responseBody = HtmlResponseRenderer.renderWelcomePage(loggedInUser);
            response.setBody(responseBody);
            response.setHeader("Content-Type", "text/html");
        }
    }
}
