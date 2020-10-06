package kr.or.connect.reservation.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
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
	private RowMapper<ReviewWrite> reviewWriteMapper = BeanPropertyRowMapper.newInstance(ReviewWrite.class);
	private SimpleJdbcInsert insertAction;
	DataSource dataSource;
	
	public ReviewWriteDao(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	// 리뷰 코멘트 등록
	public int insertUserReview(ReviewWrite reviews) {
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
		    System.out.println("코멘트삽입(reservation_user_comment) commentId: "+ commentId);
		    System.out.println("1)getReservationId : "+reservationUserComment.getReservationInfoId());
		    System.out.println("2)리뷰삽입 getProductId : "+reservationUserComment.getProductId());
		    System.out.println("3)리뷰삽입 getScore : "+reservationUserComment.getScore());
		    System.out.println("4)리뷰삽입 getComment : "+reservationUserComment.getComment());

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
		    System.out.println("이미지파일삽입(file_info) fileId: "+ fileId);
		    System.out.println("1)getFileName : "+fileInfo.getFileName());
		    System.out.println("2)getSaveFileName : "+fileInfo.getSaveFileName());
		    System.out.println("3)getContentType : "+fileInfo.getContentType());
		    System.out.println("4)getDeleteFlag : "+fileInfo.getDeleteFlag());
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		
		return fileId;
	}

	// 리뷰 + 사진 등록
	public int insertUserReviewFileImage(int reservationInfoId, int commentId, int fileId) {
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
		    System.out.println("이미지+코멘트삽입(reservation_user_comment_image) commentImageId: "+ commentImageId);
		    System.out.println("1)reservationInfoId : "+reservationInfoId);
		    System.out.println("2)commentId : "+commentId);
		    System.out.println("3)fileId : "+fileId);
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		return commentImageId;		
	}

}
