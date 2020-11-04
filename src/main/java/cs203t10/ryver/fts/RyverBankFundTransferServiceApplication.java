package cs203t10.ryver.fts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class RyverBankFundTransferServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RyverBankFundTransferServiceApplication.class, args);
	}

	@GetMapping("/")
	@Hidden
	public String getRoot() {
		return "ryver-fts service";
	}

}
