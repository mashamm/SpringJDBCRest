package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dao.leadDao;
import exception.ErrorInfo;
import exception.ErrorParamInfo;
import exception.ErrorRequestParams;
import exception.InsertException;
import exception.LeadNotFoundException;
import model.Lead;

/*
 *        
 *  leads/all               GET      getAll()
 * 	leads/{id}              GET      get()
 * 	leads/create/{name}     POST     create()
 * 	leads/{id}/update       PUT  	 update()
 * 	leads/{id}/delete      DELETE	 delete()
 * 	
 * */
@RestController
@ComponentScan("dao")
@RequestMapping(value = "/leads", 
method = { RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT,
		RequestMethod.POST })
public class leadController {
	
	@Autowired
	private leadDao leadService;
	@Autowired
	private MessageSource message;
	private static final String APPLICATION_JSON="application/json;charset=UTF-8";
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public  ResponseEntity<Lead>  get(
			  @PathVariable Long id, HttpServletRequest request, 
				HttpServletResponse httpResponse)  {
		Lead lead =null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if(id>0&&id!=null){
		       try {
			   lead = leadService.get(id);
			   return new  ResponseEntity<Lead>(lead,headers, HttpStatus.OK);
		       } catch (LeadNotFoundException e) {
			            headers.set("reason", "Lead not found with id="+id);
			            return  new ResponseEntity<Lead>(lead,headers,HttpStatus.NOT_FOUND);}
		        }
		else{
			headers.set("reason", "Not valid id="+id);
			return new ResponseEntity<Lead>(lead,headers,HttpStatus.NOT_FOUND);}
	}
	@RequestMapping(value = "/create/{name}", method = {RequestMethod.POST})
	public 	@ResponseBody Long create(
			@RequestParam(value = "id", required = false) Long id,
			@PathVariable(value = "name") String name, 
			@RequestParam(value = "info", required = false) String info,
			HttpServletRequest request, 
			HttpServletResponse httpResponse)  {
		
			Long leadId=null;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			try {
				leadId = (long) leadService.create(name, info);
				httpResponse.setStatus(HttpStatus.CREATED.value());
			} catch (InsertException e) {
				httpResponse.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
				httpResponse.setHeader("reason", "Insertion error");
			}
			return leadId;
		}
	
	@RequestMapping(value = "/{id}/update", method = {RequestMethod.PUT})
	public  @ResponseBody Long update(@PathVariable Long id,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "info") String info, 
			HttpServletRequest request,
			HttpServletResponse httpResponse) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			if(id>0){
		try {
			
			leadService.update(id, name, info);
			httpResponse.setStatus(HttpStatus.OK.value());
			return id;
		} catch (LeadNotFoundException e) {
			httpResponse.setStatus(HttpStatus.NOT_MODIFIED.value());
			httpResponse.setHeader("reason", "Lead not found id="+id);
			return id;
	    	}}
			else		
				httpResponse.setStatus(HttpStatus.NOT_MODIFIED.value());
			httpResponse.setHeader("reason", "Not valid id="+id);
			return id;
	}
	
	@RequestMapping(value = "/{id}/delete", method ={RequestMethod.DELETE})
	public 	@ResponseBody Long delete(
			@PathVariable Long id, 
			HttpServletRequest request,
			HttpServletResponse httpResponse)  {
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		if (id>0)
		{
		    try {
			      leadService.delete(id);
			      httpResponse.setStatus(HttpStatus.OK.value());
			      return id;
		         } catch (LeadNotFoundException e) {
		        	httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
		        	httpResponse.setHeader("reason", "Lead not found id="+id);
			        return id;
		         }
		}
		httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
    	httpResponse.setHeader("reason", "Not valid id="+id);
		return id;
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public  ResponseEntity<List<Lead>> getAll(
		    HttpServletRequest request, 
		    HttpServletResponse httpResponse) {
		HttpHeaders headers = new HttpHeaders();
		httpResponse.setStatus(HttpStatus.OK.value());
		headers.setContentType(MediaType.TEXT_PLAIN);
		return new ResponseEntity<List<Lead>>(leadService.getAll(),headers,HttpStatus.OK);
	}
	
	@ExceptionHandler(TypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo handleTypeMismatchException(HttpServletRequest req, TypeMismatchException ex) {
		Locale locale = LocaleContextHolder.getLocale();
		String errorMessage = message.getMessage("error.bad.lead.id", null, locale);
		errorMessage += " " + ex.getValue();
		String errorURL = req.getRequestURL().toString();
		// logger.error("Error "+ errorURL+ " "+errorMessage);
		return new ErrorInfo(errorURL, errorMessage);

	}
	
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo leadNotFound(HttpServletRequest req, EmptyResultDataAccessException ex) {
		Locale locale = LocaleContextHolder.getLocale();
		String errorMessage = message.getMessage("error.no.lead.id", null, locale);

		// errorMessage += ex.getLocalizedMessage();
		String errorURL = req.getRequestURL().toString();

		return new ErrorInfo(errorURL, errorMessage);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorParamInfo handleMethodArgumentNotValid(HttpServletRequest req, MethodArgumentNotValidException ex) {

		Locale locale = LocaleContextHolder.getLocale();
		String errorMessage = message.getMessage("error.no.lead.id", null, locale);

		String errorURL = req.getRequestURL().toString();
		ErrorParamInfo errorInfo = new ErrorParamInfo(errorURL, errorMessage);

		BindingResult result = ex.getBindingResult();
		List<FieldError> paramsErrors = result.getFieldErrors();

		errorInfo.getpErrors().addAll(populateFieldErrors(paramsErrors));

		return errorInfo;
	}

	public List<ErrorRequestParams> populateFieldErrors(List<FieldError> pErrorList) {
		List<ErrorRequestParams> pErrors = new ArrayList<>();
		StringBuilder error = new StringBuilder("");

		for (FieldError er : pErrorList) {

			error.append(er.getCode()).append(".");
			error.append(er.getObjectName()).append(".");
			error.append(er.getField());

			Locale locale = LocaleContextHolder.getLocale();
			String errorMessage = message.getMessage(error.toString(), null, locale);

			pErrors.add(new ErrorRequestParams(er.getField(), errorMessage));
			error.delete(0, error.capacity());
		}
		return pErrors;
	}

}
