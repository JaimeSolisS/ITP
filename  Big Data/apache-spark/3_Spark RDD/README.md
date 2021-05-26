# Spark RDD

- Internals of spark
- What are the types of variables?

## Spark Context
- Main Entry point for Spark Functionality 
- Represents connection to a Spark Cluster
- Current Status of Spark Application
- Used to create
    - RDD'd
    - Accumulators
    - Broadcast variables
- Only one SparkContext may be active per JVM

A Spark context contains:
- RDD graph
- DAGScheduler
- Task scheduler
- Scheduler backend
- Listener bus
- Block manager

Spark Context Methods:

- addFile, addJar, appName, applicationId, bradcast, isStopped...
- deployMode, getConf, haddopFile, hadoppRDD, listFiles...

## Accumulators
Ex. Word count - If a spark application runs on multiple nodes at the same time, how a program running in node can know the count in a program running in another noder?

- Shared Variable
- Variables that are only "added" to through an associative and commutative operation and can therefore be efficiently supported in parallel
- Used to implement Counters
- Natively supports accumulators of numeric types

## Broadcast Variables
- Shared Variable
- Allows developers to keep a read-only variable cached on all worker nodes

## RDD
Resilent Distributed Dataset
- Read Only, partitioned collection of records
- Immutable once constructed
- Lineage
- Rich set of Operations
- Coarse-Grained Transformations 

- Can only be created through deterministic operations on 
    - Data
    - Other RDD's
- Persistence
- Partitioning
- Checkpointing

### Create RDD

```bash
val data Array(1,2,3,4,5)
val rdd = sc.parallelize(data)
```
Simple operations on rdd:

```bash
rdd.reduce((a,b)=>a+b) #Sum all elements
rdd.getNumPartitions #Get number of partitions
```

## Operations

Every RDD performs two sets of operations 
- Transformations
    - A transformations is changing the form of the RDD from one stage to another stage (Creates a new dataset from an existing one)
- Actions 
    - Materializing the transformations applied (returns a value to the driver program afetr running a computation on the dataset)

## RDD Lineage
Carrying forward the origin of data. There are two types of dependencies
- Narrow Dependencies
    - The child has a one to one relationship with its parent's partition
- Wide Dependencies
    - The child has a one to many relationship with its parent's partitions
![RDD Lineage](https://programmersought.com/images/990/c3eef91f857f397ee62268325737fdce.png)

Transformations are only computed after actions are called 

![Operations](https://blog.knoldus.com/wp-content/uploads/2019/10/Screenshot-from-2019-09-29-11-53-26.png)
![Operations2](https://blog.knoldus.com/wp-content/uploads/2019/10/Screenshot-from-2019-09-29-11-53-31.png)

## Transformations

- map
    - Return a new RDD by applying a function to each element of this RDD 
    - *takes a function as an argument, performs the operation and returns the new RDD by applying the function to each element of the RDD*)
- flatMap
    - Return a new RDD by first applying a function to all elements of this RDD, and then flattening the results 
    - *same as map, but flattens the results (takes the values out of the collection)
- mapPartitions
     - Return a new RDD by applying to each partition of the RDD
     - same as map, but the function is applied to each partition of the RDD **instead of each element** of the RDD. 
- mapPartitionsWithIndex
    - Return a new RDD by applying a function to each partition of the RDD (same as mapPartitions), while tracking the index of the original partition
- getNumPartitions
    - Return the number of partitions (available in the RDD)
- filter(f)
    - Return a new RDD containing only the elements that satisfy a condition
- distinct(numPartitions=None)
    - Return a new RDD containing the distinct elements in the current RDD 
- sample(withReplacement, fraction, seed=None)
    - Return a sampled subset of this RDD.
- groupBy(f, numPartitions=None, partitionFunc)
    - Return an RDD of grouped items.
    
## Actions
- foreach
    - Applies a function to all elements of the current RDD
- collect()
    - Return a list thta contains all of the elements in the current RDD
    - `This method should only be used if the resulting array is expected to be small, as all of the data is loaded into the driver's memory`
- reduce(f)
    - Reduces the elements of the RDD using the specified commutative and associative binary operator. Currently reduces partitions locally
- max, min, count
    - similar to SQL queries

There are 2 types of actions:

- Distributed
    - Writing data to target system like HDFS localfile system or database
- Driver 
    - sum, min, average, max 
    
## Different types of RDD's

![Types of RDD's](https://i.pinimg.com/originals/a8/ee/1d/a8ee1df6532cbb65aef1c3343405b820.jpg)