package com.yorozuyas.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AppLauncher extends SpringBootServletInitializer {
	public static void main( String[] args ) {

		SpringApplication.run( AppLauncher.class, args );
	}

	@Override
	protected SpringApplicationBuilder configure( SpringApplicationBuilder application ) {
		return application.sources( AppLauncher.class );
	}
}
