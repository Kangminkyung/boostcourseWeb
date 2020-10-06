package kr.or.connect.reservation.interceptor;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String getDataFormatTime(long currentTime) {
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
		return timeFormat.format( currentTime);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if(modelAndView != null) {
			logger.debug("[postHandle] ==> 호출된 뷰:{}", modelAndView.getViewName());
		}
		
		logger.debug("[postHandle] 수행:{}, 요청URL:{}, 시간:{}, ip주소:{}", 
				handler.toString(),request.getRequestURL(), getDataFormatTime(System.currentTimeMillis()), request.getRemoteAddr());
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		logger.debug("[preHandle] 수행:{}, 요청URL:{}, 시간:{}, ip주소:{}", 
				handler.toString(),request.getRequestURL(), getDataFormatTime(System.currentTimeMillis()), request.getRemoteAddr());
		return true;
	}

		
}
