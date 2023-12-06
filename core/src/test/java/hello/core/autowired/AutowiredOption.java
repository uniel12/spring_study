package hello.core.autowired;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.member.Member;
import jakarta.annotation.Nullable;

public class AutowiredOption {

	@Test
	void AutowiredOption() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
	}

	static class TestBean {
		// 호출 안됨
		@Autowired(required = false)
		public void setNoBean1(Member noBean1) {
			System.out.println("setNoBean1 = " + noBean1);
		}

		// null 호출
		@Autowired
		public void setNoBean2(@Nullable Member noBean2) {
			System.out.println("setNoBean2 = " + noBean2);
		}

		// Optional.empty 호출
		@Autowired(required = false)
		public void setNoBean3(Optional<Member> noBean3) {
			System.out.println("setNoBean3 = " + noBean3);
		}
	}
}
