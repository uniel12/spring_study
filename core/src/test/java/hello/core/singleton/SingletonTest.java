package hello.core.singleton;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AppConfig;
import hello.core.member.MemberService;

public class SingletonTest {

	@Test
	@DisplayName("스프링 없는 순수한 DI 컨테이너")
	void pureContainer() {
		AppConfig appconfig = new AppConfig();
		// 1. 조회 : 호출할 떄마다 객체를 생성
		MemberService memberService1 = appconfig.memberService();

		// 2. 조회 : 호출할 떄마다 객체를 생성
		MemberService memberService2 = appconfig.memberService();

		// 참조값이 다른 것을 확인
		System.out.println("memberService1 = " + memberService1);
		System.out.println("memberService2 = " + memberService2);

		// memberService1 != memberService2
		assertThat(memberService1).isNotSameAs(memberService2);
	}

	// 우리가 만들었던 스프링 없는 순수한 DI 컨테이너인 AppConfig는 요청을 할 때 마다 객체를 새로 생성한다.
	// 고객 트래픽이 초당 100이 나오면 초당 100개 객체가 생성되고 소멸된다! 메모리 낭비가 심하다.
	// 해결방안은 해당 객체가 딱 1개만 생성되고, 공유하도록 설계하면 된다. 싱글톤 패턴

	@Test
	@DisplayName("싱글톤 패턴을 적용한 객체 사용")
	public void singletonServiceTest() {
		// private으로 생성자를 막아두었다. 컴파일 오류가 발생한다.
		// new SingletonService();
		// 1. 조회: 호출할 때 마다 같은 객체를 반환
		SingletonService singletonService1 = SingletonService.getInstance();
		// 2. 조회: 호출할 때 마다 같은 객체를 반환
		SingletonService singletonService2 = SingletonService.getInstance();
		// 참조값이 같은 것을 확인
		System.out.println("singletonService1 = " + singletonService1);
		System.out.println("singletonService2 = " + singletonService2);
		// singletonService1 == singletonService2
		assertThat(singletonService1).isSameAs(singletonService2);
		singletonService1.logic();
	}
	
	@Test
	@DisplayName("스프링 컨테이너와 싱글톤")
	void springContainer() {
		// AppConfig appconfig = new AppConfig();
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		// 1. 조회 : 호출할 떄마다 객체를 생성
		MemberService memberService1 = ac.getBean("memberService", MemberService.class);
		MemberService memberService2 = ac.getBean("memberService", MemberService.class);

		// 참조값이 다른 것을 확인
		System.out.println("memberService1 = " + memberService1);
		System.out.println("memberService2 = " + memberService2);

		// memberService1 != memberService2
		assertThat(memberService1).isSameAs(memberService2);
	}
	
	
	
	
	
}
