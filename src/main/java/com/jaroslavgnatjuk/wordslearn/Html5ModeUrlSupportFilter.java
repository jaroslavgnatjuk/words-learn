package com.jaroslavgnatjuk.wordslearn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@Component(value = "Html5ModeUrlSupportFilter")
public class Html5ModeUrlSupportFilter extends OncePerRequestFilter {

    @Value("${app.url.index}")
    private String indexUrl;

    @Value("${app.url.api}")
    private String apiUrl;

    @Value("${app.url.health}")
    private String healthUrl;

    @Value("${staticFileTypes}")
    private String staticFileTypes;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        if (isStatic(request) || isApi(request)) {
            filterChain.doFilter(request, response);
        } else {
            request.getRequestDispatcher(indexUrl).forward(request, response);
        }
    }

    private boolean isApi(HttpServletRequest request) {
        return request.getRequestURI().contains(apiUrl) || request.getRequestURI().contains(healthUrl);
    }

    private boolean isStatic(HttpServletRequest request) {
        return Pattern.matches(staticFileTypes, request.getRequestURI());
    }

}
