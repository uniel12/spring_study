package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCylcleTest {

	@Test
	public void lifeCylcleTest() {
		ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
		NetworkClient client = ac.getBean(NetworkClient.class);
		ac.close(); // 스프링 컨테이너를 종료, ConfigurableApplicationContext 필요

	}

	@Configuration
	static class LifeCycleConfig {


		@Bean
		public NetworkClient networkClient() {
			NetworkClient networkClient = new NetworkClient();
			networkClient.setUrl("http://hello-spring.dev");
			return networkClient;
		}
		
		
		
		
		
		
//		@Bean(initMethod ="init" , destroyMethod = "close")
		// @Bean의 destroyMethod 속성에는 아주 특별한 기능이 있다. 
		// 라이브러리는 대부분 close , shutdown 이라는 이름의 종료 메서드를 사용 
		// 기본값이 (inferred) (추론)으로 등록 은 close , shutdown 라는 이름의 메서드를 자동으로 호출
		// 추론 기능 싫으면 destroyMethod =""
	}
}
