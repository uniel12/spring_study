package com.yse.dev.book.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // JPA의 어노테이션으로 데이터베이스의 엔터티이다! 데이터베이스 레코드와 매핑됨
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 자동으로 빌더 클래스를 생성하고 해당 클래스의 모든 필드를 설정하는 메서드를 가지고 있다.
// 빌더 클래스르 사용하여 해당 클래스이 객체를 생성할 수 있고 필드값을 설정하고 
// 객체를 생성하는 코드를 간단하게 작성할 수 있게 한다. 
public class Book {
	@Id // primary key를 지정하는데 사용
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 데이터베이스가 자동으로 기본 키 값을 생성하도록 지정
	private Integer bookId;

	@Column(length = 200) // 엔티티 클래스의 필드와 데이터베이스 테이블의 열 간의 매핑을 제어
	private String title;

	private Integer price;

	@CreationTimestamp // 현재 시간 자동으로 할당
	private LocalDateTime insertDateTime;
	
	// @OneToMany  일대다 관계를 나타냄
	// mappedBy 속성 : 양방향관계
	//  "bookLogList" 필드가 Book 엔티티의 일대다 관계의 주인
	// fetch 속성: 엔터티의 데이터를 어떻게 가져올지를 정의
	// FetchType.LAZY: 지연 로딩 (성능 최적를 위해 사용) // FetchType.EAGER 즉시로딩
	
	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY) 
	@Builder.Default
	private List<BookLog> bookLogList = new ArrayList();
}
