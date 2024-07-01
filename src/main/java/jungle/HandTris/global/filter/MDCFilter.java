package jungle.HandTris.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jungle.HandTris.global.dto.WhiteListURI;
import jungle.HandTris.global.util.HttpRequestUtil;
import jungle.HandTris.global.util.MDCUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class MDCFilter extends OncePerRequestFilter {

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        System.out.println("MDCFilter is invoked");
        for (String whiteListUri : WhiteListURI.WhiteListURI) {
            if (request.getRequestURI().contains(whiteListUri)) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        HttpServletRequest httpReq = WebUtils.getNativeRequest(request, HttpServletRequest.class);

        MDCUtil.setJsonValue(MDCUtil.REQUEST_URI_MDC, HttpRequestUtil.getRequestUri(Objects.requireNonNull(httpReq)));
        MDCUtil.setJsonValue(MDCUtil.USER_IP_MDC, HttpRequestUtil.getUserIP(Objects.requireNonNull(httpReq)));
        MDCUtil.setJsonValue(MDCUtil.HEADER_MAP_MDC, HttpRequestUtil.getHeaderMap(httpReq));
        MDCUtil.setJsonValue(MDCUtil.USER_REQUEST_COOKIES, HttpRequestUtil.getUserCookies(httpReq));
        MDCUtil.setJsonValue(MDCUtil.PARAMETER_MAP_MDC, HttpRequestUtil.getParamMap(httpReq));
        MDCUtil.setJsonValue(MDCUtil.BODY_MDC, HttpRequestUtil.getBody(httpReq));

        MDCUtil.set(MDCUtil.USER_REQUEST_ORIGIN, HttpRequestUtil.getOrigin(request));

        filterChain.doFilter(request, response);
    }
}
