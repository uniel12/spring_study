package com.yse.dev.book.dto;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCreateDTO {

	@Nonnull
	private String title;
	@Nonnull
	private Integer price;
}
 