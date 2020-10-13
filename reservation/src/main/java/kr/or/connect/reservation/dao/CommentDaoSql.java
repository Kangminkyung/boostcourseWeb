package kr.or.connect.reservation.dao;

public class CommentDaoSql {
	
	public static String GET_COMMENT_LIST = 
			"select product.description as description ," +
			"	reservation_user_comment.id as commentid, " + 
			"	reservation_user_comment.product_id as productid, " + 
			"	reservation_info.display_info_id as displayinfoid, " + 
			"	reservation_info.id as reservationinfoid, " + 
			"	reservation_user_comment.score as score, " + 
			"	reservation_user_comment.comment as comment, " + 
			"	reservation_user_comment.create_date as createdate, " + 
			"	reservation_user_comment.modify_date as modifydate, " + 
			"	reservation_info.reservation_name as reservationname, " + 
			"	reservation_info.reservation_tel as reservationtel, " + 
			"	reservation_info.reservation_email as reservationemail, " + 
			"	reservation_info.reservation_date as reservationdate " + 
			"from reservation_info " + 
			"join product on reservation_info.product_id = product.id " +
			"join display_info on reservation_info.display_info_id = display_info.id " +
			"join reservation_user_comment on reservation_info.id = reservation_user_comment.reservation_info_id "+
			"where reservation_info.display_info_id = :displayInfoId " + 
			"order by reservation_user_comment.create_date desc ";
	
	public static String GET_COMMENT_IMAGES_BY_COMMENT_ID = 
			"select reservation_user_comment_image.id as imageid, " + 
			"	reservation_user_comment_image.reservation_info_id as reservationinfoid, " + 
			"	reservation_user_comment_image.reservation_user_comment_id as reservationusercommentid, " + 
			"	file_info.id as fileid, " + 
			"	file_info.file_name as filename, " + 
			"	file_info.save_file_name as savefilename, " +  
			"	file_info.content_type as contenttype, " + 
			"	file_info.delete_flag as deleteflag, " + 
			"	file_info.create_date as createdate, " + 
			"	file_info.modify_date as modifydate " + 
			"from reservation_user_comment_image " + 
			"join reservation_info on reservation_user_comment_image.reservation_info_id = reservation_info.id " + 
			"join reservation_user_comment on reservation_user_comment_image.reservation_user_comment_id = reservation_user_comment.id " +
			"join file_info on reservation_user_comment_image.file_id = file_info.id " +
			"where reservation_user_comment_image.reservation_user_comment_id = :reservationUserCommentId";

	public static String GET_AVERAGE_SCORE = 
			"select avg(score) as averagescore " +
			"from reservation_user_comment " +
			"join reservation_info on reservation_info.id = reservation_user_comment.reservation_info_id "+
			"where reservation_info.display_info_id = :displayInfoId ";
	
	public static String GET_COMMENT_IMAGE_COUNT =
		"select count(*) "+
		"from reservation_user_comment_image "+
		"where reservation_user_comment_id = :commentId";
	
	public static String SELECT_REVIEW_IMAGE_FILE = 
			"select save_file_name as savefilename " +
			 "from file_info " +
			 "where id = :id";
}
