# CCA 175 Real Time Exam Scenario 17 | JOIN Multiple DataFrames | Save as JSON and DEFLATE Compression

Data Description
All the product records are stored at /user/spark/dataset/retail_db/products
All the category records are stored at /user/spark/dataset /retail_db/categories
Data is in text format

Output Requirement
Get maximum product_price in each product_category
Get minimum product_price in each product_category
Get average product_price in each product_category
Round the values to 2 decimal places
Place the result data in HDFS directory /user/spark/dataset/result/scenario17/solution
Save the output as JSON file
Compress the output using deflate compression
Output data should contain columns category_name,max_price,min_price,avg_price

## Solution

### Read

```
val products = spark.read.csv("/user/jsolis/dataset/retail_db/products").toDF("product_id", "product_category_id", "product_name", "x", "product_price", "image")

val categories = spark.read.csv("/user/jsolis/dataset/retail_db/categories").toDF("category_id", "departmen_id", "category_name")
```

### Join & Agg

```
val pjc =
products.join(categories, products("product_category_id")=== categories("category_id"))

val res = pjc.
groupBy("category_name").
agg(
round(max("product_price"), 2).as("max_price"),
round(min("product_price"),2).as("min_price"),
round(avg("product_price"), 2).as("avg_price")
)
```

### Write

```
res.
write.
mode("overwrite").
option("compression", "deflate").
format("json").
save("/user/jsolis/dataset/result/scenario17/solution")
```

### Check Solution

```
spark.read.json("/user/jsolis/dataset/result/scenario17/solution").show
+---------+--------------------+---------+---------+
|avg_price|       category_name|max_price|min_price|
+---------+--------------------+---------+---------+
|    81.53|       Boys' Apparel|    99.95|      0.0|
|     56.5|                 NBA|     65.0|     10.0|
|   405.11|   Strength Training|   999.99|   159.99|
|    283.9|    Camping & Hiking|     99.0|   125.99|
|    250.2| Fitness Accessories|    99.99|   109.99|
|    79.95| Baseball & Softball|     99.0|      0.0|
|   111.57|  Hunting & Shooting|    99.98|   129.99|
|    82.36|         More Sports|    99.98|   134.99|
|    68.79|      World Cup Shop|     90.0|   159.99|
|   264.65|Indoor/Outdoor Games|   549.99|   119.99|
|    83.12|         MLB Players|    99.99|      0.0|
|    42.62|         Electronics|    99.99|    12.99|
|    76.45|           Team Shop|    34.99|   109.99|
|   208.53|             Boating|    99.95|  1099.99|
|     85.5|                 NFL|     60.0|    130.0|
|    92.49|              Hockey|    99.98|   134.99|
|    98.57|                 MLS|    99.99|   129.99|
|    42.45|   Training by Sport|    81.99|    100.0|
|     31.5|International Soccer|     9.99|     18.0|
|   268.47|  Women's Golf Clubs|    99.99|   119.99|
+---------+--------------------+---------+---------+
only showing top 20 rows
```

```

hdfs dfs -ls /user/jsolis/dataset/result/scenario17/solution

Found 44 items
-rw-r--r--   3 jsolis jsolis          0 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/_SUCCESS
-rw-r--r--   3 jsolis jsolis          8 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00000-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         85 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00002-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         81 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00006-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis        121 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00020-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         78 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00025-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         77 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00026-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         77 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00035-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         83 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00044-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         81 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00051-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         68 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00062-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         75 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00063-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis        106 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00069-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         68 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00073-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         81 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00076-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         82 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00078-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         78 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00083-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         80 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00085-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         79 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00089-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         76 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00090-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis        118 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00091-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         70 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00095-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         85 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00096-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         79 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00097-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         75 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00113-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         84 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00117-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         77 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00118-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         73 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00121-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         79 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00123-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         78 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00127-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         74 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00128-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         71 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00130-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis        141 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00139-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis        116 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00141-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         72 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00143-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         73 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00144-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         81 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00150-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis        102 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00160-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         96 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00172-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         79 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00175-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         72 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00181-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         80 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00182-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis         82 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00193-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
-rw-r--r--   3 jsolis jsolis        115 2021-07-15 20:51 /user/jsolis/dataset/result/scenario17/solution/part-00198-386fec45-9a9c-406f-b7d9-e84cb9ff3c84-c000.json.deflate
```
