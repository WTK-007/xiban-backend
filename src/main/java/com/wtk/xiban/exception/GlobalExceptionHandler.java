package com.wtk.xiban.exception;

import com.wtk.xiban.common.BaseResponse;
import com.wtk.xiban.common.ErrorCode;
import com.wtk.xiban.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *全局异常处理器
 *
 * @author wtk
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse bussinessExceptionHandler(BusinessException e){
        log.error("businessException" + e.getMessage(),e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler
    public BaseResponse runtimeExceptionHandler(RuntimeException e){
        log.error("runtimeException",e);
        return ResultUtils.error(ErrorCode.SYSTRM_ERROR, e.getMessage(),"");
    }

}
