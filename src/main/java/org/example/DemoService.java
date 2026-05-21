package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service // Marks this class as a Spring-managed service bean containing business logic
public class DemoService {

    @Autowired // Automatically injects a configured RestTemplate instance from the Spring container
    public RestTemplate restTemplate;

    // @Value annotations inject externalized properties
    // (usually defined in application.properties or application.yml)
    @Value("${producer.url}")
    private String url; // Target URL of the external service/producer API

    @Value("${producer.username}")
    private String userName; // Credential: Username required by the producer

    @Value("${producer.password}")
    private String password; // Credential: Password required by the producer

    /**
     * Fetches data from the external producer API using HTTP Basic Authentication.
     * * @return A formatted String containing the producer's response body.
     */
    public String fetchResponse() {

        // 1. Create and configure the HTTP Headers
        HttpHeaders httpHeaders = new HttpHeaders();

        // setBasicAuth automatically handles the Base64 encoding of "username:password"
        // and appends the standard "Authorization: Basic <encoded_string>" header to the request.
        // This replaces the manual Base64 logic commented out below.
        httpHeaders.setBasicAuth(userName, password);

        /* * Manual legacy approach (for reference):
         * String encoded = Base64
         * .getEncoder()
         * .encodeToString((userName + ":" + password).getBytes());
         * httpHeaders.set("Authorization", "Basic " + encoded);
         */

        // 2. Wrap the headers into an HttpEntity object.
        // Void is used here because a standard GET request does not contain a request body.
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        // 3. Execute the synchronous HTTP GET request using RestTemplate
        // - url: The destination endpoint
        // - HttpMethod.GET: The HTTP method being invoked
        // - httpEntity: Contains our Basic Auth headers (and no body)
        // - String.class: Specifies that we expect the response body to be mapped as a plain String
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, HttpMethod.GET, httpEntity, String.class);

        // 4. Extract and return the payload payload from the response wrapper
        return "Response from the producer is : " + responseEntity.getBody();
    }
}