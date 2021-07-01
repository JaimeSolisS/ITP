## Instructions

**Get the customers who have not placed any orders, sorted by customer_lname and then customer_fname
Data Description**

**Data is available in local file system /data/retail_db**

### retail_db information:

> **\*Source directories:** /data/retail_db/orders and /data/retail_db/customers

    **Source delimiter:** comma(",")

**Source Columns: orders-** order_id, order_date, order_customer_id, order_status  
 **Source Columns: customers -** customer_id, customer_fname, customer_lname and many more\*

### Output Requirements:

> **\*Target Columns:** customer_lname, customer_fname

    **Number of Files:** 1
    **Place the output file in the HDFS directory:** /user/`whoami`/problem2/solution/
    Replace `whoami` with your OS username
    File format should be text delimiter is (",")
    **Compression:** Uncompressed*

### End of Problem

### Solution

Move local file to HDFS. As File is already in dfs, we don't need to do this, but if not here are the commands FYI

```
hdfs dfs -copyFromLocal /data/retail_db/orders /data/retail_db/orders

hdfs dfs -copyFromLocal /data/retail_db/orders /data/retail_db/customers
```

### Read Files

```
val orders = spark.read.csv("/user/userName/data/retail_db/orders")
val customers = spark.read.csv("/user/userName/data/retail_db/customers")
```

### Understand Data

```
orders.show(false)
customers.show(false)
```

### Join and Transform

```
val solution = orders.
join(customers, customers("_c0")=== orders("_c2"), "right").
select(customers("_c2").as("customer_lname"), customers("_c1").as("customer_fname"), orders("_c2")).
filter("_c2 IS NULL").
drop("_c2").
orderBy("customer_lname","customer_fname")
```

### Export solution

solution.
coalesce(1).
write.
mode("overwrite").
option("sep", ",").
option("compression","none").
format("csv").
save("/user/jsolis/problem2/solution/")

### Check Results
scala> spark.read.text("/user/jsolis/problem2/solution/").show

```
+--------------+
|         value|
+--------------+
|   Bolton,Mary|
|Ellison,Albert|
| Green,Carolyn|
|   Greene,Mary|
|  Harrell,Mary|
|    Lewis,Mary|
|  Mueller,Mary|
| Patel,Matthew|
|     Shaw,Mary|
|  Smith,Amanda|
|  Smith,Ashley|
|    Smith,Carl|
|    Smith,Emma|
|   Smith,Grace|
|   Smith,James|
|    Smith,Joan|
| Smith,Kenneth|
|   Smith,Kevin|
|    Smith,Mary|
|    Smith,Mary|
+--------------+
only showing top 20 rows

```

If solution needs to be moved to local just use

```
hdfs dfs -copyToLocal /user/userName/problem2/solution ~/problem2
```
