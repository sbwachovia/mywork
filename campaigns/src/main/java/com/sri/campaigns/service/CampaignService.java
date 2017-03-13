/**
 * 
 */
package com.sri.campaigns.service;

import java.util.List;

import com.sri.campaigns.utility.CampaignDTO;

/**
 * @author SridharBolla
 *
 */
public interface CampaignService {
	
	public List<CampaignDTO> getAllCampaigns();
	
	public void createCampaign(CampaignDTO campaign);
	
	public boolean  hasActiveCampaign(CampaignDTO campaign) ;
	
	public void updateAd(CampaignDTO campaign);

	public CampaignDTO getCampaign(String partnerId);

	public void deleteampaign(String partner_id);
	
	
	

}
