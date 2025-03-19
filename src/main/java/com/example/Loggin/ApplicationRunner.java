package com.example.Loggin;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        LogginImpl app = context.getBean(LogginImpl.class);
        app.menu();
    }
}
