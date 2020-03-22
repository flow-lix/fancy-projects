package org.fancy.http;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@SpringBootApplication
public class FancyHttpApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FancyHttpApplication.class, args);
    }

    @Resource
    private RestTemplate restTemplate;

    @Override
    public void run(String... args) throws Exception {
    /*    for (int i = 0; i < 1000; i++) {
            String ret = restTemplate.getForObject("https://wakzz.cn", String.class);
            System.out.println("Result: " + ret);
        }*/
    }
}
