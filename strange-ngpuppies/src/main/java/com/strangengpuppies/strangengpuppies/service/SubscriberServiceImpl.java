package com.strangengpuppies.strangengpuppies.service;

import com.strangengpuppies.strangengpuppies.model.Subscriber;
import com.strangengpuppies.strangengpuppies.model.User;
import com.strangengpuppies.strangengpuppies.repository.base.SubscriberRepository;
import com.strangengpuppies.strangengpuppies.service.base.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubscriberServiceImpl  implements SubscriberService {
  private final SubscriberRepository subscriberRepository;
  
  @Autowired
  public SubscriberServiceImpl(SubscriberRepository subscriberRepository){
    this.subscriberRepository = subscriberRepository;
  }
  
  @Override
  public List<Subscriber> getAllSubscriber() {
    return subscriberRepository.getAllSubscriber();
  }
  
  @Override
  public Subscriber getByPhonenumber(String phonenumber) {
    return subscriberRepository.getById(phonenumber);
  }
  
  @Override
  public void createSubscriber(String phonenumber, String firstName, String lastName, String egn) {
    subscriberRepository.createSubscriber(phonenumber,firstName,lastName,egn);
  }
  
  @Override
  public void updateSubscriberByPhonenumber(String idPhonenumber, String phonenumber, String firstName, String lastName, String egn) {
    Subscriber subscriber = subscriberRepository.getById(idPhonenumber);
    if(phonenumber != null){
    subscriber.setPhoneNumber(phonenumber);
    }
    if(firstName != null){
	 subscriber.setFirstName(firstName);
    }
    if(lastName != null){
	 subscriber.setLastName(lastName);
    }
    if(egn != null){
	 subscriber.setEgn(egn);
    }
    subscriberRepository.updateSubscriber(subscriber);
  }
  
  @Override
  public void deleteSubscriberByPhonenumber(String phonenumber) {
    Subscriber subscriber = subscriberRepository.getById(phonenumber);
    subscriberRepository.deleteSubscriber(subscriber);
  }
}
