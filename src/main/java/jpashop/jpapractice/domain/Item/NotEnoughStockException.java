package jpashop.jpapractice.domain.Item;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String s) {
        super(s);
    }
}
