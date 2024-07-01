package jungle.HandTris.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jungle.HandTris.global.config.log.CachedBodyRequestWrapper;
import jungle.HandTris.global.dto.WhiteListURI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class ServletWrappingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        for (String whiteListUri : WhiteListURI.WhiteListURI) {
            if (request.getRequestURI().contains(whiteListUri)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        CachedBodyRequestWrapper cachedBodyRequestWrapper = new CachedBodyRequestWrapper(request);
        filterChain.doFilter(cachedBodyRequestWrapper, response);
    }
}
