package hello.core.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
// 할인 다 끝났을 때 만들어지는 객체
@AllArgsConstructor
@ToString
public class Order {
	 private Long memberId;
	 private String itemName;
	 private int itemPrice;
	 private int discountPrice;
	 
	 public int calculatePrice() {
		 // 할인 계산된 최종 금액
		 return itemPrice - discountPrice;
	}
	 
//	 @Override
//	 public String toString() {
//	 return "Order{" +
//	 "memberId=" + memberId +
//	 ", itemName='" + itemName + '\'' +
//	 ", itemPrice=" + itemPrice +
//	 ", discountPrice=" + discountPrice +
//	 '}';
//	 }
}
