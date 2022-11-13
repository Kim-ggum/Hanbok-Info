package com.bck.hanbokbck.controller;

import com.bck.hanbokbck.domain.Member;
import com.bck.hanbokbck.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/signinform")
    public String getLoginform(HttpSession session, Model model) {
        model.addAttribute("member", Member.builder().build());
        if (session.getAttribute("errorMessage") != null) {
            model.addAttribute("error",true);
            model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
            session.removeAttribute("errorMessage");
        }

        return "/member/signin-form"; // 현재는 static에 있는 index.html임 수정해야함
    }

    @GetMapping("/signupform")
    public String getRegiform(Model model) {
        model.addAttribute("member", Member.builder().build());
        return "/member/signin-form"; // 로그인 성공하면 메인 화면, 실패하면 안내 문구
    }

    @PostMapping("/signup")
    public String postMember(@ModelAttribute("member") Member member) {
        memberService.create(member);
        return "/member/signin-form"; // 안내 문구 띄우고 login 으로 가도록 다시 조정
    }
}
