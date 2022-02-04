package com.technova.shopping_cart.TechNova.Cart.controller;

//import com.fasterxml.jackson.databind.util.JSONPObject;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
import com.technova.shopping_cart.TechNova.Cart.dto.OrderRequest;
import com.technova.shopping_cart.TechNova.Cart.dto.OrderResponse;
import lombok.extern.slf4j.Slf4j;
//import org.aspectj.weaver.ast.Or;
//import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

//import java.util.HashMap;
//import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/orders")
    public ResponseEntity<Object> getAllOrders(){
        ResponseEntity<OrderResponse> orders= restTemplate.getForEntity("http://localhost:8081/api/orders", OrderResponse.class);

        if(orders.getStatusCode()== HttpStatus.OK){
           OrderResponse orderResponse= orders.getBody();
        //   Map<String,Object> resMap = new HashMap<>();
         //  resMap.put("order", orderResponse.getData());
//            JsonObject jsonObject= new JsonObject(jsonValue);
//            log.info("orders list is: {}",orders.getBody());
        return com.technova.shopping_cart.TechNova.Cart.utils.ApiResponse.generateResponse(orderResponse.getStatusCode(),orderResponse.getMessage(),orderResponse.getData(),orderResponse.getErrors());

        }
        return null;
    }
    @PostMapping("/orders")
    public ResponseEntity<Object> createOrders(@RequestBody OrderRequest orderRequest){
        ResponseEntity<OrderResponse> orders= restTemplate.postForEntity("http://localhost:8081/api/orders", orderRequest, OrderResponse.class);

        if(orders.getStatusCode()== HttpStatus.OK){
            OrderResponse orderResponse= orders.getBody();
//            JsonObject jsonObject= new JsonObject(jsonValue);
            //Object data = jsonObject.get("data");
//            log.info("orders list is: {}",orders.getBody());
            return com.technova.shopping_cart.TechNova.Cart.utils.ApiResponse.generateResponse(orderResponse.getStatusCode(),"Order created successfully",orderResponse.getData(),orderResponse.getErrors());

        }
        return com.technova.shopping_cart.TechNova.Cart.utils.ApiResponse.generateResponse((HttpStatus.BAD_REQUEST.value()),"Order failed",null,"Something went wrong");
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Object> updateOrders(@PathVariable Long id,@RequestBody OrderRequest orderRequest){
        HttpEntity<OrderRequest> requestEntity = new HttpEntity<OrderRequest>(orderRequest,null);
        //Map<String,Long> mapId = new HashMap<>();
        //mapId.put("id",id);
        ResponseEntity <OrderResponse> orders= restTemplate.exchange("http://localhost:8081/api/orders/"+id, HttpMethod.PUT, requestEntity, OrderResponse.class);

        if(orders.getStatusCode()== HttpStatus.OK){
            OrderResponse orderResponse= orders.getBody();
//            JsonObject jsonObject= new JsonObject(jsonValue);
            //Object data = jsonObject.get("data");
//            log.info("orders list is: {}",orders.getBody());
            return com.technova.shopping_cart.TechNova.Cart.utils.ApiResponse.generateResponse(orderResponse.getStatusCode(),"Order updated successfully",orderResponse.getData(),orderResponse.getErrors());

        }
        return com.technova.shopping_cart.TechNova.Cart.utils.ApiResponse.generateResponse((HttpStatus.BAD_REQUEST.value()),"Order failed",null,"Something went wrong");
    }

}
