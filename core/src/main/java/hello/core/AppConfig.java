package hello.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

// AppConfig는 애플리케이션의 실제 동작에 필요한 구현 객체를 생성
// AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결)
// 객체의 생성과 연결 담당 => DIP 완성, 관심사의 분리 완료

// 스프링 컨테이너는 @Configuration 이 붙은 AppConfig 를 설정(구성) 정보로 사용
//  스프링 설정 정보는 항상 @Configuration 을 사용하자!
@Configuration // AppConfig를 구성한다는 어노테이션 
public class AppConfig {
	
	// @Bean memberService -> new MemoryMemberRepository()
	// @Bean orderService ->  new MemoryMemberRepository()
	// 싱글톤이 깨지는 것처럼 보인다!
	
	// 예상
	// call AppConfig.memberService
	// call AppConfig.memberRepository
	// call AppConfig.memberRepository
	// call AppConfig.orderService
	// call AppConfig.memberRepository
	
	// 실제
//	call AppConfig.memberService
//	call AppConfig.memberRepository
//	call AppConfig.orderService
	
	// @Configuration 대신 스프링에서 끌어온걸 다시 돌려줌 (의존관계 자동주입)
	//@Autowired MemberRepository memberRepository;
	
	@Bean //각 메서드에 @Bean 을 붙여준다. 이렇게 하면 스프링 컨테이너에 스프링 빈으로 등록
	public MemberService memberService() {
		// 생성자 주입(연결)
		System.out.println("call AppConfig.memberService");
		return new MemberServiceImpl(memberRepository());
	}
	@Bean
	public MemberRepository memberRepository() {
		// 리팩토링
		System.out.println("call AppConfig.memberRepository");
		return new MemoryMemberRepository();
	}
	@Bean
	public OrderService orderService() {
		// 생성자 주입(연결)
		System.out.println("call AppConfig.orderService");
		return new OrderServiceImpl(memberRepository(),  discountPolicy());
		// return null;
	}
	@Bean
	public DiscountPolicy discountPolicy() {
		// 리팩토링 중복제거하고 역할과 구현 한눈에 보이게
		// return new FixDiscountPolicy();
		// 새로운 할인 정책을 하려면 애만 바꾸면 됨
		return new RateDiscountPolicy();
	}
//	빈 이름은 메서드 이름을 사용한다.
//	빈 이름을 직접 부여할 수 도 있다. 
//	@Bean(name="memberService2")
//	주의: 빈 이름은 항상 다른 이름을 부여해야한다
	
}
