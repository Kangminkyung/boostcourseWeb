package kr.or.connect.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.CommentDao;
import kr.or.connect.reservation.dao.ReviewWriteDao;
import kr.or.connect.reservation.dto.ReviewWrite;

@Service
public class ReviewWriteService {
	@Autowired
	private ReviewWriteDao reviewWriteDao;
	
	@Autowired
	private CommentDao commentDao;

	@Transactional(readOnly = false)
	public void addReviewData(ReviewWrite reviews, boolean isEmpty) {

		
		if(isEmpty) {
			// 사진 없음 텍스트만 등록
			reviewWriteDao.insertUserComment(reviews);
		}else {
			// 사진 있음 텍스트, 파일 등록
			reviewWriteDao.insertUserCommentFileImage(reviews.getReservationId(),
					reviewWriteDao.insertUserComment(reviews),
					reviewWriteDao.insertFileImage(reviews));
		}
	}

	public String selectReviewImageFile(int id) {
		return commentDao.selectReviewImageFile(id);
	}

}
