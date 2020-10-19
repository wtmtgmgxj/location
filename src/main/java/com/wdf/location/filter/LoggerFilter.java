package com.wdf.location.filter;

import com.wdf.location.constants.ApplicationConstants;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoggerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		populateMDC(request);

		filterChain.doFilter(request, response);

		MDC.clear();
	}

	protected void populateMDC(HttpServletRequest request) {

		String mdcValue = request.getParameter(request.getHeader("X-Request-ID"));

		if (StringUtils.isBlank(mdcValue)) {
			String permittedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
			mdcValue = RandomStringUtils.random(30, permittedCharacters);
		}

		MDC.put(ApplicationConstants.MDC_KEY, mdcValue);

	}

}