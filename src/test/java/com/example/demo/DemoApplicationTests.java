package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void loginProtectedRouteAndLogoutFlowWorks() throws Exception {
		String loginResponse = mockMvc.perform(post("/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "username": "admin",
								  "password": "1234"
								}
								"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").exists())
				.andReturn()
				.getResponse()
				.getContentAsString();

		String token = loginResponse.split("\"token\":\"")[1].split("\"")[0];

		mockMvc.perform(get("/auth/protected")
						.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", containsString("admin")));

		mockMvc.perform(post("/auth/logout")
						.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Token invalidated successfully")));

		mockMvc.perform(get("/auth/protected")
						.header("Authorization", "Bearer " + token))
				.andExpect(status().isUnauthorized())
				.andExpect(content().string(containsString("Token has been invalidated")));
	}

}
