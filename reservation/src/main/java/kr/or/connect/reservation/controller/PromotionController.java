package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.service.PromotionService;

@RestController
@RequestMapping(path = "/api/promotions")
public class PromotionController {
	@Autowired
	private PromotionService promotionService;
	
	@GetMapping
	public Map<String, Object> getPromotions(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("items", this.promotionService.getPromotions());
		return map;
	}

}