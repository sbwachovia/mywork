package com.sri.campaigns.repository;

import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.ArrayList;
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

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@Import({RepositoryConfig.class})
public class PartnerRepositoryTest {

    private PartnerRepository partnerRepository;

   
    /**
	 * @param partnerRepository the partnerRepository to set
	 */
    @Autowired
	public void setPartnerRepository(PartnerRepository partnerRepository) {
		this.partnerRepository = partnerRepository;
	}


	@Test
    public void testSaveProduct(){
        //setup product
        Partner partner = new Partner();
        partner.setPartnerId("target_101");
        partner.setName("Target");
        partner.setDescription("target");
        
        Campaign cp = new Campaign();
        cp.setAdContent("hi kolos");
        cp.setCreationTime(new Timestamp(new Date().getTime()));
        cp.setDuration(120);
        List<Campaign> cmapaignList2 = partner.getCmapaignList();
        
       if(cmapaignList2==null) cmapaignList2 = new ArrayList<Campaign>();
       
       cmapaignList2.add(cp);
       partner.setCmapaignList(cmapaignList2);
        Partner save = partnerRepository.save(partner);
        
        
        Partner findOne = partnerRepository.findByPartener("target_101");
        
        List<Campaign> cmapaignList = findOne.getCmapaignList();
        
        assertNotNull(findOne);
       
    }
}
