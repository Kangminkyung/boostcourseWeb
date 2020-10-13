package kr.or.connect.reservation.dao;

public class ReservationDaoSql {

	public static final String INSERT_RESERVATION = 
		    " insert into reservation_info ("
		    		  + "			product_id," 
		    		  + "			display_info_id," 
		    		  + "			reservation_name," 
		    		  + "			reservation_tel," 
		    		  + "			reservation_email," 
		    		  + "			reservation_date," 
		    		  + "			cancel_flag," 
		    		  + "			create_date," 
		    		  + "			modify_date)" 
		    		  + " values (:productId," 
		    		  + "			:displayInfoId," 
		    		  + "			:reservationName," 
		    		  + "			:reservationTel," 
		    		  + "			:reservationEmail," 
		    		  + "			:reservationDate," 
		    		  + "			default," 
		    		  + "			:createDate," 
		    		  + "			:modifyDate)"; 
	
	public static final String INSERT_PRICES = 
			"insert into reservation_info_price(reservation_info_id, product_price_id, count) values(:reservationInfoId, :productPriceId, :count)";
}
