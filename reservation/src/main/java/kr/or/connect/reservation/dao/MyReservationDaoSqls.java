package kr.or.connect.reservation.dao;

public class MyReservationDaoSqls {

	public static final String SELECT_TOTAL_RESERVATION_BY_EMAIL = 
			"SELECT pv.ticket_price , " +
			"	SUM(rip.count) as ticket_count , " +
			"	di.place_name, " +
			"	di.place_lot,  "+
			"	di.place_street, "+
			"	prod.id product_id, "+
			"	prod.description, "+
			"	resrv.id reservation_id, "+
			"	resrv.cancel_flag, "+
			"	resrv.reservation_date "+
			"FROM display_info di, product prod,reservation_info_price rip, "+
			"	(SELECT id, product_id, display_info_id, cancel_flag, reservation_date "+
			"	FROM reservation_info WHERE reservation_email = :reservationEmail) resrv, "+
			"	(SELECT SUM(rip.count*pp.price) ticket_price,rip.reservation_info_id "+
			"	FROM reservation_info_price rip,product_price pp, reservation_info ri "+
			"	WHERE rip.product_price_id = pp.id and ri.id = rip.reservation_info_id GROUP BY ri.id) pv "+
			"WHERE resrv.id = pv.reservation_info_id and di.id = resrv.display_info_id and resrv.product_id = prod.id and resrv.id = rip.reservation_info_id "+ 
			"GROUP BY resrv.id "+
			"ORDER BY resrv.reservation_date ";
	
	
	public static final String SELECT_TICKET_INFO = "SELECT pp.price_type_name, pp.price, B.count FROM product_price pp,"
			+ "(SELECT rip.product_price_id, rip.count FROM reservation_info_price rip WHERE rip.reservation_info_id = :reservationId) B"
			+ " WHERE B.product_price_id = pp.id";

	public static final String GET_RESERVATION_COUNT_BY_RESERVATIONEMAIL = 
			"SELECT COUNT(*) FROM RESERVATION_INFO WHERE RESERVATION_EMAIL = :reservationEmail";
	
	
	public static final String GET_TICKETINFO_BY_RESERVATION_ID = 
			"SELECT C.PRICE_TYPE_NAME AS PRICETYPENAME, " + 
			"	B.COUNT AS COUNT, " + 
			"	C.PRICE AS PRICE " + 
			"FROM RESERVATION_INFO A LEFT JOIN RESERVATION_INFO_PRICE B ON A.ID  = B.RESERVATION_INFO_ID " + 
			"	LEFT JOIN PRODUCT_PRICE C ON B.PRODUCT_PRICE_ID = C.ID " + 
			"WHERE A.ID = :reservationId";
}
