package org.sid.orderservice;

import org.sid.orderservice.entities.Order;
import org.sid.orderservice.entities.ProductItem;
import org.sid.orderservice.enums.OrderStatus;
import org.sid.orderservice.models.Customer;
import org.sid.orderservice.models.Product;
import org.sid.orderservice.repo.OrderRepository;
import org.sid.orderservice.repo.ProductItemRepository;
import org.sid.orderservice.services.CustomerRestClientService;
import org.sid.orderservice.services.InventoryRestClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class  OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(
			OrderRepository orderRepository,
			ProductItemRepository productItemRepository,
			CustomerRestClientService customerRestClientService,
			InventoryRestClientService inventoryRestClientService
	){
		return args -> {
			List<Customer> customers= customerRestClientService.allCustomers().getContent().stream().toList();
			List<Product> products= inventoryRestClientService.allProducts().getContent().stream().toList();
			Long customerId=1L;
			Random random = new Random();
			Customer customer=customerRestClientService.customerById(customerId);
			for(int i=0;i<20;i++){
				Order order=Order.builder()
						.createdAt(new Date())
						.status(Math.random()>0.5? OrderStatus.PENDING:OrderStatus.CREATED)
						.customerId(customers.get(random.nextInt(customers.size())).getId())
						.build();
				Order saveOrder=orderRepository.save(order);
				for(int j=0;j<products.size();j++){
					if(Math.random()>0.75){
						ProductItem productItem = ProductItem.builder()
								.order(saveOrder)
								.productId(products.get(j).getId())
								.price(products.get(j).getPrice())
								.quantity(1+random.nextInt(10))
								.discount(Math.random())
								.build();
						productItemRepository.save(productItem);
					}

				}

			}
		};
	}
}
