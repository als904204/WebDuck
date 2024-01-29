package com.example.webduck.global.advice;

import com.example.webduck.global.common.ErrorResponse;
import com.example.webduck.global.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionRestAdvice {

    /**
     * @ExceptionHandler
     * 특정 예외가 발생했을 때 처리하는 어노테이션
     */


    // 요청 바디 필드 유효성 검증 예외 처리
    /**
     * MethodArgumentNotValidException
     * @Valid 유효하지 않을 때
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        return ErrorResponse.of(e.getBindingResult());
    }

    // 유효성 검증 예외 처리
    /**
     * ConstraintViolationException
     * 제약 조건(예: @NotNull, @Size, @Min, @Max 등)이 위반
     * 데이터베이스의 제약 조건(예: 외래 키 제약, 고유 제약, 테이블 정의의 제약 조건 등)을 위반
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(
        ConstraintViolationException e) {
        return ErrorResponse.of(e.getConstraintViolations());
    }

    // url에 대해 지원하지 않는 http method 일 때 예외 처리
    /**
     * HttpRequestMethodNotSupportedException
     * 올바르지 않은 HTTP 메소드로 요청을 보낼 때
     * (GET 요청을 보내야 되는데 POST 요청을 보냄)
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleHttpRequestMethodNotSupportedException(
        HttpRequestMethodNotSupportedException e) {
        return ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED);
    }

    // 컨트롤러의 메서드로 전달된 인자의 타입이 예상되는 타입과 일치하지 않을 때 발생(converter가 변환하지 못했을 경우)
    /**
     * MethodArgumentTypeMismatchException
     * enum type에 존재하지 않는 값
     * Long 타입으로 기대하는데, 실제로 문자열이나 다른 형식의 값이 전달될 경우
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException e) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getPropertyName() + " Type Mismatched");
    }

    // HTTP Body를 제대로 파싱하지 못했을 때 예외 처리
    /**
     * JSON 요청 형식이 잘못 되었을 때
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(
        HttpMessageNotReadableException e) {

        return ErrorResponse.of(HttpStatus.BAD_REQUEST,
            "Required request body is missing");
    }

    // 요청 시 쿼리 파라미터가 결여됐을 때 예외 처리

    /**
     * 쿼리 파라미터나 폼 데이터에 필요한 항목이 없는 경우
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingServletRequestParameterException(
        MissingServletRequestParameterException e) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST,
            e.getMessage());
    }

    /**
     * 업로드 한 파일크기가 1MB 를 초과했을 때
     * 413 리턴
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public ErrorResponse handleMaxSizeException(MaxUploadSizeExceededException e) {
        return ErrorResponse.of(HttpStatus.PAYLOAD_TOO_LARGE, e.getMessage());
    }

//    /**
//     * 빈 페이지 요청
//     * 404 리턴
//     */
//    @ExceptionHandler(NoResourceFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ErrorResponse handle404(NoResourceFoundException e) {
//        return ErrorResponse.of(HttpStatus.NOT_FOUND, e.getMessage());
//    }





    // 위에서 지정한 예외 외의 서버 로직 예외에 대한 예외 처리.
    // 예상하지 못한 서버 예외
    // 운영에 치명적일 수 있음.
    // 반드시 로그를 기록하고, 관리자에게 알림을 줄 것.
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(HttpServletRequest req, Exception e) {
        log.error("# handle Exception", e);
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);
    }




    // 서버 로직 내 예외 처리
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleLogicException(CustomException e) {
        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode()
            .getStatus()));
    }
}
