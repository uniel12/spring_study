package hello.core.discount;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;

//@Qualifier 정리
//1. @Qualifier끼리 매칭
//2. 빈 이름 매칭
//3. NoSuchBeanDefinitionException 예외 발생
//@Primary // @Autowired 시에 여러 빈이 매칭되면 @Primary 가 우선권을 가진다. 
//@Qualifier("mainDiscountPolicy") // @Qualifier와 @Primary 있으면 우선권은 @Q~
@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy {

	private int discountPercent = 10; // 10% 할인

	@Override
	public int discount(Member member, int price) {
		if (member.getGrade() == Grade.VIP) {
			return price * discountPercent / 100;
		} else {
			return 0;
		}
	}

}
