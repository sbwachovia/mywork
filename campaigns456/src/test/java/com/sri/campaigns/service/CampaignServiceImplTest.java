/**
 * 
 */
package com.sri.campaigns.service;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sri.campaigns.repository.CampaignRepository;
import com.sri.campaigns.repository.PartnerRepository;
import com.sri.campaigns.service.impl.CampaignServiceImpl;

/**
 * @author SridharBolla
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CampaignServiceImplTest {
	
	private static final String ID="target_101";
	
	@Mock
	private CampaignRepository campaignRepository;
	
	@Mock
	private PartnerRepository partnerRepository;
	
	@Mock
	private CampaignService campaignService;
	
	@InjectMocks
	private CampaignServiceImpl campaignServiceImpl;
	
	
	

}
