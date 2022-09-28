package api.util;

import api.model.Order;

public class OrderGenerator {
    public static Order getOrderCreate() {
        return new Order(new String[]{"61c0c5a71d1f82001bdaaa78","609646e4dc916e00276b2870"});
    }
   public static Order getEmptyOrderCreate() {
       return new Order(new String[]{});
    }
    public static Order getIncorrectCacheOrderCreate() {
        return new Order(new String[]{"ffggdf","dfhgdfg"});
    }
}
