package com.strangengpuppies.strangengpuppies.service;

import com.strangengpuppies.strangengpuppies.model.Service;
import com.strangengpuppies.strangengpuppies.model.Bill;
import com.strangengpuppies.strangengpuppies.model.Subscriber;
import com.strangengpuppies.strangengpuppies.repository.base.BillingRecordRepository;
import com.strangengpuppies.strangengpuppies.service.Exception.CurrencyException;
import com.strangengpuppies.strangengpuppies.service.Exception.DateParseException;
import com.strangengpuppies.strangengpuppies.service.base.BillService;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;

@org.springframework.stereotype.Service
public class BillServiceImpl implements BillService {
  
  private final BillingRecordRepository billRepository;
  
  @Autowired
  public BillServiceImpl(BillingRecordRepository billRepository) {
    this.billRepository = billRepository;
  }
  
  @Override
  public List<Bill> getAllBills() {
    return billRepository.getAllBills();
  }
  
  @Override
  public List<Bill> getReadyBills() {
    return billRepository.getReadyBills();
  }
  
  @Override
  public List<Bill> getNonReadyBills() {
    return billRepository.getNonReadyBills();
  }
  
  @Override
  public Bill getBillById(int id) {
    return billRepository.getBillById(id);
  }
  
  @Override
  public void createBill(Service service, Subscriber subscriber, String startDate, String endDate, double amount, String currency) throws CurrencyException {
    HashSet<String> listOfCurrencies = new HashSet<>();
    listOfCurrencies.add("USD");
    listOfCurrencies.add("EUR");
    listOfCurrencies.add("GBP");
    listOfCurrencies.add("RUB");
    listOfCurrencies.add("BGN");
    
    if(!listOfCurrencies.contains(currency)) {
	 throw new CurrencyException("The currency not exist.");
    } else {
	 
	 try {
	   LocalDate sDate = LocalDate.parse(startDate);
	   LocalDate eDate = LocalDate.parse(endDate);
	   billRepository.createBill(service, subscriber, sDate, eDate, amount, currency);
	 } catch (DateTimeParseException dateParseEx) {
	   throw new DateParseException("Incorrect date format.");
	 }
    }
  }
  
  @Override
  public void updateBillsAccept() {
    List<Bill> bills = billRepository.getAllBills();
    for(Bill bill : bills) {
	 bill.setStatus("approved");
    }
    billRepository.updateBills(bills);
  }
  
  @Override
  public void updateBillsCancel() {
    List<Bill> bills = billRepository.getAllBills();
    for(Bill bill : bills) {
	 bill.setStatus("refused");
    }
    billRepository.updateBills(bills);
  }
  
  @Override
  public void updateBillByIdAccept(int id) {
    Bill bill = billRepository.getBillById(id);
    bill.setStatus("approved");
    billRepository.updateBill(bill);
  }
  
  @Override
  public void updateBillByIdCancel(int id) {
    Bill bill = billRepository.getBillById(id);
    bill.setStatus("refused");
    billRepository.updateBill(bill);
  }
  
}
