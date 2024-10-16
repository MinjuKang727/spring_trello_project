package com.sparta.springtrello.domain.member.argumentresolver;

import com.sparta.springtrello.annotation.RequestedMember;
import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.config.JwtAuthenticationToken;
import com.sparta.springtrello.domain.auth.dto.AuthUser;
import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.member.enums.InvitationStatus;
import com.sparta.springtrello.domain.member.repository.MemberRepository;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.repository.UserRepository;
import com.sparta.springtrello.domain.workspace.entity.Workspace;
import com.sparta.springtrello.domain.workspace.repository.WorkspaceRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final MemberRepository memberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(RequestedMember.class) != null && parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter  parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // User 객체 가져오기
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                ()->new ApiException(ErrorStatus.NOT_FOUND_USER)
        );

        //WorkSpace 가져오기
        //PathVariable에서 workspaceId 가져오기
        Map<String, String> pathVariables = (Map<String, String>) webRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, NativeWebRequest.SCOPE_REQUEST);
        String workspaceIdParam = pathVariables != null ? pathVariables.get("workspaceId") : null;

        if (workspaceIdParam == null) {
            throw new ApiException(ErrorStatus.BAD_REQUEST_INVALID_WORKSPACE_ID);
        }
        Long workspaceId = Long.parseLong(workspaceIdParam);

        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(
                () -> new ApiException(ErrorStatus.NOT_FOUND_WORKSPACE)
        );

        // Member 찾기
        return memberRepository.findAcceptedMember(user, workspace, InvitationStatus.ACCEPT).orElseThrow(
                () -> new ApiException(ErrorStatus.NOT_FOUND_MEMBER)
        );
    }
}
