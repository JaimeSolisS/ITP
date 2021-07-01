## Instructions

**Get total number of orders for each customer where the cutomer_state = 'TX'**

**Data Description: retail_db data is available in HDFS at /public/retail_db**

### retail_db data information:

> **\*Source directories:** /public/retail_db/orders and /public/retail_db/customers  
> **Source Columns: orders -** order_id, order_date, order_customer_id, order_status  
> **Source Columns: customers -** customer_id, customer_fname, customer_lname, customer_state (8th column) and many more  
> **delimiter:** (",")\*

### Output Requirements

> **\*Output Fields:** customer_fname, customer_lname, order_count  
> **File Format:** text  
> **Delimiter:** Tab character (\t)  
> **Place the result file in the HDFS directory:** /user/`whoami`/problem6/solution/  
> Replace `whoami` with your OS user name\*

### End of Problem

## Solution

### Read Files

```
val orders = spark.
read.
schema("order_id LONG, order_date STRING, order_customer_id LONG, order_status STRING").
format("csv").
load("/user/jsolis/data/retail_db/orders")

orders.show

val customers = spark.read.
schema("customer_id LONG, customer_fname STRING, customer_lname STRING, x STRING, x2 STRING, address STRING, city STRING, customer_state STRING, zip STRING").
format("csv").
load("/user/jsolis/data/retail_db/customers")

customers.show
```

### Filter, Join and Aggregation

```
val solution = customers.
filter($"customer_state" === "TX").
join(orders, customers("customer_id") === orders("order_customer_id")).
groupBy(customers("customer_fname"), customers("customer_lname")).
count.as("order_count")

val solution = customers.
filter($"customer_state" === "TX").
join(orders, customers("customer_id") === orders("order_customer_id")).
groupBy(customers("customer_fname"), customers("customer_lname")).
agg(count(lit(1)).alias("order_count"))

```

### Export solution

```
solution.
write.
mode("overwrite").
option("sep", "\t").
option("compression", "none").
format("csv").
save("/user/jsolis/problem6/solution")
```

### Check Solution

```
spark.read.csv("/user/jsolis/problem6/solution").show
spark.read.option("sep", "\t").csv("/user/jsolis/problem6/solution").show

scala> spark.read.option("sep", "\t").csv("/user/jsolis/problem6/solution").show
+---------+---------+---+
|      _c0|      _c1|_c2|
+---------+---------+---+
|  Raymond| Richards|  4|
|   Evelyn|   Wilson|  7|
|     Mary|Christian|  5|
| Nicholas|   Wright|  4|
| Benjamin|     Cole|  6|
|   Willie|   Galvan|  5|
|   Sandra|  Bennett|  6|
| Michelle|    Smith| 10|
| Jonathan|    Smith|  9|
|    Sarah|    Smith| 16|
|     Mary|  Roberts|  4|
|Elizabeth|     Ball|  2|
|   Evelyn|     Lowe|  9|
|Christina|    Ewing|  5|
|    Janet|   Wright|  7|
| Jonathan|     Cook|  6|
| Margaret|    Villa|  4|
|   Donald|  Sampson|  9|
|    Kathy|   Little|  4|
|     Mary| Mitchell|  6|
+---------+---------+---+
only showing top 20 rows
```
