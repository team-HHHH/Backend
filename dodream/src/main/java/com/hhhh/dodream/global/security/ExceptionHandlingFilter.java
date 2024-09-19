package com.hhhh.dodream.global.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhhh.dodream.global.common.dto.ResponseDto;
import com.hhhh.dodream.global.exception.kind.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ExceptionHandlingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            setExceptionResponse(response, e);
        }
    }

    private void setExceptionResponse(HttpServletResponse response, CustomException e)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = mapper.writeValueAsString(ResponseDto.of(e.getCode(), e.getMessage()));
        response.getWriter().write(jsonResponse);
    }
}
