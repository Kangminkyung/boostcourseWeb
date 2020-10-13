package kr.or.connect.reservation.dao;

public class PromotionDaoSql {

	public static final String SELECT_ALL =  
			"select promotion.id as id, " + 
			"	 	 promotion.product_id as productid, " + 
			"	  	 file_info.save_file_name as productimageurl " + 
			"from promotion " + 
			"join product on product.id = promotion.product_id " + 
			"join product_image on product_image.product_id = product.id " + 
			"join file_info on file_info.id = product_image.file_id " + 
			"where product_image.type='th'";

}
