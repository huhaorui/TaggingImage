package com.huhaorui.taggingimage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class TaggingImageApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaggingImageApplication.class, args);
    }

}
