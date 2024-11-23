package com.applemart.cart;

import com.applemart.cart.clients.product.CartItem;
import com.applemart.cart.clients.product.ProductItemDTO;
import com.applemart.cart.clients.product.ProductItemService;
import com.applemart.cart.exception.RequestValidationException;
import com.applemart.cart.exception.ResourceNotFoundException;
import com.applemart.cart.redis.BaseRedisServiceImpl;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartRedisServiceImpl extends BaseRedisServiceImpl implements CartRedisService {

    private static final String CART_KEY_PREFIX = "cart:user-";
    private static final String CART_PRODUCT_ITEM_FIELD_PREFIX = "product_item:";

    private final ProductItemService productItemService;

    @Autowired
    public CartRedisServiceImpl(RedisTemplate<String, Object> redisTemplate, ProductItemService productItemService) {
        super(redisTemplate);
        this.productItemService = productItemService;
    }

    private boolean isAuthorized(String userId) {
        String authenticatedUserId = SecurityContextHolder.getContext().getAuthentication().getName();

        return userId.equals(authenticatedUserId);
    }

    public List<CartItem> getProductsFromCartByUserId(String userId) {
        if (!isAuthorized(userId)) {
            throw new AccessDeniedException("You are not authorized to see this cart");
        }

        String cartKey = CART_KEY_PREFIX + userId;
        String idsKey = cartKey + ":ids";

        Map<String, Object> products = getField(cartKey);
        Map<String, Object> ids = getField(idsKey);

        List<CartItem> cartItems = new ArrayList<>();
        for (Map.Entry<String, Object> entry : products.entrySet()) {
            String fieldKey = entry.getKey();

            ProductItemDTO productItem = productItemService.getProductItemById(fieldKey.split(":")[1]);
            if (productItem != null) {
                CartItem cartItem = new CartItem();
                cartItem.setProductItem(productItem);
                cartItem.setQuantity((Integer) entry.getValue());
                cartItem.setId((Integer) ids.get(fieldKey));
                cartItems.add(cartItem);
            }
        }

        cartItems.sort(Comparator.comparing(CartItem::getId).reversed());

        return cartItems;
    }


    public void addProductToCart(String userId, CartItemRequest cartItemRequest) {
        if (!isAuthorized(userId)) {
            throw new AccessDeniedException("You are not authorized to modify this cart");
        }

        String cartKey = CART_KEY_PREFIX + userId;
        String idsKey = cartKey + ":ids";
        String fieldKey = CART_PRODUCT_ITEM_FIELD_PREFIX + cartItemRequest.getProductItemId();

        if (!hashExist(idsKey, fieldKey)) {
            Long id = increment(cartKey + ":id-counter");
            hashSet(idsKey, fieldKey, id);
        }

        Integer quantity = (Integer) this.hashGet(cartKey, fieldKey);
        quantity = (quantity == null ? 0 : quantity) + cartItemRequest.getQuantity();
        hashSet(cartKey, fieldKey, quantity);
    }


    @Override
    public void updateProductInCart(String userId, CartItemRequest cartItemRequest) {

        if (!isAuthorized(userId)) {
            throw new AccessDeniedException("You are not authorized to modify this cart");
        }

        String cartKey = CART_KEY_PREFIX + userId;

        String fieldKey;

        Integer quantity;

        String idsKey = cartKey + ":ids";

        fieldKey = CART_PRODUCT_ITEM_FIELD_PREFIX + cartItemRequest.getProductItemId();
        quantity = cartItemRequest.getQuantity();

        if (quantity <= 0) {
            delete(cartKey, fieldKey);
        } else {
            hashSet(cartKey, fieldKey, quantity);
        }

        if (!hashExist(idsKey, fieldKey)) {
            Long id = increment(cartKey + ":id-counter");
            hashSet(idsKey, fieldKey, id);
        }

    }

    @Override
    public void deleteProductInCart(String userId, CartItemDeletionRequest cartItemRequest) {

        if (!isAuthorized(userId)) {
            throw new AccessDeniedException("You are not authorized to modify this cart");
        }

        String key = CART_KEY_PREFIX + userId;

        String fieldKey;


        fieldKey = CART_PRODUCT_ITEM_FIELD_PREFIX + cartItemRequest.getProductItemId();

        if (!this.hashExist(key, fieldKey)) {
            throw new ResourceNotFoundException("Key [%s] with keyField [%s] not found".formatted(key, fieldKey));
        }

        delete(key, fieldKey);
    }


    @Override
    public void deleteAllProductsInCart(String userId) {

        if (!isAuthorized(userId)) {
            throw new AccessDeniedException("You are not authorized to modify this cart");
        }
        delete(CART_KEY_PREFIX + userId);
    }
}
