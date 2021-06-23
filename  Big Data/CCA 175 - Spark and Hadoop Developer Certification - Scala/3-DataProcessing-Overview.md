# Data Processing Overview

## Summary

- Read files
  `spark.read.csv("path")`
  `spark.read.json("path")`
- Infer schema
  `spark.read.option("inferSchema", "true").csv("path")`
- Option
  `spark.read.option("inferSchema", "true").option("sep", ",").csv("path")`
- Create schema
  `spark.read.schema("""columnName TYPE, columnName2 TYPE""").csv("path")`
- Format as parameter
  `spark.read.schema("""columnName TYPE""").format("csv").load("path")`
  `spark.read.schema("""columnName TYPE""").format("parquet").load("path")`
- Write File
  `df.write.parquet("path")`
  `df.write.csv("path")`
  ```
  df.write.
     coalesce(1) //coalesce will be covered in detail at a later point in time
     mode("overwrite").
     option("compression", "none").
     format("parquet").
     save("path")

  //define delimiter
  df.write.
     coalesce(1) //coalesce will be covered in detail at a later point in time
     mode("overwrite").
     option("compression", "none").
     option("sep", "\t")
     format("csv").
     save("path")
  ```
- Validate results inside spark

  ```
  import sys.process._

  "hdfs dfs -ls /user/USER/path..." !
  ```

## Pre-requisites and Module Introduction

Let us understand prerequisites before getting into the module.

- Good understanding of Data Processing using Scala.
- Data Processing Life Cycle
- Reading Data from files
- Processing Data using APIs
- Writing Processed Data back to files
- We can also use Databases as sources and sinks. It will be covered in relevant modules.
- We can also read data in streaming fashion which is out of the scope of this course.

We will get an overview of the Data Processing Life Cycle by the end of the module.

- Read data from the file.
- Preview the schema and data to understand the characteristics of the data.
- Get an overview of Data Frame APIs as well as functions used to process the data.
- Check if there are any duplicates in the data.
- Get an overview of how to write data in Data Frames to Files using File Formats such as Parquet using Compression.
- We will deep dive into Data Frame APIs to process the data in subsequent modules.

## Starting Spark Context

Let us understand more about Spark Context and also how to start it using SparkSession.

- `SparkSession` is a class that is part of `org.apache.spark.sql` package.
- When Spark application is submitted using `spark-submit` or `spark-shell` or `pyspark`, a web service called as Spark Context will be started.
- Here is the example about how Spark Shell can be launched locally.

```
spark-shell \
    --master "local[*]"
```

- Here is the example about how Spark Shell can be launched on multinode cluster such as our labs.

```
spark2-shell \
    --master yarn \
    --conf spark.ui.port=0
```

- **Make sure to understand the enviroment and use appropriate command to launch Spark Shell.**
- Spark Context maintains the context of all the jobs that are submitted until it is killed.
- `SparkSession` is nothing but wrapper on top of Spark Context.
- We need to first create SparkSession object with any name. But typically we use `spark`. Once it is created, several APIs will be exposed including `read`.
- We need to at least set Application Name and also specify the execution mode in which Spark Context should run while creating `SparkSession` object.
- We can use `appName` to specify name for the application and `master` to specify the execution mode.
- Below is the sample code snippet which will start the Spark Session object for us.

```
import org.apache.spark.sql.SparkSession

val spark = SparkSession.
    builder.
    config("spark.ui.port", "0").
    appName("Data Processing - Overview").
    master("yarn").
    getOrCreate
```

`spark`
`spark.sparkContext.getConf.getAll.foreach(println)`

## Overview of Spark read APIs

Let us get the overview of Spark read APIs to read files of different formats.

- `spark` has a bunch of APIs to read data from files of different formats.
- All APIs are exposed under `spark.read`
- `text` - to read single column data from text files as well as reading each of the whole text file as one record.
- `csv`- to read text files with delimiters. Default is a comma, but we can use other delimiters as well.
- `json` - to read data from JSON files
- `orc` - to read data from ORC files
- `parquet` - to read data from Parquet files.
- We can also read data from other file formats by plugging in and by using `spark.read.format`
- We can also pass options based on the file formats. Go to Scala Docs which will be provided as part of the certification exam to get the list of options available.
- `inferSchema` - to infer the data types of the columns based on the data.
- `header` - to use header to get the column names in case of text files.
- `schema` - to explicitly specify the schema.
- Let us see an example about how to read delimited data from text files.

### Read first few lines of file

```hdfs dfs -cat /user/jsolis/data/retail_db/orders/part-00000|more
[jsolis@yboledge02 ~]$ hdfs dfs -cat /user/jsolis/data/retail_db/orders/part-00000|more
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3,2013-07-25 00:00:00.0,12111,COMPLETE
4,2013-07-25 00:00:00.0,8827,CLOSED
5,2013-07-25 00:00:00.0,11318,COMPLETE
6,2013-07-25 00:00:00.0,7130,COMPLETE
7,2013-07-25 00:00:00.0,4530,COMPLETE
8,2013-07-25 00:00:00.0,2911,PROCESSING
9,2013-07-25 00:00:00.0,5657,PENDING_PAYMENT
10,2013-07-25 00:00:00.0,5648,PENDING_PAYMENT
11,2013-07-25 00:00:00.0,918,PAYMENT_REVIEW
12,2013-07-25 00:00:00.0,1837,CLOSED
13,2013-07-25 00:00:00.0,9149,PENDING_PAYMENT
14,2013-07-25 00:00:00.0,9842,PROCESSING
15,2013-07-25 00:00:00.0,2568,COMPLETE
16,2013-07-25 00:00:00.0,7276,PENDING_PAYMENT
17,2013-07-25 00:00:00.0,2667,COMPLETE
18,2013-07-25 00:00:00.0,1205,CLOSED
19,2013-07-25 00:00:00.0,9488,PENDING_PAYMENT
20,2013-07-25 00:00:00.0,9198,PROCESSING
21,2013-07-25 00:00:00.0,2711,PENDING
--More--
```

```
scala> val orders = spark.read.csv("/user/jsolis/data/retail_db/orders")
21/06/21 00:10:38 WARN streaming.FileStreamSink: Error while looking for metadata directory.
orders: org.apache.spark.sql.DataFrame = [_c0: string, _c1: string ... 2 more fields]

scala> orders.show
+---+--------------------+-----+---------------+
|_c0|                 _c1|  _c2|            _c3|
+---+--------------------+-----+---------------+
|  1|2013-07-25 00:00:...|11599|         CLOSED|
|  2|2013-07-25 00:00:...|  256|PENDING_PAYMENT|
|  3|2013-07-25 00:00:...|12111|       COMPLETE|
|  4|2013-07-25 00:00:...| 8827|         CLOSED|
|  5|2013-07-25 00:00:...|11318|       COMPLETE|
|  6|2013-07-25 00:00:...| 7130|       COMPLETE|
|  7|2013-07-25 00:00:...| 4530|       COMPLETE|
|  8|2013-07-25 00:00:...| 2911|     PROCESSING|
|  9|2013-07-25 00:00:...| 5657|PENDING_PAYMENT|
| 10|2013-07-25 00:00:...| 5648|PENDING_PAYMENT|
| 11|2013-07-25 00:00:...|  918| PAYMENT_REVIEW|
| 12|2013-07-25 00:00:...| 1837|         CLOSED|
| 13|2013-07-25 00:00:...| 9149|PENDING_PAYMENT|
| 14|2013-07-25 00:00:...| 9842|     PROCESSING|
| 15|2013-07-25 00:00:...| 2568|       COMPLETE|
| 16|2013-07-25 00:00:...| 7276|PENDING_PAYMENT|
| 17|2013-07-25 00:00:...| 2667|       COMPLETE|
| 18|2013-07-25 00:00:...| 1205|         CLOSED|
| 19|2013-07-25 00:00:...| 9488|PENDING_PAYMENT|
| 20|2013-07-25 00:00:...| 9198|     PROCESSING|
+---+--------------------+-----+---------------+
only showing top 20 rows
```

### InferSchema

```
scala> val orders = spark.read.option("inferSchema", "true").csv("/user/jsolis/data/retail_db/orders")
21/06/21 00:13:06 WARN streaming.FileStreamSink: Error while looking for metadata directory.
orders: org.apache.spark.sql.DataFrame = [_c0: int, _c1: timestamp ... 2 more fields]
```

### Create Schema

```
val orders = spark.read.schema("""order_id INT, order_date TIMESTAMP, order_customer_id INT, order_status STRING""").csv("/user/jsolis/data/retail_db/orders")
```

- format as parameter

```
val orders = spark.read.schema("""order_id INT, order_date TIMESTAMP, order_customer_id INT, order_status STRING""").format("csv).load("/user/jsolis/data/retail_db/orders")
```

## JSON Files

- Reading JSON data from text files. We can infer schema from the data as each JSON object contain both column name and value.
- Example for JSON

```
{ "order_id": 1, "order_date": "2013-07-25 00:00:00.0", "order_customer_id": 12345, "order_status": "COMPLETE" }
```

```
scala> val orders = spark.read.json("/user/jsolis/data/retail_db_json/orders")
------
val orders = spark.
            read.
            schema("""order_id INT, order_date TIMESTAMP, order_customer_id INT, order_status STRING""").
            json("/user/jsolis/data/retail_db/orders")
-----
val orders = spark.
            read.
            option("inferSchema", "false").
            schema("""order_id INT, order_date TIMESTAMP, order_customer_id INT, order_status STRING""").
            json("/user/jsolis/data/retail_db/orders")

val orders = val orders = spark.read.format("json").load("/user/jsolis/data/retail_db_json/orders")

scala> orders.show
+-----------------+--------------------+--------+---------------+
|order_customer_id|          order_date|order_id|   order_status|
+-----------------+--------------------+--------+---------------+
|            11599|2013-07-25 00:00:...|       1|         CLOSED|
|              256|2013-07-25 00:00:...|       2|PENDING_PAYMENT|
|            12111|2013-07-25 00:00:...|       3|       COMPLETE|
|             8827|2013-07-25 00:00:...|       4|         CLOSED|
|            11318|2013-07-25 00:00:...|       5|       COMPLETE|
|             7130|2013-07-25 00:00:...|       6|       COMPLETE|
|             4530|2013-07-25 00:00:...|       7|       COMPLETE|
|             2911|2013-07-25 00:00:...|       8|     PROCESSING|
|             5657|2013-07-25 00:00:...|       9|PENDING_PAYMENT|
|             5648|2013-07-25 00:00:...|      10|PENDING_PAYMENT|
|              918|2013-07-25 00:00:...|      11| PAYMENT_REVIEW|
|             1837|2013-07-25 00:00:...|      12|         CLOSED|
|             9149|2013-07-25 00:00:...|      13|PENDING_PAYMENT|
|             9842|2013-07-25 00:00:...|      14|     PROCESSING|
|             2568|2013-07-25 00:00:...|      15|       COMPLETE|
|             7276|2013-07-25 00:00:...|      16|PENDING_PAYMENT|
|             2667|2013-07-25 00:00:...|      17|       COMPLETE|
|             1205|2013-07-25 00:00:...|      18|         CLOSED|
|             9488|2013-07-25 00:00:...|      19|PENDING_PAYMENT|
|             9198|2013-07-25 00:00:...|      20|     PROCESSING|
+-----------------+--------------------+--------+---------------+
only showing top 20 rows

```

## Previewing Schema and Data

Here are the APIs that can be used to preview the schema and data.

- `printSchema` can be used to get the schema details.
- `show` can be used to preview the data. It will typically show first 20 records where output is truncated.
- `describe` can be used to get statistics out of our data.
- We can pass number of records and set truncate to false while previewing the data.

```
scala> orders.printSchema
root
 |-- order_customer_id: long (nullable = true)
 |-- order_date: string (nullable = true)
 |-- order_id: long (nullable = true)
 |-- order_status: string (nullable = true)
```

```
scala> orders.show(10, false)  //truncate = false to get full results
[Stage 9:>
+-----------------+---------------------+--------+---------------+
|order_customer_id|order_date           |order_id|order_status   |
+-----------------+---------------------+--------+---------------+
|11599            |2013-07-25 00:00:00.0|1       |CLOSED         |
|256              |2013-07-25 00:00:00.0|2       |PENDING_PAYMENT|
|12111            |2013-07-25 00:00:00.0|3       |COMPLETE       |
|8827             |2013-07-25 00:00:00.0|4       |CLOSED         |
|11318            |2013-07-25 00:00:00.0|5       |COMPLETE       |
|7130             |2013-07-25 00:00:00.0|6       |COMPLETE       |
|4530             |2013-07-25 00:00:00.0|7       |COMPLETE       |
|2911             |2013-07-25 00:00:00.0|8       |PROCESSING     |
|5657             |2013-07-25 00:00:00.0|9       |PENDING_PAYMENT|
|5648             |2013-07-25 00:00:00.0|10      |PENDING_PAYMENT|
+-----------------+---------------------+--------+---------------+
only showing top 10 rows
```

```
scala> orders.describe().show(false) // Not very popular
+-------+-----------------+---------------------+------------------+---------------+
|summary|order_customer_id|order_date           |order_id          |order_status   |
+-------+-----------------+---------------------+------------------+---------------+
|count  |68883            |68883                |68883             |68883          |
|mean   |6216.571098819738|null                 |34442.0           |null           |
|stddev |3586.205241263959|null                 |19884.953633337947|null           |
|min    |1                |2013-07-25 00:00:00.0|1                 |CANCELED       |
|max    |12435            |2014-07-24 00:00:00.0|68883             |SUSPECTED_FRAUD|
+-------+-----------------+---------------------+------------------+---------------+
```

## Overview of Data Frame APIs

Let us get an overview of Data Frame APIs to process data in Data Frames.

- Row Level Transformations or Projection of Data can be done using `select`, `selectExpr`, `withColumn`, `drop` on Data Frame.
- We typically apply functions from `org.apache.spark.sql.functions` on columns using `select` and `withColumn`
- Filtering is typically done either by using `filter` or `where` on Data Frame.
- We can pass the condition to `filter` or `where` either by using SQL Style or Programming Language Style.
- Global Aggregations can be performed directly on the Data Frame.
- By Key or Grouping Aggregations are typically performed using `groupBy` and then aggregate functions using `agg`
- We can sort the data in Data Frame using `sort` or `orderBy`
- We will talk about Window Functions later. We can use use Window Functions for some advanced Aggregations and Ranking.

### Tasks

Let us understand how to project the data using different options such as `select`, `selectExpr`, `withColumn`, `drop`.

- Create Dataframe **employees** using Collection

```
scala> val employees = List((1, "Scott", "Tiger", 1000.0, "united states"),
                            (2, "Henry", "Ford", 1250.0, "India"),
                            (3, "Nick", "Junior", 750.0, "united KINGDOM"),
                            (4, "Bill", "Gomes", 1500.0, "AUSTRALIA")
                           )
employees: List[(Int, String, String, Double, String)] = List((1,Scott,Tiger,1000.0,united states), (2,Henry,Ford,1250.0,India), (3,Nick,Junior,750.0,united KINGDOM), (4,Bill,Gomes,1500.0,AUSTRALIA))
```

#### Create Dataframe

```
val employeesDF = employees.
    toDF("employee_id",
         "first_name",
         "last_name",
         "salary",
         "nationality"
        )
```

`employeesDF.show`
`employeesDF.printSchema`

- Project employee first name and last name.

```
employeesDF.select("first_name", "last_name").show
```

- Project all the fields except for Nationality

```
employeesDF.drop("nationality").show
```

## Overview of Functions

Let us get an overview of different functions that are available to process data in columns.

- While Data Frame APIs work on the Data Frame, at times we might want to apply functions on column values.
- Functions to process column values are available under `org.apache.spark.sql.functions`. These are typically used in select or withColumn on top of Data Frame.
- There are approximately 300 pre-defined functions available for us.
- Some of the important functions can be broadly categorized into String Manipulation, Date Manipulation, Numeric Functions and Aggregate Functions.
- String Manipulation Functions
- Concatenating Strings - `concat`
- Getting Length - `length`
- Trimming Strings - `trim`,` rtrim`, `ltrim`
- Padding Strings - `lpad`, `rpad`
- Extracting Strings - `split`, `substring`
- Date Manipulation Functions
- Date Arithmetic - `date_add`, `date_sub`, `datediff`, `add_months`
- Date Extraction - `dayofmonth`, `month`, `year`, `date_format`
- Get beginning period - `trunc`, `date_trunc`
- Numeric Functions - `abs`, `greatest`
- Aggregate Functions - `sum`, `min`, `max`
- There are some special functions such as `col`, `lit` etc.
  - `col` is used to convert string to column type. It can also be invoked using **$** after importing `spark.implicits._`
  - `lit` is used to convert a literal to column value so that it can be used to generate derived fields by manipulating using literals.

### Tasks

Let us perform a task to understand how functions are typically used.

- Project full name by concatenating first name and last name along with other fields excluding first name and last name.

* Using col and lit

```
scala> employeesDF.show
+-----------+----------+---------+------+--------------+
|employee_id|first_name|last_name|salary|   nationality|
+-----------+----------+---------+------+--------------+
|          1|     Scott|    Tiger|1000.0| united states|
|          2|     Henry|     Ford|1250.0|         India|
|          3|      Nick|   Junior| 750.0|united KINGDOM|
|          4|      Bill|    Gomes|1500.0|     AUSTRALIA|
+-----------+----------+---------+------+--------------+
```

```
employeesDF.
    withColumn("full_name", concat(col("first_name"), lit(", "), col("last_name"))).
    drop("first_name", "last_name").
    show

+-----------+------+--------------+------------+
|employee_id|salary|   nationality|   full_name|
+-----------+------+--------------+------------+
|          1|1000.0| united states|Scott, Tiger|
|          2|1250.0|         India| Henry, Ford|
|          3| 750.0|united KINGDOM|Nick, Junior|
|          4|1500.0|     AUSTRALIA| Bill, Gomes|
+-----------+------+--------------+------------+
```

- SQL style

```
scala> employeesDF.
    selectExpr("employee_id",
               "concat(first_name, ', ', last_name) AS full_name",
               "salary",
               "nationality"
              ).
    show
+-----------+------------+------+--------------+
|employee_id|   full_name|salary|   nationality|
+-----------+------------+------+--------------+
|          1|Scott, Tiger|1000.0| united states|
|          2| Henry, Ford|1250.0|         India|
|          3|Nick, Junior| 750.0|united KINGDOM|
|          4| Bill, Gomes|1500.0|     AUSTRALIA|
+-----------+------------+------+--------------+
```

```
scala> spark.sql("SHOW functions").show
+--------------------+
|            function|
+--------------------+
|                   !|
|                   %|
|                   &|
|                   *|
|                   +|
|                   -|
|                   /|
|                   <|
|                  <=|
|                 <=>|
|                   =|
|                  ==|
|                   >|
|                  >=|
|                   ^|
|                 abs|
|                acos|
|          add_months|
|                 and|
|approx_count_dist...|
+--------------------+
only showing top 20 rows
```

## Overview of Spark Write APIs

Let us understand how we can write Data Frames to different file formats.

- All the batch write APIs are grouped under write which is exposed to Data Frame objects.
- All APIs are exposed under spark.read
- `text` - to write single column data to text files.
- `csv` - to write to text files with delimiters. Default is a comma, but we can use other delimiters as well.
- `json` - to write data to JSON files
- `orc` - to write data to ORC files
- `parquet` - to write data to Parquet files.
- We can also write data to other file formats by plugging in and by using `write.format`, for example **avro**
- We can use options based on the type using which we are writing the Data Frame to.
- `compression` - Compression codec (`gzip`, `snappy` etc)
- `sep` - to specify delimiters while writing into text files using **csv**
- We can `overwrite` the directories or `append` to existing directories using `mode`
- Create copy of orders data in **parquet** file format with no compression. If the folder already exists overwrite it. Target Location: **/user/[YOUR_USER_NAME]/retail_db/orders**
- When you pass options, if there are typos then options will be ignored rather than failing. Be careful and make sure that output is validated.
- By default the number of files in the output directory is equal to number of tasks that are used to process the data in the last stage. However, we might want to control number of files so that we don"t run into too many small files issue.
- We can control number of files by using `coalesce`. It has to be invoked on top of Data Frame before invoking `write`.

```
scala> df.write.
bucketBy   format       jdbc   mode     options   parquet       save          sortBy
csv        insertInto   json   option   orc       partitionBy   saveAsTable   text
```

```
val orders = spark.read.schema("""order_id INT, order_date STRING, order_customer_id INT, order_status STRING""").format("csv").load("/user/jsolis/data/retail_db/orders")
```

- using write.parquet

```
orders.write.parquet("/user/jsolis/training/retail_db/orders")
---
orders.write.
        mode("overwrite").
        option("compression", "none").
        parquet("/user/jsolis/training/retail_db/orders")

```

- using write.format.parquet

```
orders.write.
        mode("overwrite").
        option("compression", "none").
        format("parquet").
        save("/user/jsolis/training/retail_db/orders")
```

```
orders.write.
        coalesce(1) //coalesce will be covered in detail at a later point in time
        mode("overwrite").
        option("compression", "none").
        format("parquet").
        save("/user/jsolis/training/retail_db/orders")
```

- to validate results inside spark

```
import sys.process._

"hdfs dfs -ls /user/jsolis/training/retail_db/orders" !
```

**Parquet files cant be read using cat, they must be read with spark**

## Conclusion

Let us recap about key takeaways from this module.

- APIs to read the data from files into Data Frame.
- Previewing Schema and the data in Data Frame.
- Overview of Data Frame APIs and Functions
- Writing data from Data Frame into Files
- Reorganizing the airlines data by month
- Simple APIs to analyze the data.

Now it is time for us to deep dive into APIs to perform all the standard transformations as part of Data Processing.
