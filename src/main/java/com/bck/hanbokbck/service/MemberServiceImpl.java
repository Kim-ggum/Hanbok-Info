package com.bck.hanbokbck.service;

import com.bck.hanbokbck.domain.Member;
import com.bck.hanbokbck.domain.Role;
import com.bck.hanbokbck.entity.MemberEntity;
import com.bck.hanbokbck.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;

    private MemberEntity dtoToEntity(Member member) {
        if(member.getId() != null) {
            MemberEntity entity = MemberEntity.builder()
                    .id(member.getId()) // 회원가입 시 id를 따로 입력받지 않으므로 오류가 생김, 회원 정보 수정에서는 id를 입력받아야 할 것으로 예상
                    .email(member.getEmail())
                    .pw(passwordEncoder.encode(member.getPw()))
                    .name(member.getName())
                    .role(member.getRole()) // 위와 마찬가지로 회원가입 시 관리자인지 일반 사용자인지 체크하지 않음, 관리자의 경우 다른 방법(e.g. 관리자 페이지)으로 권한을 주어야 할 것으로 예상
                    .emailCertifiedKey(member.getEmailCertifiedKey())
                    .accountEnabled(member.getAccountEnabled())
                    .build();

            return entity;
        } else {
            MemberEntity entity = MemberEntity.builder()
//                .id(member.getId()) // 회원가입 시 id를 따로 입력받지 않으므로 오류가 생김, 회원 정보 수정에서는 id를 입력받아야 할 것으로 예상
                    .email(member.getEmail())
                    .pw(passwordEncoder.encode(member.getPw()))
                    .name(member.getName())
                    .role(Role.ROLE_USER) // 위와 마찬가지로 회원가입 시 관리자인지 일반 사용자인지 체크하지 않음, 관리자의 경우 다른 방법(e.g. 관리자 페이지)으로 권한을 주어야 할 것으로 예상
                    .emailCertifiedKey(certificationKey()) // 키 생성해서 집어넣음
                    .accountEnabled(false)
                    .build();

            return entity;
        }
    }

    private Member entityToDto(MemberEntity entity) {
        Member member = Member.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .pw(entity.getPw())
                .name(entity.getName())
                .role(entity.getRole())
                .emailCertifiedKey(entity.getEmailCertifiedKey())
                .accountEnabled(entity.getAccountEnabled())
                .build();
        return member;
    }

    @Override
    public void create(Member member) {

        MemberEntity entity = dtoToEntity(member);
        emailService.sendEmail(member.getEmail(), "[선남선녀] 이메일 인증", message(entity.getEmail(), entity.getEmailCertifiedKey()));
        memberRepository.save(entity);
    }

    @Override
    public Member getByEmail(String email) {
        Member member = entityToDto(memberRepository.findByEmail(email));
        return member;
    }

    @Override
    public void update(Member member) {
        MemberEntity entity = dtoToEntity(member);
        memberRepository.save(entity);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPw()));
        SecurityContextHolder.getContext().setAuthentication(authentication); // 이 두 줄로 로그인 되어있는 정보 변경
    }

    @Override
    public void delete(Member member) {
        MemberEntity entity = dtoToEntity(member);
        memberRepository.delete(entity);
        SecurityContextHolder.clearContext(); // 회원 탈퇴 후 로그아웃
    }

    @Override
    public void emailCertificationUpdate(String email, String key) {
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        if(memberEntity.getEmailCertifiedKey().equals(key)) {
            memberEntity.setAccountEnabled(true);
            memberRepository.save(memberEntity);
        }
    }

    @Override
    public boolean checkEmailDuplication(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public boolean checkNameDuplication(String name) {
        return memberRepository.existsByName(name);
    }

    @Override
    public boolean checkPw(Member member) {
        if(passwordEncoder.matches(member.getPw(),memberRepository.findByEmail(member.getEmail()).getPw())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String certificationKey(){
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for(int i = 0; i < 10; i++) {
            int temp = random.nextInt(2);

            if(temp == 0) {
                key.append(random.nextInt(10));
            } else {
                key.append((char) (random.nextInt(26) + 65));
            }
        }

        return key.toString();
    }

    public String message(String email, String key) {
        StringBuffer msg = new StringBuffer();

        msg.append("<!DOCTYPE html>");
        msg.append("<html lang=\"en\">");
        msg.append("<head>");
        msg.append("</head>");
        msg.append("<body>");
        msg.append("<div>");
        msg.append("<a href=\"http://localhost:8088/member/emailcertification?email=" + email + "&key=" + key + "\" target=\"_blank\">인증 링크</a>");
        msg.append("</div>");
        msg.append("</body>");
        msg.append("</html>");

        return msg.toString();
    }
}
