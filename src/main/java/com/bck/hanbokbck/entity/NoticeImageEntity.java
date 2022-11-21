package com.bck.hanbokbck.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "notice_image")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(nullable = false)
    private Long noticeId;
}
