package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreateOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        Order savedOrder = orderService.createOrder(order);
        assertNotNull(savedOrder);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testUpdateStatusSuccess() {
        when(orderRepository.findById(order.getId())).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order updatedOrder = orderService.updateStatus(order.getId(), "SUCCESS");
        assertEquals("SUCCESS", updatedOrder.getStatus());
        verify(orderRepository, times(1)).findById(order.getId());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testUpdateStatusNotFound() {
        when(orderRepository.findById("ID_NGASAL")).thenReturn(null);
        assertThrows(NoSuchElementException.class, () -> orderService.updateStatus("ID_NGASAL", "SUCCESS"));
    }

    @Test
    void testFindByIdSuccess() {
        when(orderRepository.findById(order.getId())).thenReturn(order);
        Order result = orderService.findById(order.getId());
        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(orderRepository.findById("ID_NGASAL")).thenReturn(null);
        assertThrows(NoSuchElementException.class, () -> orderService.findById("ID_NGASAL"));
    }
}