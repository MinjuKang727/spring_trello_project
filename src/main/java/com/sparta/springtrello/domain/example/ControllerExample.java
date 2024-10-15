package com.sparta.springtrello.domain.example;

import com.sparta.springtrello.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ControllerExample {
    private final ServiceExample serviceExample;

    //성공적으로 반환했을 때 어떻게 되는지 확인
    @GetMapping("/hello")
    public ResponseEntity<ApiResponse<String>> hello() {
        return ResponseEntity.ok(ApiResponse.createSuccess("성공", HttpStatus.OK.value(), "성공 데이터"));
    }

    //예외 처리가 되면 어떻게 되는지 확인
    @GetMapping("/un-hello")
    public ResponseEntity<ApiResponse<String>> returnExceptions() {
        return ResponseEntity.ok(ApiResponse.onSuccess(serviceExample.returnException()));
    }
}
