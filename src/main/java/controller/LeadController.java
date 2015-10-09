package controller;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dao.LeadDAO;
import model.Lead;

@RestController

@RequestMapping("/")
public class LeadController {
	@Autowired
	private LeadDAO leadService;
	@RequestMapping(value="/getById", method = RequestMethod.GET)
	
	public  @ResponseBody
	 Lead getByIdInJSON(@PathVariable int id) {
		Lead lead = leadService.getById(id);
		return lead;
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	void setHistoryEvent(
			@RequestParam(value = "id", required = false) int id,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "info", required = false) String info,

			HttpServletRequest request, HttpServletResponse httpResponse)
			throws IOException {

		return leadService.save(name, info);

	}
	
		 
	
	
	
} 
