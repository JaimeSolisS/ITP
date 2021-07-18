# CCA 175 Real Time Exam Scenario 18 | JOIN Multiple DataFrames, AGGREGATE and SORT data| Save as ORC

Data Description
All the Order records are stored at /user/spark/dataset/retail_db/orders
All the Customer records are stored at /user/spark/dataset/retail_db/customers

Find out total number of orders placed by each customer in the year 2014
Order status should be COMPLETE
Use ORC format for the output files
Output files must not be compressed
Place the result data in HDFS directory /user/spark/dataset/result/scenario18/solution
Output should only contain customer_fname,customer_lname,orders_count
Output should be sorted by orders_count in descending order

## Solution

### Read

```
val orders = spark.read.csv("/user/jsolis/dataset/retail_db/orders").toDF("order_id", "order_date", "order_client_id", "order_status").
filter($"order_status"==="COMPLETE").
 filter($"order_date".contains("2014"))

val customers = spark.read.csv("/user/jsolis/dataset/retail_db/customers").toDF("customer_id", "customer_fname", "customer_lname", "x", "xx", "customer_address", "customer_city", "customer_state", "customer_zip")
```

### Join & Agg

```
val ojc =
orders.
join(customers, orders("order_client_id") === customers("customer_id"))

val res =
ojc.
groupBy($"customer_id").
  agg(count(lit(1)).as("orders_count")).
  join(customers, ojc("customer_id") === customers("customer_id")).
  select("customer_fname", "customer_lname", "orders_count").
  orderBy($"orders_count".desc)
```

### Write

```
res.
write.
mode("overwrite").
option("compression", "none").
format("orc").
save("/user/jsolis/dataset/result/scenario18/solution")
```

### Checl Solution

```
spark.read.orc("/user/jsolis/dataset/result/scenario18/solution").show

  +--------------+--------------+------------+
|customer_fname|customer_lname|orders_count|
+--------------+--------------+------------+
|         Diana|         Smith|           1|
|         Carol|         Smith|           1|
|         Megan|       Bennett|           1|
|          Mary|         Olson|           1|
|      Virginia|       Elliott|           1|
|      Margaret|         Smith|           1|
|         Donna|        Hoover|           1|
|          Mary|        Powell|           1|
|        Robert|      Holloway|           1|
|          Mary|        Mullen|           1|
|          Mary|       Terrell|           1|
|     Katherine|        Massey|           1|
|          Mary|         Mills|           1|
|          Mary|      Morrison|           1|
|          Joan|    Strickland|           1|
|         Laura|          Case|           1|
|       Richard|         Welch|           1|
|        Denise|         Smith|           1|
|          Mary|         Smith|           1|
|          Mary|         Smith|           1|
+--------------+--------------+------------+
only showing top 20 rows
```

```
  hdfs dfs -ls /user/jsolis/dataset/result/scenario18/solution
  Found 5 items
-rw-r--r--   3 jsolis jsolis          0 2021-07-15 21:13 /user/jsolis/dataset/result/scenario18/solution/_SUCCESS
-rw-r--r--   3 jsolis jsolis       2604 2021-07-15 21:13 /user/jsolis/dataset/result/scenario18/solution/part-00000-406538ca-1299-4d6a-8e4b-6d5a0f15d1c3-c000.orc
-rw-r--r--   3 jsolis jsolis       6727 2021-07-15 21:13 /user/jsolis/dataset/result/scenario18/solution/part-00001-406538ca-1299-4d6a-8e4b-6d5a0f15d1c3-c000.orc
-rw-r--r--   3 jsolis jsolis      14331 2021-07-15 21:13 /user/jsolis/dataset/result/scenario18/solution/part-00002-406538ca-1299-4d6a-8e4b-6d5a0f15d1c3-c000.orc
-rw-r--r--   3 jsolis jsolis      22359 2021-07-15 21:13 /user/jsolis/dataset/result/scenario18/solution/part-00003-406538ca-1299-4d6a-8e4b-6d5a0f15d1c3-c000.orc
```
