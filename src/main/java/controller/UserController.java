package controller;

import http.HttpResponse;
import http.HttpRequest;
import model.User;
import utils.UserUtils;

public class UserController {
    public void createUser(HttpRequest request, HttpResponse response) {
        // URL에서 쿼리 문자열을 가져옴
        String queryString = request.getQueryString();

        // URL 대신 쿼리 문자열을 사용하여 User 객체 생성
        User user = UserUtils.createUserFromRequest(queryString);

        System.out.println("User created: " + user);

        // 회원가입 성공 후 리디렉션 응답 설정
        response.setStatusCode(303); // HTTP 상태 코드 302 설정
        response.setHeader("Location", "/index.html"); // 리디렉션할 페이지 설정
    }
}