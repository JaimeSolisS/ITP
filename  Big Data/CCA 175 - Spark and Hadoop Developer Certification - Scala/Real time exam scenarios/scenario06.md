# CCA 175 Real Time Exam Scenario 6 | Read Hive table | Write as PARQUET in HDFS with GZip Compression

Data Description

1.  Get data from metastore table named "orders“
2.  Table is present in the database "default"

Output Requirement

1.  Fetch orders from Jan-2013 to Dec-2013
2.  Use parquet format for the output files
3.  Place the result data in HDFS directory /user/spark/dataset/result/scenario6/solution
4.  Compress the output using Gzip compression

## Solution

### Read data

```
val orders = spark.sql("""
SELECT * from ybtrainee.jsolis_orders
WHERE order_date BETWEEN '2013-01-01' AND '2013-12-31'
""")
```

### Export

```
orders.
write.
mode("overwrite").
option("compression", "gzip").
format("parquet").
save("/user/jsolis/dataset/result/scenario6/solution")
```

### Check solution

```
scala> spark.read.parquet("/user/jsolis/dataset/result/scenario6/solution").show
[Stage 28:>                                                         (0 [Stage 28:>                                                         (0                                                                        [Stage 29:>                                                         (0                                                                        +--------+--------------------+-----------------+---------------+
|order_id|          order_date|order_customer_id|   order_status|
+--------+--------------------+-----------------+---------------+
|       1|2013-07-25 00:00:...|            11599|         CLOSED|
|       2|2013-07-25 00:00:...|              256|PENDING_PAYMENT|
|       3|2013-07-25 00:00:...|            12111|       COMPLETE|
|       4|2013-07-25 00:00:...|             8827|         CLOSED|
|       5|2013-07-25 00:00:...|            11318|       COMPLETE|
|       6|2013-07-25 00:00:...|             7130|       COMPLETE|
|       7|2013-07-25 00:00:...|             4530|       COMPLETE|
|       8|2013-07-25 00:00:...|             2911|     PROCESSING|
|       9|2013-07-25 00:00:...|             5657|PENDING_PAYMENT|
|      10|2013-07-25 00:00:...|             5648|PENDING_PAYMENT|
|      11|2013-07-25 00:00:...|              918| PAYMENT_REVIEW|
|      12|2013-07-25 00:00:...|             1837|         CLOSED|
|      13|2013-07-25 00:00:...|             9149|PENDING_PAYMENT|
|      14|2013-07-25 00:00:...|             9842|     PROCESSING|
|      15|2013-07-25 00:00:...|             2568|       COMPLETE|
|      16|2013-07-25 00:00:...|             7276|PENDING_PAYMENT|
|      17|2013-07-25 00:00:...|             2667|       COMPLETE|
|      18|2013-07-25 00:00:...|             1205|         CLOSED|
|      19|2013-07-25 00:00:...|             9488|PENDING_PAYMENT|
|      20|2013-07-25 00:00:...|             9198|     PROCESSING|
+--------+--------------------+-----------------+---------------+
only showing top 20 rows
```

```
hdfs dfs -ls /user/jsolis/dataset/result/scenario6/solution
Found 3 items
-rw-r--r--   3 jsolis jsolis          0 2021-07-12 15:23 /user/jsolis/dataset/result/scenario6/solution/_SUCCESS
-rw-r--r--   3 jsolis jsolis     118528 2021-07-12 15:23 /user/jsolis/dataset/result/scenario6/solution/part-00000-3a2c2b80-d9b0-452c-b19c-111a24a58dce-c000.gz.parquet
-rw-r--r--   3 jsolis jsolis      22506 2021-07-12 15:23 /user/jsolis/dataset/result/scenario6/solution/part-00001-3a2c2b80-d9b0-452c-b19c-111a24a58dce-c000.gz.parquet
```
