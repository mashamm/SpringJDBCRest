package controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dao.LeadDAO;
import model.Lead;
import model.LeadNotFoundException;

@RestController

@RequestMapping("/")
public class LeadController {
	@Autowired
	private LeadDAO leadService;
	@RequestMapping(value="/get", method = RequestMethod.GET)
	
	public  @ResponseBody
	 Lead get(@Valid @PathVariable int id) {
		Lead lead = leadService.get(id);
		return lead;
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	int create(
			@RequestParam(value = "id", required = false) int id,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "info", required = false) String info,

			HttpServletRequest request, HttpServletResponse httpResponse)
			throws IOException {

		return leadService.create(name, info);
	}
	@RequestMapping(value="/update",method=RequestMethod.PUT)
	public @ResponseBody
	int update(@RequestParam(value="id")int id,
			   @RequestParam(value="name",required=false)String name,
			   @RequestParam(value="info",required=false)String info,
			   HttpServletRequest request, HttpServletResponse httpResponse){
		try {
			 leadService.update(id, name, info);
		} catch (LeadNotFoundException e) {
			
			e.printStackTrace();
		}
		return id;
	}	
	@RequestMapping(value="/delete", method = RequestMethod.DELETE)
	public @ResponseBody
		void delete(@RequestParam(value="id")int id,
				HttpServletRequest request, HttpServletResponse httpResponse) throws LeadNotFoundException{
				leadService.delete(id);
		return;
	}
	@RequestMapping(value="/getAll",method=RequestMethod.GET)
	public @ResponseBody
	List<Lead> getAll(HttpServletRequest request, HttpServletResponse httpResponse){
		return leadService.getAll();
	}
	//TODO process bad requests 
		 
	
	
	
} 
