package com.lemon.violet.global;

import com.lemon.violet.pojo.vo.ResponseResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult methodArgs(MethodArgumentNotValidException ex){
        StringBuilder sb = new StringBuilder();

        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        allErrors.stream().forEach(error->{
            sb.append(error.getDefaultMessage())
                    .append("! ");
        });

        return ResponseResult.checkFail(sb.toString());
    }
}
