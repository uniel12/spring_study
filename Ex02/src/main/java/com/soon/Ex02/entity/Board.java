package com.soon.Ex02.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Board {
	
	@Id // primary 키 지정
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	// @GeneratedValue - 시퀀스를 지정 , IDENTITY - 이 컬럼만의 고유한 시퀀스 생성
	private Integer boardNum;
	@Column(length = 200)
	private String boardTitle;
	
	private String boardCon;
	
	private LocalDateTime boardDate;
	
	// mappedBy - 참조하는 엔티티의 속성명을 알려줌
	@OneToMany(mappedBy="board", cascade = CascadeType.REMOVE)
	private List<Reple> repleList;
	
}
