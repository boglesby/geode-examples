package org.apache.geodeexamples;

import org.apache.geode.cache.Region;

import org.apache.geode.cache.execute.FunctionService;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class TestClient extends TestBase {
  
  private static final Random RANDOM = new Random();
  
  private Region functionRegion;

  private Region dataRegion;
  
  public TestClient() {
    initializeClientCache();
    this.dataRegion = initializeRegion("data");
    this.functionRegion = initializeRegion("function");
  }

  public static void main(String[] args) throws Exception {
    TestClient client = new TestClient();
    String operation = args[0];
    if (operation.equals("load")) {
      client.loadEntries(Integer.parseInt(args[1]));
    } else if (operation.equals("execute-getall-with-argument-function")) {
      client.executeGetAllWithArgumentFunction(Integer.parseInt(args[1]));
    } else if (operation.equals("execute-getall-with-filter-function")) {
      client.executeGetAllWithFilterFunction(Integer.parseInt(args[1]));
    }
  }

  private Region initializeRegion(String regionName) {
    Region region = cache.getRegion(regionName);
    System.out.println("Retrieved region: " + region);
    return region;
  }
    
  private void loadEntries(int numEntries) throws Exception {
    long start=0, end=0;
    start = System.currentTimeMillis();
    for (int i=0; i<numEntries; i++) {
      String key = String.valueOf(i);
      this.dataRegion.put(key, key);
      this.functionRegion.put(key, key);
    }
    end = System.currentTimeMillis();
    System.out.println("Loaded " + numEntries + " entries in " + (end-start) + " ms");
  }
  
  private void executeGetAllWithArgumentFunction(int numKeys) {
    Set<String> keys = getKeys(numKeys);
    System.out.println("TestGetAllWithArgumentFunction keysSize=" + keys.size() + "; keys=" + keys);
    long start=0, end=0;
    start = System.currentTimeMillis();
    Object result = FunctionService
      .onRegion(this.functionRegion)
      .setArguments(keys)
      .execute("TestGetAllWithArgumentFunction")
      .getResult();
    end = System.currentTimeMillis();
    System.out.println(Thread.currentThread().getName() + ": Executed TestGetAllWithArgumentFunction with result=" + result + " in " + (end-start) + " ms");
  }
  
  private void executeGetAllWithFilterFunction(int numKeys) {
    Set<String> keys = getKeys(numKeys);
    System.out.println("Executing TestGetAllWithFilterFunction keysSize=" + keys.size() + "; keys=" + keys);
    long start=0, end=0;
    start = System.currentTimeMillis();
    Object result = FunctionService
      .onRegion(this.functionRegion)
      .withFilter(keys)
      .execute("TestGetAllWithFilterFunction")
      .getResult();
    end = System.currentTimeMillis();
    System.out.println(Thread.currentThread().getName() + ": Executed TestGetAllWithFilterFunction with result=" + result + " in " + (end-start) + " ms");
  }

  private Set<String> getKeys(int numKeys) {
    Set<String> keys = new HashSet<String>();
    for (int i=0; i<numKeys; i++) {
      keys.add(String.valueOf(i));
    }
    return keys;
  }
}