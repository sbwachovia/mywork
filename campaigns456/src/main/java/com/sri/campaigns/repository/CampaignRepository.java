/**
 * 
 */
package com.sri.campaigns.repository;

import org.springframework.data.repository.CrudRepository;

import com.sri.campaigns.model.Campaign;

/**
 * @author SridharBolla
 *
 */
public interface CampaignRepository extends CrudRepository<Campaign, Long> {

}
