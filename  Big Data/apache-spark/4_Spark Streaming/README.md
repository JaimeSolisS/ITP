# Spark Streaming 


Spark Streaming is an extensional core Spark API that enables scalable, high throughput, fault-tolerant stream processing of live data.

Data can be ingested from many sources like:
- Kafka
- Flume
- HDFS/S3
- Kinesis
- Twitter
- TCP sockets

This data can be processed using complex algorithms expressed with high-level functions like map, reduce, join and window function. Processed data can be pushed out to filesystems, databases, and live dashboards. 

Machine learning, graph processing or spark SQL can be applied on top the layer data.

## How does it work?
![how does it work](https://blog.gft.com/es/wp-content/uploads/sites/3/2018/07/imagen-3.png) 

## DStreams

Spark Streaming provides a high-level abstraction called Discretized Dtream or DStream, this represent a continuos stream of data. 

Dstreams can be created either from input data streams from sources such as Kafka or Flume, or by applying high-level operations on other DStreams.  

DStream is represented as a sequence of RDDs in a certain point of time

## DStream Operations
DStreams is nothing but the sequences of RDDs, so the operations applied to a DStream translates to operations to the sequence of RDDs. The result of an operation over a DStream is another DStream.

###  Points to Remember

- Once a context has been started, no new streaming computations can be set up or added to it
- Once a context has been stopped, it cannot be restarted
- Only one StreamingContext can be active in a JVM at the same time
- stop() on StreamingContext also stops the SparkContext. To stop only the StreamingContext, set the optional parameter of stop() called stopSparkContext to false
- A SparkContext can be re-used to create multiple StreamingContexts, as long as the previous StreamingContext is stopped (withput stopping the SparkContext) before the next StreamingContext is created

## DStreams and Receivers
- Receiver is an object which receives the data from a source and stores it in Spark's memory for processing

Spark Streaming provides two categories of built-in streaming sources:
- Basic sources: Sources directly availbale in the StreamingContext API. Ex. file systems and socket connections
- Advanced sources: Sources like Kafka, Flume, Kinesis, etc. are available thorugh extra utility classes

### Points to remember
- When running a Spark Streaming program locally, do not use "local" or "local[1]" as the master URL
- Extending the logic to running on a cluster, the number of cores allocated to the Spark Streaming application must be more than the number of receivers. Otherwise the system will receive data, but not be able to process it

## Basic Sources

- File Streams
    - For reading data from files on any file system compatible with the HDFS API (that is, HDFS, S3, NFS, etc.), a DStream can be created as `streamingContext.fileStream[KeyClass, ValueClass, InputFormatClass](dataDirectory)
- Streams based on Custom Receivers
- Queue of RDD's as a Stream

## Receiver Reliability 

- **Reliable Receiver** - A reliable receiver correctly sends acknowledgment to a reliable source when the data has been received and stored in Spark with replication
- **Unreliable Receiver** - An unreliable receiver does not send acknowledgement to a source. This can be used for sources that do not support acknowledgment, or even for reliable sources when one does not want or need to go into the complexity of acknowledgment

## Transformation on DStreams

On DStreams, count is a transformation, not an action.

## Output Operations

Similar to RDD actions

## Window Operation

Grouping multiple batches as one window.

![windows operation](https://programmerclick.com/images/943/a99c09a523542ebc1ab3d7a7516e2ecf.png)

### Output Operations

#### Points to remember

- DStreams are executed lazily by the output operations, just like RDDs are lazily executed by RDD actions
- By default, output operations are executed *one-at-a-time. And they are executed in the order they are defined in the application. 

## Caching / Persistence
- Metadata checkpointing
    - Configuration - The configuration that was used to create the streaming application.
    - DStream operations - The set of DSstreams operations that define the streamign application
    - Incomplete batches - Batches whose jobs are queued but have not completed yet
- Data checkpointing 
    - Saving of the generated RDDs to reliable storage
    
## When to enable Checkpointing
- **Usage of stateful transformations** - If either updateStateByKey or reduceByKeyAndWindow (with inverse function) is used in the application, then the checkpoint directory must be provided to allow for periodic RDD checkpointing
- **Recovering from failures of the driver running the application** - Metadata checkpoints are used to recover with progress information. 

### Checkpointg gotcha

**You need clear checkpointing dir between Spark version Upgrades**

## Structured Streaming 

- Scalable and fault tolerant
- Built on Spark SQL
    - Dataframe
    -  Datasets
- Guarantees end-to-end exactly once fault-tolerance through checkpointing and Write-Ahead-Logs

## Output Mode

- Complete Mode - The entire updated Result Table will be writtten to the external storage. It is up to the storage connector to decide how to handle writing of the entire table
- Append Mode - Only the new rows appended in the Result Table since the last trigger will be written to the external storage. This is applicable only on the queries where exisiting rows in the Result Table are not expected to change
- Update Mode - Only the rows that were updated in the Result Table since the last trigger will be written to the external storage.

## Input Sources

- File Source
- Kafka Source
- Socket Source (testing)
- Rate Source (testing)

## Best Practices

### When do you use streaming?

If data needs to be read in real time. 

### Monitor & Manage

When running a streaming application, it should run in a cluster mode. We can monitor and manage the stream statistics in the spark application master.Processing time should be less than the batch interval. If it is more than the batch interval, you will be left behind with more batches to process. The scheduling delay needs to be less.

Custom Dashboards for metrics with:
- Graphite 
- Grafana

## Prevent data loss

Streming application is moving target. We can use checkpointing.

- File based sources
    - Metadata
    - Data
- Receiver based sources
    - Enable Checkpointing
    - Enable WAL
### Graceful Shutdown    

- Offset known
- State stored externally
- Stop when batch is finished

**How to be graceful?**
- Store offsdets externally (Zk, hbase, mysql)
- Thread hook

### Performance Tuning 
- Memory Tuning
- Setting the Right Batch Interval
- Data SerDe
- Level of Parallelism