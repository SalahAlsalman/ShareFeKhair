package com.example.sharefekhair;

import com.example.sharefekhair.model.MyUser;
import com.example.sharefekhair.model.Student;
import com.example.sharefekhair.model.Teacher;
import com.example.sharefekhair.repository.UserRepository;
import com.example.sharefekhair.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;

@SpringBootApplication
public class ShareFeKhairApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShareFeKhairApplication.class, args);
    }

}
