/**
 * 
 */
package com.sri.campaigns.utility;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author SridharBolla
 *
 */
public class CampaignDTO {
	
	@NotEmpty(message = "partner_id can't empty!")
	private String partner_id;
	
	@NotEmpty(message = "ad_content can't empty!")
	private String ad_content;
	
	@NotNull(message = "duration can't  be null")
	private Integer duration;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp creationTime;
	

	/**
	 * @return the partner_id
	 */
	public String getPartner_id() {
		return partner_id;
	}

	/**
	 * @param partner_id the partner_id to set
	 */
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}

	/**
	 * @return the ad_content
	 */
	public String getAd_content() {
		return ad_content;
	}

	/**
	 * @param ad_content the ad_content to set
	 */
	public void setAd_content(String ad_content) {
		this.ad_content = ad_content;
	}

	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * @return the creationTime
	 */
	public Timestamp getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}
	
	
	

}
