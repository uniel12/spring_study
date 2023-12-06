package hello.core.scope;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;

public class SingletonWithPrototypeTest1 {
	@Test
	void prototypeFind() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
		
		PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
		prototypeBean1.addCount();
		assertThat(prototypeBean1.getCount()).isEqualTo(1);
		
		PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
		prototypeBean2.addCount();
		assertThat(prototypeBean2.getCount()).isEqualTo(1);
	}
	
	@Test
	void singletonClientUsePrototype() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
		ClientBean clientBean1 = ac.getBean(ClientBean.class);
		int count1 = clientBean1.logic();
		assertThat(count1).isEqualTo(1);
		 ClientBean clientBean2 = ac.getBean(ClientBean.class);
		 int count2 = clientBean2.logic();
		 assertThat(count2).isEqualTo(1);

	}
	
	@Scope("singleton")
	static class ClientBean{
//		private final PrototypeBean prototypeBean; // 생성시점에 주입
		
//		@Autowired
//		ApplicationContext ac; 
		// 싱글톤 빈이 프로토타입을 사용할 때 마다 스프링 컨테이너에 새로 요청
		
		// 의존관계를 외부에서 주입(DI) 받는게 아니라 이렇게 직접 필요한 의존관계를 찾는 것을 Dependency Lookup (DL) 의존관계 조회(탐색)
//		@Autowired
//		private ObjectProvider<PrototypeBean> prototypeBeanProvider; // 애도 스프링 의존적
		
		@Autowired
		private Provider<PrototypeBean> prototypeBeanProvider;// 스프링이 아닌 다른 컨테이너에서도 사용할 수 있음
		
//		@Autowired
//		public ClientBean(PrototypeBean prototypeBean) {
//			this.prototypeBean = prototypeBean;
//		}
		
		public int logic() {
//			PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
//			PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
			PrototypeBean prototypeBean = prototypeBeanProvider.get();
			prototypeBean.addCount();
			int count = prototypeBean.getCount();
			return count;
		}
	}
	
	@Scope("prototype")
	static class PrototypeBean {
		private int count = 0;

		public void addCount() {
			count++;
		}

		public int getCount() {
			return count;
		}

		@PostConstruct
		public void init() {
			System.out.println("PrototypeBean.init " + this);
		}

		@PreDestroy
		public void destroy() {
			System.out.println("PrototypeBean.destroy");
		}

	}
}
