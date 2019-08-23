package org.apache.geodeexamples.function;

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.Region;

import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.RegionFunctionContext;

import org.apache.geode.cache.partition.PartitionRegionHelper;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

public class TestGetAllWithFilterFunction implements Function, Declarable {

  private final Cache cache;

  public TestGetAllWithFilterFunction() {
    this.cache = CacheFactory.getAnyInstance();
  }
  
  public void execute(FunctionContext context) {
    // Get the keys from the filter
    RegionFunctionContext rfc = (RegionFunctionContext) context;
    Set keys = rfc.getFilter();
    System.out.println(Thread.currentThread().getName() + ": Executing function=" + getId() + "; keysSize=" + keys.size() + "; keys=" + keys);
    
    // Get datasets for the other region
    Region region = this.cache.getRegion("data");
    Region localData = PartitionRegionHelper.getLocalData(region);
    Region primaryData = PartitionRegionHelper.getLocalPrimaryData(region);

    // Get the values from the region
    Map regionKeysAndValues = region.getAll(keys);
    System.out.println(Thread.currentThread().getName() + ": region=" + region);
    System.out.println(Thread.currentThread().getName() + ": regionKeysAndValues=" + regionKeysAndValues.size() + "; regionKeysAndValues=" + regionKeysAndValues);

    // Get the values from the local data set
    Map localDataKeysAndValues = localData.getAll(keys);
    Map nonNullLocalDataKeysAndValues = new HashMap();
    for (Iterator i=localDataKeysAndValues.entrySet().iterator(); i.hasNext();) {
      Map.Entry entry = (Map.Entry) i.next();
      if (entry.getValue() != null) {
        nonNullLocalDataKeysAndValues.put(entry.getKey(), entry.getValue());
      }
    }
    System.out.println(Thread.currentThread().getName() + ": localData=" + localData);
    //System.out.println(Thread.currentThread().getName() + ": localDataKeysAndValuesSize=" + localDataKeysAndValues.size() + "; nonNullLocalDataKeysAndValuesSize=" + nonNullLocalDataKeysAndValues.size() + "; localDataKeysAndValues=" + localDataKeysAndValues + "; nonNullLocalDataKeysAndValues=" + nonNullLocalDataKeysAndValues);
    System.out.println(Thread.currentThread().getName() + ": nonNullLocalDataKeysAndValuesSize=" + nonNullLocalDataKeysAndValues.size() + "; nonNullLocalDataKeysAndValues=" + nonNullLocalDataKeysAndValues);

    // Get the values from the local primary data set
    Map primaryDataKeysAndValues = primaryData.getAll(keys);
    Map nonNullPrimaryDataKeysAndValues = new HashMap();
    for (Iterator i=primaryDataKeysAndValues.entrySet().iterator(); i.hasNext();) {
      Map.Entry entry = (Map.Entry) i.next();
      if (entry.getValue() != null) {
        nonNullPrimaryDataKeysAndValues.put(entry.getKey(), entry.getValue());
      }
    }
    System.out.println(Thread.currentThread().getName() + ": primaryData=" + primaryData);
    //System.out.println(Thread.currentThread().getName() + ": primaryDataKeysAndValuesSize=" + primaryDataKeysAndValues.size() + "; nonNullPrimaryDataKeysAndValuesSize=" + nonNullPrimaryDataKeysAndValues.size() + "; primaryDataKeysAndValues=" + primaryDataKeysAndValues + "; nonNullPrimaryDataKeysAndValues=" + nonNullPrimaryDataKeysAndValues);
    System.out.println(Thread.currentThread().getName() + ": nonNullPrimaryDataKeysAndValuesSize=" + nonNullPrimaryDataKeysAndValues.size() + "; nonNullPrimaryDataKeysAndValues=" + nonNullPrimaryDataKeysAndValues);

    // Return the results
    context.getResultSender().lastResult(regionKeysAndValues);
  }
  
  public String getId() {
    return getClass().getSimpleName();
  }

  public boolean optimizeForWrite() {
    return true;
  }

  public boolean isHA() {
    return true;
  }
}
