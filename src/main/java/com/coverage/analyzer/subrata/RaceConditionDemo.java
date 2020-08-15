package com.coverage.analyzer.subrata;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class RaceConditionDemo {
  int counter = 0;
  public synchronized void incrementCounterSync(){
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    counter++;
  }

  public  void incrementCounter(){
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    counter++;
  }

  public int getCounter(){
    return counter;
  }

  @Test
  public void testSync() throws Exception {
    RaceConditionDemo rc = new RaceConditionDemo();
    final Map<Integer, Integer> testMap = new HashMap<>();
    final AtomicInteger atomicInteger = new AtomicInteger(1);
    for(int i = 0; i < 10; i++) {
      new Thread(new Runnable() {
        @Override
        public void run() {
          rc.incrementCounterSync();
          testMap.put(atomicInteger.getAndIncrement(), new Integer(rc.getCounter()));
          System.out.println("value for " + Thread.currentThread().getName() + " - " + rc.getCounter());
        }
      }).start();
    }

    Thread.sleep(2000);
    testMap.entrySet().stream().forEach(entry -> Assert.assertEquals(entry.getKey(),entry.getValue()));
  }

  @Test
  public void testNonSync() throws Exception {
    RaceConditionDemo rc = new RaceConditionDemo();
    final Map<Integer, Integer> testMap = new HashMap<>();
    final AtomicInteger atomicInteger = new AtomicInteger(1);
    for(int i = 0; i < 10; i++) {
      new Thread(new Runnable() {
        @Override
        public void run() {
          rc.incrementCounter();
          testMap.put(atomicInteger.getAndIncrement(), new Integer(rc.getCounter()));
          System.out.println("value for " + Thread.currentThread().getName() + " - " + rc.getCounter());
        }
      }).start();
    }

    Thread.sleep(2000);

    final AtomicBoolean isAnyMismatch = new AtomicBoolean(false);
    testMap.entrySet().stream().forEach(entry -> {
      if(!entry.getKey().equals(entry.getValue())) {
        isAnyMismatch.set(true);
      }
    });
    Assert.assertTrue(isAnyMismatch.get());

    //Write something in file that the method incrementCounter = true/false
  }

  @After
  public void tearDown() {

  }

}