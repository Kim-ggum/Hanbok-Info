package com.bck.hanbokbck.service;

import com.bck.hanbokbck.domain.Member;
import com.bck.hanbokbck.entity.MemberEntity;
import com.bck.hanbokbck.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    private MemberEntity dtoToEntity(Member member) {
        MemberEntity entity = MemberEntity.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .pw(member.getEmail())
                .role(member.getRole())
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
}
