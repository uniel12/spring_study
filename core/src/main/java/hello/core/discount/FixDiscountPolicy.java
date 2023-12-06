package hello.core.discount;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import hello.core.member.Grade;
import hello.core.member.Member;

// 정액 할인 정책
@Component
// @Qualifier("fixDiscountPolicy") // 조회 대상 빈 2개일 떄 해결방법2 : @Qualifier 는 추가 구분자를 붙여주는 방법
public class FixDiscountPolicy implements DiscountPolicy {

	private int discountFixAmount = 1000; // 1000원 할인

	@Override
	public int discount(Member member, int price) {
		if (member.getGrade() == Grade.VIP) {
			return discountFixAmount;
		} else {
			return 0;
		}
	}

}
