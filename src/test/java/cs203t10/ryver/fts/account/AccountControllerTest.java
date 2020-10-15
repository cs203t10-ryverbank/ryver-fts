package cs203t10.ryver.fts.account;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(roles = { "ANALYST", "MANAGER" })
	public void getAccountsForbidden() throws Exception {
		mockMvc.perform(get("/accounts")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(roles = { "USER", "ANALYST" })
	public void createAccountForbidden() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		Account account = Account.builder().id(1).customerId(1).balance(1000.0)
				.availableBalance(1000.0).build();
		mockMvc.perform(post("/accounts").contentType("application/json")
				.content(objectMapper.writeValueAsString(account)))
				.andExpect(status().isForbidden());
	}

}