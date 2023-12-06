package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Item {
	
	 private Long id;
	 private String itemName;
	 private Integer price; // int 가 아니라 integer 을 쓰는 이유는? => integer 은 null 이 된다!
	 private Integer quantity; 
	 
	 public Item(String itemName, Integer price, Integer quantity) {
	 this.itemName = itemName;
	 this.price = price;
	 this.quantity = quantity;
	 }
}
