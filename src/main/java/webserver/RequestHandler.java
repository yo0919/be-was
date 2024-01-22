package webserver;

import java.io.*;
import java.net.Socket;
import controller.UserController;
import utils.HttpUtils;
import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StaticFileUtils; // StaticFileUtils를 import 합니다.

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;

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

            // UserController 인스턴스 생성
            UserController userController = new UserController();

            // 요청 경로에 따라 처리
            if (request.getPath().startsWith("/user/create")) {
                userController.createUser(request, response);
            } else {
                // 정적 파일 처리를 StaticFileUtils를 사용하여 처리합니다.
                StaticFileUtils.handleStaticFile(request.getPath(), response);
            }

            // 응답 전송
            response.send(dos);
        } catch (IOException e) {
            logger.error("Error processing request: ", e);
        }
    }
}