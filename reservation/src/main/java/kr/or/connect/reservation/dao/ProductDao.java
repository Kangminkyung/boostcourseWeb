package kr.or.connect.reservation.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.Product;
import kr.or.connect.reservation.dto.ProductImage;
import kr.or.connect.reservation.dto.ProductPrice;

import static kr.or.connect.reservation.dao.ProductDaoSql.*;

@Repository
public class ProductDao {

	private final NamedParameterJdbcTemplate jdbc;
	private RowMapper<Product> productMapper = BeanPropertyRowMapper.newInstance(Product.class);
	private RowMapper<ProductImage> productImageMapper = BeanPropertyRowMapper.newInstance(ProductImage.class);
	private RowMapper<ProductPrice> productPriceMapper = BeanPropertyRowMapper.newInstance(ProductPrice.class);

	public ProductDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	 
	/* product List 정보 불러오기*/
	public int getCountProduct() {
		return jdbc.queryForObject(GET_COUNT_PRODUCT, Collections.emptyMap(), Integer.class);
	} 

	public int getCountProductByCategory(int categoryId) {
		Map<String, Integer> map = new HashMap<>();
		map.put("categoryId",categoryId);
		return jdbc.queryForObject(GET_COUNT_PRODUCT_BY_CATEGORY, map, Integer.class);
	}
		
	public List<Product> getProductList(int start ,int limit) {
		Map<String, Integer> map = new HashMap<>();
		map.put("start", start);
		map.put("limit", limit);
		return jdbc.query(GET_PRODUCT_LIST, map, productMapper);
	}

	
	public List<Product> getProductListByCategory(int start, int limit, int categoryId) {
		Map<String, Integer> map = new HashMap<>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("categoryId", categoryId);
		return jdbc.query(GET_PRODUCT_LIST_BY_CATEGORY, map, productMapper);
	}

	/* productDetail 정보 불러오기 */
	
	public List<ProductImage> getProductImages(int productId) {
		Map<String, Integer> map = new HashMap<>();
		map.put("productId", productId);
		return jdbc.query(GET_PRODUCT_IMAGES_BY_PRODUCT_ID, map, productImageMapper);
	}

	public List<ProductPrice> getPriceList(int productId) {
		Map<String, Integer> map = new HashMap<>();
		map.put("productId", productId);		
		return jdbc.query(GET_PRODUCT_PRICE, map, productPriceMapper);

	}

}
