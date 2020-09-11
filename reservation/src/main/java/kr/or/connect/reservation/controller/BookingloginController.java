package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.connect.reservation.service.ReservationService;

@Controller
public class BookingloginController {

	@Autowired
	ReservationService reservationService;
	
	@GetMapping(path = "/bookingloginPage")
	public ModelAndView bookingloginPage() {
		ModelAndView bookingloginPage = new ModelAndView("/bookinglogin");
		return bookingloginPage;
	}
	
}


