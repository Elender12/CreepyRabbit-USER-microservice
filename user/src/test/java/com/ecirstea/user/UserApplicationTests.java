package com.ecirstea.user;

import com.ecirstea.user.exception.UserException;
import com.ecirstea.user.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest
@Testcontainers
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Verify UserApiIntegrationTest")
class UserApplicationTests {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext webAppContext;

	@Container
	private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10");

	private MockMvc mockMvc;

	private static User user = new User();

	private final String userToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbGVuYSIsInBhc3N3b3JkIjoiJDJhJDEwJHdaZk03U3RmeURyZVo4a3NhYllaNWVrd0pPTVF4S0l0U0NOS2tuOUJILzFyZkZTSzhsZGp5IiwiaWF0IjoxNjMwNDAwMzkyLCJleHAiOjE2MzEyNjQzOTJ9.TP4a8b42_FsUdmauEhiRq3xegZAEcMnQD3cFmaLQfbOAAPkFY953y4OMrqvWt7DStA5pto5gl5Q3HfIrI_XovQ";

	@DynamicPropertySource
	static void setProperties( DynamicPropertyRegistry registry )
	{
		registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
		registry.add("spring.data.mongodb.user", () -> "");
		registry.add("spring.data.mongodb.pass", () -> "");
		registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);
	}



	@BeforeEach
	public void setUp()
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}

/*	@Test
	void contextLoads() {
	}*/

	@Test
	@Order(1)
	@DisplayName("Save a user")
	void save() throws Exception
	{
		//Save it only using all mandatory fields and check the optional fields have the default value:
		user.setUsername("username");
		user.setPassword("password");
		user.setEmail("email@example.com");
		user.setName("elena");

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders
						.post("/users")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user))
						.characterEncoding(String.valueOf(StandardCharsets.UTF_8))
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.getUsername()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.password").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(user.getName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.created").exists());
		user = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), User.class);
		System.out.println("SAVED: " + user.toString());
	}

	@Test
	@Order(2)
	@DisplayName("Update a user")
	void edit() throws Exception
	{
		//Modify all optional fields and check they are updated;

		user.setUsername("username2");
		user.setPassword("password2");
		user.setEmail("email2@example.com");

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders
						.put("/users")
						.header("authorization", userToken)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user))
						.characterEncoding(String.valueOf(StandardCharsets.UTF_8))
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.getUsername()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.password").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(user.getName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.created").exists());

		user = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), User.class);
		System.out.println("UPDATED: " + user.toString());
	}
	@Test
	@Order(3)
	@DisplayName("Find user by id")
	void findById() throws Exception
	{
		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders
						.get("/users/{id}", user.getId())
						.header("authorization", userToken)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding(String.valueOf(StandardCharsets.UTF_8))
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId().toString()));

		user = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), User.class);
		System.out.println("GET BY ID: " + user.toString());
	}
	@Test
	@Order(7)
	@DisplayName("Find all user")
	void findAll() throws Exception
	{
		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders
						.get("/users")
						.header("authorization", userToken)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding(String.valueOf(StandardCharsets.UTF_8))
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

		List<User> list = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(),
				objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
		System.out.println("GET ALL (SIZE): " + list.size());
	}



	@Test
	@Order(99)
	@DisplayName("Delete a user")
	void delete() throws Exception
	{
		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders
						.delete("/users/{id}", user.getId())
						.header("authorization", userToken)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding(String.valueOf(StandardCharsets.UTF_8))
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId().toString()));

		user = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), User.class);
		System.out.println("DELETED: " + user.toString());
	}
	@Test
	@Order(101)
	@DisplayName("Get a user by id and throw not found")
	void findById_throwNotFoundEx() throws Exception
	{
		ResultActions result = this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/users/{id}", user.getId())
						.accept(MediaType.APPLICATION_JSON)
						.header("authorization", userToken)
						.characterEncoding(String.valueOf(StandardCharsets.UTF_8))
				)
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(res -> Assertions.assertTrue(res.getResolvedException() instanceof UserException));

		UserException notFoundException = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), UserException.class);
		System.out.println("GET BY ID (Not found): " + notFoundException.getMessage());
	}
}
