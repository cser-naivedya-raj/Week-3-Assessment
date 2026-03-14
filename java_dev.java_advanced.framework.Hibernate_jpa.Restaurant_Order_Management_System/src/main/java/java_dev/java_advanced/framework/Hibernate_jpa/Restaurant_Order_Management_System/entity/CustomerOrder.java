package java_dev.java_advanced.framework.Hibernate_jpa.Restaurant_Order_Management_System.entity;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@jakarta.persistence.Table(name = "orders")
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String customerName;
    private String orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Dish> dishes;

    public CustomerOrder() {}

    public CustomerOrder(String customerName, String orderDate) {
        this.customerName = customerName;
        this.orderDate = orderDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    public List<Dish> getDishes() { return dishes; }
    public void setDishes(List<Dish> dishes) { this.dishes = dishes; }

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerName=" + customerName + ", orderDate=" + orderDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerName, dishes, id, orderDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerOrder other = (CustomerOrder) obj;
		return Objects.equals(customerName, other.customerName) && Objects.equals(dishes, other.dishes)
				&& id == other.id && Objects.equals(orderDate, other.orderDate);
	}
    
    
}