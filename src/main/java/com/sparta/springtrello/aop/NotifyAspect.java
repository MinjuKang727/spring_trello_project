package com.sparta.springtrello.aop;

import com.sparta.springtrello.annotation.NotifyEvent;
import com.sparta.springtrello.domain.notification.dto.request.SlackNotificationRequest;
import com.sparta.springtrello.domain.notification.enums.NotificationCategory;
import com.sparta.springtrello.domain.notification.enums.NotificationMessage;
import com.sparta.springtrello.domain.workspace.dto.WorkspaceResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Map;

import static com.sparta.springtrello.domain.member.enums.MemberRole.WORKSPACE;
import static com.sparta.springtrello.domain.notification.enums.NotificationMessage.WORKSPACE_CREATED;

@Aspect
public class NotifyAspect {

    @Pointcut("@annotation(notifyEvent)")
    private void notifyAnnotation(NotifyEvent notifyEvent) {}

    @After("notifyAnnotation(notifyEvent)")
    public Object adviceAnnotation(ProceedingJoinPoint joinPoint, NotifyEvent notifyEvent) throws Throwable {
        Object result = null;
        try {
            result = joinPoint.proceed();
            return result;

        } finally {
            SlackNotificationRequest request;
            NotificationMessage message = notifyEvent.message();
            NotificationCategory category = notifyEvent.category();
            Long workspaceId;
            Long contentId;
            Object args = joinPoint.getArgs();

            switch (category) {
                case WORKSPACE -> {
                    workspaceId = ((WorkspaceResponseDto) result).getWorkspace_id();
                    request = new SlackNotificationRequest(workspaceId, message,category);
                }
                case CARD -> {

                }
            }

        }
    }
}
