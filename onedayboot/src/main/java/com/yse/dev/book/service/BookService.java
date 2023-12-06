package com.yse.dev.book.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.yse.dev.book.dto.BookCreateDTO;
import com.yse.dev.book.dto.BookEditDTO;
import com.yse.dev.book.dto.BookEditResponseDTO;
import com.yse.dev.book.dto.BookListResponseDTO;
import com.yse.dev.book.dto.BookReadResponseDTO;
import com.yse.dev.book.entity.Book;
import com.yse.dev.book.entity.BookRepository;




@Service // 스프링컨테이너에 빈으로 등록하고 의존성 주입받을 수 있게 하는 것
public class BookService {

	private BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	// 책 입력 서비스
	public Integer insert(BookCreateDTO bookCreateDTO) {
		Book book = Book.builder() // 롬복의 빌더 패턴을 사용해 Book 객체 생성
				.title(bookCreateDTO.getTitle()).price(bookCreateDTO.getPrice()).build();
		this.bookRepository.save(book); // save 메서드로 객체를 데이터 베이스에 저장
		return book.getBookId();
	}
	

	public BookReadResponseDTO read(Integer bookId) throws NoSuchElementException {
		Book book = this.bookRepository.findById(bookId).orElseThrow();
		// orElseThrow() =>
		// Optional이 값이 존재하는 경우, 해당 값을 반환합니다.
		// Optional이 비어 있는 경우, NoSuchElementException 예외를 발생시킵니다.
		BookReadResponseDTO bookReadResponseDTO = new BookReadResponseDTO();
		bookReadResponseDTO.fromBook(book);
		return bookReadResponseDTO;
	}

	public BookEditResponseDTO edit(Integer bookId) throws NoSuchElementException {
		Book book = this.bookRepository.findById(bookId).orElseThrow();
		return BookEditResponseDTO.BookFactory(book);
	}

	// 책 정보 수정 서비스
	public void update(BookEditDTO bookEditDTO) throws NoSuchElementException {
		Book book = this.bookRepository.findById(bookEditDTO.getBookId()).orElseThrow();
		book = bookEditDTO.fill(book);
		this.bookRepository.save(book);
	}
	
	// 책 정보 삭제 서비스
	public void delete(Integer bookId) throws NoSuchElementException {  
	    Book book = this.bookRepository.findById(bookId).orElseThrow();  
	    this.bookRepository.delete(book);  
	}
	
	// 책 목록 메소드
	public List<BookListResponseDTO> bookList(String title, Integer page){  
	    final int pageSize = 3;  

	    List<Book> books;  

	    if (page == null) {  
	        page = 0;  
	    }else {  
	        page -= 1;  
	    }  

	    if (title == null) {  
	        Pageable pageable = PageRequest.of(page, pageSize, Direction.DESC, "insertDateTime");  
	        books = this.bookRepository.findAll(pageable).toList();  
	    }  
	    else {  
	        Pageable pageable = PageRequest.of(page, pageSize);  
	        Sort sort = Sort.by(Order.desc("insertDateTime"));  
	        pageable.getSort().and(sort);  
	        books = this.bookRepository.findByTitleContains(title, pageable);  
	    }  

	    return books.stream().map(book ->  
	        new BookListResponseDTO(book.getBookId(), book.getTitle())  
	    ).collect(Collectors.toList());  
	}  
	


}
