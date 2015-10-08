package controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dao.LeadDAO;
import model.Lead;

@RestController
@RequestMapping("/data")
public class RestController {
	@Autowired
	private LeadDAO leadService;
	@RequestMapping("/getById")
	public Lead getById(@RequestParam(value = "id") Integer id) {
		Lead lead = leadService.getById(id);
		return lead;
	}
} 
