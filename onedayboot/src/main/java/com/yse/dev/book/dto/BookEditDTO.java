package com.yse.dev.book.dto;

import com.yse.dev.book.entity.Book;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookEditDTO {

	@Nonnull
	@Positive // 해당 필드의 값이 양수 아니면 유효성 검사 실패
	private Integer bookId;

	@Nonnull
	@NotBlank // 공백이 아닌 문자를 포함하게 함, 공백시 유효성 검사 실패 
	private String title; 

	@Nonnull
	@Min(1000) // 최솟값 지정, 그 이하면 유효성 검사 실패
	private Integer price;

	public Book fill(Book book) {
		book.setTitle(this.title);
		book.setPrice(this.price);
		return book;
	}
}
