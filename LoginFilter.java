package com.liyuting.drug.commons.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.liyuting.drug.domain.User;


/**
 * 登录验证
 * @author Administrator
 *
 */
public class LoginFilter implements Filter {
	private FilterConfig filterConfig;
	@Override
	public void destroy() {}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig=filterConfig;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)resp;
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		
		if(user!=null){
			chain.doFilter(request, resp);
		}else{
			if("/jsp/login.jsp".equals(request.getServletPath())||"/settings/qx/user/login.do".equals(request.getServletPath())){
				chain.doFilter(request, resp);
			}else{
				response.sendRedirect(request.getContextPath());
			}
		}
		
	}

}
