package controller;

import http.annotation.PostMapping;
import http.response.HttpResponse;
import http.request.HttpRequest;
import model.User;
import utils.UserUtils;

import java.util.Map;

public class UserController {

    @PostMapping("/user/create")
    public void createUser(HttpRequest request, HttpResponse response) {
        // POST 요청 본문 데이터를 사용하여 User 객체 생성
        Map<String, String> formData = request.getBodyParams();
        User user = UserUtils.createUserFromRequest(formData);

        System.out.println("User created: " + user);

        // 회원가입 성공 후 리디렉션 응답 설정
        response.setStatusCode(302);
        response.setHeader("Location", "/index.html");
    }
}
