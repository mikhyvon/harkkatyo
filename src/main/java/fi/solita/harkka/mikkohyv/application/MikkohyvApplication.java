package fi.solita.harkka.mikkohyv.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackageClasses = {fi.solita.harkka.mikkohyv.domain.EntityRoot.class})
public class MikkohyvApplication {

	public static void main(String[] args) {
		SpringApplication.run(MikkohyvApplication.class, args);
	}

}
