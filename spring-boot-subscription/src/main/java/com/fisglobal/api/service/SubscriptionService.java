package com.fisglobal.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fisglobal.api.model.Books;
import com.fisglobal.api.model.Subscription;
import com.fisglobal.api.repository.SubscriptionRepository;

@Service
public class SubscriptionService {
	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
    RestTemplate restTemplate;
	
	HttpHeaders headers = new HttpHeaders();
	HttpEntity<String> entity = new HttpEntity<String>(headers);

	public ResponseEntity<String> subscribeProcess(Subscription subscription, int copies) {
		String id = subscription.getBookId();
		String uri = "http://BookService/books/{id}";
		Books responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<Books>() {},id).getBody();				
		if (responseEntity.getCopiesAvailable() > 0) {
			subscription = subscriptionRepository.save(subscription);
			String uri2 = "http://BookService/books/UpdateAvailability/{id}/{copies}";
			restTemplate.exchange(uri2, HttpMethod.PATCH, entity, new ParameterizedTypeReference<String>() {},id,copies).getBody();				
			return new ResponseEntity<String>("Subscribed", HttpStatus.CREATED);
		} else
// This Kafka implementation of write message is commented as I have some problem in starting kafka server 
//			if (subscription.getNotify()=="yes") {
//			  template.send("SubscriptionTopic", "Book ID: " + id);
//			}
			return new ResponseEntity<String>("book copies not available", HttpStatus.UNPROCESSABLE_ENTITY);
	}

	public ResponseEntity<String> returnProcess(Subscription subscription, int copies) {
		String id = subscription.getBookId();
		subscription = subscriptionRepository.save(subscription);
		String uri2 = "http://BookService/books/UpdateAvailability/{id}/{copies}";
		restTemplate.exchange(uri2, HttpMethod.PATCH, entity, new ParameterizedTypeReference<String>() {},id,copies).getBody();				
		return new ResponseEntity<String>("Book returned", HttpStatus.CREATED);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestFactory.setConnectionRequestTimeout(30000);
		httpRequestFactory.setConnectTimeout(30000);
		httpRequestFactory.setReadTimeout(30000);
	        
	return new RestTemplate(httpRequestFactory);
	}

}
