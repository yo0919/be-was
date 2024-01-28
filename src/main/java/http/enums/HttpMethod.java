package http.enums;

import java.lang.annotation.Annotation;
import http.annotation.GetMapping;
import http.annotation.PostMapping;
import java.util.stream.Stream;

public enum HttpMethod {
    GET(GetMapping.class),
    POST(PostMapping.class);

    private final Class<? extends Annotation> annotationClass;

    HttpMethod(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public static HttpMethod resolve(Class<? extends Annotation> annotationClass) {
        return Stream.of(HttpMethod.values())
                .filter(httpMethod -> httpMethod.annotationClass.equals(annotationClass))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지않는 어노테이션: " + annotationClass));
    }
}
