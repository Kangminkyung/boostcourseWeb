package kr.or.connect.reservation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.dto.Reservation;
import kr.or.connect.reservation.dto.ReservationForm;
import kr.or.connect.reservation.dto.ReservationPrice;
import kr.or.connect.reservation.service.ProductService;
import kr.or.connect.reservation.service.ReservationService;

@RestController
@RequestMapping(path = "/api/reservepage")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	private ProductService productService;
	
	// 예매하기
	@PostMapping(path = "/addReservation")
	public void reserve(ReservationForm  reservationForm, HttpServletResponse response) throws IOException {
		
		int displayInfoId = reservationForm.getDisplayInfoId();
		int productId = reservationForm.getProductId();
		
		int reservationId = reservationService.addReservation(reservationForm);
		
		if(reservationId > 0) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('예약 성공!!'); location.href='http://localhost:8080/reservation/reserve?displayInfoId="+displayInfoId+"&productId="+productId+"';</script>"); 
			out.flush();
		
		}else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('예약 실패!!'); location.href='http://localhost:8080/reservation/reserve?displayInfoId="+displayInfoId+"&productId="+productId+"';</script>"); 
			out.flush();
		}
		
	}


}
