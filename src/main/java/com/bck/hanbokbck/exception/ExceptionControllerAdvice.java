package com.bck.hanbokbck.exception;

import com.bck.hanbokbck.domain.ErrorResponse;
import com.bck.hanbokbck.exception.customException.NoticeCrudFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(NoticeCrudFailureException.class)
    protected String handleNoticeCrudFailureException(Exception e, Model model) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        model.addAttribute("error", errorResponse);
        return "/error/error_page";
    }
}
