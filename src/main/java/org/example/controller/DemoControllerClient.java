package org.example.controller;

import org.example.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/demo")
public class DemoControllerClient {

    @Autowired
    private DemoService demoService;

    @GetMapping(value = "/fetchFromProducer")
    public ResponseEntity<String> fetchFromProducer() {
        String fetchResponse = demoService.fetchResponse();
        return new ResponseEntity<>(fetchResponse, HttpStatus.OK);
    }
}
