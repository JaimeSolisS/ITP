# CCA 175 Real Time Exam Scenario 11 | Read AVRO Data | Write as Tab Separated Value bzip2 compression

Data Description
All the customer records are stored at
/user/spark/dataset/retail_db/customers-avro
Data is in Avro format

Output Requirement
Convert all data into tab delimited file
Use text format for the output files
Use bzip2 compression
Place the result data at /user/spark/dataset/result/scenario11/solution
Output should only contain customer_id, customer_name
( only first character of first name and complete last name separated by space )

## Solution

### Read file

```
val customers = spark.read.format("com.databricks.spark.avro").load("/user/jsolis/dataset/retail_db/customers-avro")
```

### Transform

```
val res = customers.
withColumn("customer_name", concat(substring($"customer_fname", 0,1), lit(" "), $"customer_lname")).
select($"customer_id", $"customer_name").
map(x => x.mkString("\t"))
```

### Export

```
res.
write.
mode("overwrite").
option("compression", "bzip2").
format("text").
save("/user/jsolis/dataset/result/scenario11/solution")
```

### Check Solution

```
spark.read.text("/user/jsolis/dataset/result/scenario11/solution").show

+---------------+
| value|
+---------------+
| 9327 D Smith|
| 9328 M Perez|
| 9329 E Powell|
| 9330 M Conley|
| 9331 D Smith|
| 9332 M Jordan|
| 9333 A Mills|
|9334 M Johnston|
| 9335 J Smith|
| 9336 J Guzman|
| 9337 M Smith|
| 9338 J Davis|
| 9339 A Moyer|
| 9340 M Smith|
| 9341 K Collins|
| 9342 T Grant|
| 9343 M Knapp|
| 9344 K Smith|
| 9345 M Branch|
| 9346 J Smith|
+---------------+
only showing top 20 rows
```

```
hdfs dfs -ls /user/jsolis/dataset/result/scenario11/solution
Found 3 items
-rw-r--r--   3 jsolis jsolis          0 2021-07-15 18:54 /user/jsolis/dataset/result/scenario11/solution/_SUCCESS
-rw-r--r--   3 jsolis jsolis      24712 2021-07-15 18:54 /user/jsolis/dataset/result/scenario11/solution/part-00000-da286fa4-b93d-4163-bcaa-73887259f783-c000.txt.bz2
-rw-r--r--   3 jsolis jsolis      24355 2021-07-15 18:54 /user/jsolis/dataset/result/scenario11/solution/part-00001-da286fa4-b93d-4163-bcaa-73887259f783-c000.txt.bz2
```
