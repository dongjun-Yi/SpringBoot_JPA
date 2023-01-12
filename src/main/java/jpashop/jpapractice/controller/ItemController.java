package jpashop.jpapractice.controller;

import jpashop.jpapractice.domain.Item.Book;
import jpashop.jpapractice.domain.Item.Item;
import jpashop.jpapractice.domain.service.ItemService;
import jpashop.jpapractice.web.BookForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createBookForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String createBook(BookForm form) {
        Book book = new Book();
        book.setAuthor(form.getAuthor());
        book.setName(form.getName());
        book.setIsbn(form.getIsbn());
        book.setStockQuantity(form.getStockQuantity());
        book.setPrice(form.getPrice());
        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String showItemList(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }
}
