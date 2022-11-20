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
        if(member.getId() != null) {
            MemberEntity entity = MemberEntity.builder()
                    .id(member.getId()) // 회원가입 시 id를 따로 입력받지 않으므로 오류가 생김, 회원 정보 수정에서는 id를 입력받아야 할 것으로 예상
                    .email(member.getEmail())
                    .pw(passwordEncoder.encode(member.getPw()))
                    .name(member.getName())
                    .role(member.getRole()) // 위와 마찬가지로 회원가입 시 관리자인지 일반 사용자인지 체크하지 않음, 관리자의 경우 다른 방법(e.g. 관리자 페이지)으로 권한을 주어야 할 것으로 예상
                    .build();

            return entity;
        } else {
            MemberEntity entity = MemberEntity.builder()
//                .id(member.getId()) // 회원가입 시 id를 따로 입력받지 않으므로 오류가 생김, 회원 정보 수정에서는 id를 입력받아야 할 것으로 예상
                    .email(member.getEmail())
                    .pw(passwordEncoder.encode(member.getPw()))
                    .name(member.getName())
                    .role(Role.ROLE_USER) // 위와 마찬가지로 회원가입 시 관리자인지 일반 사용자인지 체크하지 않음, 관리자의 경우 다른 방법(e.g. 관리자 페이지)으로 권한을 주어야 할 것으로 예상
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
                .build();
        return member;
    }

    @Override
    public void create(Member member) {
        MemberEntity entity = dtoToEntity(member);
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
