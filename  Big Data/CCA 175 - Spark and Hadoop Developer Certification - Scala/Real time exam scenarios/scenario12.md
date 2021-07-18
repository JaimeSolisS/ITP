# CCA 175 Real Time Exam Scenario 12 | Read PARQUET Data | Save as JSON with Snappy Compression

Data Description
All the order records are stored at
/user/spark/dataset/retail_db/orders_parquet
Data is in parquet format

Output Requirement
Output all the PENDING orders in July 2013
Use JSON format for the output files
Place the result data in HDFS directory /user/spark/dataset/result/scenario11/solution
Result should only contain records that have order_status value as "PENDING"
order_date should be in format yyyy-MM-dd
Compress the output using snappy compression and output should only contain order_date, order_status

## Solution

### Read file

```
val orders = spark.read.parquet("/user/jsolis/dataset/retail_db/orders_parquet")
```

### Filter

```
val res = orders.
withColumn("order_date", from_unixtime($"order_date"/1000)).
 withColumn("order_date", to_date($"order_date")).
filter($"order_status" === "PENDING").
 filter($"order_date".between("2013-07-01", "2013-07-31")).
select("order_date", "order_status")
```

### Write

```
res.
write.
mode("overwrite").
option("compression", "snappy").
format("json").
save("/user/jsolis/dataset/result/scenario12/solution")
```

### Check Solution

```
spark.read.json("/user/jsolis/dataset/result/scenario12/solution ").show
+----------+------------+
|order_date|order_status|
+----------+------------+
|2013-07-24| PENDING|
|2013-07-24| PENDING|
|2013-07-24| PENDING|
|2013-07-24| PENDING|
|2013-07-24| PENDING|
|2013-07-24| PENDING|
|2013-07-24| PENDING|
|2013-07-24| PENDING|
|2013-07-24| PENDING|
|2013-07-24| PENDING|
|2013-07-24| PENDING|
|2013-07-25| PENDING|
|2013-07-25| PENDING|
|2013-07-25| PENDING|
|2013-07-25| PENDING|
|2013-07-25| PENDING|
|2013-07-25| PENDING|
|2013-07-25| PENDING|
|2013-07-25| PENDING|
|2013-07-25| PENDING|
+----------+------------+
only showing top 20 rows
```

```
hdfs dfs -ls /user/jsolis/dataset/result/scenario12/solution
Found 2 items
-rw-r--r--   3 jsolis jsolis          0 2021-07-15 19:13 /user/jsolis/dataset/result/scenario12/solution/_SUCCESS
-rw-r--r--   3 jsolis jsolis        606 2021-07-15 19:13 /user/jsolis/dataset/result/scenario12/solution/part-00000-c5185d65-05a6-4eac-829e-ae217d3f0dec-c000.json.snappy

```
