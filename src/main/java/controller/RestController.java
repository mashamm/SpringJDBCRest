package controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.LeadDAO;
import model.Lead;

@Controller

@RequestMapping("/")
public class RestController {
	@Autowired
	private LeadDAO leadService;
	@RequestMapping(value="/getById", method = RequestMethod.GET)
	
	  @ResponseBody
	public Lead getByIdInJSON(@PathVariable int id) {
		Lead lead = leadService.getById(id);
		return lead;
	}
	
		 
	
	
	
} 
