package kr.or.connect.reservation.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.DisplayInfoImage;
import static kr.or.connect.reservation.dao.DisplayInfoSql.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DisplayInfoDao {

	private final NamedParameterJdbcTemplate jdbc;
	private RowMapper<DisplayInfo> displayInfoMapper = BeanPropertyRowMapper.newInstance(DisplayInfo.class);
	private RowMapper<DisplayInfoImage> displayInfoImageMapper = BeanPropertyRowMapper.newInstance(DisplayInfoImage.class);

	public DisplayInfoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	 
	/* productDetail 정보 불러오기 */
	
	public DisplayInfo getDisplayInfo(int displayInfoId) {
		Map<String, Integer> map = new HashMap<>();
		map.put("displayInfoId",displayInfoId);
		return jdbc.queryForObject(GET_DISPLAY_INFO_ONE, map, displayInfoMapper);
	}

	public List<DisplayInfoImage> getDisplayInfoImages(int displayInfoId) {
		Map<String, Integer> map = new HashMap<>();
		map.put("displayInfoId",displayInfoId);
		return jdbc.query(GET_DISPLAY_IMAGES_BY_DISPLAY_INFO_ID, map, displayInfoImageMapper);
	}

	public int getProductIdByDisplayInfoId(int displayInfoId) {
		Map<String, Integer> map = new HashMap<>();
		map.put("displayInfoId",displayInfoId);
		return jdbc.queryForObject(GET_PRODUCT_ID_BY_DISPLAY_INFO_ID, map, Integer.class);
	}

	

}
