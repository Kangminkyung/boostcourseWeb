package kr.or.connect.reservation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.or.connect.reservation.service.MyReservationService;
import kr.or.connect.reservation.service.ProductService;

@Controller
public class PageController {
	
	@Autowired
	private MyReservationService myReservationService;
	private ProductService productService;
	
	@GetMapping("/detail")
	public ModelAndView productDetail(@RequestParam int displayInfoId, @RequestParam int productId) {
		// detail페이지로 이동할때 상품 id를 detail View(jsp)에 넘겨준다
		ModelAndView detail = new ModelAndView("/detail");
		
		detail.addObject("displayInfoId", displayInfoId);  // displayInfoId
		detail.addObject("productId", productId);  // productId
		return detail;
	}
	
	@GetMapping("/review")
	public ModelAndView productReview(@RequestParam int displayInfoId, @RequestParam int productId) {
		// 상세페이지 -> 리뷰 더보기
		// review페이지로 이동할때 상품 id를 review View(jsp)에 넘겨준다
		ModelAndView review = new ModelAndView("/review");
		
		review.addObject("displayInfoId", displayInfoId); // displayInfoId
		review.addObject("productId", productId); // productId

		return review;
	}
	
	@GetMapping("/reserve")
	public ModelAndView productReserve(@RequestParam int displayInfoId, @RequestParam int productId) {
		// 상세페이지 -> 예약하기
		// reserve페이지로 이동할때 상품 id를 reserve View(jsp)에 넘겨준다
		
		// 1. id값
		ModelAndView reserve = new ModelAndView("/reserve");
		reserve.addObject("displayInfoId", displayInfoId);
		reserve.addObject("productId", productId);

		// 2. 날짜
		String reservationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
		reserve.addObject("reservationDate", reservationDate);

		return reserve;
	}
	
	// 로그인 -> 나의 예매 페이지
	@GetMapping(path = "/myreservation")
	public ModelAndView myreservationPage(ModelAndView model, HttpServletRequest request, HttpSession session) throws IOException {
	
		String reservationEmail = request.getParameter("reservationEmail");
		System.out.println(("pageController /myreservation , reservationEmail = "+reservationEmail));
/*		if(session.getAttribute("email") == null && myReservationService.getTotalReservationCountByEmail(reservationEmail) > 0) {
			session.setAttribute("reservationEmail", reservationEmail);
		}else if(myReservationService.getTotalReservationCountByEmail(reservationEmail) == 0) {
			System.out.println("아이디가 존재하지 않습니다.");
		}
	
		System.out.println("세션 확인: "+ session.getAttribute(reservationEmail));
		System.out.println("pageController reservationEmail: " + reservationEmail);
		 */
		model.setViewName("myreservation");
		return model;
	}
	
}
