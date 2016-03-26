package com.swansoftwaresolutions.jirareport.rest.client;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

/**
 * @author Vutaliy Holovko
 */
public abstract class AbstractRestClient {

    protected HttpEntity<String> request = new HttpEntity<>(getHeaders());
    RestTemplate restTemplate = new RestTemplate();

    public HttpHeaders getHeaders() {
        String plainCreds = "vholovko:12345";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        return headers;
    }
}
