package com.bck.hanbokbck.service;

import com.bck.hanbokbck.domain.Member;
import com.bck.hanbokbck.entity.MemberEntity;
import com.bck.hanbokbck.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // 회원 찾는 로직
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity member = memberRepository.findByEmail(email);
        if(member == null) {
            throw new UsernameNotFoundException("");
        }
        return Member.builder()
                .id(member.getId())
                .email(member.getEmail())
                .pw(member.getPw())
                .name(member.getName())
                .role(member.getRole())
                .emailCertifiedKey(member.getEmailCertifiedKey())
                .accountEnabled(member.getAccountEnabled())
                .build();
    }
}
