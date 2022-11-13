package com.bck.hanbokbck.controller;

import com.bck.hanbokbck.domain.Member;
import com.bck.hanbokbck.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/signinform")
    public String getLoginform(HttpSession session, Model model) {
        if (session.getAttribute("errorMessage") != null) {
            model.addAttribute("error",true);
            model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
            session.removeAttribute("errorMessage");
        }
        return "/member/index";
    }

    @GetMapping("/signupform")
    public String getRegiform(Model model) {
        model.addAttribute("member", Member.builder().build());
        return "/index"; // 로그인 성공하면 메인 화면, 실패하면 안내 문구
    }

    @PostMapping("/signup")
    public String postMember(@ModelAttribute("member") Member member, Model model) {
        memberService.create(member);
        return "/index"; // 안내 문구 띄우고 login 으로 가도록 다시 조정
    }
}
