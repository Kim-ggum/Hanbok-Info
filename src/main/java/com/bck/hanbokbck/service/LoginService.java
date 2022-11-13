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
        MemberEntity member = memberRepository.findByMemberEmail(email);
        if(member == null) {
            throw new UsernameNotFoundException("");
        }
        return Member.builder()
                .memberId(member.getMemberId())
                .memberEmail(member.getMemberEmail())
                .memberPw(member.getMemberPw())
                .memberName(member.getMemberName())
                .memberRole(member.getMemberRole())
                .build();
    }
}
