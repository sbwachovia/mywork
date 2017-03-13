/**
 * 
 */
package com.sri.campaigns.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sri.campaigns.model.Partner;

/**
 * @author SridharBolla
 *
 */
@Repository
public interface PartnerRepository extends CrudRepository<Partner, Long> {
	
	public Partner findByPartener(String partneIid);

}
