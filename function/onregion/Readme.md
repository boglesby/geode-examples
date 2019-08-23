# Geode Function OnRegion Examples

## Compile Classes
Compile the classes using the maven `package` task like:

```
mvn package
```
## Start Locator
Modify the `GEODE` environment variable in the `setenv.sh` script to point to a Geode 1.9.0 installation.

Note: The `pom.xml` currently depends on Geode 1.9.0, but it could be any version as long as the version used by `gfsh` in the `startlocator.sh` script matches the `pom.xml` dependency. 

Start the locator using the `startlocator.sh` script like:

```
./startlocator.sh 
......
Locator in /path/to/locator on host1[12345] as locator is currently online.
Process ID: 18066
Uptime: 7 seconds
Geode Version: 1.9.0
Java Version: 1.8.0_121
Log File: /path/to/locator/locator.log
JVM Arguments: ...
Class-Path: ...

Successfully connected to: JMX Manager [host=host1, port=1099]

Cluster configuration service is up and running.
```
## Start Servers

Start each server using the maven `exec` task like:

```
mvn exec:exec -Dserver -Dexec.workingdir=server-1
...
Created GemFireCache[id = 854640632; isClosing = false; isShutDownAll = false; created = Fri Aug 23 14:38:18 PDT 2019; server = false; copyOnRead = false; lockLease = 120; lockTimeout = 60]
```
```
mvn exec:exec -Dserver -Dexec.workingdir=server-2
...
Created GemFireCache[id = 287859212; isClosing = false; isShutDownAll = false; created = Fri Aug 23 14:38:46 PDT 2019; server = false; copyOnRead = false; lockLease = 120; lockTimeout = 60]
```
```
mvn exec:exec -Dserver -Dexec.workingdir=server-3
...
Created GemFireCache[id = 697463019; isClosing = false; isShutDownAll = false; created = Fri Aug 23 14:38:53 PDT 2019; server = false; copyOnRead = false; lockLease = 120; lockTimeout = 60]
```
## Load Entries
Load entries into the regions using the maven `exec` task like:

```
mvn exec:exec -Dclient -Dexec.workingdir=client -Doperation=load -Dparameter=1000
...
Created GemFireCache[id = 662925691; isClosing = false; isShutDownAll = false; created = Fri Aug 23 14:40:24 PDT 2019; server = false; copyOnRead = false; lockLease = 120; lockTimeout = 60]
Retrieved region: org.apache.geode.internal.cache.LocalRegion[path='/data';scope=LOCAL';dataPolicy=EMPTY; concurrencyChecksEnabled]
Retrieved region: org.apache.geode.internal.cache.LocalRegion[path='/function';scope=LOCAL';dataPolicy=EMPTY; concurrencyChecksEnabled]
Loaded 1000 entries in 3955 ms
```
## Execute Function With Argument
Execute the function with keys in the argument using the maven `exec` task like:

```
mvn exec:exec -Dclient -Dexec.workingdir=client -Doperation=execute-getall-with-argument-function -Dparameter=10
...
Created GemFireCache[id = 1011044643; isClosing = false; isShutDownAll = false; created = Fri Aug 23 15:33:05 PDT 2019; server = false; copyOnRead = false; lockLease = 120; lockTimeout = 60]
Retrieved region: org.apache.geode.internal.cache.LocalRegion[path='/data';scope=LOCAL';dataPolicy=EMPTY; concurrencyChecksEnabled]
Retrieved region: org.apache.geode.internal.cache.LocalRegion[path='/function';scope=LOCAL';dataPolicy=EMPTY; concurrencyChecksEnabled]
TestGetAllWithArgumentFunction keysSize=10; keys=[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
main: Executed TestGetAllWithArgumentFunction with result=[{0=0, 1=1, 3=3, 5=5, 7=7, 8=8, 9=9}, {0=0, 2=2, 3=3, 4=4, 6=6, 7=7}, {1=1, 2=2, 4=4, 5=5, 6=6, 8=8, 9=9}] in 47 ms
```
The server windows will log messages like:

```
Function Execution Processor2: Executing function=TestGetAllWithArgumentFunction; keysSize=10; keys=[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
Function Execution Processor2: region=Partitioned Region @62566842 [path='/data'; dataPolicy=PARTITION; prId=2; isDestroyed=false; isClosed=false; retryTimeout=3600000; serialNumber=122; partition attributes=PartitionAttributes@24041145[redundantCopies=1;localMaxMemory=890;totalMaxMemory=2147483647;totalNumBuckets=113;partitionResolver=null;colocatedWith=null;recoveryDelay=-1;startupRecoveryDelay=0;FixedPartitionAttributes=null;partitionListeners=null]; on VM 10.255.202.119(24889)<v2>:41002]
Function Execution Processor2: regionKeysAndValues=10; regionKeysAndValues={0=0, 1=1, 2=2, 3=3, 4=4, 5=5, 6=6, 7=7, 8=8, 9=9}
Function Execution Processor2: localData=org.apache.geode.internal.cache.LocalDataSet[path='/data';scope=DISTRIBUTED_NO_ACK';dataPolicy=PARTITION ;bucketIds=[0, 2, 3, 4, 7, 8, 9, 11, 13, 15, 16, 17, 20, 21, 24, 25, 26, 27, 29, 30, 31, 34, 35, 36, 37, 38, 40, 42, 43, 45, 48, 49, 51, 53, 55, 56, 57, 59, 60, 62, 63, 65, 66, 67, 68, 69, 70, 71, 74, 75, 76, 77, 78, 79, 80, 81, 83, 84, 87, 88, 89, 90, 93, 95, 97, 99, 100, 101, 103, 104, 106, 107, 110, 111, 112]]
Function Execution Processor2: nonNullLocalDataKeysAndValuesSize=7; nonNullLocalDataKeysAndValues={0=0, 1=1, 3=3, 5=5, 7=7, 8=8, 9=9}
Function Execution Processor2: primaryData=org.apache.geode.internal.cache.LocalDataSet[path='/data';scope=DISTRIBUTED_NO_ACK';dataPolicy=PARTITION ;bucketIds=[0, 4, 69, 7, 71, 8, 74, 11, 76, 77, 79, 16, 17, 81, 83, 20, 87, 25, 89, 26, 90, 29, 93, 31, 34, 37, 101, 38, 104, 42, 111, 49, 51, 56, 57, 59, 60, 63]]
Function Execution Processor2: nonNullPrimaryDataKeysAndValuesSize=4; nonNullPrimaryDataKeysAndValues={1=1, 3=3, 8=8, 9=9}
```
## Execute Function With Filter
Execute the function with keys in the filter using the maven `exec` task like:

```
mvn exec:exec -Dclient -Dexec.workingdir=client -Doperation=execute-getall-with-filter-function -Dparameter=10
...
Created GemFireCache[id = 1011044643; isClosing = false; isShutDownAll = false; created = Fri Aug 23 15:35:03 PDT 2019; server = false; copyOnRead = false; lockLease = 120; lockTimeout = 60]
Retrieved region: org.apache.geode.internal.cache.LocalRegion[path='/data';scope=LOCAL';dataPolicy=EMPTY; concurrencyChecksEnabled]
Retrieved region: org.apache.geode.internal.cache.LocalRegion[path='/function';scope=LOCAL';dataPolicy=EMPTY; concurrencyChecksEnabled]
Executing TestGetAllWithFilterFunction keysSize=10; keys=[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
main: Executed TestGetAllWithFilterFunction with result=[{0=0, 5=5, 7=7}, {8=8, 1=1, 9=9, 3=3}, {4=4, 2=2, 6=6}] in 28 ms
```
The server windows will log messages like:

```
Function Execution Processor2: Executing function=TestGetAllWithFilterFunction; keysSize=3; keys=[0, 5, 7]
Function Execution Processor2: region=Partitioned Region @62566842 [path='/data'; dataPolicy=PARTITION; prId=2; isDestroyed=false; isClosed=false; retryTimeout=3600000; serialNumber=122; partition attributes=PartitionAttributes@992904741[redundantCopies=1;localMaxMemory=890;totalMaxMemory=2147483647;totalNumBuckets=113;partitionResolver=null;colocatedWith=null;recoveryDelay=-1;startupRecoveryDelay=0;FixedPartitionAttributes=null;partitionListeners=null]; on VM 10.255.202.119(24890)<v3>:41003]
Function Execution Processor2: regionKeysAndValues=3; regionKeysAndValues={0=0, 5=5, 7=7}
Function Execution Processor2: localData=org.apache.geode.internal.cache.LocalDataSet[path='/data';scope=DISTRIBUTED_NO_ACK';dataPolicy=PARTITION ;bucketIds=[1, 3, 4, 5, 6, 9, 10, 11, 12, 14, 16, 18, 19, 20, 22, 23, 24, 27, 28, 30, 32, 33, 36, 37, 38, 39, 41, 42, 44, 45, 46, 47, 49, 50, 52, 53, 54, 56, 57, 58, 59, 60, 61, 62, 64, 66, 69, 70, 72, 73, 75, 77, 78, 80, 82, 84, 85, 86, 90, 91, 92, 93, 94, 96, 97, 98, 101, 102, 104, 105, 106, 108, 109, 111, 112]]
Function Execution Processor2: nonNullLocalDataKeysAndValuesSize=1; nonNullLocalDataKeysAndValues={5=5}
Function Execution Processor2: primaryData=org.apache.geode.internal.cache.LocalDataSet[path='/data';scope=DISTRIBUTED_NO_ACK';dataPolicy=PARTITION ;bucketIds=[64, 1, 66, 3, 6, 70, 9, 75, 12, 14, 78, 80, 19, 84, 22, 86, 24, 27, 92, 30, 94, 33, 97, 98, 36, 102, 39, 41, 105, 106, 44, 108, 45, 112, 50, 53, 54, 62]]
Function Execution Processor2: nonNullPrimaryDataKeysAndValuesSize=1; nonNullPrimaryDataKeysAndValues={5=5}
```
## Shutdown Servers And Locator
Shutdown the servers and locator using the `shutdownall.sh` script like:

```
./shutdownall.sh 

(1) Executing - connect --locator=localhost[12345]

Connecting to Locator at [host=localhost, port=12345] ..
Connecting to Manager at [host=host1, port=1099] ..
Successfully connected to: [host=host1, port=1099]


(2) Executing - shutdown --include-locators=true

Shutdown is triggered
```