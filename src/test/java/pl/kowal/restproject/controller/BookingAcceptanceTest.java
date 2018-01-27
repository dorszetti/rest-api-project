package pl.kowal.restproject.controller;


import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
public class BookingAcceptanceTest {

	private static final Long CLIENT_ID = 1L;
	private static final String EXPECTED_ROOM = "\"id\":6";
	
	@Inject
	private MockMvc mockMvc;

	@Test
	public void registerAvailableRoom() throws Exception {

		//Search for available rooms using given criteria and assure a room with id = 6 (pre-configured via db) has been found
		RequestBuilder searchForRooms = MockMvcRequestBuilders.get("/room?startDate=2017-08-01&endDate=2017-08-03&minPrice=100.00&maxPrice=190.00&city=Szczecin").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(searchForRooms).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(EXPECTED_ROOM)));

		//Make a booking for the room
		String bookingBody = "{\"checkIn\":\"2017-08-01\",\"checkOut\":\"2017-08-03\",\"roomId\":\"6\"}";
		RequestBuilder bookIt = MockMvcRequestBuilders.post("/booking/client/" + CLIENT_ID).contentType(MediaType.APPLICATION_JSON).content(bookingBody);
		MvcResult bookingResult = mockMvc.perform(bookIt).andDo(print()).andExpect(status().isOk()).andReturn();
		JSONObject bookingResponseBody =  new JSONObject(bookingResult.getResponse().getContentAsString());
		
		//Search with the same criteria and assure that room with id = 6 is not on the list anymore
		mockMvc.perform(searchForRooms).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string((not(containsString(EXPECTED_ROOM)))));
		
		//Cancel the booking we created earlier
		RequestBuilder cancelBooking = MockMvcRequestBuilders.delete("/booking/" + bookingResponseBody.get("id")).accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(cancelBooking).andDo(print()).andExpect(status().isOk()).andReturn();

		//Room should be visible on the list of available ones once again
		mockMvc.perform(searchForRooms).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString(EXPECTED_ROOM)));
	}
	
	@Test
	public void tryToBookiTwice() throws Exception {

		//Search for available rooms using given criteria and assure a room with id = 6 (pre-configured via db) has been found
		RequestBuilder searchForRooms = MockMvcRequestBuilders.get("/room?startDate=2017-08-01&endDate=2017-08-03&minPrice=100.00&maxPrice=190.00&city=Szczecin").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(searchForRooms).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(EXPECTED_ROOM)));

		//Make a booking for the room
		String bookingBody = "{\"checkIn\":\"2017-08-01\",\"checkOut\":\"2017-08-03\",\"roomId\":\"6\"}";
		RequestBuilder bookIt = MockMvcRequestBuilders.post("/booking/client/" + CLIENT_ID).contentType(MediaType.APPLICATION_JSON).content(bookingBody);
		MvcResult bookingResult = mockMvc.perform(bookIt).andDo(print()).andExpect(status().isOk()).andReturn();
		JSONObject bookingResponseBody =  new JSONObject(bookingResult.getResponse().getContentAsString());
		
		//Second booking should return a CONFLICT response
		mockMvc.perform(bookIt).andDo(print()).andExpect(status().isConflict()).andReturn();
		
		//Cleanup by canceling the booking
		RequestBuilder cancelBooking = MockMvcRequestBuilders.delete("/booking/" + bookingResponseBody.get("id")).accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(cancelBooking).andDo(print()).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void tryToBookForUnavailableRoom() throws Exception {

		//Make a booking for an unavailable room - should return a CONFLICT response
		String bookingBody = "{\"checkIn\":\"2010-08-01\",\"checkOut\":\"2010-08-03\",\"roomId\":\"6\"}";
		RequestBuilder bookIt = MockMvcRequestBuilders.post("/booking/client/" + CLIENT_ID).contentType(MediaType.APPLICATION_JSON).content(bookingBody);
		mockMvc.perform(bookIt).andDo(print()).andExpect(status().isConflict()).andReturn();
	}
	
	@Test
	public void tryToBookForWithWrongDateOrder() throws Exception {

		//Make a booking for with checkIn after checkOut - should return a BAD REQUEST response
		String bookingBody = "{\"checkIn\":\"2017-08-07\",\"checkOut\":\"2017-08-05\",\"roomId\":\"6\"}";
		RequestBuilder bookIt = MockMvcRequestBuilders.post("/booking/client/" + CLIENT_ID).contentType(MediaType.APPLICATION_JSON).content(bookingBody);
		mockMvc.perform(bookIt).andDo(print()).andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	public void tryToCancelForWithWrongBookingId() throws Exception {

		//Cancel a booking which does not exist - should return a NOT FOUND response
		RequestBuilder cancelBooking = MockMvcRequestBuilders.delete("/booking/999").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(cancelBooking).andDo(print()).andExpect(status().isNotFound()).andReturn();
	}

}
