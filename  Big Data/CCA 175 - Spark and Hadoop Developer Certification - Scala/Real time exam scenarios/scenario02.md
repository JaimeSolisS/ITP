# CCA 175 Real Time Exam Scenario 2 | Read Parquet File | Write as JSON in HDFS with GZIP Compression

Data Description

1. All the order records are stored in the HDFS directory
   /user/spark/dataset/retail_db/orders_parquet
2. Data is in Parquet format

Output Requirement

1.  Output all the completed orders that is records where order_status value is "COMPLETE“
2.  Use JSON format for the output files
3.  Place the result data in HDFS directory /user/spark/dataset/result/scenario2/solution
4.  order_date should be in format yyyy-MM-dd
5.  Compress the output using gzip compression
6.  Output should only contain order_id, order_date,status

## Solution

### Read Data

```
val orders = spark.read.parquet("/user/jsolis/dataset/retail_db/orders_parquet")
```

### filter

```
val res =
orders.filter($"order_status" === "COMPLETE").
withColumn("order_date", to_date(from_unixtime($"order_date"/1000))).
select($"order_id", $"order_date", $"order_status")
```

### Export

```
res.
write.
mode("overwrite").
option("compression", "gzip").
format("json").
save("/user/jsolis/dataset/result/scenario2/solution")
```

### Check solution

```
scala> spark.read.json("/user/jsolis/dataset/result/scenario2/solution").show
21/07/12 17:52:54 WARN streaming.FileStreamSink: Error while looking for metadata directory.
+----------+--------+------------+
|order_date|order_id|order_status|
+----------+--------+------------+
|2013-07-24|       3|    COMPLETE|
|2013-07-24|       5|    COMPLETE|
|2013-07-24|       6|    COMPLETE|
|2013-07-24|       7|    COMPLETE|
|2013-07-24|      15|    COMPLETE|
|2013-07-24|      17|    COMPLETE|
|2013-07-24|      22|    COMPLETE|
|2013-07-24|      26|    COMPLETE|
|2013-07-24|      28|    COMPLETE|
|2013-07-24|      32|    COMPLETE|
|2013-07-24|      35|    COMPLETE|
|2013-07-24|      45|    COMPLETE|
|2013-07-24|      56|    COMPLETE|
|2013-07-24|      63|    COMPLETE|
|2013-07-24|      65|    COMPLETE|
|2013-07-24|      67|    COMPLETE|
|2013-07-24|      71|    COMPLETE|
|2013-07-24|      72|    COMPLETE|
|2013-07-24|      76|    COMPLETE|
|2013-07-24|      80|    COMPLETE|
+----------+--------+------------+
only showing top 20 rows
```

```
hdfs dfs -ls /user/jsolis/dataset/result/scenario2/solution
Found 2 items
-rw-r--r--   3 jsolis jsolis          0 2021-07-12 17:52 /user/jsolis/dataset/result/scenario2/solution/_SUCCESS
-rw-r--r--   3 jsolis jsolis      79328 2021-07-12 17:52 /user/jsolis/dataset/result/scenario2/solution/part-00000-e3dcbc1a-529d-409f-a185-4b14481c7793-c000.json.gz
```
