## Problem:

- Consider orders only in “COMPLETE” status and order id between 1000 and 50000 (1001 to 49999)
- Save the output (only 2 columns orderid and orderstatus) in parquet format with gzip compression in location
  **/user/yourusername/problem19/**
- Advanced : Try if you can save output only in 2 files (Tip : use coalesce(2))

## Solution

### Conect to DB

```
scala> spark.sql("USE ybtrainee")
res0: org.apache.spark.sql.DataFrame = []

scala> spark.sql("Select current_database()").show
+------------------+
|current_database()|
+------------------+
|         ybtrainee|
+------------------+
```

### Get table

```
val solution =
spark.sql("""
Select order_id, order_status from orders
Where order_status = "COMPLETE" and order_id between 1001 and 49999
    """)

```

### Export Solution

solution.
coalesce(2).
write.
mode("overwrite").
option("compression", "gzip").
format("parquet").
save("/user/jsolis/problem19/")

### Check Solution

```
scala> spark.read.parquet("/user/jsolis/problem19/").show
+--------+------------+
|order_id|order_status|
+--------+------------+
|    1001|    COMPLETE|
|    1003|    COMPLETE|
|    1004|    COMPLETE|
|    1006|    COMPLETE|
|    1009|    COMPLETE|
|    1010|    COMPLETE|
|    1011|    COMPLETE|
|    1018|    COMPLETE|
|    1021|    COMPLETE|
|    1024|    COMPLETE|
|    1026|    COMPLETE|
|    1030|    COMPLETE|
|    1031|    COMPLETE|
|    1032|    COMPLETE|
|    1034|    COMPLETE|
|    1035|    COMPLETE|
|    1038|    COMPLETE|
|    1039|    COMPLETE|
|    1040|    COMPLETE|
|    1051|    COMPLETE|
+--------+------------+
only showing top 20 rows
```

```
$ hdfs dfs -ls /user/jsolis/problem19/
Found 3 items
-rw-r--r--   3 jsolis jsolis          0 2021-07-02 15:29 /user/jsolis/problem19/_SUCCESS
-rw-r--r--   3 jsolis jsolis      23525 2021-07-02 15:29 /user/jsolis/problem19/part-00000-50cf95f1-0923-4c34-aef1-1b59d3c31a4d-c000.gz.parquet
-rw-r--r--   3 jsolis jsolis      23525 2021-07-02 15:29 /user/jsolis/problem19/part-00001-50cf95f1-0923-4c34-aef1-1b59d3c31a4d-c000.gz.parquet

```
