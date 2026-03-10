package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {
    private OrderRepository orderRepository;
    private Order order;

    @BeforeEach
    void setUp() {
        orderRepository = new OrderRepository();
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testSaveCreate() {
        Order savedOrder = orderRepository.save(order);
        assertEquals(order.getId(), savedOrder.getId());
    }

    @Test
    void testSaveUpdate() {
        orderRepository.save(order);
        Order updatedOrder = new Order(order.getId(), order.getProducts(), order.getOrderTime(), order.getAuthor(), "SUCCESS");
        orderRepository.save(updatedOrder);
        Order result = orderRepository.findById(order.getId());
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testFindByIdIfFound() {
        orderRepository.save(order);
        Order result = orderRepository.findById(order.getId());
        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
    }

    @Test
    void testFindByIdIfNotFound() {
        Order result = orderRepository.findById("ID_TIDAK_ADA");
        assertNull(result);
    }

    @Test
    void testFindAllByAuthorIfFound() {
        orderRepository.save(order);
        List<Order> results = orderRepository.findAllByAuthor(order.getAuthor());
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }
}