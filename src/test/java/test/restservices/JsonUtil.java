package test.restservices;

import java.io.IOException;
import test.restservices.ResultMessage;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public  class JsonUtil {

	public static <lead> lead readObject(String jsonData, Class<lead> clazz) {
		lead res = null;
		try {
			res = new ObjectMapper().readValue(jsonData, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return res;
	}

	public static <lead> lead readObject(InputStream is, Class<lead> clazz) {
		lead res = null;
		try {
			res = new ObjectMapper().readValue(is, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return res;
	}

	public static String toJson(Object res) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		prepareMapperForOutput(mapper);
		return mapper.writeValueAsString(res);
	}

	private static void prepareMapperForOutput(ObjectMapper mapper) {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	public static ResponseEntity<String> toJsonResponse(HttpStatus httpStatus, Object res) {
		String json;
		try {
			json = toJson(res);
		} catch (JsonProcessingException e) {
			// LOG.error("Exception happen during convert object " + res + " to
			// json.", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
		headers.setContentType(mediaType);
		return new ResponseEntity<>(json, headers, httpStatus);
	}

	public static ResponseEntity<String> toJsonResponse(Object res) {
		return toJsonResponse(HttpStatus.OK, res);
	}

	public static ResponseEntity<String> toJsonResponse(HttpStatus httpStatus, ResultMessage resultMessage) {
		String json;
		try {
			json = toJson(resultMessage);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		HttpHeaders headers = new HttpHeaders();

		MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
		headers.setContentType(mediaType);
		return new ResponseEntity<>(json, headers, httpStatus);
	}

}
