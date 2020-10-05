package kr.or.connect.reservation.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import kr.or.connect.reservation.resolver.ArgumentResolver;
import kr.or.connect.reservation.interceptor.LoginInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;



//WebMvcContextConfiguration는 DispatcherServlet가 읽어들일 설정
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"kr.or.connect.reservation.controller"})
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter{
	
	private static final int SEC_IN_A_YEAR = 60 * 60 * 24 *365;
	// addResourceHandlers는 css, /img 와 같은 URL 요청시 연결해줄 위치 설정을 해준다
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/assets/css/").setCachePeriod(SEC_IN_A_YEAR);
		registry.addResourceHandler("/font/**").addResourceLocations("/assets/font/").setCachePeriod(SEC_IN_A_YEAR);
		registry.addResourceHandler("/img/**").addResourceLocations("/assets/images/img/").setCachePeriod(SEC_IN_A_YEAR);
		registry.addResourceHandler("/img_map/**").addResourceLocations("/assets/images/img_map/").setCachePeriod(SEC_IN_A_YEAR);
		registry.addResourceHandler("/js/**").addResourceLocations("/assets/js/").setCachePeriod(SEC_IN_A_YEAR);
	}

	// configureDefaultServletHandling는 URL 에 요청 정보가 없을시 WAS 의 default servlet이 static한 자원을 읽어서 보여준다
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	// addViewControllers는 특정한 URL 에 대한 처리를 controller 클래스를 작성하지 않고 매핑할 수 있도록 해주는 부분이다
	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		System.out.println("addViewController가 호출됩니다");
		registry.addViewController("/").setViewName("main");
	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	//	registry.addInterceptor(new LoginInterceptor());
	}

	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// TODO Auto-generated method stub
		argumentResolvers.add(new ArgumentResolver());
	}

	
}
