package http.session;

import model.User;
import http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionHandler {
    private static final Logger logger = LoggerFactory.getLogger(SessionHandler.class);

    public User checkSession(HttpRequest request) {
        String sessionId = request.getSessionId();
        logger.debug("Checking session with ID: {}", sessionId);

        if (sessionId != null) {
            User user = SessionStorage.getInstance().getUserBySessionId(sessionId);
            logger.debug("User retrieved from session: {}", user);
            return user;
        }

        logger.debug("No session ID found or no user associated with this session ID.");
        return null;
    }
}
