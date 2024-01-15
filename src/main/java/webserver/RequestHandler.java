package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            processRequest(in, dos); // HTTP 요청 처리를 processRequest 메서드로 위임
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

    private void processRequest(InputStream in, DataOutputStream dos) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = br.readLine();

        // 요청 라인에서 URL 추출
        String url = extractUrl(line);

        // 해당 파일 읽기
        byte[] body = readFile(url);

        if (body == null) {
            // 파일이 없는 경우, 404 처리
            response404Header(dos);
        } else {
            // 정상적인 경우, 응답 처리
            response200Header(dos, body.length);
            responseBody(dos, body);
        }
    }

    private String extractUrl(String requestLine) {
        String[] tokens = requestLine.split(" ");
        if (tokens.length > 1) {
            return tokens[1];  // "GET /index.html HTTP/1.1"에서 "/index.html" 반환
        }
        return null; // 적절한 URL을 찾을 수 없는 경우
    }

    private byte[] readFile(String url) {
        try {
            Path filePath = Paths.get("./src/main/resources/templates", url);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            logger.error("File reading error: " + e.getMessage());
            return null;
        }
    }

    private void response404Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("\r\n"); // 헤더와 본문을 분리하기 위해 사용
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}