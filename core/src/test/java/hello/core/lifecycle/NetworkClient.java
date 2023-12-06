package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class NetworkClient { 
	private String url;


	public NetworkClient() {
		System.out.println("생성자 호출, url = " + url);

	}

	public void setUrl(String url) {
		this.url = url;
	}

	// 서비스 시작시 호출
	public void connect() {
		System.out.println("connect: " + url);
	}

	public void call(String message) {
		System.out.println("call: " + url + " message = " + message);
	}

	// 서비스 종료시 호출
	public void disconnect() {
		System.out.println("close: " + url);
	}
	
	

	@PostConstruct // 스프링이 권장하는 초기화 어노테이션
	public void init() {
		System.out.println("NetworkClient.init");
		connect();
		call("초기화 연결 메시지");
		
	}

	@PreDestroy // 스프링이 권장하는 소멸 어노테이션
	public void close() {
		System.out.println("NetworkClient.close");
		disconnect();
	}
	
	
	// @PostConstruct, @PreDestroy 애노테이션 특징
//	최신 스프링에서 가장 권장하는 방법이다.
//	애노테이션 하나만 붙이면 되므로 매우 편리하다.
//	패키지를 잘 보면 javax.annotation.PostConstruct 이다. 스프링에 종속적인 기술이 아니라 JSR-250
//	라는 자바 표준이다. 따라서 스프링이 아닌 다른 컨테이너에서도 동작한다.
//	컴포넌트 스캔과 잘 어울린다.
//	유일한 단점은 "외부 라이브러리에는 적용하지 못한다는 것이다." 외부 라이브러리를 초기화, 종료 해야 하면
//	@Bean의 기능을 사용하자!
	
	
	
	
	//	설정 정보에 사용 특징 (설정 정보에 초기화 소멸 메서드 지정) //@Bean(initMethod ="init" , destroyMethod = "close")
//	메서드 이름을 자유롭게 줄 수 있다.
//	스프링 빈이 스프링 코드에 의존하지 않는다.
//	코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있다.	
	
	
	
	
	
	//초기화, 소멸 인터페이스 implements InitializingBean, DisposableBean
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		// 스프링이 의존관계 주입이 다 끝나고 
//		System.out.println("NetworkClient.afterPropertiesSet");
//		connect();
//		call("초기화 연결 메시지");
//		
//	}
//
//	@Override
//	public void destroy() throws Exception {
//		System.out.println("NetworkClient.destroy");
//		disconnect();
//	}
	
	//	초기화, 소멸 인터페이스 단점
//	이 인터페이스는 스프링 전용 인터페이스다. 해당 코드가 스프링 전용 인터페이스에 의존한다.
//	초기화, 소멸 메서드의 이름을 변경할 수 없다.
//	내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다.
//	참고: 인터페이스를 사용하는 초기화, 종료 방법은 스프링 초창기에 나온 방법들이고, 지금은 다음의 더 나은 방법
//	들이 있어서 거의 사용하지 않는다
}
