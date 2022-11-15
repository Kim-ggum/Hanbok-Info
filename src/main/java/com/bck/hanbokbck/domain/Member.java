package com.bck.hanbokbck.domain;

import com.bck.hanbokbck.util.RoleConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Convert;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@Builder
public class Member implements UserDetails {
    private Long memberId;
    private String memberEmail;
    private String memberPw;
    private String memberName;

    @Convert(converter = RoleConverter.class)
    private Role memberRole;

    // 해당 유저의 권한목록 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return memberRole.toString();
            }
        });
        return collect;
    }

    // 비밀번호 리턴
    @Override
    public String getPassword() {
        return getMemberPw();
    }

    // 로그인할 때 ID값 리턴 : email로 로그인하기 때문에 email 리턴
    @Override
    public String getUsername() {
        return getMemberEmail();
    }

    // 계정 만료 여부
    // true : 만료안됨
    // false : 만료됨
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부
    // true : 잠기지 않음
    // false : 잠김
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 비밀번호 만료 여부
    // true : 만료되지 않음
    // false : 만료됨
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부
    // true : 활성화 됨
    // false : 비활성화 됨
    @Override
    public boolean isEnabled() {
        return true;
    }
}
