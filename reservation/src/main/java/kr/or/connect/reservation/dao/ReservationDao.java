package kr.or.connect.reservation.dao;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.MyReservation;
import kr.or.connect.reservation.dto.ReservationForm;
import kr.or.connect.reservation.dto.ReservationPrice;

import static kr.or.connect.reservation.dao.ReservationDaoSqls.*;

@Repository
public class ReservationDao {
	
	private NamedParameterJdbcTemplate jdbc;

	public ReservationDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public int insertReservation(ReservationForm reservationform) {
		LocalDateTime now = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
		KeyHolder holder = new GeneratedKeyHolder();

		// 1. reservation_info table 업데이트
		SqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("productId", reservationform.getProductId())
				.addValue("displayInfoId", reservationform.getDisplayInfoId())
				.addValue("reservationName", reservationform.getReservationName())
				.addValue("reservationTel", reservationform.getReservationTel())
				.addValue("reservationEmail", reservationform.getReservationEmail())
				.addValue("reservationDate", reservationform.getReservationDate())
				.addValue("createDate", now)
				.addValue("modifyDate", now);
		
		jdbc.update(INSERT_RESERVATION, paramSource, holder);
		int rsvnId = holder.getKey().intValue();

		System.out.println(rsvnId);
		return rsvnId;
	}

	public void insertPrices(ReservationForm reservationForm, int reservationInfoId) {
		List<ReservationPrice> prices = reservationForm.getReservationPrices();
		
		// 2. reservation_info table 업데이트
		for(ReservationPrice price : prices) {
			SqlParameterSource paramSource = new MapSqlParameterSource()
					.addValue("reservationInfoId", reservationInfoId)
					.addValue("productPriceId", price.getProductPriceId())
					.addValue("count", price.getCount());
			
			jdbc.update(INSERT_PRICES, paramSource);
		}
	}
	
	

}
