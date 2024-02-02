package com.example.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.TodoEntity;
import com.example.model.TodoRequest;
import com.example.model.TodoResponse;
import com.example.service.TodoService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/")

public class TodoController {

	private final TodoService todoService;

	@PostMapping
	public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request) {
		log.info("CREATE");

		if (ObjectUtils.isEmpty(request.getTitle()))
			return ResponseEntity.badRequest().build();

		if (ObjectUtils.isEmpty(request.getOrder()))
			request.setOrder(0L);

		if (ObjectUtils.isEmpty(request.getCompleted()))
			request.setCompleted(false);

		TodoEntity result = this.todoService.add(request);
		return ResponseEntity.ok(new TodoResponse(result));
	}

	@GetMapping
	public ResponseEntity<List<TodoResponse>> readAll() {
		log.info("READ ALL");
		List<TodoEntity> result = this.todoService.searchAll();
		List<TodoResponse> response = result.stream().map(TodoResponse::new).collect(Collectors.toList());
		return ResponseEntity.ok(response);
	}

	@GetMapping("{id}")
	public ResponseEntity<TodoResponse> readOne(@PathVariable Long id) {
		log.info("READ");
		TodoEntity result = this.todoService.searchById(id);
		return ResponseEntity.ok(new TodoResponse(result));
	}

	@PatchMapping("{id}")
	public ResponseEntity<TodoResponse> update(@PathVariable Long id, @RequestBody TodoRequest request) {
		log.info("UPDATE");
		TodoEntity result = this.todoService.updateById(id, request);
		return ResponseEntity.ok(new TodoResponse(result));
	}

	@DeleteMapping
	public ResponseEntity<?> deleteAll() {
		log.info("DELETE ALL");
		this.todoService.deleteAll();
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteOne(@PathVariable Long id) {
		log.info("DELETE");
		this.todoService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
