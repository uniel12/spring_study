package hello.core.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;

@Component
// @RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	// private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
	// private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
	// 인터페이스만 의존하게 DIP 지키기!
	// @Autowired
	// final 생성자에서 혹시라도 값이 설정되지 않는 오류를 컴파일 시점에 막아준다
	private final MemberRepository memberRepository; 
	// @Autowired
	private  final DiscountPolicy discountPolicy;
	// 필드 주입
	// 코드가 간결해서 많은 개발자들을 유혹하지만 외부에서 변경이 불가능해서 테스트 하기 힘들다는 치명적인 단점이 있다.
	// DI 프레임워크가 없으면 아무것도 할 수 없다.
	// 사용하지 말자!
	
	//필드 명 매칭은 먼저 타입 매칭을 시도 하고 그 결과에 여러 빈이 있을 때 추가로 동작하는 기능
	
//	Autowired 매칭 정리
//	1. 타입 매칭
//	2. 타입 매칭의 결과가 2개 이상일 때 필드 명, 파라미터 명으로 빈 이름 매칭

	@Autowired
	public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {//@Qualifier("mainDiscountPolicy")
		System.out.println("1. OrderServiceImpl.OrderServiceImpl");
		this.memberRepository = memberRepository;
		this.discountPolicy = discountPolicy;
		// 생성자 주입
		// 생성자 호출시점에 딱 1번만 호출되는 것이 보장된다.
		// 불변, 필수 의존관계에 사용
		// 중요! "생성자가 딱 1개만 있으면" @Autowired를 생략해도 자동 주입 된다. 물론 스프링 빈에만 해당
	}

//	@Autowired
//	public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//		this.memberRepository = memberRepository;
//		this.discountPolicy = discountPolicy;
//		// 일반 메서드 주입
//		// 한번에 여러 필드를 주입받을 수 있다.
//		// 일반적으로 잘 사용하지 않는다.
//	}

//	@Autowired
//	public void setMemberRepository(MemberRepository memberRepository) {
//		System.out.println("memberRepository = " + memberRepository);
//		this.memberRepository = memberRepository;
//		// 수정자 주입(Setter 주입)
//		// 선택, 변경 가능성이 있는 의존관계에 사용, 선택적으로 하려면  @Autowired(required = false)
//		// 자바빈 프로퍼티 규약의 수정자 메서드 방식을 사용하는 방법
//	}
//
//	@Autowired
//	public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//		System.out.println("discountPolicy = " + discountPolicy);
//		this.discountPolicy = discountPolicy;
//	}

	// 문제점!! 추상(인터페이스) 뿐만 아니라 구체(구현) 클래스에도 의존 => DIP 위반
	// 중요!: 그래서 FixDiscountPolicy 를 RateDiscountPolicy 로 변경하는 순간
	// OrderServiceImpl 의 소스 코드도 함께 변경해야 한다! => OCP 위반
	// 인터페이스에만 의존하도록 변경 => null point exception => 해결하자
	@Override
	public Order createOrder(Long memberId, String itemName, int itemPrice) {
		Member member = memberRepository.findById(memberId);
		int discountPrice = discountPolicy.discount(member, itemPrice);
		return new Order(memberId, itemName, itemPrice, discountPrice);
	}

	// 테스트 용도
	public MemberRepository getMemberRepository() {
		return memberRepository;
	}

}
