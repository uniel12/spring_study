package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class StatefulServiceTest {
	@Test
	void statefulServiceSingleton() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
		
		StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
		StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);
		
		// ThreadA: A사용자 10000원 주문
		//statefulService1.order("userA", 10000);
		// ThreadB: B사용자 20000원 주문
		//statefulService2.order("userB", 20000);
		// ThreadA: 사용자A 주문 금액 조회
		//int price = statefulService1.getPrice();
		
		// ThreadA: 사용자A는 10000원을 기대했지만, 기대와 다르게 20000원 출력
		// System.out.println("price = " + price);
		// Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
		
// 실무에서 이런 경우를 종종 보는데, 이로인해 정말 해결하기 어려운 큰 문제들이 터진다.(몇년에 한번씩 꼭 만난다.)
// 진짜 공유필드는 조심해야 한다! 스프링 빈은 항상 무상태(stateless)로 설계하자
		int userAprice = statefulService1.order("userA", 10000);
		int userBprice = statefulService2.order("userB", 20000);
		System.out.println("price = " + userAprice);
	}

	static class TestConfig {
		@Bean
		public StatefulService statefulService() {
			return new StatefulService();
		}
	}
}