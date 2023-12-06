package com.soon.Ex02;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soon.Ex02.entity.Board;

// JpaRepository를 상속받는 레파지토리 - DB와 연동할 수 있음
// <대상이 되는 엔터티의 타입, 해당 엔터티의 PK타입>
public interface BoardRepository extends JpaRepository<Board, Integer> {
	
}
