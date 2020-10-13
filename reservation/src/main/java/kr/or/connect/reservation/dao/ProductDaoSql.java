package kr.or.connect.reservation.dao;

public class ProductDaoSql {
	
	public static final String GET_COUNT_PRODUCT = 
			"select count(*) as totalcount from display_info";
	
	public static final String GET_COUNT_PRODUCT_BY_CATEGORY = 
			"select count(*) as totalcount " + 
			"from display_info join product " + 
			"on display_info.product_id = product.id " + 
			"where product.category_id = :categoryId";
	
	public static final String GET_PRODUCT_LIST = 
			"select product.id as productid, " + 
			"	display_info.id as displayinfoid, " + 
			"	display_info.place_name as placename, " + 
			"	product.description as productdescription, " + 
			"	product.content as productcontent," +
			"	file_info.save_file_name as productimageurl " + 
			"from product " + 
			"join display_info on product.id = display_info.product_id " + 
			"join product_image on product.id = product_image.product_id " + 
			"join file_info on product_image.file_id = file_info.id " + 
			"where product_image.type = 'th' " + 
			"limit :start, :limit";
	
	public static final String GET_PRODUCT_LIST_BY_CATEGORY = 
			"select product.id as productid, " + 
			"	display_info.id as displayinfoid, " + 
			"	display_info.place_name as placename, " + 
			"	product.description as productdescription, " + 
			"	product.content as productcontent, " +
			"	file_info.save_file_name as productimageurl " + 
			"from product " + 
			"join display_info on product.id = display_info.product_id " + 
			"join product_image on product.id = product_image.product_id " + 
			"join file_info on product_image.file_id = file_info.id " + 
			"where product_image.type = 'th' and product.category_id = :categoryId " + 
			"limit :start, :limit";			

	public static final String GET_PRODUCT_IMAGES_BY_PRODUCT_ID = 
		    "select product_image.product_id as productid, " + 
		    "	product_image.id as productimageid, " + 
		    "	product_image.type as type, " + 
		    "	file_info.id as fileid, " + 
		    "	file_info.file_name as filename, " + 
		    "	file_info.save_file_name as productsavefilename, " + 
		    "	file_info.content_type as contenttype, " + 
		    "	file_info.delete_flag as deleteflag, " + 
		    "	file_info.create_date as createdate, " + 
		    "	file_info.modify_date as modifydate " + 
		    "from product_image " + 
		    "join file_info on product_image.file_id = file_info.id " + 
		    "where  product_id = :productId " + 
		    "and (type = 'ma' or type = 'et' )";
	
	public static final String GET_PRODUCT_PRICE = 
		    "select id as productpriceid, " + 
		    "	product_id as productid, " + 
		    "	price_type_name as pricetypename, " + 
		    "	price, " + 
		    "	discount_rate as discountrate,  " + 
		    "	create_date as createdate, " + 
		    "	modify_date as modifydate " + 
		    "from   product_price " + 
		    "where  product_id = :productId " + 
		    "order by id desc ";

}
