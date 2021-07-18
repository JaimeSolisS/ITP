# CCA 175 Real Time Exam Scenario 13 | Read Hive Table | Write as PARQUET with SNAPPY Compression

Data Description
Get data from metastore table named "customers“
Table is present in the database "default"

Output Requirement
Get Records from the metastore table named "customers" whose customer_fname is like "Rich“
Use parquet format for the output files
Place the result data in HDFS directory /user/spark/dataset/result/scenario13/solution
Compress the output using snappy compression

## Solution

### Read

```
val customers = spark.read.table("ybtrainee.dluevano_customers")
```

### filter

```
customers.
filter($"customer_fname".contains("Rich")).
show

val custormers = spark.sql("""
select * from ybtrainee.dluevano_customers
where customer_fname like "%Rich%"
""").show
```

### Write

```
customers.
write.
mode("overwrite").
option("compression", "snappy").
format("parquet").
save("/user/jsolis/dataset/result/scenario13/solution")
```

### Check solution

```
spark.read.parquet("/user/jsolis/dataset/result/scenario13/solution").show

+-----------+--------------+--------------+--------------+-----------------+--------------------+-------------+--------------+----------------+
|customer_id|customer_fname|customer_lname|customer_email|customer_password|     customer_street|customer_city|customer_state|customer_zipcode|
+-----------+--------------+--------------+--------------+-----------------+--------------------+-------------+--------------+----------------+
|          1|       Richard|     Hernandez|     XXXXXXXXX|        XXXXXXXXX|  6303 Heather Plaza|  Brownsville|            TX|           78521|
|          2|          Mary|       Barrett|     XXXXXXXXX|        XXXXXXXXX|9526 Noble Embers...|    Littleton|            CO|           80126|
|          3|           Ann|         Smith|     XXXXXXXXX|        XXXXXXXXX|3422 Blue Pioneer...|       Caguas|            PR|             725|
|          4|          Mary|         Jones|     XXXXXXXXX|        XXXXXXXXX|  8324 Little Common|   San Marcos|            CA|           92069|
|          5|        Robert|        Hudson|     XXXXXXXXX|        XXXXXXXXX|10 Crystal River ...|       Caguas|            PR|             725|
|          6|          Mary|         Smith|     XXXXXXXXX|        XXXXXXXXX|3151 Sleepy Quail...|      Passaic|            NJ|            7055|
|          7|       Melissa|        Wilcox|     XXXXXXXXX|        XXXXXXXXX|9453 High Concession|       Caguas|            PR|             725|
|          8|         Megan|         Smith|     XXXXXXXXX|        XXXXXXXXX|3047 Foggy Forest...|     Lawrence|            MA|            1841|
|          9|          Mary|         Perez|     XXXXXXXXX|        XXXXXXXXX| 3616 Quaking Street|       Caguas|            PR|             725|
|         10|       Melissa|         Smith|     XXXXXXXXX|        XXXXXXXXX|8598 Harvest Beac...|     Stafford|            VA|           22554|
|         11|          Mary|       Huffman|     XXXXXXXXX|        XXXXXXXXX|    3169 Stony Woods|       Caguas|            PR|             725|
|         12|   Christopher|         Smith|     XXXXXXXXX|        XXXXXXXXX|5594 Jagged Ember...|  San Antonio|            TX|           78227|
|         13|          Mary|       Baldwin|     XXXXXXXXX|        XXXXXXXXX|7922 Iron Oak Gar...|       Caguas|            PR|             725|
|         14|     Katherine|         Smith|     XXXXXXXXX|        XXXXXXXXX|5666 Hazy Pony Sq...|  Pico Rivera|            CA|           90660|
|         15|          Jane|          Luna|     XXXXXXXXX|        XXXXXXXXX|    673 Burning Glen|      Fontana|            CA|           92336|
|         16|       Tiffany|         Smith|     XXXXXXXXX|        XXXXXXXXX|      6651 Iron Port|       Caguas|            PR|             725|
|         17|          Mary|      Robinson|     XXXXXXXXX|        XXXXXXXXX|     1325 Noble Pike|       Taylor|            MI|           48180|
|         18|        Robert|         Smith|     XXXXXXXXX|        XXXXXXXXX|2734 Hazy Butterf...|     Martinez|            CA|           94553|
|         19|     Stephanie|      Mitchell|     XXXXXXXXX|        XXXXXXXXX|3543 Red Treasure...|       Caguas|            PR|             725|
|         20|          Mary|         Ellis|     XXXXXXXXX|        XXXXXXXXX|      4703 Old Route|West New York|            NJ|            7093|
+-----------+--------------+--------------+--------------+-----------------+--------------------+-------------+--------------+----------------+
only showing top 20 rows
```

```
hdfs dfs -ls /user/jsolis/dataset/result/scenarionFound 2 items
-rw-r--r--   3 jsolis jsolis          0 2021-07-15 19:25 /user/jsolis/dataset/result/scenario13/solution/_SUCCESS
-rw-r--r--   3 jsolis jsolis     249694 2021-07-15 19:25 /user/jsolis/dataset/result/scenario13/solution/part-00000-a447568e-b3e1-4291-ab78-2bbc3542e6fb-c000.snappy.parquet
```
