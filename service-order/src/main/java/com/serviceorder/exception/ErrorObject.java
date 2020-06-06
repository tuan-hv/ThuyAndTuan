package com.serviceorder.exception;

import lombok.Data;

import java.util.Map;

@Data
public class ErrorObject {
    private Map<String, String> messages;
}
