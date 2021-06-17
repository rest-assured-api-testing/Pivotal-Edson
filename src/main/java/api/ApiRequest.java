package api;

import io.restassured.http.Header;
import io.restassured.http.Headers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiRequest {
    private String baseUri;
    private String endpoint;
    private String body;
    private String token;
    private Enum<ApiMethod> method;
    private List<Header> headers;
    private Map<String, String> queryParams;
    private Map<String, String> pathParams;

    public ApiRequest() {
        headers = new ArrayList<>();
        queryParams = new HashMap<>();
        pathParams = new HashMap<>();
    }

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Enum<ApiMethod> getMethod() {
        return method;
    }

    public void setMethod(Enum<ApiMethod> method) {
        this.method = method;
    }

    public void setHeader(final String header, final String value) {
        headers.add(new Header(header, value));
    }

    public void setQueryParam(final String param, final String value) {
        queryParams.put(param, value);
    }

    public void setPathParam(final String param, final String value) {
        pathParams.put(param, value);
    }

    public Headers getHeaders() {
        return new Headers(headers);
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public Map<String, String> getPathParams() {
        return pathParams;
    }

    public ApiRequest baseUri(String baseUri) {
        this.setBaseUri(baseUri);
        return this;
    }

    public ApiRequest endpoint(String endpoint) {
        this.setEndpoint(endpoint);
        return this;
    }

    public ApiRequest body(String body) {
        this.setBody(body);
        return this;
    }

    public ApiRequest token(String token) {
        this.setToken(token);
        return this;
    }

    public ApiRequest method(Enum<ApiMethod> method) {
        this.setMethod(method);
        return this;
    }

    public ApiRequest addHeader(final String header, final String value) {
        this.setHeader(header, value);
        return this;
    }

    public ApiRequest addQueryParam(final String param, final String value) {
        this.setQueryParam(param, value);
        return this;
    }

    public ApiRequest addPathParam(final String param, final String value) {
        this.setPathParam(param, value);
        return this;
    }
}
