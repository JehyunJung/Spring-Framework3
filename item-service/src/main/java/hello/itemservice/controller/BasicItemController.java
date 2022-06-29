package hello.itemservice.controller;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items=itemRepository.findAll();
        model.addAttribute("items",items);
        return "/basic/items";
    }

    /*
    * PostConstruct --> 테스트 데이터를 추가한다.
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));

    }

    @GetMapping("/{itemId}")
    public String getItem(@PathVariable Long itemId, Model model){
        Item item=itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "/basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "/basic/addForm";
    }

    /*@PostMapping("/add")
    public String addItemV1(String itemName, Integer price, Integer quantity,Model model){
        Item item=new Item(itemName,price,quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "/basic/item";
    }*/

   /* @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item,Model model){
        itemRepository.save(item);
        return "/basic/item";
    }*/

   /* @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item,Model model){
        itemRepository.save(item);
        return "/basic/item";
    }*/

    @PostMapping("/add")
    public String addItemV4(Item item,Model model){
        itemRepository.save(item);
        return "/basic/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editItemForm(@PathVariable Long itemId,Model model){
        Item item=itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "/basic/updateForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.updateItem(itemId,item);
        return "redirect:/basic/items/{itemId}";
    }
}
