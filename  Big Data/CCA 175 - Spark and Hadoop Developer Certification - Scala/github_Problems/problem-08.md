## Instructions

**List the order Items where the order_status = 'PENDING PAYMENT' order by order_id**

**Data Description: Data is available in HDFS location**

### retail_db data information:

> **\*Source directories:** /data/retail_db/orders  
> **Source delimiter:** comma(",")  
> **Source Columns: orders** - order_id, order_date, order_customer_id, order_status\*

### Output Requirements

> **\*Target columns:** order_id, order_date, order_customer_id, order_status  
> **File Format:** orc  
> **Place the output files in the HDFS directory:** /user/`whoami`/problem8/solution/  
> Replace `whoami` with your OS user name\*

### End of Problem

## Solution

### See file

```
$ hdfs dfs -cat /user/jsolis/data/retail_db/orders/part-00000 | head -5
1,2013-07-25 00:00:00.0,11599,CLOSED
2,2013-07-25 00:00:00.0,256,PENDING_PAYMENT
3,2013-07-25 00:00:00.0,12111,COMPLETE
4,2013-07-25 00:00:00.0,8827,CLOSED
5,2013-07-25 00:00:00.0,11318,COMPLETE
```

### Read File

```
val orders = spark.
read.
schema("order_id INT, order_date STRING, order_customer_id LONG, order_status STRING").
csv("/user/jsolis/data/retail_db/orders")

orders.show
```

### Filter and Order

```
val solution = orders.
filter($"order_status" === "PENDING_PAYMENT").
orderBy("order_id")
```

### Export Solution

```
solution.
write.
mode("overwrite").
option("compression", "none").
format("orc").
save("user/jsolis/problem8/solution/")
```

### Check Results

```
spark.read.orc("user/jsolis/problem8/solution/").show
+--------+--------------------+-----------------+---------------+
|order_id| order_date|order_customer_id| order_status|
+--------+--------------------+-----------------+---------------+
| 68491|2014-05-24 00:00:...| 7736|PENDING_PAYMENT|
| 68496|2014-05-25 00:00:...| 3156|PENDING_PAYMENT|
| 68497|2014-05-25 00:00:...| 1973|PENDING_PAYMENT|
| 68498|2014-05-26 00:00:...| 11257|PENDING_PAYMENT|
| 68499|2014-05-26 00:00:...| 10220|PENDING_PAYMENT|
| 68508|2014-05-30 00:00:...| 3081|PENDING_PAYMENT|
| 68511|2014-05-31 00:00:...| 5012|PENDING_PAYMENT|
| 68512|2014-06-01 00:00:...| 1137|PENDING_PAYMENT|
| 68517|2014-06-04 00:00:...| 931|PENDING_PAYMENT|
| 68523|2014-06-05 00:00:...| 1432|PENDING_PAYMENT|
| 68533|2014-06-07 00:00:...| 10699|PENDING_PAYMENT|
| 68538|2014-06-09 00:00:...| 915|PENDING_PAYMENT|
| 68539|2014-06-10 00:00:...| 7445|PENDING_PAYMENT|
| 68540|2014-06-10 00:00:...| 5211|PENDING_PAYMENT|
| 68545|2014-06-12 00:00:...| 3582|PENDING_PAYMENT|
| 68546|2014-06-12 00:00:...| 6405|PENDING_PAYMENT|
| 68550|2014-06-13 00:00:...| 11785|PENDING_PAYMENT|
| 68553|2014-06-13 00:00:...| 10037|PENDING_PAYMENT|
| 68560|2014-06-15 00:00:...| 2572|PENDING_PAYMENT|
| 68563|2014-06-16 00:00:...| 4378|PENDING_PAYMENT|
+--------+--------------------+-----------------+---------------+
only showing top 20 rows
```
