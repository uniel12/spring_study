package hello.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

public class MemberApp {
	public static void main(String[] args) {

//		AppConfig appConfig = new AppConfig();
//		MemberService memberService =  appConfig.memberService();
		// MemberService memberService = new MemberServiceImpl();
		
		// ApplicationContext : 스프링의 컨테이너! ApplicationContext 는 인터페이스
		// AppConfig 클레스의 환경 설정 정보를 가지고, Spring 빈에 ,Spring 컨테이너에 객체 생성한 걸 다 관리해줌
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		//스프링 빈은 applicationContext.getBean() 메서드를 사용해서 찾을 수 있음
		MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
		
		// 회원가입
		Member member = new Member(1L, "memberA", Grade.VIP);
		memberService.join(member);
		// 회원가입한 멤버 찾기
		Member findMember = memberService.findMember(1L);
		System.out.println("new member = " + member.getName());
		System.out.println("find Member = " + findMember.getName());
	}

}
