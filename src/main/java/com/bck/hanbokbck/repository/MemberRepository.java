package com.bck.hanbokbck.repository;

import com.bck.hanbokbck.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByEmail(String email);

    // 이메일 닉네임 중복 검사, JPA 에서는 데이터가 존재하는지 확인하기 위해 exists 사용
    boolean existsByEmail(String email);
    boolean existsByName(String name);
}
