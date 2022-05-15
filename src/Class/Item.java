package Class;

import java.io.Serializable;

public class Item implements Serializable,Info {
    private Integer id;
    private String name;
    private int price;

    public Item() {
    }

    public Item(Integer id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String info() {
        return getId() + ") " + getName() + ", Price:" + getPrice();
    }
}
