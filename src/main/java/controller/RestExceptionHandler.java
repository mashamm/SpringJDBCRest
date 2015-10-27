package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import exception.ErrorInfo;
import exception.ErrorParamInfo;
import exception.ErrorRequestParams;
import exception.LeadNotFoundException;

//exception handling on @Controller level advice

@ControllerAdvice
public class RestExceptionHandler {
	@Autowired
	private MessageSource errorSource;
	
	
	

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorParamInfo handleMethodArgumentNotValid(HttpServletRequest req, 
		MethodArgumentNotValidException ex) {
		
		String errorMessage = localizeErrorMessage("error.bad.arguments");
		String errorURL = req.getRequestURL().toString();
		
		ErrorParamInfo errorInfo = new ErrorParamInfo(errorURL, errorMessage);
		
		BindingResult result = ex.getBindingResult();		
		List<FieldError> paramsErrors = result.getFieldErrors();
		
		errorInfo.getpErrors().addAll(populateFieldErrors(paramsErrors));
		
		return errorInfo;
	}

	public List<ErrorRequestParams> populateFieldErrors(List<FieldError> pErrorList) {
		List<ErrorRequestParams> pErrors = new ArrayList<>();
		StringBuilder errorMessage = new StringBuilder("");
		
		for (FieldError er : pErrorList) {
			
			errorMessage.append(er.getCode()).append(".");
			errorMessage.append(er.getObjectName()).append(".");
			errorMessage.append(er.getField());
			
			String localizedErrorMsg = localizeErrorMessage(errorMessage.toString());
			
			pErrors.add(new ErrorRequestParams(er.getField(), localizedErrorMsg));
			errorMessage.delete(0, errorMessage.capacity());
		}
		return pErrors;
	}
	/**
	 * Method returns appropriate localized error message from the {@link MessageSource}.
	 * 
	 * @param errorCode - key of the error message
	 * @return {@link String} localized error message 
	 */
	public String localizeErrorMessage(String errorCode) {
		Locale locale = LocaleContextHolder.getLocale();
		String errorMessage = errorSource.getMessage(errorCode, null, locale);
		return errorMessage;
	}
	
}
