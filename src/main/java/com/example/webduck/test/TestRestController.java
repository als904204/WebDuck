package com.example.webduck.test;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.global.exception.exceptionCode.ValidationExceptionCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class TestRestController {

    @GetMapping("/logic")
    public String errorPage1() {
        if (true) {
            throw new CustomException(LogicExceptionCode.BAD_REQUEST);
        }
        return "error";
    }

    @GetMapping("/validate")
    public String errorPage2() {
        if (true) {
            throw new CustomException(ValidationExceptionCode.INVALID_STATE);
        }
        return "error";
    }
}
