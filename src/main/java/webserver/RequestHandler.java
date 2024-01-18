package webserver;

import java.io.*;
import java.net.Socket;

import controller.UserController;
import http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileUtils;
import http.HttpRequest;
import http.HttpResponse;


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
                // 정적 파일 처리
                handleStaticFile(request.getPath(), response);
            }

            // 응답 전송
            response.send(dos);
        } catch (IOException e) {
            logger.error("Error processing request: ", e);
        }
    }

    private void handleStaticFile(String url, HttpResponse response) throws IOException {
        if (url.equals("/")) {
            url = "/index.html"; // 루트 URL 요청 시, index.html로 변경
        }
        byte[] body = FileUtils.readFile("src/main/resources/templates" + url);
        if (body == null) {
            response.setStatusCode(404); // 파일이 없을 경우 404 응답
        } else {
            response.setStatusCode(200); // 200 OK 응답
            response.setHeader("Content-Type", "text/html;charset=utf-8");
            response.setBody(body);
        }
    }
}