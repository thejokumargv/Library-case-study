package com.fisglobal.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fisglobal.api.model.*;


public interface SubscriptionRepository extends JpaRepository<Subscription, String> {

	List<Subscription> findBysubscriberName(String subscriberName);

}


