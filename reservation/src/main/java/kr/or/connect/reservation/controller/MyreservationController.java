package kr.or.connect.reservation.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.dto.MyReservation;
import kr.or.connect.reservation.dto.TicketInfo;
import kr.or.connect.reservation.service.MyReservationService;


@RestController
@RequestMapping(path = "/api/myreservationPage")
public class MyreservationController {

	@Autowired
	private MyReservationService myReservationService;
	
	private enum Status {
		CANCEL, CONFIRMED, USED
	}
	
	// 예약 상태 분류
	private Status ClassifyList(MyReservation MyReservation) throws ParseException {
		
		if(MyReservation.getCancelFlag() == 1) {
			return Status.CANCEL;
		}else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date reservationDate = dateFormat.parse(MyReservation.getReservationDate());
			
			System.out.println("reservationDate: " + reservationDate);
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
	
	
	@GetMapping(path = "/{reservationEmail:.+}")
	public Map<String, Object> getMyListByEmail(@PathVariable("reservationEmail") String reservationEmail) throws ParseException{
		
		System.out.println("MyreservationController getMyListByEmail => reservationEmail : "+reservationEmail);
		Map<String, Object> params = new HashMap<>();
		
		List<MyReservation> totalReservationList = myReservationService.getTotalReservationByEmail(reservationEmail);
		
		if(totalReservationList == null) {
			System.out.println("마이 리스트 null");
		}
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
	
}
