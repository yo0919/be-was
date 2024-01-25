package controller;

import http.response.HttpResponse;
import http.request.HttpRequest;
import model.User;
import utils.UserUtils;

import java.util.Map;

public class UserController {
    public void createUser(HttpRequest request, HttpResponse response) {
        // 'POST' 요청일 경우 본문 데이터를 사용하여 User 객체 생성
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            Map<String, String> formData = request.getBodyParams();
            User user = UserUtils.createUserFromRequest(formData);

            System.out.println("User created: " + user);

            // 회원가입 성공 후 리디렉션 응답 설정
            response.setStatusCode(302); // HTTP 상태 코드 302 설정
            response.setHeader("Location", "/index.html"); // 리디렉션할 페이지 설정
        }
    }
}
