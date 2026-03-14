package java_dev.java_advanced.framework.Hibernate_jpa.Restaurant_Order_Management_System.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String dishName;
    private int quantity;
    private double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder order;

    public Dish() {}

    public Dish(String dishName, int quantity, double price, CustomerOrder order) {
        this.dishName = dishName;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public CustomerOrder getOrder() { return order; }
    public void setOrder(CustomerOrder order) { this.order = order; }

	@Override
	public String toString() {
		return "Dish [id=" + id + ", dishName=" + dishName + ", quantity=" + quantity + ", price=" + price + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dishName, id, order, price, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dish other = (Dish) obj;
		return Objects.equals(dishName, other.dishName) && id == other.id && Objects.equals(order, other.order)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price) && quantity == other.quantity;
	}
    
    
}