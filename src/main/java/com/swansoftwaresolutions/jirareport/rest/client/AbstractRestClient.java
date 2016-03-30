package com.swansoftwaresolutions.jirareport.rest.client;

import com.swansoftwaresolutions.jirareport.core.dto.config.ConfigDto;
import com.swansoftwaresolutions.jirareport.core.service.ConfigService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Vutaliy Holovko
 */
@Service
public class AbstractRestClient {

    private ConfigService configService;


    protected HttpEntity<String> request;
    RestTemplate restTemplate = new RestTemplate();


    @Autowired
    public AbstractRestClient(ConfigService configService) {
        this.configService = configService;

        request = new HttpEntity<>(getHeaders());

    }

    public HttpHeaders getHeaders() {
        ConfigDto configDto = configService.retrieveConfig();
        String plainCreds = configDto.getJiraUser() + ":" + configDto.getJiraPass();
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        return headers;
    }
}
