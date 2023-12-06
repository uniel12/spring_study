package hello.core.member;

import java.util.HashMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component // memoryMemberRepository
public class MemoryMemberRepository implements MemberRepository {
	
	// 저장소 만들기
	// HashMap은 매우 효율적인 키-값 저장 및 검색을 제공하는 데이터 구조
	// 데이터 베이스가 확정이 안돼서 임시적으로 메모리 회원 저장소 구현 해놓은 것
	// 동시성 이슈 떄문에 실무에서는 ConcurrentHashMap<>()을 사용
	private static Map<Long, Member> store = new ConcurrentHashMap<>();
	
	@Override
	public void save(Member member) {
		store.put(member.getId(), member);

	}

	@Override
	public Member findById(Long memberId) {
		
		return store.get(memberId);
	}

}
