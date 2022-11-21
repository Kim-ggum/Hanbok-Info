package com.bck.hanbokbck.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hanbok")
@RequiredArgsConstructor
public class HanbokController {
    @GetMapping("/main")
    public String main() {
        return "/index";
    }
}
