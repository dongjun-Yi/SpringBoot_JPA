package jpashop.jpapractice.controller;

import jpashop.jpapractice.domain.Item.Book;
import jpashop.jpapractice.domain.Item.Item;
import jpashop.jpapractice.domain.service.ItemService;
import jpashop.jpapractice.web.BookForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/items/{itemId}/edit")
    public String showEditForm(@PathVariable("itemId") Long itemId, Model model) {
        Book findItem = (Book) itemService.findOne(itemId);
        BookForm bookForm = new BookForm();
        bookForm.setId(findItem.getId());
        bookForm.setName(findItem.getName());
        bookForm.setAuthor(findItem.getAuthor());
        bookForm.setIsbn(findItem.getIsbn());
        bookForm.setStockQuantity(findItem.getStockQuantity());
        bookForm.setPrice(findItem.getPrice());

        model.addAttribute("form", bookForm);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form, @PathVariable Long itemId) {
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
        return "redirect:/items";
    }
}
