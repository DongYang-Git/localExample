package com.neo.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getError")
    public String getUserById(){
        ResponseEntity<String> responseEntity=restTemplate.getForEntity(
                "http://SPRING-CLOUD-PRODUCER/hello?name={1}",String.class,"Hystrix");
        String body=responseEntity.getBody();
        throw new RuntimeException("error,,,程序异常抛出可被捕捉的异常");
//        return body;
    }

    @HystrixCommand(fallbackMethod = "defaultNameSec")
    public String defaultName(){
//        throw new RuntimeException("error");
        return "服务降级";
    }

    public String defaultNameSec(){
        return "服务二次降级";
    }

    public String getError(Throwable e){
//      assert "error".equals(e.getMessage());
      return "捕捉异常:"+e.getMessage();
    }

}
