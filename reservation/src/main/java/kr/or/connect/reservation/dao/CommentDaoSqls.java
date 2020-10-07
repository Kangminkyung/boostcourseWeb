package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.CommentDaoSqls.GET_COMMENT_IMAGES_BY_COMMENT_ID;

public class CommentDaoSqls {
	
	public static String GET_COMMENT_LIST = 
			"SELECT PRODUCT.DESCRIPTION AS DESCRIPTION ," +
			"	RESERVATION_USER_COMMENT.ID AS COMMENTID, " + 
			"	RESERVATION_USER_COMMENT.PRODUCT_ID AS PRODUCTID, " + 
			"	RESERVATION_INFO.DISPLAY_INFO_ID AS DISPLAYINFOID, " + 
			"	RESERVATION_INFO.ID AS RESERVATIONINFOID, " + 
			"	RESERVATION_USER_COMMENT.SCORE AS SCORE, " + 
			"	RESERVATION_USER_COMMENT.COMMENT AS COMMENT, " + 
			"	RESERVATION_USER_COMMENT.CREATE_DATE AS CREATEDATE, " + 
			"	RESERVATION_USER_COMMENT.MODIFY_DATE AS MODIFYDATE, " + 
			"	RESERVATION_INFO.RESERVATION_NAME AS RESERVATIONNAME, " + 
			"	RESERVATION_INFO.RESERVATION_TEL AS RESERVATIONTEL, " + 
			"	RESERVATION_INFO.RESERVATION_EMAIL AS RESERVATIONEMAIL, " + 
			"	RESERVATION_INFO.RESERVATION_DATE AS RESERVATIONDATE " + 
			"FROM RESERVATION_INFO " + 
			"JOIN PRODUCT ON RESERVATION_INFO.PRODUCT_ID = PRODUCT.ID " +
			"JOIN DISPLAY_INFO ON RESERVATION_INFO.DISPLAY_INFO_ID = DISPLAY_INFO.ID " +
			"JOIN RESERVATION_USER_COMMENT ON RESERVATION_INFO.ID = RESERVATION_USER_COMMENT.RESERVATION_INFO_ID "+
			"WHERE RESERVATION_INFO.DISPLAY_INFO_ID = :displayInfoId " + 
			"ORDER BY RESERVATION_USER_COMMENT.CREATE_DATE DESC ";
	
	public static String GET_COMMENT_IMAGES_BY_COMMENT_ID = 
			"SELECT RESERVATION_USER_COMMENT_IMAGE.ID AS IMAGEID, " + 
			"	RESERVATION_USER_COMMENT_IMAGE.RESERVATION_INFO_ID AS RESERVATIONINFOID, " + 
			"	RESERVATION_USER_COMMENT_IMAGE.RESERVATION_USER_COMMENT_ID AS RESERVATIONUSERCOMMENTID, " + 
			"	FILE_INFO.ID AS FILEID, " + 
			"	FILE_INFO.FILE_NAME AS FILENAME, " + 
			"	FILE_INFO.SAVE_FILE_NAME AS SAVEFILENAME, " +  
			"	FILE_INFO.CONTENT_TYPE AS CONTENTTYPE, " + 
			"	FILE_INFO.DELETE_FLAG AS DELETEFLAG, " + 
			"	FILE_INFO.CREATE_DATE AS CREATEDATE, " + 
			"	FILE_INFO.MODIFY_DATE AS MODIFYDATE " + 
			"FROM RESERVATION_USER_COMMENT_IMAGE " + 
			"JOIN RESERVATION_INFO ON RESERVATION_USER_COMMENT_IMAGE.RESERVATION_INFO_ID = RESERVATION_INFO.ID " + 
			"JOIN RESERVATION_USER_COMMENT ON RESERVATION_USER_COMMENT_IMAGE.RESERVATION_USER_COMMENT_ID = RESERVATION_USER_COMMENT.ID " +
			"JOIN FILE_INFO ON RESERVATION_USER_COMMENT_IMAGE.FILE_ID = FILE_INFO.ID " +
			"WHERE RESERVATION_USER_COMMENT_IMAGE.RESERVATION_USER_COMMENT_ID = :reservationUserCommentId";

	public static String GET_AVERAGE_SCORE = 
			"SELECT AVG(SCORE) AS AVERAGESCORE " +
			"FROM RESERVATION_USER_COMMENT " +
			"JOIN RESERVATION_INFO ON RESERVATION_INFO.ID = RESERVATION_USER_COMMENT.RESERVATION_INFO_ID "+
			"WHERE RESERVATION_INFO.DISPLAY_INFO_ID = :displayInfoId ";
	
	public static String GET_COMMENT_IMAGE_COUNT =
		"SELECT COUNT(*) "+
		"FROM RESERVATION_USER_COMMENT_IMAGE "+
		"WHERE RESERVATION_USER_COMMENT_ID = :commentId";
	
	public static String SELECT_REVIEW_IMAGE_FILE = 
			"SELECT SAVE_FILE_NAME AS SAVEFILENAME " +
			 "FROM FILE_INFO " +
			 "WHERE ID = :id";
}
