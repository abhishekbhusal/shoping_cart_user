package com.technova.shopping_cart.TechNova.Cart.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.technova.shopping_cart.TechNova.Cart.dto.OrderResponse;
import com.technova.shopping_cart.TechNova.Cart.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
//            JsonObject jsonObject= new JsonObject(jsonValue);
//            log.info("orders list is: {}",orders.getBody());
        return ApiResponse.generateResponse(orderResponse.getStatusCode(),orderResponse.getMessage(),orderResponse.getData(),orderResponse.getErrors());

        }
        return null;
    }
}
