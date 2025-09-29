package lutfia.example.donation.management.system;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DonationManagementSystemApplication {
	@Bean
	public ModelMapper modelMapper (){
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(DonationManagementSystemApplication.class, args);
	}

}
