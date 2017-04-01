package com.sri.campaigns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author SridharBolla
 *
 * This class launch a Spring Boot application from a Java main method.
 * 
 */
@SpringBootApplication

public class CampaignsWebApplication 
{
    public static void main( String[] args )
    
    {
    	
    	SpringApplication.run(CampaignsWebApplication.class, args);
    	System.out.println("applicatio END****");
    }
}
