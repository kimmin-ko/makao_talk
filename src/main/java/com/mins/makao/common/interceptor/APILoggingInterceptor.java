package com.mins.makao.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNullApi;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.net.URI;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public class APILoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);

        ClientHttpResponse response = execution.execute(request, body);

        traceResponse(response, request.getURI());
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) {
        String requestLog =
                "[REQUEST] " +
                        "URI: " + request.getURI() +
                        ", METHOD: " + request.getMethod() +
                        ", REQUEST BODY: " + new String(body, UTF_8);

        log.info(requestLog);
    }

    private void traceResponse(ClientHttpResponse response, URI uri) throws IOException {
        String responseLog =
                "[RESPONSE] " +
                        "URI: " + uri +
                        ", STATUS CODE: " + response.getStatusCode() +
                        ", RESPONSE BODY: " + StreamUtils.copyToString(response.getBody(), UTF_8);

        log.info(responseLog);
    }

}