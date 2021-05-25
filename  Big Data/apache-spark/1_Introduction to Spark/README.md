# Introduction to Spark

## Distributed Computing Before Spark 

### New Paradigms 

- Take the computing to the data
- Perform local cumpations, aggregate the results *This is the MapReduce paradigm
- Don't do expensive transformations
- Scale by adding nodes 

### MapReduce
- Framework for processing HDFS data
- Original use case was for distributed batch processing
- Map-Shuffle-Reduce paradigm for processing 
- MapReduce daemons package algorithms to where the data is isntead of moving data around 
- Also manages all of the resources required for the jobs/tasks

### How MapReduce Works
[<img src="https://www.guru99.com/images/Big_Data/061114_0930_Introductio1.png">](https://www.guru99.com/introduction-to-mapreduce.html)

- An input to a MapReduce job is divided into fixed-size pieces called input splits. Input split is a chunk of the input that is consumed by a single map. 
- Then data in each split is passed to a mapping function to produce output values. 
- In Shuffling task is to consolidate the relevant records from Mapping phase output. In our example, the same words are clubed together along with their respective frequency.
- In Reducing, output values from the Shuffling phase are aggregated. This phase combines values from Shuffling phase and returns a single output value. In short, this phase summarizes the complete dataset.

### Challenge with MapReduce 
- No Real Time Processing
- Everything cannot be implemented as MapReduce
- Expensive Job creation and startup 
  - Iterative algorithms means multiple jobs in MapReduce
- Skews in the data
  - Creates stragglers in Reduce Phase 
- Suffle is costly operation 

# Spark 
- Distributed processing framework
- Suports Java, Scala, Python and R 
  - Easier to use
  - Less lines of code (Scala).
- Unified stack for different workloads 
  - SQL/DataFrames, Machine Learning, Graph, Streaming
- Supports multiple environments (yarn, docker, kubernetes...).
<img src="https://www.kdnuggets.com/wp-content/uploads/spark-7-1.jpg">

## RDD 

RDD = Resilient Distributed Dataset  
**Resilient**: Fault-tolerant  
**Distributed**: Stored across multiple nodes  
**Dataset**: Collection of partitioned data
    

- **Read only** partitioned collection of records.
- Immutable once constructed
- Lineage
- Rich set of operations
- Coarse-Grained Transformation **LOOK AT THIS**
  - Coarse  transformation is applied to a set with more than one record
  - Grained is a tranformation to a single record
  
- can only be created through deterministic operations on 
  - Data 
  - other RDD's.
- Persistence on RDD
- Partitioning on RDD
- Checkpointing on RDD

## Dataframe

- A distributed collection of data organized into named columns
  - Construct DataFrames via existing dataframe or files in storage system)
- Inspired by dataframe in R and Python
- Ability to scale from kilobytes in a single computer to petabytes on a large cluster
- Support for a wide array of data formats and storage systems 
- Integration with all big data toolind and infrastructure via Spark
- APIs for Python, Java, Scala and R.
- Faster than RDD
 - Spark Python DF = Spark Scala DF < RDD Scala < RDD Python
 
## Datasets 

- Strongly-typed, immutable collection of objects that are mapped to a relational schema 
- It is an extension of the Dataframe API
- Data is encoded using Spark's Tungsten Binary Format
- Uses Catalyst Optimizer

- **Best of both words (RDD and DataFrame)**
- **Faster than RDD and better memory usage**

# Spark Architecture

- Application: the set of jobs managed by a single driver
  - Driver program
    - Spark context - the master which communicates with each worker node
  - Cluster manager - deals with allocation of resources
  - Worker node - node manager 
    - Executor 
      - Cache
      - Stage - set of tasks that can be executed in parallel
        - Job - set of stages executed as a result of an *action* 
          - Tasks - individual unit of work sent to one executor
          
<img src="https://spark.apache.org/docs/latest/img/cluster-overview.png">

# Deploy and manage

## Deploy 

- Spark supports multiple environments:
  - Deploy on Hadoop, Docker or Kubernetes.
## Manage
- Hadoop -> Yarn 
- Docker -> Apache Mesos.

## Deploy modes
- Client (default) - the driver runs on the machine that the spark application was launched    
- Cluster - the driver runs on a random node in a cluster

# Spark Development

- Spark shell
  - Interactive - for learning or data exploration.
  - REPL - Read/Evaluate/Print/Loop
  - Python or Scala.
- Spark applications
  - For large scale data processing
  - Python, Scala, or Java.
