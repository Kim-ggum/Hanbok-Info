package com.bck.hanbokbck.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ErrorResponse {
    private int status; // 상태코드
    private String message; // 에러 메세지
}
