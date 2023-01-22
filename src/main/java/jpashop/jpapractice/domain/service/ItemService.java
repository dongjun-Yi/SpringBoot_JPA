package jpashop.jpapractice.domain.service;

import jpashop.jpapractice.domain.Item.Item;
import jpashop.jpapractice.repository.ItemDataJpaRepository;
import jpashop.jpapractice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    //private final ItemRepository itemRepository;

    private final ItemDataJpaRepository itemDataJpaRepository;

    @Transactional
    public void saveItem(Item item) {
        itemDataJpaRepository.save(item);
    }

    public List<Item> findItems() {
        return itemDataJpaRepository.findAll();
    }

    public Item findOne(Long itemId) {

        //return itemRepository.findOne(itemId);
        return itemDataJpaRepository.findOneById(itemId);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        //Item item = itemRepository.findOne(itemId);
        Item item = itemDataJpaRepository.findOneById(itemId);
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
    }
}
