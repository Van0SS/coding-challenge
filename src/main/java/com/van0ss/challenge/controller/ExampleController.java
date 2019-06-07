package com.van0ss.challenge.controller;

import com.van0ss.challenge.service.WordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/words")
@Api(tags = "example")
public class ExampleController {
    private final WordService service;

    @Autowired
    public ExampleController(WordService service) {
        this.service = service;
    }

    @GetMapping
    @ApiOperation(value = "Get info about words frequency")
    public String get(long limit, long offset) {
        return service.getAsCsv(limit, offset);
    }
}
