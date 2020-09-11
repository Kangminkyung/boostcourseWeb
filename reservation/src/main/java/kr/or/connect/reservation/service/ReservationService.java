package kr.or.connect.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.ReservationDao;
import kr.or.connect.reservation.dto.Reservation;
import kr.or.connect.reservation.dto.ReservationForm;

@Service
public class ReservationService {

	@Autowired
	private ReservationDao reservationDao;

	public int addReservation(ReservationForm reservationForm) {
		
		// 예약 테이블에 데이터 삽입
		int rsvnId = reservationDao.insertReservation(reservationForm);
		
		// 가격 테이블에 데이터 삽입
		reservationDao.insertPrices(reservationForm, rsvnId);
		return rsvnId;
	}


}
