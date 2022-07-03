package hello.itemservice.repository;

import hello.itemservice.domain.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository itemRepository=new ItemRepository();

    @AfterEach
    public void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    public void save(){
        //given
        Item item=new Item("item1",1000,1);

        //when
        Item savedItem = itemRepository.save(item);
        Item findItem = itemRepository.findById(savedItem.getId());
        //then

        Assertions.assertThat(savedItem).isEqualTo(findItem);
    }

    @Test
    public void findAll(){
        //given
        Item item=new Item("item1",1000,1);
        Item item2=new Item("item2",3000,1);
        //when
        Item savedItem=itemRepository.save(item);
        Item savedItem2=itemRepository.save(item2);
        List<Item> items=  itemRepository.findAll();
        //then

        Assertions.assertThat(items.size()).isEqualTo(2);
    }
    @Test
    public void updateItem(){
        //given
        Item item=new Item("item1",1000,1);
        Item savedItem=itemRepository.save(item);
        Long itemId=savedItem.getId();

        //when

        Item updateParam=new Item("item2",2000,2);
        itemRepository.updateItem(itemId,updateParam);

        Item updatedItem=itemRepository.findById(itemId);
        //then

        Assertions.assertThat(updateParam.getItemName()).isEqualTo(updatedItem.getItemName());
        Assertions.assertThat(updateParam.getPrice()).isEqualTo(updatedItem.getPrice());
        Assertions.assertThat(updateParam.getQuantity()).isEqualTo(updatedItem.getQuantity());
    }
    @Test
    public void deleteItem(){
        //given
        Item item=new Item("item1",1000,1);
        Item savedItem=itemRepository.save(item);
        Long itemId=savedItem.getId();

        //when
        itemRepository.deleteItem(itemId);

        Item findItem = itemRepository.findById(itemId);
        //then
        Assertions.assertThat(findItem).isNull();
    }
}