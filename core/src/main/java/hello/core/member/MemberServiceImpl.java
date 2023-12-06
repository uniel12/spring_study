package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 인터페이스 구현체가 하나일 때 관례적으로 Impl 붙임
@Component
public class MemberServiceImpl implements MemberService {

	// MemberRepository 인터페이스만 의존 MemoryMemberRepository 를 의존하지 않는다!
	private final MemberRepository memberRepository;

	// 생성자를 통해서 멤버 레파지토리의 구현체가 뭐가 들어갈 지
	// MemberServiceImpl 의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부( AppConfig )에서 결정
	// DI(Dependency Injection) 우리말로 의존관계 주입 또는 의존성 주입
	
	@Autowired //ac.getBean(MemberRepository.class)
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public void join(Member member) {
		// 다형성에 의해 MemoryMemberRepository 안에 있는 save 호출
		memberRepository.save(member);
	}

	@Override
	public Member findMember(Long memberId) {
		return memberRepository.findById(memberId);
	}

	// 테스트 용도
	public MemberRepository getMemberRepository() {
		return memberRepository;
	}
}
