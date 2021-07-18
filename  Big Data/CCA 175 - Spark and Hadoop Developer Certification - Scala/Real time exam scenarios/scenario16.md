# CCA 175 Real Time Exam Scenario 16 | Read CSV | Save as PARQUET with SNAPPY Compression

Data Description
All the Order records are stored at /user/spark/dataset/retail_db/orders

Output Requirement
Find all Fraud transactions per month
Order Status must be equal to SUSPECTED_FRAUD
Use parquet format for the output files
Use snappy compression
Place the result data in HDFS directory /user/spark/dataset/result/scenario16/solution
Output should only contain order_date,count
Use yyyy-MM format for order_date
Output should be sorted by order_date in descending order

## Solution

### Read

```
val orders = spark.read.csv("/user/jsolis/dataset/retail_db/orders")
```

### Process

```
val res = orders.
filter($"_c3" === "SUSPECTED_FRAUD").
withColumn("_c1", date_format(to_date($"_c1"), "yyyy-MM")).
groupBy($"_c1".as("order_date")).
agg(count(lit(1)).as("count")).
orderBy($"order_date".desc)
```

### Write

```
res.
write.
mode("overwrite").
option("compression", "snappy").
format("parquet").
save("/user/jsolis/dataset/result/scenario16/solution")
```

### Check Solution

```
spark.read.parquet("/user/jsolis/dataset/result/scenario16/solution").show

+----------+-----+
|order_date|count|
+----------+-----+
|   2014-07|  101|
|   2014-06|  131|
|   2014-05|  130|
|   2014-04|  112|
|   2014-03|  138|
|   2014-02|  119|
|   2014-01|  131|
|   2013-12|  126|
|   2013-11|  150|
|   2013-10|  108|
|   2013-09|  148|
|   2013-08|  131|
|   2013-07|   33|
+----------+-----+
```

```
hdfs dfs -ls /user/jsolis/dataset/result/scenario16/solution

Found 14 items
-rw-r--r--   3 jsolis jsolis          0 2021-07-15 20:30 /user/jsolis/dataset/result/scenario16/solution/_SUCCESS
-rw-r--r--   3 jsolis jsolis        562 2021-07-15 20:30 /user/jsolis/dataset/result/scenario16/solution/part-00000-a218b5eb-d308-48ca-82fb-8cd7478597c9-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis        562 2021-07-15 20:30 /user/jsolis/dataset/result/scenario16/solution/part-00001-a218b5eb-d308-48ca-82fb-8cd7478597c9-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis        562 2021-07-15 20:30 /user/jsolis/dataset/result/scenario16/solution/part-00002-a218b5eb-d308-48ca-82fb-8cd7478597c9-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis        562 2021-07-15 20:30 /user/jsolis/dataset/result/scenario16/solution/part-00003-a218b5eb-d308-48ca-82fb-8cd7478597c9-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis        562 2021-07-15 20:30 /user/jsolis/dataset/result/scenario16/solution/part-00004-a218b5eb-d308-48ca-82fb-8cd7478597c9-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis        562 2021-07-15 20:30 /user/jsolis/dataset/result/scenario16/solution/part-00005-a218b5eb-d308-48ca-82fb-8cd7478597c9-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis        562 2021-07-15 20:30 /user/jsolis/dataset/result/scenario16/solution/part-00006-a218b5eb-d308-48ca-82fb-8cd7478597c9-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis        562 2021-07-15 20:30 /user/jsolis/dataset/result/scenario16/solution/part-00007-a218b5eb-d308-48ca-82fb-8cd7478597c9-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis        562 2021-07-15 20:30 /user/jsolis/dataset/result/scenario16/solution/part-00008-a218b5eb-d308-48ca-82fb-8cd7478597c9-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis        562 2021-07-15 20:30 /user/jsolis/dataset/result/scenario16/solution/part-00009-a218b5eb-d308-48ca-82fb-8cd7478597c9-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis        562 2021-07-15 20:30 /user/jsolis/dataset/result/scenario16/solution/part-00010-a218b5eb-d308-48ca-82fb-8cd7478597c9-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis        562 2021-07-15 20:30 /user/jsolis/dataset/result/scenario16/solution/part-00011-a218b5eb-d308-48ca-82fb-8cd7478597c9-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis        562 2021-07-15 20:30 /user/jsolis/dataset/result/scenario16/solution/part-00012-a218b5eb-d308-48ca-82fb-8cd7478597c9-c000.snappy.parquet
```
