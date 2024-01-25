package http.session;

import model.User;
import http.request.HttpRequest;

public class SessionHandler {
    public User checkSession(HttpRequest request) {
        String sessionId = request.getSessionId();
        if (sessionId != null) {
            return SessionStorage.getInstance().getUserBySessionId(sessionId);
        }
        return null;
    }
}
