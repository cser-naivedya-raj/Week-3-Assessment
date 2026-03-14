package java_dev.java_advanced.framework.Hibernate_jpa.Restaurant_Order_Management_System.dao;

import jakarta.persistence.*;
import java_dev.java_advanced.framework.Hibernate_jpa.Restaurant_Order_Management_System.entity.Dish;
import java_dev.java_advanced.framework.Hibernate_jpa.Restaurant_Order_Management_System.entity.CustomerOrder;

import java.util.List;

public class DAO {

    private EntityManagerFactory emf;

    public DAO() {
        emf = Persistence.createEntityManagerFactory("Restaurant");
    }

    // ADD Order with Dish
    public void addOrder(String customerName, String orderDate,
                         String dishName, int quantity, double price) {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        CustomerOrder order = new CustomerOrder(customerName, orderDate);
        em.persist(order);

        Dish dish = new Dish(dishName, quantity, price, order);
        em.persist(dish);

        em.getTransaction().commit();
        System.out.println("Order added successfully");

        // Print after saving
        printOrder(em, order.getId());

        em.close();
    }

    // SEARCH Order by ID
    public void searchOrder(int id) {
        EntityManager em = emf.createEntityManager();
        printOrder(em, id);
        em.close();
    }

    // UPDATE Dish quantity
    public void updateDishQuantity(int orderId, int dishId, int newQuantity) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // Check order exists
        CustomerOrder order = em.createQuery("SELECT o FROM Order o WHERE o.id = :id", CustomerOrder.class)
                        .setParameter("id", orderId)
                        .getResultStream().findFirst().orElse(null);

        if (order == null) {
            System.out.println("Order not found");
            em.getTransaction().rollback();
            em.close();
            return;
        }

        // Find dish
        Dish dish = em.createQuery("SELECT d FROM Dish d WHERE d.id = :id", Dish.class)
                      .setParameter("id", dishId)
                      .getResultStream().findFirst().orElse(null);

        if (dish == null) {
            System.out.println("Dish not found");
            em.getTransaction().rollback();
            em.close();
            return;
        }

        dish.setQuantity(newQuantity);
        em.merge(order);

        em.getTransaction().commit();
        System.out.println("Dish updated successfully");
        em.close();
    }

    // DELETE Order
    public void deleteOrder(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        CustomerOrder order = em.createQuery("SELECT o FROM Order o WHERE o.id = :id", CustomerOrder.class)
                .setParameter("id", id)   // use 'id' not 'orderId'
                .getResultStream().findFirst().orElse(null);

        if (order == null) {
            System.out.println("Order not found");
            em.getTransaction().rollback();
            em.close();
            return;
        }

        em.remove(order);
        em.getTransaction().commit();
        System.out.println("Order deleted successfully");
        em.close();
    }

    // Helper: print order details
    private void printOrder(EntityManager em, int id) {
        CustomerOrder order = em.createQuery("SELECT o FROM CustomerOrder o WHERE o.id = :id", CustomerOrder.class)
                        .setParameter("id", id)
                        .getResultStream().findFirst().orElse(null);

        if (order == null) {
            System.out.println("Order not found");
            return;
        }

        List<Dish> dishes = em.createQuery(
                "SELECT d FROM Dish d WHERE d.order.id = :id", Dish.class)
                .setParameter("id", id)
                .getResultList();

        System.out.println("ID: " + order.getId());
        System.out.println("Customer: " + order.getCustomerName());
        System.out.println("Order Date: " + order.getOrderDate());
        System.out.println("Dishes:");
        for (Dish d : dishes) {
            System.out.println("  Dish Name: " + d.getDishName());
            System.out.println("  Quantity: " + d.getQuantity());
            System.out.println("  Price: " + (int) d.getPrice());
        }
    }

    public void close() {
        emf.close();
    }
}