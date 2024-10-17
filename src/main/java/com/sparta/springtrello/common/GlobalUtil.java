package com.sparta.springtrello.common;

import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.auth.dto.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalUtil {

    public static void hasAuthUser(AuthUser authUser) {
        if(authUser == null) {
            throw new ApiException(ErrorStatus.NOT_FOUND_AUTHENTICATION);
        }
    }
}
