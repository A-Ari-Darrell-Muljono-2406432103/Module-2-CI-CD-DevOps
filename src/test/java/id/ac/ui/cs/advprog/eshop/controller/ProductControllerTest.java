package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
            .andExpect(status().isOk())
            .andExpect(view().name("CreateProduct"))
            .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProductPost() throws Exception {
        Product product = new Product();
        when(productService.create(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/product/create")
                .flashAttr("product", product))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("list"));
    }

    @Test
    void testProductListPage() throws Exception {
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> products = Arrays.asList(product1, product2);

        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/product/list"))
            .andExpect(status().isOk())
            .andExpect(view().name("ProductList"))
            .andExpect(model().attribute("products", products));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(get("/product/delete/123"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("../list"));

        Mockito.verify(productService).delete("123");
    }

    @Test
    void testEditProductPage_Found() throws Exception {
        Product product = new Product();
        product.setProductId("123");

        when(productService.findById("123")).thenReturn(product);

        mockMvc.perform(get("/product/edit/123"))
            .andExpect(status().isOk())
            .andExpect(view().name("EditProduct"))
            .andExpect(model().attribute("product", product));
    }

    @Test
    void testEditProductPage_NotFound() throws Exception {
        when(productService.findById("unknown")).thenReturn(null);

        mockMvc.perform(get("/product/edit/unknown"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("../list"));
    }

    @Test
    void testEditProductPost() throws Exception {
        Product product = new Product();
        product.setProductId("123");

        mockMvc.perform(post("/product/edit")
                .flashAttr("product", product))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("list"));

        Mockito.verify(productService).edit(any(Product.class));
    }
}