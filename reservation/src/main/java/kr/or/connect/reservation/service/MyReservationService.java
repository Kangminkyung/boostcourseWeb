package kr.or.connect.reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.MyReservationDao;
import kr.or.connect.reservation.dto.MyReservation;
import kr.or.connect.reservation.dto.TicketInfo;

@Service
public class MyReservationService {

	@Autowired
	private MyReservationDao myReservationDao;
	
	// email로 내 전체 예약 리스트 불러오기
	public List<MyReservation> getTotalReservationByEmail(String reservationEmail) {
		System.out.println("service : "+reservationEmail);
		return myReservationDao.selectTotalReservationByEmail(reservationEmail);
	}

	// 티켓 정보 불러오기
	public List<TicketInfo> getTicketInfo(int reservationId) {
		return myReservationDao.selectTicketInfo(reservationId);
	}
	
	// 예약 건수 가져오기 by reservationEmail
	public int getReservationCount(String reservationEmail) {
		return myReservationDao.getReservationCount(reservationEmail);
	}





	
}
