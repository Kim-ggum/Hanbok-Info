package com.bck.hanbokbck.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages")
public class PageController {

    @GetMapping("/notice")
    public String noticePage() {
        return "/main_content/board1";
    }
    @GetMapping("/post")
    public String postPage() {
        return "/main_content/board_view";
    }
}
