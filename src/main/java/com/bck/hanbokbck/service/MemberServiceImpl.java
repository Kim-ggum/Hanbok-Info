package com.bck.hanbokbck.service;

import com.bck.hanbokbck.domain.Member;
import com.bck.hanbokbck.domain.Role;
import com.bck.hanbokbck.entity.MemberEntity;
import com.bck.hanbokbck.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private MemberEntity dtoToEntity(Member member) {
        MemberEntity entity = MemberEntity.builder()
                .id(member.getId()) // 추가
                .email(member.getEmail())
                .pw(passwordEncoder.encode(member.getPw()))
                .name(member.getName())
                .role(member.getRole()) // 변경
                .build();
        return entity;
    }

    @Override
    public void create(Member member) {
        MemberEntity entity = dtoToEntity(member);
        memberRepository.save(entity);
    }

    @Override
    public void update(Member member) {

    }

    @Override
    public void delete(Member member) {

    }

    @Override
    public boolean checkEmailDuplication(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public boolean checkNameDuplication(String name) {
        return memberRepository.existsByName(name);
    }
}
