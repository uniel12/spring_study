package hello.core.scan.filter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class ComponentFilterAppConfigTest {

	@Test
	void filterScan() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);

		BeanA beanA = ac.getBean("beanA", BeanA.class);
		assertThat(beanA).isNotNull();
		Assertions.assertThrows(
				NoSuchBeanDefinitionException.class,
				() -> ac.getBean("beanB", BeanB.class));
//		includeFilters 에 MyIncludeComponent 애노테이션을 추가해서 BeanA가 스프링 빈에 등록된다.
//		excludeFilters 에 MyExcludeComponent 애노테이션을 추가해서 BeanB는 스프링 빈에 등록되지 않는다.
	}

	@Configuration
	@ComponentScan(
			includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyincludeComponent.class),
			excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
	)
	static class ComponentFilterAppConfig {

	}
}


// FilterType 옵션
//FilterType은 5가지 옵션이 있다.
//ANNOTATION: 기본값, 애노테이션을 인식해서 동작한다.
//	ex) org.example.SomeAnnotation
//ASSIGNABLE_TYPE: 지정한 타입과 자식 타입을 인식해서 동작한다.
//	ex) org.example.SomeClass
//ASPECTJ: AspectJ 패턴 사용
//	ex) org.example..*Service+
//REGEX: 정규 표현식
//	ex) org\.example\.Default.*
//CUSTOM: TypeFilter 이라는 인터페이스를 구현해서 처리
//	ex) org.example.MyTypeFilter

