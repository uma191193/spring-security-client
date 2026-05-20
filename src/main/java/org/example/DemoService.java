package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class DemoService {

    @Autowired
    public RestTemplate restTemplate;

    @Value("${producer.url}")
    private String url;
    @Value("${producer.username}")
    private String userName;
    @Value("${producer.password}")
    private String password;

    public String fetchResponse() {
        // Encode to Base64
        String encoded = Base64
                .getEncoder()
                .encodeToString((userName + ":" + password).getBytes());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Basic " + encoded);

        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, HttpMethod.GET, httpEntity, String.class);
        return "Response from the producer is : " + responseEntity.getBody();
    }
}
