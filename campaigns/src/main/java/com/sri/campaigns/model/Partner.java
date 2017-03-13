/**
 * 
 */
package com.sri.campaigns.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author SridharBolla
 *
 */

@Entity
@NamedQuery(name = "Partner.findByPartener", query = "SELECT p FROM Partner p WHERE LOWER(p.partner_id) = LOWER(?1)")
@Table(name = "partner")
public class Partner {

	@Id
	@Column(name="partner_id")
	private String partnerId;

	@Column(name="name")
	private String name;

	@Column(name="description")
	private String description;
	
	
	@OneToMany(cascade=CascadeType.ALL, targetEntity=Campaign.class)
	@JoinTable(name="partner_campaign", joinColumns={@JoinColumn(name="partner_id")}, 
	inverseJoinColumns ={@JoinColumn(name="id",table="campaign")})
	private List<Campaign> cmapaignList;
	
	



	/**
	 * @return the cmapaignList
	 */
	public List<Campaign> getCmapaignList() {
		
		if(cmapaignList ==null) cmapaignList= new ArrayList<>();
		return cmapaignList;
	}

	/**
	 * @param cmapaignList the cmapaignList to set
	 */
	public void setCmapaignList(List<Campaign> cmapaignList) {
		this.cmapaignList = cmapaignList;
	}

	/**
	 * @return the partner_id
	 */
	public String getPartnerId() {
		return partnerId;
	}

	/**
	 * @param partnerId the partner_id to set
	 */
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
