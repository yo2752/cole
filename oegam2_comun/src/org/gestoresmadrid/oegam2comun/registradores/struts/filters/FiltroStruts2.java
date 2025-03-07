package org.gestoresmadrid.oegam2comun.registradores.struts.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class FiltroStruts2 extends StrutsPrepareAndExecuteFilter {


	@Override
	public void doFilter(ServletRequest requestParam,
			ServletResponse responseParam, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) requestParam;
		request.setCharacterEncoding("UTF-8");
		if ((request.getServletPath() != null)
				&& (request.getServletPath().equals("/viafirmaClientResponseServlet"))) {
			chain.doFilter(requestParam, responseParam);
		} else {
			super.doFilter(requestParam, responseParam, chain);
		}

	}
}
