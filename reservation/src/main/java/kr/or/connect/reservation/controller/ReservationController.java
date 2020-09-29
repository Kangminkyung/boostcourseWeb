package kr.or.connect.reservation.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.or.connect.reservation.dto.MyReservation;
import kr.or.connect.reservation.dto.ReservationForm;
import kr.or.connect.reservation.dto.TicketInfo;
import kr.or.connect.reservation.service.MyReservationService;
import kr.or.connect.reservation.service.ReservationService;

@RestController
@RequestMapping(path = "/api/reserve")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private MyReservationService myReservationService;
	
	private enum Status {
		CANCEL, CONFIRMED, USED
	}
	
	/* reservation*/
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
	
	/* myreservation*/
	// 예약 상태 분류
	private Status ClassifyList(MyReservation myReservation) throws ParseException {
		
		if(myReservation.getCancelFlag() == 1) {
			return Status.CANCEL;
		}else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date reservationDate = dateFormat.parse(myReservation.getReservationDate());
			
			Date currentDate = new Date();
			Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();
			startDate.setTime(currentDate);
			endDate.setTime(reservationDate);
			
			long diffMillis = startDate.getTimeInMillis() - endDate.getTimeInMillis();
			int diff = (int) (diffMillis / (24 * 60 * 60 * 1000));

			if (diff > 0) {
				return Status.USED;
			} else {
				return Status.CONFIRMED;
			}
		}
	}
	
	
	@GetMapping(path = "/reservations")
	public Map<String, Object> getMyListByEmail(@RequestParam(value = "reservationEmail", required = false) String reservationEmail, HttpServletResponse response) throws ParseException, IOException{
	
		Map<String, Object> params = new HashMap<>();
			
		List<MyReservation> totalReservationList = myReservationService.getTotalReservationByEmail(reservationEmail);
		List<MyReservation> canceledReservation = new ArrayList<>();
		List<MyReservation> confirmedReservation = new ArrayList<>();
		List<MyReservation> usedReservation = new ArrayList<>();
			
		for(MyReservation myList : totalReservationList) {
			List<TicketInfo> tickets = myReservationService.getTicketInfo(myList.getReservationId());
			
			myList.setTicketInfo(tickets);
			Status status = this.ClassifyList(myList);
			
			switch(status) {
			case CANCEL :
				canceledReservation.add(myList);
				break;
					
			case CONFIRMED :
				confirmedReservation.add(myList);
				break;
					
			case  USED:
				usedReservation.add(myList);
				break;
			}
		}
			
		params.put("canceledReservation", canceledReservation);
		params.put("confirmedReservation", confirmedReservation);
		params.put("usedReservation", usedReservation);

		return params;
	}
	
	@GetMapping(path = "/reservations/id")
	public Map<String, Object> getOneListByEmail(@RequestParam(name = "reservationId", required = false, defaultValue = "") int reservationId){
	
		Map<String, Object> params = new HashMap<>();
		MyReservation myreservation = myReservationService.getMyListByReservationId(reservationId);
	
		List<TicketInfo> tickets = myReservationService.getTicketInfo(myreservation.getReservationId());
		myreservation.setTicketInfo(tickets);

		params.put("Myreservation", myreservation);
		return params;
	}

	@PutMapping(path = "/reservations")
	public int cancelReservation(@RequestParam(name = "reservationId", required = false, defaultValue = "") int reservationId) {
		return myReservationService.cancelReservation(reservationId);
	}
	
}
