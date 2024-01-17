package controller;

import model.User;
import utils.UserUtils;

public class UserController {
    public byte[] createUser(String url) {
        User user = UserUtils.createUserFromRequest(url);

        System.out.println("User created: " + user);

        // 성공 응답 메시지
        String successResponse = "회원가입 성공";
        return successResponse.getBytes();
    }
}