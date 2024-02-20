package com.enoca.cartApi.common.exceptionHandlers;

import com.enoca.cartApi.common.exceptions.BusinessException;
import com.enoca.cartApi.common.responseHandlers.ResponseHandler;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBusinessException(BusinessException businessException){
        return ResponseHandler.getFailResponse(HttpStatus.BAD_REQUEST,businessException.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        //Validation error mesajlarını mapliyoruz.
        Map<String,String> validationErrors = new HashMap<>();
        for(FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()){
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseHandler.getFailResponse(HttpStatus.BAD_REQUEST,validationErrors);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleValidException(ConstraintViolationException constraintViolationException){
        //Validation error mesajlarını mapliyoruz.
        Map<String,String> validationErrors = new HashMap<>();
        constraintViolationException.getConstraintViolations().forEach(violation ->
                validationErrors.put("Error", violation.getPropertyPath().toString() +" "+ violation.getMessage())
        );
        return ResponseHandler.getFailResponse(HttpStatus.BAD_REQUEST,validationErrors);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(code=HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object>handleValidException(HttpMessageNotReadableException httpMessageNotReadableException){
        Map<String,String> validationErrors = new HashMap<>();
        validationErrors.put("Error","JSON gövdesi okunamadı.");
        return ResponseHandler.getFailResponse(HttpStatus.BAD_REQUEST,validationErrors);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(code=HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object>handleValidException(DataIntegrityViolationException dataIntegrityViolationException){
        Map<String,String> validationErrors = new HashMap<>();
        validationErrors.put("Error","Ürün bir müşterinin sepetindeyken silinemez.");
        return ResponseHandler.getFailResponse(HttpStatus.BAD_REQUEST,validationErrors);
    }
}
