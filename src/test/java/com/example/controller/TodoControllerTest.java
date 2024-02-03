package com.example.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.model.TodoEntity;
import com.example.model.TodoRequest;
import com.example.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TodoControllerTest.class)
class TodoControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	TodoService todoService;

	private TodoEntity expected;

	@BeforeEach
	void setup(){
		// 테스트 전에 사용할 예상 TodoEntity 객체 초기화
		this.expected = new TodoEntity();
		this.expected.setId(123L);
		this.expected.setTitle("TEST TITLE");
		this.expected.setOrder(0L);
		this.expected.setCompleted(false);
	}

	@Test
	void create() throws Exception{
		// Mock 객체를 사용하여 todoService.add 호출 시 예상된 TodoEntity를 반환하도록 설정
		when(this.todoService.add(any(TodoRequest.class))).then((i) -> {
			TodoRequest request = i.getArgument(0, TodoRequest.class);
			return new TodoEntity(this.expected.getId(), request.getTitle(), this.expected.getOrder(),
				this.expected.getCompleted());
		});

		// 테스트를 위한 TodoRequest 객체 생성 및 JSON 형식으로 변환
		TodoRequest request = new TodoRequest();
		request.setTitle("ANY TITLE");

		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);

		// POST 요청 수행 및 응답 확인
		this.mvc.perform(post("/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title").value("ANY TITLE"));
	}

	@Test
	void readOne() throws Exception {
		// Mock 객체를 사용하여 todoService.searchById 호출 시 예상된 TodoEntity를 반환하도록 설정
		given(todoService.searchById(123L)).willReturn(expected);

		// GET 요청 수행 및 응답 확인
		mvc.perform(get("/123"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(expected.getId()))
			.andExpect(jsonPath("$.title").value(expected.getTitle()))
			.andExpect(jsonPath("$.order").value(expected.getOrder()))
			.andExpect(jsonPath("$.completed").value(expected.getCompleted()));
	}
}