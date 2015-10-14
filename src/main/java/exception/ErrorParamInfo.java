package exception;

import java.util.ArrayList;
import java.util.List;


public class ErrorParamInfo {
	
	private String url;
	private String message;
	private List<ErrorRequestParams> pErrors = new ArrayList<>();
	
	public ErrorParamInfo () {}
	
	public ErrorParamInfo (String url, String message) {
		this.url = url;
		this.message = message;
	}
	
	public ErrorParamInfo (List<ErrorRequestParams> pErrors, String url, String message) {
		this.pErrors = pErrors;
		this.url = url;
		this.message = message;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ErrorRequestParams> getpErrors() {
		return pErrors;
	}

	public void setpErrors(List<ErrorRequestParams> pErrors) {
		this.pErrors = pErrors;
	}


	

}
