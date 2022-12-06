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

    @GetMapping("/hanbok-info")
    public String hanbokInfo() {
        return "/contents/hanbok-info";
    }

    @GetMapping("/hanbok-service")
    public String hanbokService() {
        return "/contents/hanbok-service";
    }

    @GetMapping("/post")
    public String post() {
        return "/post/post";
    }

    @GetMapping("/team")
    public String team() {
        return "/team/team_dev";
    }

}
