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
	
	// email로 예약 건수가 있는지 조회
	public int getTotalReservationCountByEmail(String reservationEmail) {
		return myReservationDao.selectTotalReservationCountByEmail(reservationEmail);
	}
	
	// email로 내 전체 예약 리스트 불러오기
	public List<MyReservation> getTotalReservationByEmail(String reservationEmail) {
		return myReservationDao.selectTotalReservationByEmail(reservationEmail);
	}

	// 티켓 정보 불러오기
	public List<TicketInfo> getTicketInfo(int reservationId) {
		return myReservationDao.selectTicketInfo(reservationId);
	}

	// 하나의 예약 리스트 정보 불러오기
	public MyReservation getMyListByReservationId(int reservationId) {
		return myReservationDao.selectMyListByReservationId(reservationId);
	}

	// 예약 취소
	public int cancelReservation(int reservationId) {
		return myReservationDao.updateReservationCancelFlag(reservationId);
	}
	

}
