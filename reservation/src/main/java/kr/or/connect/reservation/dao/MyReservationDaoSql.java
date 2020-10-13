package kr.or.connect.reservation.dao;

public class MyReservationDaoSql {

	public static final String SELECT_TOTAL_RESERVATION_COUNT_BY_EMAIL = 
			"select count(*) from reservation_info where reservation_email = :reservationEmail";
	
	public static final String SELECT_TOTAL_RESERVATION_BY_EMAIL = 
			"select sum(d.count * e.price) as ticketprice," + 
			"	sum(d.count) as ticketcount, " + 
			"	c.id as displayinfoid, " +		
			"	c.place_name as placename, " + 
			"	c.place_lot as placelot, " + 
			"	c.place_street as placestreet, " + 
			"	b.id as productid, " + 
			"	b.description as description, " + 
			"	a.id as reservationid, " + 
			"	a.cancel_flag as cancelflag, " + 
			"	a.reservation_date as reservationdate " + 
			"from reservation_info a join product b on a.product_id = b.id " + 
			"	join display_info c on a.display_info_id = c.id " + 
			"	join reservation_info_price d on a.id = d.reservation_info_id " + 
			"	join product_price e on d.product_price_id = e.id " + 
			"where a.reservation_email = :reservationEmail " +
			"group by a.id " +
			"order by a.id desc";

	
	public static final String SELECT_TICKET_INFO = 
			"select b.price_type_name as pricetypename, " + 
			"	b.price as price, " + 
			"	a.count as count " + 
			"from reservation_info_price a join product_price b on a.product_price_id = b.id " + 
			"where a.reservation_info_id = :reservationId ";

	
	public static final String GET_TICKETINFO_BY_RESERVATION_ID = 
			"select c.price_type_name as pricetypename, " + 
			"	b.count as count, " + 
			"	c.price as price " + 
			"from reservation_info a left join reservation_info_price b on a.id  = b.reservation_info_id " + 
			"	left join product_price c on b.product_price_id = c.id " + 
			"where a.id = :reservationId";
	
	public static final String SELECT_MYLIST_BY_RESERVATIOM_ID = 
			"select sum(d.count * e.price) as ticketprice, " + 
			"	sum(d.count) as ticketcount, " + 
			"	c.id as displayinfoid, " +		
			"	c.place_name as placename, " + 
			"	c.place_lot as placelot, " + 
			"	c.place_street as placestreet, " + 
			"	b.id as productid, " + 
			"	b.description as description, " + 
			"	a.id as reservationid, " + 
			"	a.cancel_flag as cancelflag, " + 
			"	a.reservation_date as reservationdate " + 
			"from reservation_info a join product b on a.product_id = b.id " + 
			"	join display_info c on a.display_info_id = c.id " + 
			"	join reservation_info_price d on a.id = d.reservation_info_id " + 
			"	join product_price e on d.product_price_id = e.id " + 
			"where a.id = :reservationId " + 
			"group by a.id " +
			"order by a.id desc";
	
	public static final String UPDATE_RESERVATION_CANCELFLAG = 
			"update reservation_info set cancel_flag = 1 where id = :reservationId ";
}
