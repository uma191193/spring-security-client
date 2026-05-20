package org.example.controller;

import org.example.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoControllerClient {

    @Autowired
    private DemoService demoService;

    @GetMapping(value = "/fetchFromProducer")
    public ResponseEntity<String> fetchFromProducer() {
        String fetchResponse = demoService.fetchResponse();
        return new ResponseEntity<>(fetchResponse, HttpStatusCode.valueOf(200));
    }
}
