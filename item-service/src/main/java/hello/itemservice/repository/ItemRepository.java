package hello.itemservice.repository;

import hello.itemservice.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    private Map<Long, Item> items=new HashMap<>();
    private static Long sequence=0L;

    public Item save(Item item){
        item.setId(++sequence);
        items.put(item.getId(),item);
        return item;
    }
    public Item findById(Long id){
        return items.get(id);
    }
    public List<Item> findAll(){
        return new ArrayList<>(items.values());
    }
    public void updateItem(Long itemId, Item updatedItem){
        Item item = findById(itemId);
        item.setItemName(updatedItem.getItemName());
        item.setPrice(updatedItem.getPrice());
        item.setQuantity(updatedItem.getQuantity());
    }
    public void deleteItem(Long itemId){
        items.remove(itemId);
    }
    public void clearStore(){
        items.clear();
    }
}
