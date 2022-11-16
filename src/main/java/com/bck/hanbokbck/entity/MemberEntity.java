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
    private long id;

    @Column(length = 255, nullable = false)
    private String email;

    @Column(length = 255, nullable = false)
    private String pw;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    @Convert(converter = RoleConverter.class)
    private Role role;
}
