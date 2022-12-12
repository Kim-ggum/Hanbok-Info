package com.bck.hanbokbck.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class Notice {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
