package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private Payment payment;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        Order order = new Order("order-1", new ArrayList<>(), 1708560000L, "Safira Sudrajat");
        payment = new Payment("payment-1", order, "CASH_ON_DELIVERY", new HashMap<>());
    }

    @Test
    void testSaveAndFindById() {
        paymentRepository.save(payment);
        Payment found = paymentRepository.findById("payment-1");
        assertNotNull(found);
        assertEquals("payment-1", found.getId());
    }

    @Test
    void testFindByIdNotFound() {
        assertNull(paymentRepository.findById("invalid-id"));
    }
}