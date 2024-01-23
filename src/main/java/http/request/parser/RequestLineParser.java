package http.request.parser;

import http.request.HttpRequest;
import java.util.StringTokenizer;

public class RequestLineParser {
    public static void parse(HttpRequest request, String requestLine) {
        StringTokenizer tokenizer = new StringTokenizer(requestLine);
        request.setMethod(tokenizer.nextToken());
        request.setPath(tokenizer.nextToken());
    }
}
