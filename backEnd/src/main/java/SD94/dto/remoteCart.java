package SD94.dto;

import lombok.Data;

@Data
public class remoteCart {
    private long item;

    private int quantity;

    private long cart_item;

    public remoteCart(long item, int quantity, long cartItem) {
    }
}
