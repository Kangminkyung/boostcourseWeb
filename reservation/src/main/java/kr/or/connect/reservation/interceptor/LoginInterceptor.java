package kr.or.connect.reservation.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter{


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	/*	
		if (modelAndView != null) {
			System.out.println("[postHandle] ====> 호출된 뷰:" + modelAndView.getViewName());
			logger.debug("[postHandle] ==> 호출된 뷰:{}", modelAndView.getViewName());
		}
		
		printLogInfo(request, response, handler, modelAndView);
		*/
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	//	printLogInfo(request, response, handler);
		return true;
	}
	
}
