package http.request;

import http.request.parser.RequestLineParser;
import http.request.parser.RequestHeaderParser;
import http.request.parser.RequestBodyParser;
import java.util.StringTokenizer;

public class HttpRequestParser {
    public static void parse(HttpRequest request, String requestText) {
        StringTokenizer tokenizer = new StringTokenizer(requestText, "\n");
        RequestLineParser.parse(request, tokenizer.nextToken());
        RequestHeaderParser.parse(request, tokenizer);

        if (tokenizer.hasMoreTokens()) {
            String body = tokenizer.nextToken();
            RequestBodyParser.parse(request, body);
        }
    }
}
