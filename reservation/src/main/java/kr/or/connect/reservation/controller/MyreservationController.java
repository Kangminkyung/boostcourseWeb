package kr.or.connect.reservation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			System.out.println("cancel 임");
			return Status.CANCEL;
		}else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date reservationDate = dateFormat.parse(MyReservation.getReservationDate());
			
			Date currentDate = new Date();
			Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();
			startDate.setTime(currentDate);
			endDate.setTime(reservationDate);
			
			long diffMillis = startDate.getTimeInMillis() - endDate.getTimeInMillis();
			int diff = (int) (diffMillis / (24 * 60 * 60 * 1000));

			if (diff > 0) {
				System.out.println("used임");
				return Status.USED;
			} else {
				System.out.println("confirm임");

				return Status.CONFIRMED;
			}
		}
	}
	
	
	@GetMapping(path = "/{reservationEmail:.+}")
	public Map<String, Object> getMyListByEmail(@PathVariable("reservationEmail") String reservationEmail, HttpServletResponse response) throws ParseException, IOException{
		
		Map<String, Object> params = new HashMap<>();
		
		int count = 0;
		
		// 이메일 .com으로 받아오면 406 에러뜸
		// co.kr로 예약하면 문제없이 잘 뜸
		
		count = myReservationService.getTotalReservationCountByEmail(reservationEmail);
		
		System.out.println("count : "+count);
		// 이메일 조회시 등록된 예약 건수가 존재함
		if(count != 0) {
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

		}else { // 이메일로 등록된 예약 건수가 없음
			System.out.println("아이디 존재 하지 않음");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('이메일이 존재하지 않습니다'); location.href='http://localhost:8080/reservation/bookingloginPage';</script>");
			 
			out.flush();
		}
		
		return params;
	}
	
	@GetMapping(path = "/{reservationEmail:.+}/{reservationId}")
	public Map<String, Object> getOneListByEmail(@RequestParam(name = "reservationId", required = false, defaultValue = "") int reservationId){
	
		Map<String, Object> params = new HashMap<>();
		MyReservation myList = myReservationService.getMyListByReservationId(reservationId);
		params.put("myList", myList);
		return params;
	}

}
