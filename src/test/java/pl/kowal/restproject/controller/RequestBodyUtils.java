package pl.kowal.restproject.controller;

class RequestBodyUtils {

	static String prepareRequestBody(String jsonFields) {
		return "{"+jsonFields+"}";
	}
}
