package http.mapping;

import java.lang.reflect.Method;

public class RequestMethodMapping {
    private final RequestMappingInfo requestMappingInfo;
    private final Method method;

    public RequestMethodMapping(RequestMappingInfo requestMappingInfo, Method method) {
        this.requestMappingInfo = requestMappingInfo;
        this.method = method;
    }

    public RequestMappingInfo getRequestMappingInfo() {
        return requestMappingInfo;
    }

    public Method getMethod() {
        return method;
    }
}
