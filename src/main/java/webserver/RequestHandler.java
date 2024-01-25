package webserver;

import controller.mapping.ControllerMethodMapper;
import controller.mapping.RequestMappingInfo;
import controller.UserController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpUtils;
import utils.StaticFileUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;
    private final ControllerMethodMapper controllerMethodMapper;
    private final UserController userController;

    public RequestHandler(Socket connectionSocket, ControllerMethodMapper controllerMethodMapper, UserController userController) {
        this.connection = connectionSocket;
        this.controllerMethodMapper = controllerMethodMapper;
        this.userController = userController;
    }
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            String httpRequestString = HttpUtils.readHttpRequest(in);
            HttpRequest request = new HttpRequest(httpRequestString);
            HttpResponse response = new HttpResponse();

            RequestMappingInfo requestMappingInfo = new RequestMappingInfo(request.getPath(), request.getHttpMethod());
            Method method = controllerMethodMapper.getMappedMethod(requestMappingInfo);

            if (method != null) {
                // 매핑된 컨트롤러 메서드 호출
                invokeControllerMethod(method, request, response);
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

    private void invokeControllerMethod(Method method, HttpRequest request, HttpResponse response) {
        try {
            method.invoke(userController, request, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("컨트롤러 메서드 호출 에러", e);
        }
    }
}
