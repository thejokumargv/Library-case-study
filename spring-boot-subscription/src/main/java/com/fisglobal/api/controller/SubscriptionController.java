package com.fisglobal.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fisglobal.api.model.Subscription;
import com.fisglobal.api.repository.SubscriptionRepository;
import com.fisglobal.api.service.SubscriptionService;

@RestController
public class SubscriptionController {
	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private SubscriptionService subscriptionService;

//	@Autowired
//	private KafkaTemplate<String, String> template;


	HttpHeaders headers = new HttpHeaders();
	HttpEntity<String> entity = new HttpEntity<String>(headers);

	@PostMapping("/saveSubscription")
	public String saveSubscription(@RequestBody Subscription subscription) {
		subscriptionRepository.save(subscription);
		return "Subscription saved";
	}

	@GetMapping("/subscriptions")
	public List<Subscription> getAll(@RequestParam(value = "subscriber", required = false) String subscriberName) {
		System.out.println("Subscriber name:" + subscriberName);
		if (subscriberName == null)
			return subscriptionRepository.findAll();
		else
			return subscriptionRepository.findBysubscriberName(subscriberName);
	}

	@PostMapping("/POST/subscriptions")
	public ResponseEntity<String> postSubscriptions(@RequestBody Subscription subscription) {
		ResponseEntity<String> subscriptionStatus = subscriptionService.subscribeProcess(subscription, -1);
		return subscriptionStatus;
	}

	@PostMapping("/POST/returns")
	public ResponseEntity<String> postReturns(@RequestBody Subscription subscription) {
		ResponseEntity<String> returnStatus = subscriptionService.returnProcess(subscription, 1);
		return returnStatus;
	}	
	
	
}
