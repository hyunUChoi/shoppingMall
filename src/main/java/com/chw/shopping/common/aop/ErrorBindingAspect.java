package com.chw.shopping.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ErrorBindingAspect {

    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping)")
    public Object handleBindingErrors(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        for(Object arg : args) {
            if(arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if(bindingResult.hasErrors()) {
                    Map<String, String> errors = new HashMap<>();
                    bindingResult.getFieldErrors().forEach((error) -> {
                        errors.put(error.getField(), error.getDefaultMessage());
                    });
                   return ResponseEntity.unprocessableEntity().body(errors);
                }
            }
        }

        return joinPoint.proceed();
    }

}
