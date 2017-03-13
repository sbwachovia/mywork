package com.sri.campaigns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author SridharBolla
 *
 */
@SpringBootApplication
//@Import({RepositoryConfig.class}) 
public class CampaignsWebApplication 
{
    public static void main( String[] args )
    
    {
    	
    	SpringApplication.run(CampaignsWebApplication.class, args);
    	System.out.println("applicatio END****");
    }
}
