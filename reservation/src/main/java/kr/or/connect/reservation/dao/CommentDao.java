package kr.or.connect.reservation.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.Comment;
import kr.or.connect.reservation.dto.CommentImage;
import kr.or.connect.reservation.dto.FileInfo;

import static kr.or.connect.reservation.dao.CommentDaoSqls.*;

@Repository
public class CommentDao {

	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Comment> commentMapper = BeanPropertyRowMapper.newInstance(Comment.class);
	private RowMapper<CommentImage> commentImageMapper = BeanPropertyRowMapper.newInstance(CommentImage.class);

	public CommentDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	/* productDetail 정보 불러오기 */
	
	public List<Comment> getComments(int displayInfoId) {
				
		Map<String, Integer> map = new HashMap<>();
		map.put("displayInfoId", displayInfoId);
				
		// 1. comment 정보를 comments불에 담고
		List<Comment> commentList = jdbc.query(GET_COMMENT_LIST, map, commentMapper);
		
		// 2. 관련 이미지들을 comments에 넣음displayInfoId
		for(Comment comment : commentList) {
		
			// 3. comment 댓글 이미지 경로를 변경함(saveFileName)
			List<CommentImage> imageList = getImages(comment.getCommentId());
			
			for(CommentImage image : imageList) {
				String newName = "/tmp/reviewImage/";
				newName += image.getFileName();
				image.setSaveFileName(newName);
			}
		
			comment.setCommentImages(imageList);
			
			// 4. comment 별 count 구하기
			int imageCount = 0;
			imageCount = getCommentImageCount(comment.getCommentId());
			
			if(imageCount !=0 ) 
				comment.setImageCount(imageCount);
		}

		return commentList;
	}


	public List<CommentImage> getImages(int reservationUserCommentId){
		Map<String, Integer> map = new HashMap<>();
		map.put("reservationUserCommentId", reservationUserCommentId);
		return jdbc.query(GET_COMMENT_IMAGES_BY_COMMENT_ID, map, commentImageMapper);
	}
	
	public double getAverageScore(int displayInfoId) {
		Map<String, Integer> map = new HashMap<>();
		map.put("displayInfoId", displayInfoId);
		return jdbc.queryForObject(GET_AVERAGE_SCORE, map, double.class);
	}


	private int getCommentImageCount(int commentId) {
		Map<String, Integer> map = new HashMap<>();
		map.put("commentId", commentId);
		return jdbc.queryForObject(GET_COMMENT_IMAGE_COUNT, map, Integer.class);

	}

	public String selectReviewImageFile(int id) {
		Map<String, Integer> map = new HashMap<>();
		map.put("id", id);
		return jdbc.queryForObject(SELECT_REVIEW_IMAGE_FILE, map, String.class);
	}
}
