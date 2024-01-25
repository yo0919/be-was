package controller;

import db.Database;
import http.annotation.PostMapping;
import http.response.HttpResponse;
import http.request.HttpRequest;
import model.User;
import session.SessionStorage;
import utils.UserUtils;
import java.util.UUID;
import java.util.Map;

public class UserController {

    @PostMapping("/user/create")
    public void createUser(HttpRequest request, HttpResponse response) {
        // POST 요청 본문 데이터를 사용하여 User 객체 생성
        Map<String, String> formData = request.getBodyParams();
        User user = UserUtils.createUserFromRequest(formData);

        // User 객체를 데이터베이스에 저장
        Database.addUser(user);

        System.out.println("User created: " + user);

        // 회원가입 성공 후 리디렉션 응답 설정
        response.setStatusCode(302);
        response.setHeader("Location", "/index.html");
    }
    @PostMapping("/user/login")
    public void login(HttpRequest request, HttpResponse response) {
        String userId = request.getBodyParams().get("userId");
        String password = request.getBodyParams().get("password");

        User user = Database.findUserById(userId);

        if (user != null && user.getPassword().equals(password)) {
            String sessionId = UUID.randomUUID().toString(); // 세션 ID 생성
            SessionStorage.addSession(sessionId, user); // 세션 저장소에 사용자 정보 저장

            response.setHeader("Set-Cookie", "sid=" + sessionId + "; Path=/; HttpOnly"); // 쿠키 설정
            response.setStatusCode(302);
            response.setHeader("Location", "/index.html");
        }
        else {
            // 로그인 실패 처리
            response.setStatusCode(302);
            response.setHeader("Location", "/user/login_failed.html");
        }
    }
}
