package controller;

import http.annotation.PostMapping;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import service.UserService;
import session.SessionStorage;
import java.util.Map;

public class UserController {

    private final UserService userService;

    public UserController() {
        this.userService = UserService.getInstance();
    }

    @PostMapping("/user/create")
    public void createUser(HttpRequest request, HttpResponse response) {
        // POST 요청 본문 데이터를 사용하여 User 객체 생성
        Map<String, String> formData = request.getBodyParams();
        String userId = formData.get("userId");
        String password = formData.get("password");
        String name = formData.get("name");
        String email = formData.get("email");

        User user = userService.registerUser(userId, password, name, email);
        System.out.println("user: " + user);

        // 회원가입 성공 후 리디렉션 응답 설정
        response.setStatusCode(302);
        response.setHeader("Location", "/index.html");
    }

    @PostMapping("/user/login")
    public void login(HttpRequest request, HttpResponse response) {
        String userId = request.getBodyParams().get("userId");
        String password = request.getBodyParams().get("password");

        String sessionId = userService.loginUser(userId, password);

        if (sessionId != null) {
            User user = userService.getUserById(userId); // UserService를 사용하여 사용자 정보 가져오기
            SessionStorage sessionStorage = SessionStorage.getInstance();
            sessionStorage.addSession(sessionId, user);

            response.setHeader("Set-Cookie", "sid=" + sessionId + "; Path=/; HttpOnly");
            response.setStatusCode(302);
            response.setHeader("Location", "/index.html");
        } else {
            response.setStatusCode(302);
            response.setHeader("Location", "/user/login_failed.html");
        }
    }
}
