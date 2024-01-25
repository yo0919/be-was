package webserver;

import http.mapping.ControllerMethodMapper;
import http.mapping.RequestMappingInfo;
import controller.UserController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpUtils;
import utils.StaticFileUtils;
import http.session.SessionHandler;
import model.User;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            String httpRequestString = HttpUtils.readHttpRequest(in);
            HttpRequest request = new HttpRequest(httpRequestString);
            HttpResponse response = new HttpResponse();

            // 세션 확인 및 사용자 정보 조회
            SessionHandler sessionHandler = new SessionHandler();
            User user = sessionHandler.checkSession(request);

            ControllerMethodMapper controllerMethodMapper = new ControllerMethodMapper();
            UserController userController = new UserController();
            controllerMethodMapper.scan(userController);

            RequestMappingInfo requestMappingInfo = new RequestMappingInfo(request.getPath(), request.getHttpMethod());
            Method method = controllerMethodMapper.getMappedMethod(requestMappingInfo);

            if (method != null) {
                // 매핑된 컨트롤러 메서드 호출
                invokeControllerMethod(method, userController, request, response, user); // 사용자 정보 추가 전달
            } else {
                // 정적 파일 처리 또는 404 Not Found 처리
                StaticFileUtils.handleStaticFile(request.getPath(), response);
            }

            // 응답 전송
            response.send(dos);
        } catch (IOException e) {
            logger.error("요청 처리 에러: ", e);
        }
    }

    private void invokeControllerMethod(Method method, UserController userController, HttpRequest request, HttpResponse response, User user) {
        try {
            // 추가된 user 매개변수를 사용하는 로직 구현
            method.invoke(userController, request, response, user);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("컨트롤러 메서드 호출 에러", e);
        }
    }
}
