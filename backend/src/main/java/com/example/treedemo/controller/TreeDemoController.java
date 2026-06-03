package com.example.treedemo.controller;

import com.example.treedemo.dto.TreeDemoRequest;
import com.example.treedemo.dto.TreeDemoResponse;
import com.example.treedemo.service.TreeDemoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trees")
@CrossOrigin(origins = "http://localhost:5173")
public class TreeDemoController {

    private final TreeDemoService treeDemoService;

    public TreeDemoController(TreeDemoService treeDemoService) {
        this.treeDemoService = treeDemoService;
    }

    @PostMapping("/demo")
    public TreeDemoResponse demo(@RequestBody TreeDemoRequest request) {
        return treeDemoService.buildDemo(request.values);
    }
}
