package session;

import model.User;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStorage {
    private static Map<String, User> sessions = new ConcurrentHashMap<>();

    public static void addSession(String sessionId, User user) {
        sessions.put(sessionId, user);
    }
}
