package com.parent.aoptest.exception;

import com.parent.aoptest.pojo.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.logging.ErrorManager;

@ControllerAdvice
public class GlobException {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ErrorInfo<Exception> jsonApiErrorHandler(HttpServletRequest request,Exception e) {

        ErrorInfo<Exception> errorInfo = new ErrorInfo<>();
        try {
            e.printStackTrace();

            Throwable cause = e.getCause();
            while (cause != null) {
                if (cause.getCause() == null) {
                    //跳出循环
                    break;
                }
                cause = cause.getCause();
            }

            if (cause == null) {
                errorInfo.setMessage(e.getMessage());
                errorInfo.setError(e.toString());
            } else {
                errorInfo.setMessage(cause.getMessage());
                errorInfo.setError(cause.toString());
            }

            errorInfo.setData(e);
            errorInfo.setTimestamp(new Date());
            errorInfo.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorInfo.setUrl(request.getRequestURL().toString());
            errorInfo.setPath(request.getServletPath());

        } catch (Exception ex) {
            e.printStackTrace();

            errorInfo.setMessage(ex.getMessage());
            errorInfo.setError(ex.toString());

        }
        return errorInfo;
    }
}
