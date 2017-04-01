/**
 * 
 */
package com.sri.campaigns.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author SridharBolla
 *
 */
@Entity
@Table(name="campaign")
public class Campaign {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="creation_time")
	Timestamp creationTime;
	
	@Column(name="ad_content")
	String adContent;
	
	@Column(name="duration")
	Integer duration;
	
	@ManyToOne( cascade = CascadeType.ALL, fetch=FetchType.EAGER )
    @JoinTable(name="partner_campaign",
        joinColumns = @JoinColumn(name="id"),
        inverseJoinColumns = @JoinColumn(name="partner_id"))
	private Partner partner;
	
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	/**
	 * @return the ad_content
	 */
	public String getAdContent() {
		return adContent;
	}
	/**
	 * @param ad_content the ad_content to set
	 */
	public void setAdContent(String ad_content) {
		this.adContent = ad_content;
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
	 * @return the partner
	 */
	public Partner getPartner() {
		return partner;
	}
	/**
	 * @param partner the partner to set
	 */
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	
	
	
	

}
