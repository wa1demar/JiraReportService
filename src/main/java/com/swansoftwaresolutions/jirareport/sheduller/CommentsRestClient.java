package com.swansoftwaresolutions.jirareport.sheduller;

import com.swansoftwaresolutions.jirareport.sheduller.dto.ProjectDto;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

/**
 * @author Vladimir Martynyuk
 */
public class CommentsRestClient {
    static Logger log = Logger.getLogger(CommentsRestClient.class.getName());


    private void getComments() {
        final String uri = "https://swansoftwaresolutions.atlassian.net/rest/api/2/project.json";

        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ProjectDto[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, ProjectDto[].class);

        log.warning(response.getBody().toString());
    }

    private HttpHeaders getHeaders() {
        String plainCreds = "vholovko:12345";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        return headers;
    }

    public static void main(String[] args) {
        CommentsRestClient client = new CommentsRestClient();
        client.getComments();

    }
}
