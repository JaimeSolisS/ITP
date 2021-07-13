# CCA 175 Real Time Exam Scenario 9 | Read AVRO Data | Write as JSON in HDFS

Data Description

1.  All the product records are stored at /user/spark/dataset/retail_db/products_avro
2.  Data is in avro format
3.  Data is compressed with snappy compression

NOTE:

1. Use below command to start spark shell on Cloudera VM
   spark-shell --packages org.apache.spark:spark-avro_2.11:2.4.4 In CCA 175
2. spark shell is already enabled with avro packages.

Output Requirement

1.  Output should contain columns product_id, product_price
2.  Save output as a JSON file
3.  Place the result data in HDFS directory /user/spark/dataset/result/scenario9/solution
4.  Use no compression

## Solution

### Read file

```
val products = spark.read.format("com.databricks.spark.avro").load("/user/jsolis/dataset/retail_db/products_avro")
```

### Select columns

```
val res = products.select("product_id", "product_price")
```

### Write solution

```
res.write.
mode("overwrite").
option("compression", "none").
format("json").
save("/user/jsolis/dataset/result/scenario9/solution")
```

### Check Solution

```
scala> spark.read.json("/user/jsolis/dataset/result/scenario9/solution").show
+----------+-------------+
|product_id|product_price|
+----------+-------------+
| 1009| 599.99|
| 1010| 19.98|
| 1011| 499.99|
| 1012| 299.99|
| 1013| 349.99|
| 1014| 49.98|
| 1015| 399.99|
| 1016| 549.99|
| 1017| 19.98|
| 1018| 399.99|
| 1019| 44.98|
| 1020| 549.99|
| 1021| 499.99|
| 1022| 49.98|
| 1023| 29.99|
| 1024| 21.99|
| 1025| 369.99|
| 1026| 119.99|
| 1027| 34.99|
| 1028| 399.99|
+----------+-------------+
only showing top 20 rows
```

```
$ hdfs dfs -ls /user/jsolis/dataset/result/scenario9/solution
Found 3 items
-rw-r--r--   3 jsolis jsolis          0 2021-07-12 17:19 /user/jsolis/dataset/result/scenario9/solution/_SUCCESS
-rw-r--r--   3 jsolis jsolis      28016 2021-07-12 17:19 /user/jsolis/dataset/result/scenario9/solution/part-00000-a16a82e6-4b8a-4c77-ade1-8e58b7dc8615-c000.json
-rw-r--r--   3 jsolis jsolis      27520 2021-07-12 17:19 /user/jsolis/dataset/result/scenario9/solution/part-00001-a16a82e6-4b8a-4c77-ade1-8e58b7dc8615-c000.json
```
