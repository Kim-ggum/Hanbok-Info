package com.bck.hanbokbck.entity;

import com.bck.hanbokbck.domain.Role;
import com.bck.hanbokbck.util.RoleConverter;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "notice")
@Builder
@Data
@ToString(callSuper = true) // 부모 클래스 필드 포함
@EqualsAndHashCode(callSuper = true) // 부모 클래스 필드 포함
@AllArgsConstructor
@NoArgsConstructor
public class NoticeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 255, nullable = false)
    private String writer;
}
