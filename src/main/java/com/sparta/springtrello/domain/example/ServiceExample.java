package com.sparta.springtrello.domain.example;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import org.springframework.stereotype.Service;

@Service
public class ServiceExample {

    public String returnException() {
        throw new ApiException(ErrorStatus._TEST_ERROR);
    }
}
