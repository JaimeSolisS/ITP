# CCA 175 Real Time Exam Scenario 20 | JOIN Multiple DataFrames | Save as PARQUET | SNAPPY Compression

Data Description
All the Order records are stored at /user/spark/dataset/retail_db/orders
All the Order Items records are stored at /user/spark/dataset/retail_db/order_items
All the Customer records are stored at /user/spark/dataset/retail_db/customers

Output Requirement
Get customers in each city who have placed successful orders of revenue more than 500
Use parquet format for the output files
Use snappy compression
Place the result data in HDFS directory /user/spark/dataset/result/scenario20/solution
Output should only contain customer_fname, customer_lname, order_revenue

## Solution

### Read

```
val orders = spark.read.csv("/user/jsolis/dataset/retail_db/orders").toDF("order_id", "order_date", "order_customer_id", "order_status")

val order_items = spark.read.csv("/user/jsolis/dataset/retail_db/order_items").toDF("order_item_id", "order_item_order_id", "order_item_product_id", "order_item_quantity", "order_item_subtotal", "order_item_product_price")

val customers = spark.read.csv("/user/jsolis/dataset/retail_db/customers").toDF("customer_id", "customer_fname", "customer_lname", "x", "xx", "customer_addres", "customer_city", "customer_state", "customer_zip")
```

### Joins & Agg

```
val join =
orders.join(customers, orders("order_customer_id") === customers("customer_id")).
join(order_items, orders("order_id") === order_items("order_item_order_id"))

val join2 = join.
groupBy("order_id").
agg(sum("order_item_subtotal").as("order_revenue")).
filter($"order_revenue" > 500)

val join3 =join2.
join(orders, join2("order_id") === orders("order_id"))

val join4 = join3.
join(customers, join3("order_customer_id") === customers("customer_id"))

val res = join4.select("customer_fname", "customer_lname", "order_revenue")
```

### Write Solution

```
res.
write.
coalesce
mode("overwrite").
option("compression", "snappy").
format("parquet").
save("/user/jsolis/dataset/result/scenario20/solution")
```

### Check Solution

```
spark.read.parquet("/user/jsolis/dataset/result/scenario20/solution").show
+--------------+--------------+------------------+
|customer_fname|customer_lname|     order_revenue|
+--------------+--------------+------------------+
|          Mary|         Allen|            899.94|
|         Betty|      Phillips|           1074.89|
|        Cheryl|         Kline|1629.7900000000002|
|          Judy|         Smith|            649.95|
|          Mary|         Mckee|            529.96|
|          Mary|        Herman| 679.9300000000001|
|          Mary|         Smith|            529.97|
|          Mary|    Mclaughlin| 679.9100000000001|
|          Mary|          Wong|            599.98|
|        Ronald|      Harrison|            799.96|
|        Robert|      Holloway| 599.9300000000001|
|        Sharon|         Smith|            539.91|
|          Mary|         Smith| 659.9200000000001|
|          Mark|         Nunez| 799.9000000000001|
|         James|         Smith|           1359.84|
|         Ralph|         Hicks| 624.7800000000001|
|          Mary|       Acevedo|           1029.93|
|     Catherine|         Smith|            549.95|
|        Eugene|        Powell| 729.8800000000001|
|         Emily|         Jones|            743.85|
+--------------+--------------+------------------+
only showing top 20 rows
```

```
hdfs dfs -ls /user/jsolis/dataset/result/scenario20/solution

Found 201 items
-rw-r--r--   3 jsolis jsolis          0 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/_SUCCESS
-rw-r--r--   3 jsolis jsolis       3336 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00000-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3240 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00001-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3171 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00002-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3330 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00003-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3795 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00004-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3460 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00005-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3499 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00006-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3694 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00007-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3282 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00008-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3004 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00009-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3602 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00010-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3597 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00011-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3577 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00012-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3479 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00013-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3419 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00014-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3508 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00015-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3147 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00016-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3288 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00017-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3595 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00018-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3614 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00019-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3745 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00020-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3230 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00021-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3499 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00022-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3510 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00023-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3365 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00024-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3572 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00025-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3295 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00026-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3583 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00027-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3333 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00028-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3248 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00029-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3429 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00030-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3459 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00031-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3575 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00032-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3351 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00033-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3179 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00034-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3472 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00035-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3357 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00036-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3394 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00037-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3279 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00038-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3299 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00039-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3346 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00040-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3188 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00041-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3462 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00042-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3497 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00043-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3337 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00044-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3332 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00045-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3264 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00046-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3504 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00047-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3209 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00048-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3118 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00049-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3537 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00050-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3570 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00051-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3403 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00052-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3664 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00053-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3556 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00054-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3292 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00055-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3267 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00056-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3099 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00057-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3221 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00058-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3339 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00059-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3202 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00060-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3543 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00061-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3656 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00062-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3398 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00063-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3667 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00064-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3815 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00065-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3546 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00066-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3437 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00067-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3315 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00068-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3486 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00069-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3066 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00070-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3455 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00071-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3348 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00072-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3219 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00073-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3358 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00074-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3315 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00075-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       2963 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00076-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3554 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00077-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3257 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00078-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3718 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00079-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3428 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00080-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3193 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00081-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3334 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00082-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3343 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00083-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3472 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00084-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3360 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00085-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3348 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00086-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3254 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00087-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3198 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00088-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       2931 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00089-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3272 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00090-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3011 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00091-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3282 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00092-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3544 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00093-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3439 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00094-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3587 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00095-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3570 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00096-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3227 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00097-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3641 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00098-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3351 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00099-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3595 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00100-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3419 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00101-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3256 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00102-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3506 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00103-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3684 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00104-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       2993 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00105-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3111 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00106-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3280 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00107-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3467 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00108-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3179 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00109-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3293 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00110-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3362 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00111-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3317 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00112-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3464 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00113-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3663 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00114-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3118 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00115-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3499 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00116-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3767 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00117-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3489 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00118-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3366 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00119-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3330 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00120-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3352 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00121-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3262 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00122-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3812 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00123-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3558 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00124-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3555 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00125-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3282 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00126-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3460 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00127-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3068 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00128-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3368 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00129-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3342 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00130-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3573 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00131-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3199 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00132-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3597 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00133-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3559 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00134-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3496 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00135-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3278 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00136-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3677 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00137-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3468 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00138-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3252 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00139-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3193 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00140-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3474 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00141-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       2996 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00142-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3347 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00143-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3560 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00144-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3440 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00145-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3366 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00146-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3374 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00147-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3424 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00148-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3456 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00149-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3402 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00150-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3525 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00151-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3544 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00152-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3350 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00153-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3277 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00154-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3428 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00155-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3428 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00156-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3277 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00157-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3763 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00158-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3456 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00159-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3673 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00160-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3264 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00161-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3549 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00162-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3295 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00163-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3287 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00164-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3453 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00165-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3449 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00166-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3309 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00167-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3656 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00168-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3168 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00169-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3296 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00170-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3611 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00171-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3327 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00172-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3609 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00173-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3306 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00174-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3680 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00175-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3392 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00176-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3237 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00177-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3517 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00178-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3283 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00179-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3254 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00180-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       2947 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00181-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3322 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00182-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3157 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00183-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3594 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00184-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3289 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00185-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3593 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00186-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3475 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00187-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3348 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00188-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3386 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00189-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3273 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00190-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3349 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00191-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3653 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00192-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3496 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00193-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3353 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00194-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3620 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00195-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3443 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00196-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3240 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00197-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3336 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00198-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
-rw-r--r--   3 jsolis jsolis       3169 2021-07-15 23:05 /user/jsolis/dataset/result/scenario20/solution/part-00199-514e475f-673d-4480-87ed-d1ef2c25d38d-c000.snappy.parquet
```
