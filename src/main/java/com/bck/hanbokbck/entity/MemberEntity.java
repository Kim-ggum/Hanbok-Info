package com.bck.hanbokbck.entity;

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
    private long member_id;

    @Column(length = 30, nullable = false)
    private String member_email;

    @Column(length = 100, nullable = false)
    private String member_pw;

    @Column(length = 30, nullable = false)
    private String member_name;

}
