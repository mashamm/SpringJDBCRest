package exception;

public class ErrorRequestParams {

	private String paramName;
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamError() {
		return paramError;
	}

	public void setParamError(String paramError) {
		this.paramError = paramError;
	}

	private String paramError;
	
	public ErrorRequestParams(String paramName, String paramError) {
		this.paramName = paramName;
		this.paramError = paramError;
	}

	

}
