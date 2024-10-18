package com.sparta.springtrello.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@memberAuthorizeService.hasMemberRoleWORKSPACE(authentication.principal, #workspaceId)")
public @interface RoleWorkspace {
}
