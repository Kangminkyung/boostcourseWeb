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

import kr.or.connect.reservation.dto.MyReservation;
import kr.or.connect.reservation.dto.ProductImage;
import kr.or.connect.reservation.dto.TicketInfo;

import static kr.or.connect.reservation.dao.MyReservationDaoSqls.*;

@Repository
public class MyReservationDao {

	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<MyReservation> myReservationMapper = BeanPropertyRowMapper.newInstance(MyReservation.class);
	private RowMapper<TicketInfo> ticketInfoMapper = BeanPropertyRowMapper.newInstance(TicketInfo.class);

	public MyReservationDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	

	public int selectTotalReservationCountByEmail(String reservationEmail) {
		Map<String, String> params = new HashMap<>();
		params.put("reservationEmail", reservationEmail);
		return jdbc.queryForObject(SELECT_TOTAL_RESERVATION_COUNT_BY_EMAIL, params, Integer.class);
	}

	public List<MyReservation> selectTotalReservationByEmail(String reservationEmail) {
		Map<String, String> params = new HashMap<>();
		List<MyReservation> myList = null;
		
		params.put("reservationEmail", reservationEmail);
		myList = jdbc.query(SELECT_TOTAL_RESERVATION_BY_EMAIL, params, myReservationMapper);	
		
		return myList;		
	}
	

	public List<TicketInfo> selectTicketInfo(int reservationId) {
		List<TicketInfo> tickets = Collections.emptyList();
		Map<String, Integer> params = new HashMap<>();

		try {
			params.put("reservationId", reservationId);
			tickets = jdbc.query(SELECT_TICKET_INFO, params,ticketInfoMapper);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return tickets;
	}

	

}
