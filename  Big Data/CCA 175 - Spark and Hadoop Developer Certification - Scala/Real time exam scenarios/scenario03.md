# CCA 175 Real Time Exam Scenario 3 | Read Tab Delimited File | Write as ORC with SNAPPY Compression

Data Description

1.  All the customer records are stored at
    /user/spark/dataset/retail_db/customers-tab-delimited
2.  Data is in text format
3.  Data is tab delimited

| Schema            |        |
| ----------------- | ------ |
| customer_id       | int    |
| customer_fname    | string |
| customer_lname    | string |
| customer_email    | string |
| customer_password | string |
| customer_street   | string |
| customer_city     | string |
| customer_state    | string |
| customer_zipcode  | string |

Output Requirement

1.  Output all the customers who live in "Caguas" city
2.  Place the result data in HDFS directory /user/spark/dataset/result/scenario3/solution
3.  Result should only contain records that have customer_city value as "Caguas“
4.  Compress the output using snappy compression
5.  Save the output using orc format

## Solution

### Read file

```
val customers = spark.read.option("sep", "\t").csv("/user/jsolis/dataset/retail_db/customers-tab-delimited").toDF("customer_id", "customer_fname", "customer_lname", "customer_email", "customer_password", "customer_street", "customer_city", "customer_state", "customer_zipcode" )
```

### Filter

```
val res = customers.
filter($"_c6" === "Caguas")
```

### Export Solution

```
res.
write.
mode("overwrite").
option("compression", "snappy").
format("orc").
save("/user/jsolis/dataset/result/scenario3/solution")
```

### Check Solution

```
scala> spark.read.orc("/user/jsolis/dataset/result/scenario3/solution").show                                                                          +-----------+--------------+--------------+--------------+-----------------+--------------------+-------------+--------------+----------------+
|customer_id|customer_fname|customer_lname|customer_email|customer_password|     customer_street|customer_city|customer_state|customer_zipcode|
+-----------+--------------+--------------+--------------+-----------------+--------------------+-------------+--------------+----------------+
|          3|           Ann|         Smith|     XXXXXXXXX|        XXXXXXXXX|3422 Blue Pioneer...|       Caguas|            PR|           00725|
|          5|        Robert|        Hudson|     XXXXXXXXX|        XXXXXXXXX|10 Crystal River ...|       Caguas|            PR|           00725|
|          7|       Melissa|        Wilcox|     XXXXXXXXX|        XXXXXXXXX|9453 High Concession|       Caguas|            PR|           00725|
|          9|          Mary|         Perez|     XXXXXXXXX|        XXXXXXXXX| 3616 Quaking Street|       Caguas|            PR|           00725|
|         11|          Mary|       Huffman|     XXXXXXXXX|        XXXXXXXXX|    3169 Stony Woods|       Caguas|            PR|           00725|
|         13|          Mary|       Baldwin|     XXXXXXXXX|        XXXXXXXXX|7922 Iron Oak Gar...|       Caguas|            PR|           00725|
|         16|       Tiffany|         Smith|     XXXXXXXXX|        XXXXXXXXX|      6651 Iron Port|       Caguas|            PR|           00725|
|         19|     Stephanie|      Mitchell|     XXXXXXXXX|        XXXXXXXXX|3543 Red Treasure...|       Caguas|            PR|           00725|
|         21|       William|     Zimmerman|     XXXXXXXXX|        XXXXXXXXX|3323 Old Willow M...|       Caguas|            PR|           00725|
|         24|          Mary|         Smith|     XXXXXXXXX|        XXXXXXXXX| 9417 Emerald Towers|       Caguas|            PR|           00725|
|         27|          Mary|       Vincent|     XXXXXXXXX|        XXXXXXXXX|1768 Sleepy Zephy...|       Caguas|            PR|           00725|
|         30|       Barbara|         Smith|     XXXXXXXXX|        XXXXXXXXX|   2455 Merry Hollow|       Caguas|            PR|           00725|
|         32|         Alice|         Smith|     XXXXXXXXX|        XXXXXXXXX|   2082 Hidden Green|       Caguas|            PR|           00725|
|         34|          Mary|         Smith|     XXXXXXXXX|        XXXXXXXXX|3330 Easy Berry R...|       Caguas|            PR|           00725|
|         36|      Michelle|         Carey|     XXXXXXXXX|        XXXXXXXXX| 6336 Fallen Village|       Caguas|            PR|           00725|
|         39|          Juan|      Mckinney|     XXXXXXXXX|        XXXXXXXXX|7274 Blue Wagon  ...|       Caguas|            PR|           00725|
|         43|          Mary|       Herring|     XXXXXXXXX|        XXXXXXXXX|   4575 Thunder Dale|       Caguas|            PR|           00725|
|         47|          Lori|        Fuller|     XXXXXXXXX|        XXXXXXXXX|      357 Noble Lane|       Caguas|            PR|           00725|
|         49|        Martha|         Smith|     XXXXXXXXX|        XXXXXXXXX|    7449 Merry Chase|       Caguas|            PR|           00725|
|         51|       Jessica|         Smith|     XXXXXXXXX|        XXXXXXXXX|8344 Dewy Fawn Farms|       Caguas|            PR|           00725|
+-----------+--------------+--------------+--------------+-----------------+--------------------+-------------+--------------+----------------+
only showing top 20 rows
```

```
hdfs dfs -ls /user/jsolis/dataset/result/scenario3/solution
Found 2 items
-rw-r--r--   3 jsolis jsolis          0 2021-07-11 22:15 /user/jsolis/dataset/result/scenario3/solution/_SUCCESS
-rw-r--r--   3 jsolis jsolis      87397 2021-07-11 22:15 /user/jsolis/dataset/result/scenario3/solution/part-00000-81577353-8d14-4280-9a68-1cc4ecc8d46d-c000.snappy.orc
```
