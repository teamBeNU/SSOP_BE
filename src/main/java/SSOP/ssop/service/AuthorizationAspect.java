package SSOP.ssop.service;

import SSOP.ssop.config.UserDetail;
import SSOP.ssop.controller.Login;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
public class AuthorizationAspect {

    @Before("@annotation(Login)")
    public void checkUserPermission(JoinPoint joinPoint, Login Login) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetail)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰을 입력해주세요.");
        }

        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        Long authenticatedUserId = userDetail.getUser().getUserId();

        // 사용자 ID가 메서드 인자로 전달
        Object[] args = joinPoint.getArgs();
        Long requestedUserId = (Long) args[0]; // 메서드 시그니처에 따라 인덱스 조정

        // 인증된 사용자와 요청된 사용자가 다르면 예외처리
        if (!authenticatedUserId.equals(requestedUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다.");
        }
    }
}