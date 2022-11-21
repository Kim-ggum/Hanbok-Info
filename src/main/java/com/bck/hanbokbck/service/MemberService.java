package com.bck.hanbokbck.service;

import com.bck.hanbokbck.domain.Member;


public interface MemberService {
    void create(Member member); // 회원 가입
    void update(Member member); // 회원 정보 수정
    void delete(Member member); // 회원 탈퇴
    void emailCertificationUpdate(String email, String key);

    boolean checkEmailDuplication(String email);

    boolean checkNameDuplication(String name);

    boolean checkPw(Member member);

    Member getByEmail(String email);

    String certificationKey();
}
