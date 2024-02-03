package com.example.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.example.model.TodoEntity;
import com.example.model.TodoRequest;
import com.example.repository.TodoRepository;

@ExtendWith(MockitoExtension.class) // JUnit 5에서 Mockito를 사용
	// Mockito와 JUnit 5 간의 상호 작용을 원활하게 만들어주는 역할을 합니다. MockitoExtension은 Mockito 기능을 JUnit 5에 연결하여 Mock 객체를 초기화하고 관리하는 데 도움을 준다.
	// Mockito 테스트 확장을 활성화하며, Mockito의 기능을 JUnit 5에 통합시키는 역할을 한다.
class TodoServiceTest {

	@Mock // Mock 객체 생성
	// 사용하는 이유: 외부 시스템에 의존하지 않고 자체 테스트를 실행하기 위해서
	// 유닛 테스트는 네트워크에 연결되지 않아도 실행되어야 하므로 외부 의존성을 Mock 객체로 대체하여 테스트 가능하게 함
	// 또한, 실제 DB에 연결하지 않고도 Mock을 통해 테스트를 수행할 수 있음
	private TodoRepository todoRepository;

	@InjectMocks // Mock 객체를 주입받아서 사용하는 객체에 주입
	private TodoService todoService;
	// 사용하는 이유: Mock 객체를 주입받아 TodoService의 의존성을 주입하는 어노테이션
	// Mock 객체가 자동으로 주입되어 테스트 대상인 TodoService가 실제 TodoRepository의 Mock을 사용하도록 함


	@Test
	void add() {
		// Mock 객체를 사용하여 todoRepository.save(any(TodoEntity.class)) 호출 시 첫 번째 인자를 반환하도록 설정
		when(this.todoRepository.save(any(TodoEntity.class))).then(AdditionalAnswers.returnsFirstArg());

		// 테스트를 위한 TodoRequest 객체 생성
		TodoRequest expected = new TodoRequest();
		expected.setTitle("Test Title");

		// TodoService의 add 메서드 호출
		TodoEntity actual = this.todoService.add(expected);

		// 반환된 값과 예상값을 비교하여 테스트 수행
		assertEquals(expected.getTitle(), actual.getTitle()); // 제목 비교
	}

	@Test
	void searchById() {
		// 테스트용 TodoEntity 객체 생성
		TodoEntity entity = new TodoEntity();
		entity.setId(123L);
		entity.setTitle("TITLE");
		entity.setOrder(0L);
		entity.setCompleted(false);

		// 생성된 TodoEntity를 Optional로 감싸기
		Optional<TodoEntity> optional = Optional.of(entity);

		// Mock 객체를 사용하여 todoRepository.findById(anyLong()) 호출 시 위에서 생성한 Optional 반환하도록 설정
		given(this.todoRepository.findById(anyLong())).willReturn(optional);

		// TodoService의 searchById 메서드 호출
		TodoEntity actual = this.todoService.searchById(123L);

		// 예상 결과값 설정
		TodoEntity expected = optional.get();

		// 반환된 값과 예상값을 비교하여 테스트 수행
		assertEquals(expected.getId(), actual.getId()); // ID 비교
		assertEquals(expected.getTitle(), actual.getTitle()); // 제목 비교
		assertEquals(expected.getOrder(), actual.getOrder()); // 순서 비교
		assertEquals(expected.getCompleted(), actual.getCompleted()); // 완료 여부 비교
	}

	// 에러가 잘 발생하는지에 대한 테스트
	@Test
	public void searchByIdFailed(){
		// Mock 객체를 사용하여 todoRepository.findById(anyLong()) 호출 시 Optional.empty()를 반환하도록 설정
		given(this.todoRepository.findById(anyLong())).willReturn(Optional.empty());

		// searchById 메서드 호출 시 예외 발생을 확인하는 테스트
		assertThrows(ResponseStatusException.class, () -> {
			this.todoService.searchById(123L);
		});
	}
}