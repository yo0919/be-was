package http.response.handler;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.SessionHandler;
import model.User;
import http.response.renderer.HtmlResponseRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginResponseHandler {

    private final SessionHandler sessionHandler;
    private static final Logger logger = LoggerFactory.getLogger(LoginResponseHandler.class);

    public LoginResponseHandler() {
        this.sessionHandler = new SessionHandler();
    }

    public void handle(HttpRequest request, HttpResponse response) {
        User loggedInUser = sessionHandler.checkSession(request);
        logger.debug("Handling login response for request path: {}", request.getPath());
        logger.debug("Logged in user: {}", loggedInUser);

        if (request.getPath().equals("/index.html") || request.getPath().equals("/")) {
            byte[] responseBody = HtmlResponseRenderer.renderWelcomePage(loggedInUser);
            response.setBody(responseBody);
            response.setHeader("Content-Type", "text/html");
            logger.debug("Response body set for welcome page");
        }
    }
}

