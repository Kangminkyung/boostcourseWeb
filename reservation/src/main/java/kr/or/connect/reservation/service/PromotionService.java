package kr.or.connect.reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.PromotionDao;
import kr.or.connect.reservation.dto.Promotion;

@Service
public class PromotionService {
	
	@Autowired
	private PromotionDao promotionDao;
	
	@Transactional(readOnly=true)
	public List<Promotion> getPromotions(){
		return this.promotionDao.selectAll();
	}

}
