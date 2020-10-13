package kr.or.connect.reservation.dao;

public class DisplayInfoSql {
	public static final String GET_DISPLAY_INFO_ONE = 
			"select display_info.product_id as productid, " + 
			"	category.id as categoryid, " + 
			"	display_info.id as displayinfoid, " + 
			"	category.name as categoryname, " + 
			"	product.description as productdescription, " + 
			"	product.content as productcontent, " + 
			"	product.event as productevent, " + 
			"	display_info.opening_hours as openinghours, " + 
			"	display_info.place_name as placename, " + 
			"	display_info.place_lot as placelot, " + 
			"	display_info.place_street as placestreet, " + 
			"	display_info.tel as telephone, " + 
			"	display_info.homepage as homepage, " + 
			"	display_info.email as email, " + 
			"	display_info.create_date as createdate, " + 
			"	display_info.modify_date as modifydate " + 
			"from display_info " + 
			"join product on product.id = display_info.product_id " + 
			"join category on category.id = product.category_id " + 
			"where display_info.id = :displayInfoId"; 

	
	public static final String GET_DISPLAY_IMAGES_BY_DISPLAY_INFO_ID = 
			"select display_info_image.id as displayinfoimageid, " + 
			"	display_info_image.display_info_id as displayinfoid, " + 
			"	display_info_image.file_id as fileid, " + 
			"	file_info.file_name as filename, " + 
			"	file_info.save_file_name as displayinfosavefilename, " + 
			"	file_info.content_type as contenttype, " + 
			"	file_info.delete_flag as deleteflag, " + 
			"	file_info.create_date as createdate, " + 
			"	file_info.modify_date as modifydate " + 
			"from display_info_image " + 
			"join file_info on file_info.id = display_info_image.file_id " + 
			"where display_info_image.display_info_id = :displayInfoId";
	
	public static final String  GET_PRODUCT_ID_BY_DISPLAY_INFO_ID = 
			"select product_id " +
			"from display_info " +
			"where id = :displayInfoId";
 
}
