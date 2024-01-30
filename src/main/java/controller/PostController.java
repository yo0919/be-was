package controller;

import db.Database;
import http.annotation.PostMapping;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileUtils;
import webserver.RequestHandler;


import java.io.IOException;

public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    @PostMapping("/qna/write")
    public void handleWriteRequest(HttpRequest request, HttpResponse response) {
        String writer = request.getBodyParams().get("writer");
        String title = request.getBodyParams().get("title");
        String contents = request.getBodyParams().get("contents");

        // 게시글 객체 생성 및 데이터베이스에 추가
        Post post = new Post(writer, title, contents);
        Database.addPost(post);

        // createIndexPage 메서드를 호출하여 동적으로 생성된 HTML 페이지를 가져옵니다.
        String updatedHtml = createIndexPage();

        // 응답 본문을 업데이트된 HTML 페이지로 설정합니다.
        response.setBody(updatedHtml.getBytes());
        response.setHeader("Content-Type", "text/html; charset=utf-8");
        response.setStatusCode(200); // HTTP 상태 코드를 200(OK)로 설정
    }

    public String createIndexPage() {
        try {
            // 'index.html' 템플릿 파일을 문자열로 읽어옵니다.
            String templateHtml = FileUtils.readFileAsString("templates/index.html");

            // 게시글 목록을 HTML 형식으로 변환합니다.
            StringBuilder postsHtml = new StringBuilder();
            logger.debug("변환완료");

            // Database 클래스에서 모든 게시글을 가져와 HTML 요소로 변환합니다.
            for (Post post : Database.getAllPosts()) {
                // 여기서 각 게시글을 HTML 요소로 변환합니다. 예를 들어:
                postsHtml.append("<li><div class=\"wrap\"><div class=\"main\">")
                        .append("<strong class=\"subject\"><a href=\"./qna/show.html\">")
                        .append(post.getTitle())
                        .append("</a></strong>")
                        // 여기에 추가적인 게시글 정보를 포함할 수 있습니다.
                        .append("</div></div></li>");
            }

            // 템플릿 파일에서 <!-- Posts --> 마커를 변환된 게시글 목록으로 대체합니다.
            return templateHtml.replace("<!-- Posts -->", postsHtml.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return "Error loading index page";
        }
    }
}
