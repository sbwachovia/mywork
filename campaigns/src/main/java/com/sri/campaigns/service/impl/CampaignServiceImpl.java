/**
 * 
 */
package com.sri.campaigns.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sri.campaigns.model.Campaign;
import com.sri.campaigns.model.Partner;
import com.sri.campaigns.repository.CampaignRepository;
import com.sri.campaigns.repository.PartnerRepository;
import com.sri.campaigns.service.CampaignService;
import com.sri.campaigns.utility.CampaignDTO;

/**
 * @author SridharBolla
 *
 */
@Service
public class CampaignServiceImpl implements CampaignService {
	
	@Autowired
	private CampaignRepository campaignRepository;
	
	@Autowired
	private PartnerRepository partnerRepository;
	

	/* (non-Javadoc)
	 * @see com.sri.campaigns.service.CampaignService#getAllCampaigns()
	 */
	public List<CampaignDTO> getAllCampaigns() {
		
		Iterable<Campaign> findAll = campaignRepository.findAll();
		
		 List<CampaignDTO> campignList = new ArrayList<CampaignDTO>();
		findAll.forEach(campaign ->{
			CampaignDTO dto = new CampaignDTO();
			dto.setPartner_id(campaign.getPartner().getPartnerId());
			dto.setAd_content(campaign.getAdContent());
			dto.setDuration(campaign.getDuration());
			dto.setCreationTime(campaign.getCreationTime());
			campignList.add(dto);
		});
		
		
		return campignList;
	}

	/* (non-Javadoc)
	 * @see com.sri.campaigns.service.CampaignService#createCampaign(com.sri.campaigns.model.CampaignDTO)
	 */
	public void createCampaign(CampaignDTO campaignDto) {
		Partner partener=  partnerRepository.findByPartener(campaignDto.getPartner_id());
		Campaign campaign = new Campaign();
		if(partener==null){
			partener= new Partner();
		}
		
		campaign.setAdContent(campaignDto.getAd_content());
		campaign.setDuration(campaignDto.getDuration());
		campaign.setCreationTime(new Timestamp(new Date().getTime()));
		
		partener.setPartnerId(campaignDto.getPartner_id());
		partener.setName(campaignDto.getPartner_id());
		
		partener.getCmapaignList().add(campaign);
		
		partnerRepository.save(partener);
		

	}

	/* (non-Javadoc)
	 * @see com.sri.campaigns.service.CampaignService#updateAd(com.sri.campaigns.model.CampaignDTO)
	 */
	public void updateAd(CampaignDTO campaignDto) {
		Partner findByPartener = partnerRepository.findByPartener(campaignDto.getPartner_id());
		
		 findByPartener.getCmapaignList().forEach(camp->{
		 
			 if(camp.getAdContent().equals(campaignDto.getAd_content())){
				 camp.setCreationTime(new Timestamp(new Date().getTime()));
				 camp.setDuration(campaignDto.getDuration());
				 camp.setPartner(findByPartener);
			 }
	
		 });
		 
		 partnerRepository.save(findByPartener);
		
		
		

	}

	/* (non-Javadoc)
	 * @see com.sri.campaigns.service.CampaignService#getCampaign(java.lang.String)
	 */
	public CampaignDTO getCampaign(String partnerId) {
		Partner findByPartener = partnerRepository.findByPartener(partnerId);
		
		if(findByPartener==null) return null;
		
		Campaign campaign = findByPartener.getCmapaignList().get(0);
		
		
		CampaignDTO dto = new CampaignDTO();
		
		dto.setPartner_id(findByPartener.getPartnerId());
		dto.setAd_content(campaign.getAdContent());
		dto.setDuration(campaign.getDuration());
		dto.setCreationTime(campaign.getCreationTime());
		return dto;
	}

	/* (non-Javadoc)
	 * @see com.sri.campaigns.service.CampaignService#deleteampaign(java.lang.String)
	 */
	public void deleteampaign(String partner_id) {
	
		Partner findByPartener = partnerRepository.findByPartener(partner_id);
		partnerRepository.delete(findByPartener);

	}

	/* (non-Javadoc)
	 * @see com.sri.campaigns.service.CampaignService#hasActiveCampaign(com.sri.campaigns.model.CampaignDTO)
	 */
	@Override
	public boolean hasActiveCampaign(CampaignDTO campaignDto) {
		Partner findByPartener = partnerRepository.findByPartener(campaignDto.getPartner_id());
		if(findByPartener==null) return false;
		
		boolean hasActiveCampaign=false;
		List<Campaign> cmapaignList = findByPartener.getCmapaignList();
		for (Campaign campaign : cmapaignList) {
			//Java 8 Instant
			Instant newAdd = java.time.Instant.now();
			Instant existingTS = java.time.Instant.from(campaign.getCreationTime().toInstant()).plusSeconds(campaign.getDuration());
			if(newAdd.isBefore(existingTS)){
				hasActiveCampaign=true;
				break;
			}
		}
		
		
		return hasActiveCampaign;
	}


}
