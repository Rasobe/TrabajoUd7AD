package com.dam2.ud7;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrabajoAlbertoUd7Application {

	public static void main(String[] args) {
		SpringApplication.run(TrabajoAlbertoUd7Application.class, args);
		
		// Creamos carpeta para cuando se inicie el programa SI NO EXISTE
		String folderPath = new File("src/main/resources/static/img").getAbsolutePath();

        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
		
	}

}