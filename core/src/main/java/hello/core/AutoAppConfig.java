package hello.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

@Configuration
@ComponentScan(
// 탐색할 패키지의 시작 위치를 지정한다. 이 패키지를 포함해서 하위 패키지를 모두 탐색
// 지정하지 않으면 default @ComponentScan 이 붙은 설정 정보 클래스의 패키지가 시작 위치
// 권장하는 방법
//		개인적으로 즐겨 사용하는 방법은 패키지 위치를 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것
//		이다. 최근 스프링 부트도 이 방법을 기본으로 제공한다.
		basePackages = "hello.core",
		// excludeFilters: 컴포넌트 스캔을 사용하면 @Configuration 이 붙은 설정 정보도 자동으로 등록되기 때문에 기존
		// 예제코드 유지위해
		excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = Configuration.class))
public class AutoAppConfig {


	//수동 빈등록 VS 자동 빈 등록
	
//	 @Bean(name="memoryMemberRepository") public MemberRepository
//	 memberRepository() { // 리팩토링
//	 System.out.println("call AppConfig.memberRepository"); return new
//	 MemoryMemberRepository(); }
	 

//	이 경우 수동 빈 등록이 우선권을 가진다.
//	(수동 빈이 자동 빈을 오버라이딩 해버린다.)
//	최근 스프링 부트에서는 수동 빈 등록과 자동 빈 등록이 충돌나면 오류가 발생하도록 기본 값을 바꾸었다

}

//참고로 스프링 부트를 사용하면 스프링 부트의 대표 시작 정보인 @SpringBootApplication 를 이 프로젝트 시작
//루트 위치에 두는 것이 관례이다. (그리고 이 설정안에 바로 @ComponentScan 이 들어있다!)

// 컴포넌트 스캔 기본 대상
// 컴포넌트 스캔은 @Component 뿐만 아니라 다음과 내용도 추가로 대상에 포함한다.
// @Component : 컴포넌트 스캔에서 사용
// @Controller : 스프링 MVC 컨트롤러에서 사용
// @Service : 스프링 비즈니스 로직에서 사용
// @Repository : 스프링 데이터 접근 계층에서 사용
// @Configuration : 스프링 설정 정보에서 사용

//컴포넌트 스캔의 용도 뿐만 아니라 다음 애노테이션이 있으면 스프링은 부가 기능을 수행한다.
//@Controller : 스프링 MVC 컨트롤러로 인식
//@Repository : 스프링 데이터 접근 계층으로 인식하고, 데이터 계층의 예외를 스프링 예외로 변환해준다.
//@Configuration : 앞서 보았듯이 스프링 설정 정보로 인식하고, 스프링 빈이 싱글톤을 유지하도록 추가 처리
//를 한다.
//@Service : 사실 @Service 는 특별한 처리를 하지 않는다. 대신 개발자들이 핵심 비즈니스 로직이 여기에 있
//겠구나 라고 비즈니스 계층을 인식하는데 도움이 된다.