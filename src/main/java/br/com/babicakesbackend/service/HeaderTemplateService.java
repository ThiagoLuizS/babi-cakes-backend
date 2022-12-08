package br.com.babicakesbackend.service;

import com.google.common.base.Charsets;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


public class HeaderTemplateService {

    public HttpHeaders createHeaderPixForBasicAuthorization(String apiKey) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json;charset=utf-8");
        httpHeaders.add("Authorization", apiKey);
        return httpHeaders;
    }

    public HttpHeaders createHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Accept-Encoding", "gzip, deflate, br");
        httpHeaders.set("cache-control", "no-cache");
        httpHeaders.add("User-Agent", "PostmanRuntime");
        httpHeaders.add("Connection", "keep-alive");
        httpHeaders.add("Accept", "*/*");
        httpHeaders.add("Content-Type", "application/json;charset=utf-8");
        return httpHeaders;
    }
}
