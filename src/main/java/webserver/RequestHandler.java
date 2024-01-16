package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            String url = extractUrl(httpRequest);
            if (url.equals("/")) {
                url = "/index.html"; // 루트 URL 요청 시, index.html로 변경
            }

            // 파일 읽기
            byte[] body = readFile("src/main/resources/templates" + url);
            if (body == null) {
                response404Header(dos); // 파일이 없을 경우 404 응답
            } else {
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
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
    private String extractUrl(String httpRequest) {
        String requestLine = httpRequest.split("\n")[0]; // 첫 번째 라인 추출
        return requestLine.split(" ")[1]; // "GET /index.html HTTP/1.1"에서 "/index.html" 추출
    }

    private byte[] readFile(String filePath) {
        try {
            Path path = Paths.get(filePath).normalize();
            if (Files.exists(path)) {
                return Files.readAllBytes(path);
            }
        } catch (IOException e) {
            logger.error("File reading error: " + e.getMessage());
        }
        return null;
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

    private User createUserFromRequest(String url) {
        String queryString = url.split("\\?")[1];//? 문자는 정규표현식의 예약어이기 때문에 split() 메소드를 사용할 때 "?"이 아닌 "\\?"를 사용
        String[] params = queryString.split("&");//&을 기준으로 쿼리 스트링을 파라미터들로 분리

        String userId = "";
        String password = "";
        String name = "";
        String email = "";

        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length != 2) {
                continue; // 키와 밸류 둘다 있는게 아니면 건너뛰기
            }

            String key = keyValue[0];
            String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);

            if ("userId".equals(key)) {
                userId = value;
            } else if ("password".equals(key)) {
                password = value;
            } else if ("name".equals(key)) {
                name = value;
            } else if ("email".equals(key)) {
                email = value;
            }
        }

        return new User(userId, password, name, email);
    }
}