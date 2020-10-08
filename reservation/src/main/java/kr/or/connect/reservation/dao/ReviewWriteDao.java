package kr.or.connect.reservation.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.FileInfo;
import kr.or.connect.reservation.dto.ReservationUserComment;
import kr.or.connect.reservation.dto.ReservationUserCommentImage;
import kr.or.connect.reservation.dto.ReviewWrite;

@Repository
public class ReviewWriteDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	DataSource dataSource;
	
	public ReviewWriteDao(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	// 코멘트 등록
	public int insertUserComment(ReviewWrite reviews) {
		int commentId = 0;
		
		try {
			ReservationUserComment reservationUserComment = new ReservationUserComment();
			
			reservationUserComment.setReservationInfoId(reviews.getReservationId());
			reservationUserComment.setProductId(reviews.getProductId());
			reservationUserComment.setScore(reviews.getScore());
			reservationUserComment.setComment(reviews.getComment());

		    SqlParameterSource params = new BeanPropertySqlParameterSource(reservationUserComment);

		    // commentId 고유키 가져오기
			this.insertAction = new SimpleJdbcInsert(this.dataSource).withTableName("reservation_user_comment").usingGeneratedKeyColumns("id");
		    commentId = this.insertAction.executeAndReturnKey(params).intValue();
		   
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		return commentId;
	}

	// 이미지 등록
	public int insertFileImage(ReviewWrite reviews) {
		
		int fileId = 0;
		
		try {
			FileInfo fileInfo = new FileInfo();
			fileInfo.setFileName(reviews.getFileName());
			fileInfo.setSaveFileName(reviews.getSaveFileName());
			fileInfo.setContentType(reviews.getContentType());
			fileInfo.setDeleteFlag(reviews.getDeleteFlag());
			
		    SqlParameterSource params = new BeanPropertySqlParameterSource(fileInfo);

		    // fileId 고유키 가져오기
			this.insertAction = new SimpleJdbcInsert(this.dataSource).withTableName("file_info").usingGeneratedKeyColumns("id");
		    fileId = this.insertAction.executeAndReturnKey(params).intValue();
		  
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		
		return fileId;
	}

	// 리뷰 + 사진 등록
	public int insertUserCommentFileImage(int reservationInfoId, int commentId, int fileId) {
		int commentImageId = 0;
		
		try {
			ReservationUserCommentImage reservationUserCommentImage = new ReservationUserCommentImage();
			
			reservationUserCommentImage.setReservationInfoId(reservationInfoId);
			reservationUserCommentImage.setReservationUserCommentId(commentId);
			reservationUserCommentImage.setFileId(fileId);
			
			SqlParameterSource params = new BeanPropertySqlParameterSource(reservationUserCommentImage);

		    // commentId 고유키 가져오기
			this.insertAction = new SimpleJdbcInsert(this.dataSource).withTableName("reservation_user_comment_image").usingGeneratedKeyColumns("id");
			commentImageId = this.insertAction.executeAndReturnKey(params).intValue();
		
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		return commentImageId;		
	}

}
