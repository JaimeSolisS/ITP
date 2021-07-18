# CCA 175 Real Time Exam Scenario 14 | Read Tab Separated Values | Save PARQUET with GZIP compression

Data Description
All the customer records are stored at
/user/spark/dataset/retail_db/customers-tab-delimited
Data is in text format Data is tab delimited

Output Requirement
Get total numbers of customers in each state whose first name starts with 'M'
Use parquet format for the output files
Place the result data in HDFS directory /user/spark/dataset/result/scenario14/solution
Use gzip compression
Output should contain the columns customer_state, count

## Solution

### Read

```
val customers = spark.read.option("sep", "\t").csv("/user/jsolis/dataset/retail_db/customers-tab-delimited")
```

### filter

```
val res = customers.
withColumn("firstName",substring($"_c1", 0,1)).
filter($"firstName" === "M").
groupBy($"_c7".as("customer_state")).
agg(count(lit(1)).as("count"))

customers.
filter("_c1 like 'M%'").
show
```

### Write

```
res.
write.
mode("overwrite").
option("compression", "gzip").
format("parquet").
save("user/jsolis/dataset/result/scenario14/solution")
```

### Check solution

```
spark.read.parquet("user/jsolis/dataset/result/scenario14/solution").show

+--------------+-----+
|customer_state|count|
+--------------+-----+
|            AZ|   98|
|            SC|   16|
|            WA|   32|
|            ND|    6|
|            NM|   22|
|            IA|    2|
|            CO|   51|
|            FL|  162|
|            CA|  850|
|            NY|  331|
|            TX|  267|
|            PR| 2063|
|            LA|   24|
|            MN|   14|
|            NJ|   87|
|            DC|   17|
|            OR|   45|
|            VA|   59|
|            RI|    8|
|            KY|   13|
+--------------+-----+
only showing top 20 rows

```

```
hdfs dfs -ls user/jsolis/dataset/result/scenario14/solution

hdfs dfs -ls user/jsolis/dataset/result/scenario14/solution
Found 42 items
-rw-r--r-- 3 jsolis jsolis 0 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/\_SUCCESS
-rw-r--r-- 3 jsolis jsolis 343 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00000-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 588 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00004-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00009-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00010-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00016-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00021-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00030-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00042-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00047-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00049-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00054-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00055-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00059-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00060-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 580 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00065-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00066-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00067-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00070-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00074-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00075-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00078-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00080-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 588 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00091-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00095-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00097-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00107-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00108-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 588 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00110-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00115-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 580 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00130-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 580 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00151-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00154-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00165-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00167-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00169-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 588 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00175-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00183-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00185-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 580 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00186-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00195-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
-rw-r--r-- 3 jsolis jsolis 579 2021-07-15 19:42 user/jsolis/dataset/result/scenario14/solution/part-00199-d3806c02-c166-479c-8578-0e2376c78d95-c000.gz.parquet
```
