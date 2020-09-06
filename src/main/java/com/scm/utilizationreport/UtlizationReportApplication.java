package com.scm.utilizationreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.scm.utilizationreport", "com.scm.utilizationreport.controller" })
public class UtlizationReportApplication {
	public static void main(String[] args) {
		SpringApplication.run(UtlizationReportApplication.class, args);
	}
}