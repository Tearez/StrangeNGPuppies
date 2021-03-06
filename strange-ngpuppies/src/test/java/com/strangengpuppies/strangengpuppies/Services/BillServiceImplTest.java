package com.strangengpuppies.strangengpuppies.Services;

import com.strangengpuppies.strangengpuppies.model.Bill;
import com.strangengpuppies.strangengpuppies.model.Service;
import com.strangengpuppies.strangengpuppies.model.Subscriber;
import com.strangengpuppies.strangengpuppies.repository.base.BillingRecordRepository;
import com.strangengpuppies.strangengpuppies.service.Exception.CurrencyException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@RunWith (MockitoJUnitRunner.class)
public class BillServiceImplTest {
  @Mock
  BillingRecordRepository mockRepository;
  @InjectMocks
  com.strangengpuppies.strangengpuppies.service.BillServiceImpl service;
  
  @Test
  public void ListAllBills_Returns_Entities() {
    // ARRANGE
    Mockito.when(
		  this.mockRepository.getAllBills())
		  .thenReturn(Arrays.asList(
				new Bill(new Service("Television"), new Subscriber(), LocalDate.of(2018, 8, 2), LocalDate.of(2018, 12, 2), 15.0, "BGN"),
				new Bill(new Service("Internet"), new Subscriber(), LocalDate.of(2018, 3, 2), LocalDate.of(2019, 12, 5), 15.0, "BGN"),
				new Bill(new Service("Phone"), new Subscriber(), LocalDate.of(2017, 5, 2), LocalDate.of(2019, 12, 1), 15.0, "BGN"),
				new Bill(new Service("Tv&Phone"), new Subscriber(), LocalDate.of(2017, 8, 2), LocalDate.of(2018, 8, 6), 15.0, "BGN")));
    // ACT
    List<Bill> result = this.service.getAllBills();
    // ASSERT
    Assert.assertEquals(4, result.size());
    Assert.assertEquals("Television", (result.get(0)).getService().getName());
    Assert.assertEquals("Internet", (result.get(1)).getService().getName());
    Assert.assertEquals("Phone", (result.get(2)).getService().getName());
    Assert.assertEquals("Tv&Phone", (result.get(3)).getService().getName());
  }
  
  @Test
  public void getReadyBills_Returns_Entities() {
    // ARRANGE
    Mockito.when(
		  this.mockRepository.getReadyBills())
		  .thenReturn(Arrays.asList(
				new Bill(new Service("Television"), new Subscriber(), LocalDate.of(2018, 8, 2), LocalDate.of(2018, 12, 2), 15.0, "BGN", "approved"),
				new Bill(new Service("Internet"), new Subscriber(), LocalDate.of(2018, 3, 2), LocalDate.of(2019, 12, 5), 15.0, "BGN", "approved"),
				new Bill(new Service("Phone"), new Subscriber(), LocalDate.of(2017, 5, 2), LocalDate.of(2019, 12, 1), 15.0, "BGN", "approved"),
				new Bill(new Service("Tv&Phone"), new Subscriber(), LocalDate.of(2017, 8, 2), LocalDate.of(2018, 8, 6), 15.0, "BGN", "approved")));
    // ACT
    List<Bill> result = this.service.getReadyBills();
    // ASSERT
    Assert.assertEquals(4, result.size());
    Assert.assertEquals("approved", (result.get(0)).getStatus());
    Assert.assertEquals("approved", (result.get(1)).getStatus());
    Assert.assertEquals("approved", (result.get(2)).getStatus());
    Assert.assertEquals("approved", (result.get(3)).getStatus());
  }
  
  @Test
  public void getNonReadyBills_Returns_Entities() {
    // ARRANGE
    Mockito.when(
		  this.mockRepository.getNonReadyBills())
		  .thenReturn(Arrays.asList(
				new Bill(new Service("Television"), new Subscriber(), LocalDate.of(2018, 8, 2), LocalDate.of(2018, 12, 2), 15.0, "BGN", "waiting"),
				new Bill(new Service("Internet"), new Subscriber(), LocalDate.of(2018, 3, 2), LocalDate.of(2019, 12, 5), 15.0, "BGN", "waiting"),
				new Bill(new Service("Phone"), new Subscriber(), LocalDate.of(2017, 5, 2), LocalDate.of(2019, 12, 1), 15.0, "BGN", "waiting"),
				new Bill(new Service("Tv&Phone"), new Subscriber(), LocalDate.of(2017, 8, 2), LocalDate.of(2018, 8, 6), 15.0, "BGN", "waiting")));
    // ACT
    List<Bill> result = this.service.getNonReadyBills();
    // ASSERT
    Assert.assertEquals(4, result.size());
    Assert.assertEquals("waiting", (result.get(0)).getStatus());
    Assert.assertEquals("waiting", (result.get(1)).getStatus());
    Assert.assertEquals("waiting", (result.get(2)).getStatus());
    Assert.assertEquals("waiting", (result.get(3)).getStatus());
  }
  
  @Test
  public void getBillById_Returns_Entity() {
    // ARRANGE
    Mockito.when(
		  this.mockRepository.getBillById(11))
		  .thenReturn(
				new Bill(new Service("Television"), new Subscriber(), LocalDate.of(2018, 8, 2), LocalDate.of(2018, 12, 2), 15.0, "BGN", "waiting"));
    // ACT
    Bill result = this.service.getBillById(11);
    // ASSERT
    Assert.assertEquals(0, result.getId());
    Assert.assertEquals("Television", result.getService().getName());
    
  }
  
  @Test
  public void createBill_Returns_Entity() throws CurrencyException {
    // ARRANGE
    Service television = new Service("Television");
    Subscriber subscriber = new Subscriber();
    // ACT
    service.createBill(television, subscriber, "2017-08-02", "2018-08-06", 15.0, "BGN");
    // ASSERT
    Mockito.verify(mockRepository).createBill(television, subscriber, LocalDate.of(2017, 8, 02), LocalDate.of(2018, 8, 06), 15.0, "BGN");
  }
  
  @Test
  public void updateBills_Update_Entities_With_AcceptStatus() {
    // ARRANGE
    Service television = new Service("Television");
    Subscriber subscriber = new Subscriber();
    Bill bill = new Bill(television, subscriber, LocalDate.of(2018, 8, 2), LocalDate.of(2018, 12, 2), 15.0, "BGN");
    Mockito.when(this.mockRepository.getAllBills()).thenReturn(Arrays.asList(bill));
    // ACT
    service.updateBillsAccept();
    // ASSERT
    Mockito.verify(mockRepository).updateBills(Arrays.asList(bill));
  }
  
  @Test
  public void updateBills_Update_Entities_With_CancelStatus() {
    // ARRANGE
    Service television = new Service("Television");
    Subscriber subscriber = new Subscriber();
    Bill bill = new Bill(television, subscriber, LocalDate.of(2018, 8, 2), LocalDate.of(2018, 12, 2), 15.0, "BGN");
    Mockito.when(this.mockRepository.getAllBills()).thenReturn(Arrays.asList(bill));
    // ACT
    service.updateBillsCancel();
    // ASSERT
    Mockito.verify(mockRepository).updateBills(Arrays.asList(bill));
  }
  
  @Test
  public void updateBill_Update_Entity_With_AcceptStatus() {
    // ARRANGE
    Service television = new Service("Television");
    Subscriber subscriber = new Subscriber();
    Bill bill = new Bill(television, subscriber, LocalDate.of(2018, 8, 2), LocalDate.of(2018, 12, 2), 15.0, "BGN");
    Mockito.when(this.mockRepository.getBillById(1)).thenReturn(bill);
    // ACT
    service.updateBillByIdAccept(1);
    // ASSERT
    Mockito.verify(mockRepository).updateBill(bill);
  }
  
  @Test
  public void updateBill_Update_Entity_With_CancelStatus() {
    // ARRANGE
    Service television = new Service("Television");
    Subscriber subscriber = new Subscriber();
    Bill bill = new Bill(television, subscriber, LocalDate.of(2018, 8, 2), LocalDate.of(2018, 12, 2), 15.0, "BGN");
    Mockito.when(this.mockRepository.getBillById(1)).thenReturn(bill);
    // ACT
    service.updateBillByIdCancel(1);
    // ASSERT
    Mockito.verify(mockRepository).updateBill(bill);
  }
}