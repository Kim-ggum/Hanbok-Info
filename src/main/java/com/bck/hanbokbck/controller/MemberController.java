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
        if (session.getAttribute("emailFail") != null) {
            model.addAttribute("signupFail", session.getAttribute("emailFail"));
            session.removeAttribute("emailFail");
        } else if (session.getAttribute("nameFail") != null) {
            model.addAttribute("signupFail", session.getAttribute("nameFail"));
            session.removeAttribute("nameFail");
        } else if (session.getAttribute("signupSuccess") != null) {
            model.addAttribute("successMessage", session.getAttribute("signupSuccess"));
            session.removeAttribute("signupSuccess");
        }

        return "/member/signin-form";
    }

    /*@GetMapping("/signupform")
    public String getRegiform(Model model) {
        model.addAttribute("member", Member.builder().build());
        return "/member/signin-form";
    }*/
    // html 파일이 하나라서 두개로 나눌 필요 없음

    @PostMapping("/signup")
    public String postMember(@ModelAttribute("member") Member member, Model model, HttpSession session) {
        if(memberService.checkEmailDuplication(member.getEmail())) {
            session.setAttribute("emailFail", "이미 가입된 이메일입니다.");
        } else if(memberService.checkNameDuplication(member.getName())) {
            session.setAttribute("nameFail", "중복 닉네임입니다.");
        } else {
            memberService.create(member);
            session.setAttribute("signupSuccess", "회원가입이 완료되었습니다.");
        } // redirect를 하기 위해 여기에서 session에 에러 메시지를 담아줌
        return "redirect:/member/signinform";
    }

    @GetMapping("/updateform")
    public String getUpdateform(HttpSession session, Model model) {
        return "/member/member-edit";
    }
}
