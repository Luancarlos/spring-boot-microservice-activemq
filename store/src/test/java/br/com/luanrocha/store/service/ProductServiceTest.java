package br.com.luanrocha.store.service;

import br.com.luanrocha.store.entity.Product;
import br.com.luanrocha.store.repository.ProductRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;


    @Test
    public void shouldReturnAllProducts() {
        List<Product> productsMock = Arrays.asList(
                new Product(1L, "iPhone X", "Best iPhone X", BigDecimal.valueOf(6500), false),
                new Product(2L, "Macbook Pro 2019", "Best Macbook", BigDecimal.valueOf(25000), false),
                new Product(3L, "Macmini 2012", "Best Macmini", BigDecimal.valueOf(3500), false),
                new Product(4L, "Tesla", "Best Tesla", BigDecimal.valueOf(260500), false)
        );

        doReturn(productsMock).when(productRepository).findAll();

        List<Product> products = productService.findAll();

        assertEquals(products.size(), productsMock.size());
    }

    @Test
    public void shouldReturnOneProduct() {
        Product productMock =  new Product(1L, "iPhone X", "Best iPhone X", BigDecimal.valueOf(6500), false);

        doReturn(Optional.of(productMock)).when(productRepository).findById(1L);

        Optional<Product> product = productService.findById(1L);

        Product product1 = product.orElse(new Product());

        assertSame(product1, productMock);
    }

    @Test
    public void shouldSaveProduct() {
        Product productMock =  new Product(null, "iPhone X", "Best iPhone X", BigDecimal.valueOf(6500), false);
        Product productMockSaved =  new Product(1L, "iPhone X", "Best iPhone X", BigDecimal.valueOf(6500), false);

        doReturn(productMockSaved).when(productRepository).save(productMock);

        Product product = productService.save(productMock);

        assertSame(product, productMockSaved);
    }


    @Test
    public void shouldAddInvoiceProduct() {
        Product productMock =  new Product(1L, "iPhone X", "Best iPhone X", BigDecimal.valueOf(6500), true);
        Product productMockGet =  new Product(1L, "iPhone X", "Best iPhone X", BigDecimal.valueOf(6500), false);

        doReturn(Optional.of(productMockGet)).when(productRepository).findById(1L);
        doReturn(productMock).when(productRepository).save(productMockGet);

        Product product = productService.addInvoice(1L);

        assertSame(product, productMock);
    }


    @Test
    public void shoulThrowExceptionProductNull() {
        Product productMock     = new Product(1L, "iPhone X", "Best iPhone X", BigDecimal.valueOf(6500), true);
        Product productMockGet  = new Product(1L, "iPhone X", "Best iPhone X", BigDecimal.valueOf(6500), false);

        doReturn(null).when(productRepository).findById(1L);
        doReturn(productMock).when(productRepository).save(productMockGet);

        Assertions.assertThrows(NullPointerException.class,  () -> {
            Product product = productService.addInvoice(1L);
        });
    }

}
