package kr.or.connect.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.or.connect.reservation.service.ReservationService;

@Controller
public class BookingloginController {

	@Autowired
	ReservationService reservationService;
	
	@GetMapping(path = "/bookingloginPage")
	public ModelAndView bookingloginPage() {
		System.out.println("bookintlogin controller");
		ModelAndView bookingloginPage = new ModelAndView("/bookinglogin");
		return bookingloginPage;
	}
	
}


