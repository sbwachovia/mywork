package com.sri.campaigns.repository;

import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sri.campaigns.config.db.RepositoryConfig;
import com.sri.campaigns.model.Campaign;
import com.sri.campaigns.model.Partner;
import com.sri.campaigns.utility.CampaignDTO;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@Import({RepositoryConfig.class})
public class CampaignRepositoryTest {


   
    /**
	 * @param partnerRepository the partnerRepository to set
	 */
    @Autowired
    private CampaignRepository campaignRepository;
	
    
    

	@Test
    public void testSaveProduct(){
        //setup product
    	
    	Campaign campaign = new Campaign();
    	
    	campaign.setCreationTime(new Timestamp(new Date().getTime()));
    	campaign.setAdContent("Hello Add1");
    	campaign.setDuration(30);
    	
    	
    	Partner partener = new Partner();
    	
    	partener.setPartnerId("Khol_101");
    	partener.setName("khol");
    	
    	partener.setDescription("hiii");
    	
    	campaign.setPartner(partener);
    	Campaign save= campaignRepository.save(campaign);
    	Campaign findOne2 = campaignRepository.findOne(save.getId());
    	
    	assertNotNull(findOne2);
    	
    	assertNotNull(findOne2.getPartner());
    	
    	Iterable<Campaign> findAll = campaignRepository.findAll();
    	for (Campaign campaign2 : findAll) {
			System.out.println(campaign2.getPartner());
		}
    
       
    }
}
