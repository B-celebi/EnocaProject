package com.enoca.cartApi.business.concretes;

import com.enoca.cartApi.business.abstracts.CartBusiness;
import com.enoca.cartApi.common.exceptions.BusinessException;
import com.enoca.cartApi.common.responseHandlers.ResponseHandler;
import com.enoca.cartApi.common.utilities.abstracts.ModelMapperBusiness;
import com.enoca.cartApi.dao.CartDao;
import com.enoca.cartApi.dao.CartProductDao;
import com.enoca.cartApi.dao.ProductDao;
import com.enoca.cartApi.dto.requests.AddProductToCartRequest;
import com.enoca.cartApi.dto.requests.RemoveProductFromCartRequest;
import com.enoca.cartApi.dto.responses.GetCartResponse;
import com.enoca.cartApi.entities.concretes.Cart;
import com.enoca.cartApi.entities.concretes.CartProduct;
import com.enoca.cartApi.entities.concretes.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

@Service
public class CartManager implements CartBusiness {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private CartProductDao cartProductDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ModelMapperBusiness modelMapperBusiness;
    public void calculateTotalPrice(String id){
        double totalPrice = 0;
        Cart cart = this.cartDao.findById(id).orElseThrow(() -> new BusinessException("No carts found with this id."));
        if(cart.getCartProducts().size()==0 || cart.getCartProducts() == null){
            cart.setTotalPrice(0.0);
            this.cartDao.save(cart);
            return;
        }
        for(CartProduct cartProduct : cart.getCartProducts()){
            totalPrice+=(cartProduct.getQuantity().doubleValue()*cartProduct.getProduct().getPrice());
        }
        cart.setTotalPrice(totalPrice);
        this.cartDao.save(cart);
    }
    @Override
    public ResponseEntity<Object> getCartById(String id) {
        Cart cart = this.cartDao.findById(id).orElseThrow(() -> new BusinessException("No carts found with this id."));
        GetCartResponse getCartResponse = this.modelMapperBusiness.forResponse().map(cart,GetCartResponse.class);
        return ResponseHandler.getSuccessResponse(HttpStatus.OK,getCartResponse);
    }

    @Override
    public ResponseEntity<Object> emptyCart(String cartId,boolean isOrdered) {
        Cart cart = this.cartDao.findById(cartId).orElseThrow(()-> new BusinessException("No carts found with this id."));
        if(cart.getCartProducts().isEmpty()){
            throw new BusinessException("No products found in your Cart.");
        }
        for(CartProduct cP : cart.getCartProducts()){
            Product product = this.productDao.findById(cP.getProduct().getId()).orElse(null);
            if(product == null){
                continue;
            }

            /*if(!isOrdered) {
                product.setStock((product.getStock() + cP.getQuantity()));
            }else{*/
              if(isOrdered) {
                  product.setStock((product.getStock() - cP.getQuantity()));
                  //}
                  this.productDao.save(product);
              }
            RemoveProductFromCartRequest removeProductFromCartRequest = new RemoveProductFromCartRequest(
                    cP.getProductCart().getId(), cP.getProduct().getId(), cP.getQuantity()
            );
            this.removeProduct(removeProductFromCartRequest);
            }
        this.calculateTotalPrice(cartId);
        this.cartDao.save(cart);
        GetCartResponse getCartResponse = this.modelMapperBusiness.forResponse().map(cart,GetCartResponse.class);
        return ResponseHandler.getSuccessResponse(HttpStatus.OK,getCartResponse);
    }

    public void addProduct(AddProductToCartRequest addProductToCartRequest){
    Cart cart = this.cartDao.findById(addProductToCartRequest.getCartId()).orElseThrow(()-> new BusinessException("No carts found with this id."));
    Product product = this.productDao.findById(addProductToCartRequest.getProductId()).orElseThrow(() -> new BusinessException("No products found with this id."));


//    Set<CartProduct> cartProducts =  cart.getCartProducts();
//zaten varsa quantity arttır.
    boolean isAlreadyExists = false;
    Set<CartProduct> cartProducts = cart.getCartProducts();
    for(CartProduct cP : cartProducts){
        if(cP.getProduct().getId().equals(addProductToCartRequest.getProductId()) && cP.getProductCart().getId().equals(addProductToCartRequest.getCartId())){
            isAlreadyExists = true;
            cP.setQuantity((cP.getQuantity()+addProductToCartRequest.getQuantity()));
            System.out.println(cart.getCartProducts());
            break;
        }
    }
    String randomUUID = UUID.randomUUID().toString();
    //eğer yoksa ürünü direkt maple ve ekle.
    if(isAlreadyExists == false) {
        CartProduct cartProduct = this.modelMapperBusiness.forRequest().map(addProductToCartRequest, CartProduct.class);
        cartProduct.setId(randomUUID);
        cartProducts.add(cartProduct);

        this.cartProductDao.save(cartProduct);
//        this.cartProductDao.save(cartProduct);
    }
        cart.setCartProducts(cartProducts);
        product.setStock(product.getStock()-addProductToCartRequest.getQuantity());
        this.productDao.save(product);

        this.cartDao.save(cart);
        if(isAlreadyExists==false){
            CartProduct cartProduct = this.cartProductDao.findById(randomUUID).orElseThrow(() -> new BusinessException("No CartProducts found with this id"));
            cartProduct.setProductCart(cart);
            this.cartProductDao.save(cartProduct);
        }
        //her zaman kaydettikten sonra hesapla.

        this.calculateTotalPrice(cart.getId());
        this.cartDao.save(cart);
    }
    public void removeProduct(RemoveProductFromCartRequest removeProductFromCartRequest){
        Cart cart = this.cartDao.findById(removeProductFromCartRequest.getCartId())
                .orElseThrow(() -> new BusinessException("No carts found with this id."));
        Product product = this.productDao.findById(removeProductFromCartRequest.getProductId())
                .orElseThrow(() -> new BusinessException("No products found with this id."));

        Set<CartProduct> cartProducts = new HashSet<>(cart.getCartProducts()); // Kopya bir set oluştur
        Iterator<CartProduct> iterator = cartProducts.iterator();

        boolean isMinusEquals = false;
        String ifMinusEquals = "";

        while(iterator.hasNext()){
            CartProduct cp = iterator.next();
            Product productInsideCart = cp.getProduct();

            if(productInsideCart.getId().equals(removeProductFromCartRequest.getProductId())){
                if(cp.getQuantity() == removeProductFromCartRequest.getQuantity()){
                    productInsideCart.setStock((productInsideCart.getStock() + removeProductFromCartRequest.getQuantity()));
                    isMinusEquals = true;
                    ifMinusEquals = cp.getId();
                    iterator.remove(); // Koleksiyondan elemanı kaldır.
                    break;
                }
                else if(cp.getQuantity() > removeProductFromCartRequest.getQuantity()){
                    cp.setQuantity((cp.getQuantity() - removeProductFromCartRequest.getQuantity()));
                    productInsideCart.setStock((productInsideCart.getStock() + removeProductFromCartRequest.getQuantity()));

                    this.calculateTotalPrice(cart.getId());
                    this.productDao.save(productInsideCart);
                    this.cartDao.save(cart);
                } else {
                    throw new BusinessException("You entered more quantity in than you have in your cart.");
                }
            }
        }

        if(isMinusEquals){
            cartProductDao.deleteById(ifMinusEquals);
        }
        cart.setCartProducts(cartProducts);
        this.cartDao.save(cart);

        this.calculateTotalPrice(cart.getId());
        this.cartDao.save(cart);
    }
}
