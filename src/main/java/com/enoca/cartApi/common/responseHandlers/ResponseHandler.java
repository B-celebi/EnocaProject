package com.enoca.cartApi.common.responseHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;


public class ResponseHandler {
    public static ResponseEntity<Object> getSuccessResponse(HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status.value());
        map.put("data", responseObj);
        map.put("success", true);

        return ResponseEntity.status(status).body(map);
    }
    public static ResponseEntity<Object> getFailResponse(HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status.value());
        map.put("message", responseObj);
        map.put("success", false);
        return ResponseEntity.status(status).body(map);
    }
}
