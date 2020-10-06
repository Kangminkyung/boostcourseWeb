package kr.or.connect.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.ReservationDao;
import kr.or.connect.reservation.dao.ReviewWriteDao;
import kr.or.connect.reservation.dto.ReviewWrite;

@Service
public class ReviewWriteService {
	@Autowired
	private ReviewWriteDao reviewWriteDao;

	@Transactional(readOnly = false)
	public void addReviewData(ReviewWrite reviews, boolean isEmpty) {

		System.out.println("서비스 addReviewData empty = "+isEmpty);
		
		if(isEmpty) {
			// 사진 없음 텍스트만 등록
			reviewWriteDao.insertUserReview(reviews);
		}else {
			// 사진 있음 텍스트, 파일 등록
			reviewWriteDao.insertUserReviewFileImage(reviews.getReservationId(),
					reviewWriteDao.insertUserReview(reviews),
					reviewWriteDao.insertFileImage(reviews));
		}
	}

}
