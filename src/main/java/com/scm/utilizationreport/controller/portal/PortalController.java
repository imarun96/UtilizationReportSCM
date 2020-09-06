package com.scm.utilizationreport.controller.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortalController {
	Logger log = LoggerFactory.getLogger(PortalController.class);

	@GetMapping("/json")
	public Map<String, Object> jsonValue() {
		Map<String, Object> map = new HashMap<>();
		List<Object> newList = new ArrayList<>();
		List<Object> newList1 = new ArrayList<>();
		newList1.add("arun");
		newList1.add("balaji");
		newList.add(newList1);
		map.put("String", newList);
		map.put("Arun", "Balaji");
		return map;
	}
}