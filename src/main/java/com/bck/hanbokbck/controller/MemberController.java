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

        return "/member/signin-form";
    }

    /*@GetMapping("/signupform")
    public String getRegiform(Model model) {
        model.addAttribute("member", Member.builder().build());
        return "/member/signin-form";
    }*/

    @PostMapping("/signup")
    public String postMember(@ModelAttribute("member") Member member, Model model) {
        if(memberService.checkEmailDuplication(member.getMemberEmail())) {
            model.addAttribute("message", "이미 가입된 이메일입니다.");
        } else if(memberService.checkNameDuplication(member.getMemberName())) {
            model.addAttribute("message", "중복 닉네임입니다.");
        } else {
            memberService.create(member);
            model.addAttribute("message", "회원가입이 완료되었습니다.");
        }
        return "/member/signin-form";
    }
}
