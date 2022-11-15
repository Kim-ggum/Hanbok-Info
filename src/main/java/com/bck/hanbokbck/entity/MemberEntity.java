package com.bck.hanbokbck.entity;

import com.bck.hanbokbck.domain.Role;
import com.bck.hanbokbck.util.RoleConverter;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column(length = 30, nullable = false)
    private String memberEmail;

    @Column(length = 100, nullable = false)
    private String memberPw;

    @Column(length = 30, nullable = false)
    private String memberName;

    @Column(length = 30, nullable = false)
    @Convert(converter = RoleConverter.class)
    private Role memberRole;
}
