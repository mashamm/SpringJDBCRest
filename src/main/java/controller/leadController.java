package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dao.InsertException;
import dao.leadDao;
import model.LeadNotFoundException;
import model.lead;
/*
 *        
 *  leads/                  GET      getAll()
 * 	leads/{id}              GET      get()
 * 	leads/create/{name}     POST     create()
 * 	leads/update/{id}       PUT  	 update()
 * 	leads/delete/{id}      DELETE	 delete()
 * 	
 * */
@RestController
@ComponentScan("dao") 
@RequestMapping("/leads")
public class leadController {
	
	@Autowired
	private leadDao leadService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody lead get(
		   @PathVariable Long id) {
		
		lead lead = leadService.get(id);
		return lead;
	}

	@RequestMapping(value = "/create/{name}", method = RequestMethod.POST)
	public 	@ResponseBody Long create(
			@RequestParam(value = "id", required = false) Long id,
			@PathVariable(value = "name") String name, 
			@RequestParam(value = "info", required = false) String info,
			HttpServletRequest request, 
			HttpServletResponse httpResponse)  {
		
			Long leadId=-1l;
			try {
				leadId = (long) leadService.create(name, info);
			} catch (InsertException e) {
				httpResponse.setStatus(403);
				httpResponse.setHeader("reason", "Insertion error");
				// e.printStackTrace();
			}
			return leadId;
		}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public  @ResponseBody Long update(@PathVariable Long id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "info", required = false) String info, HttpServletRequest request,
			HttpServletResponse httpResponse) {
		try {
			leadService.update(id, name, info);
		} catch (LeadNotFoundException e) {
			httpResponse.setStatus(403);
			httpResponse.setHeader("reason", "Record not found");
			// e.printStackTrace();
		}
		return id;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public 	@ResponseBody void delete(
			@PathVariable Long id, 
			HttpServletRequest request,
			HttpServletResponse httpResponse) throws LeadNotFoundException {
		if (id!=null)
		{
		leadService.delete(id);}
		else throw new LeadNotFoundException("Not valid id");
		return;
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<lead> getAll(
		    HttpServletRequest request, 
		    HttpServletResponse httpResponse) {
		return leadService.getAll();
	}

}
