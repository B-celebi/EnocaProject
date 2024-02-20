package com.enoca.cartApi.common.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }
}
