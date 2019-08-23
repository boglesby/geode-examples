package org.apache.geodeexamples;

public class TestServer extends TestBase {
  
  public static void main(String[] args) throws Exception {
    TestServer server = new TestServer();

    // Initialize the cache
    server.initializeServerCache();
  }
}