# CCA 175 Real Time Exam Scenario 15 | Read CSV Data | JOIN Multiple DataFrames | Save as CSV

Data Description
All the Order records are stored at /user/spark/dataset/retail_db/orders
All the Customer records are stored at /user/spark/dataset/retail_db/customers

Output Requirement
Find out the customers who have made more than 5 orders
Customer name must start with "M"
Use text format for the output files
Use "|" as field separator
Use gzip compression
Place the result data in HDFS directory /user/spark/dataset/result/scenario15/solution
Output should only contain customer_fname, customer_lname, count
Output should be sorted by count in descending order

## Solution

### Read

```
val orders = spark.read.csv("/user/jsolis/dataset/retail_db/orders")
val customers = spark.read.csv("/user/jsolis/dataset/retail_db/customers").
filter("\_c1 like 'M%'")
```

### Join & Agg

```
val join =
orders.join(customers, orders("_c2") === customers("_c0")).
groupBy(customers("_c0")).
agg(count(lit(1)).as("count")).
filter($"count" > 5)

val res =
join.
join(customers, join("_c0")=== customers("_c0"))

val res2 =res.
select($"_c1".as("customer_fname"), $"_c2".as("customer_lname"), $"count").
orderBy($"count".desc).
map(x => x.mkString("|"))
```

### Write

```
res2.
coalesce(1).
write.
mode("overwrite").
option("compression", "gzip").
format("text").
save("/user/jsolis/dataset/result/scenario15/solution")
```

### Check Solution

```
spark.read.text("/user/jsolis/dataset/result/scenario15/solution").show
+-----------------+
|            value|
+-----------------+
|  Mary|Griffin|16|
|     Mary|Rios|16|
|     Mary|Frye|16|
|      Mary|Cox|15|
|     Mary|Mata|15|
|    Mary|Smith|15|
|   Mary|Butler|14|
|    Mary|Cline|14|
|    Mary|Smith|14|
|    Mary|Smith|14|
|    Mary|Smith|14|
|  Mary|Preston|13|
|  Mary|Huffman|13|
|    Mary|Smith|13|
|    Mary|Smith|13|
|    Mary|Smith|13|
|Mary|Blackburn|13|
|     Mary|Ward|13|
| Michael|Scott|13|
|  Mary|Camacho|13|
+-----------------+
only showing top 20 rows
```

```
hdfs dfs -ls /user/jsolis/dataset/result/scenario15/solution
Found 2 items
-rw-r--r--   3 jsolis jsolis          0 2021-07-15 23:31 /user/jsolis/dataset/result/scenario15/solution/_SUCCESS
-rw-r--r--   3 jsolis jsolis       7864 2021-07-15 23:31 /user/jsolis/dataset/result/scenario15/solution/part-00000-2dc7d119-8053-445e-afb1-bd4c9f50542f-c000.txt.gz
```
