package webserver;

import java.io.*;
import java.net.Socket;

import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileUtils;
import utils.HttpUtils;


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

            // HTTP 요청 전체를 읽고 로그로 출력
            String httpRequest = readHttpRequest(in);
            logger.debug("Received HTTP Request:\n{}", httpRequest);

            // 요청 URL 추출
            String url = HttpUtils.extractUrl(httpRequest);

            // UserController 인스턴스 생성
            UserController userController = new UserController();

            // URL 경로에 따른 처리
            if (url.startsWith("/user/create")) {
                byte[] responseBody = userController.createUser(url);
                response200Header(dos, responseBody.length);
                responseBody(dos, responseBody);
            } else {
                handleStaticFile(url, dos);
            }
        } catch (IOException e) {
            logger.error("Error processing request: ", e);
        }
    }

    private void handleStaticFile(String url, DataOutputStream dos) throws IOException {
        if (url.equals("/")) {
            url = "/index.html"; // 루트 URL 요청 시, index.html로 변경
        }
        byte[] body = FileUtils.readFile("src/main/resources/templates" + url);
        if (body == null) {
            response404Header(dos); // 파일이 없을 경우 404 응답
        } else {
            response200Header(dos, body.length);
            responseBody(dos, body);
        }
    }


    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response404Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found\r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String readHttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder requestBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            requestBuilder.append(line).append("\n");
        }
        return requestBuilder.toString();
    }
}