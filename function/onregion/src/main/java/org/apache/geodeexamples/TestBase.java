package org.apache.geodeexamples;

import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.GemFireCache;

import org.apache.geode.cache.client.ClientCacheFactory;

public class TestBase {

  protected GemFireCache cache;

  protected void initializeClientCache() {
    this.cache = new ClientCacheFactory().create();
    System.out.println("Created " + this.cache);
  }
  
  protected void initializeServerCache() {
    this.cache = new CacheFactory().create();
    System.out.println("Created " + this.cache);
  }
  
  protected void closeCache() {
    this.cache.close();
  }
}
