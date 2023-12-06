package hello.itemservice.domain.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {
	// 실무에서는 멀티 쓰레드 상태에서 여러개가 동시에 접근하면  HashMap 쓰면 안된다 사용하려면  ConcurrentHashMap<>() 써야함
	private static final Map<Long, Item> store = new HashMap<>(); // static 사용
	private static long  sequence = 0L; //static 사용
	
	public Item save(Item item) {
		item.setId(++sequence);
		store.put(item.getId(), item);
		return item;
	}
	
	public Item findById(Long id) {
		return store.get(id);
	}
	
	public List<Item> findAll(){
		return new ArrayList<>(store.values());
	}
	
	public void update(Long itemId, Item updateParam) {
		Item findItem = findById(itemId);
		findItem.setItemName(updateParam.getItemName());
		findItem.setPrice(updateParam.getPrice());
		findItem.setQuantity(updateParam.getQuantity());
				
	}
	
	public void clearStore() {
		store.clear();
	}
}
