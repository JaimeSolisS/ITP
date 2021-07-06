## Problem:

- Read orderitems and products from HDFS location
- Produce this output: ORDER_ITEM_ORDER_ID PRODUCT_ID PRODUCT_NAME PRODUCT_PRICE ORDER_SUBTOTAL
- Save above output as avro snappy in hdfs location /user/yourusername/problem20/output-avro-snappy/

> **Source Columns: order_items -** order_item_id, order_item_order_id, order_item_product_id, order_item_quantity, order_item_subtotal, order_item_product_price  
> **Source Columns: products -** product_id, product_category_id, product_name, product_description, product_price, product_image\*

### Launch spark

```
spark2-shell --master yarn --packages com.databricks:spark-avro_2.11:4.0.0
```

### Read files

```
val orders_items =
spark.
read.
csv("/user/jsolis/data/retail_db/order_items").
toDF("order_item_id", "order_item_order_id", "order_item_product_id", "order_item_quantity", "order_item_subtotal", "order_item_product_price")

val products =
spark.
read.
csv("/user/jsolis/data/retail_db/products").
toDF("product_id", "product_category_id", "product_name", "product_description", "product_price", "product_image")
```

### Join

```
val solution = products.
join(orders_items, products("product_id") === orders_items("order_item_product_id")).
select($"order_item_order_id".as("ORDER_ITEM_ORDER_ID"), $"product_id".as("PRODUCT_ID"), $"product_name".as("PRODUCT_NAME"), $"product_price".as("PRODUCT_PRICE"), $"order_item_subtotal".as("ORDER_SUBTOTAL"))
```

### Export Solution

```
solution.
write.
mode("overwrite").
format("com.databricks.spark.avro").
option("compression", "snappy").
save("/user/jsolis/problem20/output-avro-snappy/")
```

### Check Solution

```
scala> spark.read.format("com.databricks.spark.avro").load("/user/jsolis/problem20/output-avro-snappy/").show
+-------------------+----------+--------------------+-------------+--------------+
|ORDER_ITEM_ORDER_ID|PRODUCT_ID|        PRODUCT_NAME|PRODUCT_PRICE|ORDER_SUBTOTAL|
+-------------------+----------+--------------------+-------------+--------------+
|                  1|       957|Diamondback Women...|       299.98|        299.98|
|                  2|      1073|Pelican Sunstream...|       199.99|        199.99|
|                  2|       502|Nike Men's Dri-FI...|         50.0|         250.0|
|                  2|       403|Nike Men's CJ Eli...|       129.99|        129.99|
|                  4|       897|Team Golf New Eng...|        24.99|         49.98|
|                  4|       365|Perfect Fitness P...|        59.99|        299.95|
|                  4|       502|Nike Men's Dri-FI...|         50.0|         150.0|
|                  4|      1014|O'Brien Men's Neo...|        49.98|        199.92|
|                  5|       957|Diamondback Women...|       299.98|        299.98|
|                  5|       365|Perfect Fitness P...|        59.99|        299.95|
|                  5|      1014|O'Brien Men's Neo...|        49.98|         99.96|
|                  5|       957|Diamondback Women...|       299.98|        299.98|
|                  5|       403|Nike Men's CJ Eli...|       129.99|        129.99|
|                  7|      1073|Pelican Sunstream...|       199.99|        199.99|
|                  7|       957|Diamondback Women...|       299.98|        299.98|
|                  7|       926|Glove It Imperial...|        15.99|         79.95|
|                  8|       365|Perfect Fitness P...|        59.99|        179.97|
|                  8|       365|Perfect Fitness P...|        59.99|        299.95|
|                  8|      1014|O'Brien Men's Neo...|        49.98|        199.92|
|                  8|       502|Nike Men's Dri-FI...|         50.0|          50.0|
+-------------------+----------+--------------------+-------------+--------------+
only showing top 20 rows
```
