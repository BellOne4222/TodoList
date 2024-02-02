package com.example.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.model.TodoEntity;
import com.example.model.TodoRequest;
import com.example.repository.TodoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor


public class TodoService {

	private final TodoRepository todoRepository;


	// 1. todo 리스트 목록에 아이템을 추가
	// request를 받아서 Entity를 반환하는 메서드
	public TodoEntity add(TodoRequest request){
		TodoEntity todoEntity = new TodoEntity();
		todoEntity.setTitle(request.getTitle());
		todoEntity.setOrder(request.getOrder());
		todoEntity.setCompleted(request.getCompleted());

		return this.todoRepository.save(todoEntity);
	}

	// 2. todo 리스트 목록 중 특정 아이템을 조회
	// 아이템을 조회하는 메서드
	// 조회된 아이템을 반환하고 id기준으로 조회
	public TodoEntity searchById(Long id){
		return this.todoRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 404
	}

	// 3. todo 리스트 전체 목록을 조회
	// 리스트의 전체 목록을 조회 하는 메서드
	// 전체 조회이므로 id는 필요없다.
	public List<TodoEntity> searchAll(){
		return this.todoRepository.findAll();
	}

	// 4. todo 리스트 목록 중 특정 아이템을 수정
	// 수정할 아이템의 id값을 파라미터로 받아서 request 값으로 수정하는 메서드
	public TodoEntity updateById(Long id, TodoRequest request){
		TodoEntity todoEntity = this.searchById(id);
		if (request.getTitle() != null){
			todoEntity.setTitle(request.getTitle());
		}
		if (request.getOrder() != null){
			todoEntity.setOrder(request.getOrder());
		}
		if (request.getCompleted() != null){
			todoEntity.setCompleted(request.getCompleted());
		}
		return this.todoRepository.save(todoEntity);
	}

	// 5. todo 리스트 목록 중 특정 아이템을 삭제
	// id로 삭제
	public void deleteById(Long id){
		this.todoRepository.deleteById(id);
	}

	// 6. todo 리스트 전체 목록을 삭제
	// 전체 삭제
	public void deleteAll(){
		this.todoRepository.deleteAll();
	}


}
