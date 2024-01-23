package http.request.parser;

import http.request.HttpRequest;
import java.util.StringTokenizer;

public class RequestHeaderParser {
    public static void parse(HttpRequest request, StringTokenizer tokenizer) {
        while (tokenizer.hasMoreTokens()) {
            String line = tokenizer.nextToken();
            if (line.equals("\r")) break;
            int idx = line.indexOf(":");
            if (idx != -1) {
                String key = line.substring(0, idx).trim();
                String value = line.substring(idx + 1).trim();
                request.getHeaders().put(key, value);
            }
        }
    }
}