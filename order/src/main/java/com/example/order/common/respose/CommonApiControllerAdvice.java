package com.example.order.common.respose;

import com.example.order.common.exception.BaseException;
import com.example.order.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.MDC;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class CommonApiControllerAdvice {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity onException(Exception e, HttpServletRequest request) {
        return ResponseEntity.status(ErrorCode.COMMON_SYSTEM_ERROR.getStatus())
                .body(CommonResponse.fail(e.getMessage(), ErrorCode.COMMON_SYSTEM_ERROR.name()));
    }

    @ResponseBody
    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity onBaseException(BaseException e) {
        log.error("[BaseException] cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());

        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(CommonResponse.fail(e.getMessage(), e.getErrorCode().name()));
    }
}