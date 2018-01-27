package pl.kowal.restproject.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.kowal.restproject.controller.RequestBodyUtils.prepareRequestBody;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import pl.kowal.restproject.Application;

/*
 * Acceptance test with MockMVC and full context on (including services and db)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  classes = Application.class)
@AutoConfigureMockMvc
public class ClientAcceptanceTest {

	private static final String NEW_CLIENT_DATA = "\"firstName\":\"Igor\",\"lastName\":\"Orka\"";
	private static final String EXISTING_CLIENT_DATA = "\"firstName\":\"Robert\",\"lastName\":\"Robson\"";
	
	@Inject
	private MockMvc mockMvc;

	@Test
	public void listAllClients() throws Exception {

		//calling the list of clients to check if pre-added user is listed
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/client").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(EXISTING_CLIENT_DATA)));
	}

	@Test
	public void createClient() throws Exception {
		//creating a new client
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/client").contentType(MediaType.APPLICATION_JSON)
			       .content(prepareRequestBody(NEW_CLIENT_DATA))
			       .accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());

		//calling the list of clients to check if the new client is listed
		RequestBuilder verifyRequestBuilder = MockMvcRequestBuilders.get("/client").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(verifyRequestBuilder).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(NEW_CLIENT_DATA)));
	}

}
