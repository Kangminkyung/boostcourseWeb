package kr.or.connect.reservation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.or.connect.reservation.dto.ReservationForm;
import kr.or.connect.reservation.service.ReservationService;

@RestController
@RequestMapping(path = "/api/reserve")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	
	// 예매하기
	@PostMapping(path = "/addReservation")
	public ModelAndView reserve(ReservationForm  reservationForm, HttpServletResponse response) throws IOException {
		
		int displayInfoId = reservationForm.getDisplayInfoId();
		int productId = reservationForm.getProductId();
		int reservationId = reservationService.addReservation(reservationForm);
		
		ModelAndView alert = new ModelAndView("/alert");
		String url = "http://localhost:8080/reservation/reserve?displayInfoId=";
		url += displayInfoId;
		url += "&productId=";
		url += productId;
		
		System.out.println(url);
		
		alert.addObject("url", url);

		if(reservationId > 0) {
			alert.addObject("message", "예약 성공!!");
		
		}else {
			alert.addObject("message", "예약 실패!!");
		}
		
		return alert;
		
	}

}
