/**
 * 
 */
package com.sri.campaigns.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sri.campaigns.service.CampaignService;
import com.sri.campaigns.utility.CampaignDTO;

/**
 * @author SridharBolla
 *
 */
@RestController
public class CampaignController {

	@Autowired
	private CampaignService campaignService;

	@GetMapping("/ad")
	public List<CampaignDTO> getAllCampaigns() {

		return campaignService.getAllCampaigns();
	}

	@GetMapping("/ad/{partner_id}")
	public ResponseEntity<?> getCampPaign(@PathVariable("partner_id") String partnerId) {
		
		CampaignDTO campaignDto = campaignService.getCampaign(partnerId);
		if (!campaignService.hasActiveCampaign(campaignDto)) {
			return ResponseEntity.badRequest().body("no active ad campaigns exist for ".concat(campaignDto.getPartner_id()));
		}
		return ResponseEntity.ok(campaignDto);
	}

	@PostMapping("/ad")
	public ResponseEntity<?> createCampaign(@RequestBody @Valid CampaignDTO campaign, BindingResult result) {

		if (result.hasErrors()) {

			// Replace with Actual Error handling
			return ResponseEntity.badRequest().body(getErrorMsg(result.getAllErrors()));

		}

		if (campaignService.hasActiveCampaign(campaign)) {
			return ResponseEntity.badRequest().body("Only one active campaign can exist ");
		}
		campaignService.createCampaign(campaign);

		return ResponseEntity.ok(new String("sucess"));
	}

	
	@PutMapping("/ad")
	public ResponseEntity<?> updateCampaign(@RequestBody @Valid CampaignDTO campaign, BindingResult result) {

		if (result.hasErrors()) {
			// Replace with Actual Error handling
			return ResponseEntity.badRequest().body(getErrorMsg(result.getAllErrors()));

		}
		campaignService.updateAd(campaign);
		return ResponseEntity.ok(new String("sucess"));
	}

	/**
	 * @param allErrors
	 * @return
	 */
	private String getErrorMsg(List<ObjectError> allErrors) {
		return allErrors.stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(","));
	}

}
