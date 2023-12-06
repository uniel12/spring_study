package hello.core.common;

import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) 
public class MyLogger {
	//"request" scope HTTP 요청 당 하나씩 생성되고, HTTP 요청이 끝나는 시점에 소멸
	// 프록시 방식 : proxyMode = ScopedProxyMode.TARGET_CLASS(인테페이스일 떄  INTERFACES)
//	 MyLogger의 가짜 프록시 클래스를 만들어두고 HTTP request와 상관 없이 가짜 프록시 클래스를 다른 빈에 미리 주입
	
	private String uuid; // HTTP 요청 당 하나씩 생성되므로, uuid를 저장해두면 다른 HTTP 요청과 구분할 수 있음
	private String requestURL; // 빈이 생성되는 시점에는 알 수 없으므로, 외부에서 setter로 입력

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	public void log(String message) {
		System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
	}

	@PostConstruct
	public void init() {
		uuid = UUID.randomUUID().toString(); // 전세계적으로 겹치치 않는 아이디 생성
		System.out.println("[" + uuid + "] request scope bean create:" + this);
	}

	@PreDestroy
	public void close() {
		// request는 종료 호출됨
		System.out.println("[" + uuid + "] request scope bean close:" + this);
	}
}
