package com.springboot.practiceimitateshopeebackend.service.impl;

import com.springboot.practiceimitateshopeebackend.entity.Cart;
import com.springboot.practiceimitateshopeebackend.entity.Product;
import com.springboot.practiceimitateshopeebackend.entity.User;
import com.springboot.practiceimitateshopeebackend.model.CartRequest;
import com.springboot.practiceimitateshopeebackend.repository.CartRepository;
import com.springboot.practiceimitateshopeebackend.repository.ProductRepository;
import com.springboot.practiceimitateshopeebackend.repository.UserRepository;
import com.springboot.practiceimitateshopeebackend.security.JwtAuthenticationFilter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartServiceImplTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    CartRepository cartRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void updateQuantityIfProductAlreadyExistsInTheCartTest(){

        CartRequest cartRequest = new CartRequest();
        cartRequest.setProductId(1L);
        cartRequest.setQuantity(1L);

        User user = new User();
        user.setEmail(JwtAuthenticationFilter.CURRENT_USER);

        Product product = new Product();
        product.setPrice(100.0);
        product.setProductId(1L);
        product.setQuantity(10L);

        Cart existingCart = new Cart();
        existingCart.setCreatedBy(user.getEmail());
        existingCart.setProduct(product);
        existingCart.setQuantity(3L);

        when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));
        when(productRepository.findById(cartRequest.getProductId())).thenReturn(Optional.of(product));
        when(cartRepository.findByProduct_ProductIdAndUserEmail(cartRequest.getProductId(), user.getEmail())).thenReturn(Optional.of(existingCart));

        cartService.addToCart(cartRequest);

        verify(cartRepository, times(1)).save(any(Cart.class));

        Assertions.assertThat(existingCart.getQuantity()).isLessThan(product.getQuantity());
        Assertions.assertThat(existingCart.getProduct().getProductId()).isEqualTo(cartRequest.getProductId());
        Assertions.assertThat(existingCart.getQuantity()).isEqualTo(4L);
        Assertions.assertThat(existingCart.getTotalAmount()).isEqualTo(400);
        Assertions.assertThat(existingCart.getCreatedBy()).isEqualTo(user.getEmail());
        Assertions.assertThat(existingCart.getLastModifiedBy()).isEqualTo(user.getEmail());
    }
    @Test
    public void addNewProductToCartIfProductDoesntExistsInTheCart(){

        CartRequest cartRequest = new CartRequest();
        cartRequest.setProductId(1L);
        cartRequest.setQuantity(1L);

        User user = new User();
        user.setEmail(JwtAuthenticationFilter.CURRENT_USER);

        Product product = new Product();
        product.setPrice(100.0);
        product.setProductId(1L);
        product.setQuantity(10L);
        product.setShopName("SHOP");
        product.setProductName("PRODUCT NAME");

        Cart newCart = new Cart();
        newCart.setProduct(product);
        newCart.setQuantity(cartRequest.getQuantity());
        newCart.setTotalAmount(product.getPrice() * cartRequest.getQuantity());
        newCart.setShopName(product.getShopName());
        newCart.setProductName(product.getProductName());
        newCart.setUser(user);
        newCart.setCreatedBy(user.getEmail());

        when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));
        when(productRepository.findById(cartRequest.getProductId())).thenReturn(Optional.of(product));

        cartService.addToCart(cartRequest);

        verify(cartRepository, times(1)).save(any(Cart.class));

        Assertions.assertThat(newCart.getQuantity()).isLessThan(product.getQuantity());
        Assertions.assertThat(newCart.getProduct().getProductId()).isEqualTo(cartRequest.getProductId());
        Assertions.assertThat(newCart.getShopName()).isEqualTo(product.getShopName());
        Assertions.assertThat(newCart.getProductName()).isEqualTo(product.getProductName());
        Assertions.assertThat(newCart.getQuantity()).isEqualTo(cartRequest.getQuantity());
        Assertions.assertThat(newCart.getTotalAmount()).isEqualTo(100);
        Assertions.assertThat(newCart.getUser()).isEqualTo(user);
        Assertions.assertThat(newCart.getCreatedBy()).isEqualTo(user.getEmail());
        Assertions.assertThat(newCart.getLastModifiedBy()).isEqualTo(null);

    }

    @Test
    public void increaseProductQuantityInTheCartList(){

        Long id = 1L;

        User user = new User();
        user.setEmail(JwtAuthenticationFilter.CURRENT_USER);

        Product product = new Product();
        product.setPrice(100.0);
        product.setProductId(1L);
        product.setQuantity(10L);

        Cart existingCart = new Cart();
        existingCart.setCreatedBy(user.getEmail());
        existingCart.setProduct(product);
        existingCart.setQuantity(3L);

        when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(cartRepository.findByProduct_ProductIdAndUserEmail(id, user.getEmail())).thenReturn(Optional.of(existingCart));

        cartService.increaseQuantity(id);

        verify(cartRepository, times(1)).save(any(Cart.class));

        Assertions.assertThat(existingCart.getQuantity()).isLessThan(product.getQuantity());
        Assertions.assertThat(existingCart.getCreatedBy()).isEqualTo(user.getEmail());
        Assertions.assertThat(existingCart.getQuantity()).isEqualTo(4L);
        Assertions.assertThat(existingCart.getTotalAmount()).isEqualTo(400);

    }

    @Test
    public void decreaseProductQuantityInTheCartList(){

        Long id = 1L;

        User user = new User();
        user.setEmail(JwtAuthenticationFilter.CURRENT_USER);

        Product product = new Product();
        product.setPrice(100.0);
        product.setProductId(1L);
        product.setQuantity(10L);

        Cart existingCart = new Cart();
        existingCart.setCreatedBy(user.getEmail());
        existingCart.setProduct(product);
        existingCart.setQuantity(3L);

        when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(cartRepository.findByProduct_ProductIdAndUserEmail(id, user.getEmail())).thenReturn(Optional.of(existingCart));

        cartService.decreaseQuantity(id);

        verify(cartRepository, times(1)).save(any(Cart.class));

        Assertions.assertThat(existingCart.getQuantity()).isLessThan(product.getQuantity());
        Assertions.assertThat(existingCart.getCreatedBy()).isEqualTo(user.getEmail());
        Assertions.assertThat(existingCart.getQuantity()).isEqualTo(2L);
        Assertions.assertThat(existingCart.getTotalAmount()).isEqualTo(200);

    }

    @Test
    public void filterProductsInCartOneByOneForCheckout(){

        Long id = 1L;

        User user = new User();
        user.setEmail(JwtAuthenticationFilter.CURRENT_USER);

        Product product = new Product();
        product.setProductId(1L);

        Cart existingCart = new Cart();
        existingCart.setCreatedBy(user.getEmail());
        existingCart.setProduct(product);
        existingCart.setFilter(false);

        when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(cartRepository.findByProduct_ProductIdAndUserEmail(id, user.getEmail())).thenReturn(Optional.of(existingCart));

        cartService.filterCart(id);

        Assertions.assertThat(existingCart.isFilter()).isEqualTo(true);
    }

    @Test
    public void deleteProductsInCart(){

        Long id = 1L;
        String username = JwtAuthenticationFilter.CURRENT_USER;
        cartService.deleteProductsInCart(id);

        verify(cartRepository, times(1)).deleteByProduct_ProductIdAndUserEmail(id,username);

    }
}