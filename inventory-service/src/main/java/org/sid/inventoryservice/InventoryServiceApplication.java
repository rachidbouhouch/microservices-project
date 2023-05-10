package org.sid.inventoryservice;

import org.sid.inventoryservice.entities.Product;
import org.sid.inventoryservice.repo.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(ProductRepository productRepository){
		return args -> {
			productRepository.saveAll(
					List.of(
							Product.builder()
									.name("Iphone 6")
									.price(1500.0)
									.quantity(15)
									.build(),
							Product.builder()
									.name("Samsung J1")
									.price(500.0)
									.quantity(2)
									.build(),
							Product.builder()
									.name("Mac")
									.price(10000.0)
									.quantity(5)
									.build()
					)
			);
			productRepository.findAll().forEach(
					System.out::println
			);
		};
	}

}
