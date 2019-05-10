package com.neo.controller;

import com.neo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private UserService userService;

    //RestTemplate的get请求
    @GetMapping("/ribbon-consumer")
    public String helloConsumer(){
        ResponseEntity<String> responseEntity=restTemplate.getForEntity(
                "http://SPRING-CLOUD-PRODUCER/hello?name={1}",String.class,"董阳111");
        String body=responseEntity.getBody();
        return body;
    }

    @GetMapping("/log-user-instance")
    public void logUserInstance(){
        ServiceInstance serviceInstance=this.loadBalancerClient.choose("SPRING-CLOUD-PRODUCER");

        System.out.println(serviceInstance.getServiceId()+":"+serviceInstance.getHost()+":"+serviceInstance.getPort());
    }

    @GetMapping("/hystrix-consumer")
    public String getHystrixName(){
       return userService.getUserById();
    }





















}