# CCA 175 Real Time Exam Scenario 5 | Read AVRO data | Write PARQUET in HDFS with SNAPPY Compression

Data Description

1.  All the product records are stored at
    /user/spark/dataset/retail_db/products_avro
2.  Data is in AVRO format
3.  Data is compressed with snappy compression

Output Requirement

1.  Output should only contain the products with price greator than 1000.0
2.  Use PARQUET format for the output files
3.  Place the result data in HDFS directory /user/spark/dataset/result/scenario5/solution
4.  Compress the output using SNAPPY compression

## Solution

### Launch Spark

```
spark2-shell --master yarn --packages com.databricks:spark-avro_2.11:4.0.0
```

### Read file

```
val products = spark.read.option("compression", "snappy").format("com.databricks.spark.avro").load("/user/jsolis/dataset/retail_db/products_avro")
```

### Filter

```
val res = products.
filter($"product_price" > 1000.0)
```

### Export

```
res.
write.
mode("overwrite").
option("compression", "snappy").
format("parquet").
save("/user/jsolis/dataset/result/scenario5/solution")
```

### Check Solution

```
scala> spark.read.parquet("/user/jsolis/dataset/result/scenario5/s[Stage 25:>                                                       [Stage 25:>                                                         (0 + 1                                                                          [Stage 26:>                                                         (0 + 1                                                                          +----------+-------------------+--------------------+-------------------+-------------+--------------------+
|product_id|product_category_id|        product_name|product_description|product_price|       product_image|
+----------+-------------------+--------------------+-------------------+-------------+--------------------+
|      1048|                 47|Spalding Beast 60...|                   |      1099.99|http://images.acm...|
|       496|                 22|  SOLE F85 Treadmill|                   |      1799.99|http://images.acm...|
|        66|                  4|  SOLE F85 Treadmill|                   |      1799.99|http://images.acm...|
|       199|                 10|  SOLE F85 Treadmill|                   |      1799.99|http://images.acm...|
|       208|                 10| SOLE E35 Elliptical|                   |      1999.99|http://images.acm...|
+----------+-------------------+--------------------+-------------------+-------------+--------------------+
```

```
hdfs dfs -ls /user/jsolis/dataset/result/scenario5/solution
Found 3 items
-rw-r--r--   3 jsolis jsolis          0 2021-07-11 22:36 /user/jsolis/dataset/result/scenario5/solution/_SUCCESS
-rw-r--r--   3 jsolis jsolis       1956 2021-07-11 22:36 /user/jsolis/dataset/result/scenario5/solution/part-00000-a3958ee0-fd8e-4a7a-8323-73d0c8a2fb32-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       1800 2021-07-11 22:36 /user/jsolis/dataset/result/scenario5/solution/part-00001-a3958ee0-fd8e-4a7a-8323-73d0c8a2fb32-c000.snappy.parquet
```
