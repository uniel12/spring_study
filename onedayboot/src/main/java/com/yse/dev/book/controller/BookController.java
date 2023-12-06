package com.yse.dev.book.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yse.dev.book.dto.BookCreateDTO;
import com.yse.dev.book.dto.BookEditDTO;
import com.yse.dev.book.dto.BookEditResponseDTO;
import com.yse.dev.book.dto.BookListResponseDTO;
import com.yse.dev.book.dto.BookReadResponseDTO;
import com.yse.dev.book.service.BookService;

@Controller
public class BookController {

	@Autowired // 서비스 클래스를 사용하기 위한 의존성 주입
	private BookService bookService;

	@GetMapping("/book/create")
	public String create() {
		return "book/create";
	}

	@PostMapping("/book/create") // Post는 데이터를 생성할 때 사용하는 메소드
	public String insert(BookCreateDTO bookCreateDTO) {
		Integer bookId = this.bookService.insert(bookCreateDTO);
		return String.format("redirect:/book/read/%s", bookId);
		// String.format() 메서드 : 지정된 형식으로 문자열을 생성하는 Java 메서드
		// return "redirect:/book/read/"+bookId;

	}

	@GetMapping("/book/read/{bookId}")
	public ModelAndView read(@PathVariable Integer bookId) {
		ModelAndView mav = new ModelAndView();

		try {
			BookReadResponseDTO bookReadResponseDTO = this.bookService.read(bookId);
			mav.addObject("bookReadResponseDTO", bookReadResponseDTO);
			mav.setViewName("book/read");

		} catch (NoSuchElementException ex) {
			mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
			mav.addObject("message", "책 정보가 없습니다.");
			mav.addObject("location", "/book");
			mav.setViewName("common/error/422");
		}

		return mav;
	}

//	// NoSuchElementException 오류가 날경우 자동으로 메소드 실행
//	@ExceptionHandler(NoSuchElementException.class)
//	public ModelAndView noSuchElementExceptionHandler(NoSuchElementException ex) {
//		ModelAndView mav = new ModelAndView();
//		mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
//		mav.addObject("message", "책 정보가 없습니다.");
//		mav.addObject("location", "/book/list");
//		mav.setViewName("common/error/422");
//		return mav;
//	}
	@ExceptionHandler(NoSuchElementException.class)
	public ModelAndView noSuchElementExceptionHandler(NoSuchElementException ex) {
		return this.error422("책 정보가 없습니다.", "/book/list");
	}

	@GetMapping("/book/edit/{bookId}")
	public ModelAndView edit(@PathVariable Integer bookId) throws NoSuchElementException {
		ModelAndView mav = new ModelAndView();
		BookEditResponseDTO bookEditResponseDTO = this.bookService.edit(bookId);
		mav.addObject("bookEditResponseDTO", bookEditResponseDTO);
		mav.setViewName("book/edit");
		return mav;
	}

	private ModelAndView error422(String message, String location) {
		// 유효성 검사가 실패한 경우
		ModelAndView mav = new ModelAndView();
		mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
		mav.addObject("message", message);
		mav.addObject("location", location);
		mav.setViewName("common/error/422");
		return mav;
	}

	@PostMapping("/book/edit/{bookId}")
	public ModelAndView update(@Validated BookEditDTO bookEditDTO, Errors errors) {
		// @Validated는 해당객체가 유효성 검사를 받아야함을 나타냄
		// 검사결과는 Errors 객체에 저장됨
		if (errors.hasErrors()) {
			// 검사결과 유효성 검사 에러가 존재한다면 
			// .hasErrors() 오류가 있는지 확인하는 메서드
			// .getFieldErrors() 필드 오류 목록 반환하는 메서드
			String errorMessage = errors.getFieldErrors()
					.stream()
					.map(x -> x.getField() + " : " + x.getDefaultMessage())
					.collect(Collectors.joining("\n"));

			return this.error422(
					errorMessage,
					String.format("/book/edit/%s", bookEditDTO.getBookId()));
		}

		this.bookService.update(bookEditDTO);

		ModelAndView mav = new ModelAndView();
		mav.setViewName(String.format("redirect:/book/read/%s", bookEditDTO.getBookId()));
		return mav;
	}
	
	@PostMapping("/book/delete")  
	public String delete(Integer bookId) throws NoSuchElementException{  
	    this.bookService.delete(bookId); 
	    return "redirect:/book/list";  
	}  
	
	@GetMapping(value= {"/book/list", "/book"})  
	public ModelAndView bookList(String title, Integer page, ModelAndView mav){  
	    mav.setViewName("/book/list");  

	    List<BookListResponseDTO> books = this.bookService.bookList(title, page);         
	    mav.addObject("books", books);        
	    return mav;  
	}  


}
