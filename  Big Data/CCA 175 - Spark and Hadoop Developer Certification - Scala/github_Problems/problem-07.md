## Instructions

**List the names of the Top 5 products by revenue ordered on '2013-07-26'. Revenue is considered only for COMPLETE and CLOSED orders.**

**Data Description: retail_db data is available in HDFS at /public/retail_db**

### retail_db data information:

> **\*Source directories:**  
> /public/retail_db/orders  
> /public/retail_db/order_items  
> /public/retail_db/products  
> **Source delimiter:** comma(",")  
> **Source Columns: orders -** order_id, order_date, order_customer_id, order_status  
> **Source Columns: order_items -** order_item_id, order_item_order_id, order_item_product_id, order_item_quantity, order_item_subtotal, order_item_product_price  
> **Source Columns: products -** product_id, product_category_id, product_name, product_description, product_price, product_image\*

### Output Requirements

> **\*Target Columns:** order_date, order_revenue, product_name, product_category_id  
> **Data has to be sorted in descending order by order_revenue**  
> **File Format:** text  
> **Delimiter:** colon (:)  
> **Place the output file in the HDFS directory:** /user/`whoami`/problem7/solution/  
> Replace `whoami` with your OS user name\*

### End of Problem

## Solution

### Read Files

```
val orders = spark.read.
schema("order_id LONG, order_date STRING, order_customer_id LONG, order_status STRING").
csv("/user/jsolis/data/retail_db/orders").
filter("order_status IN ('COMPLETE', 'CLOSED')").
withColumn("order_date", to_date($"order_date", "yyyy-MM-dd")).
filter($"order_date" === "2013-07-26")

val order_items = spark.read.
schema("order_item_id LONG, order_item_order_id LONG, order_item_product_id LONG, order_item_quantity INT, order_item_subtotal FLOAT, order_item_product_price FLOAT").
csv("/user/jsolis/data/retail_db/order_items")

val products = spark.read.
schema("product_id LONG, product_category_id LONG, product_name STRING, product_description STRING, product_price FLOAT, product_image STRING").
csv("/user/jsolis/data/retail_db/products")
```

### Join

```
val join =
order_items.
join(orders, order_items("order_item_order_id") === orders("order_id")).
join(products,order_items("order_item_product_id") === products("product_id"))
```

### Aggrgation

```
val solution = join.
groupBy($"order_item_product_id", $"product_name", $"product_category_id", $"order_date").
agg(sum($"order_item_subtotal").as("order_revenue")).
select("order_date","order_revenue", "product_name", "product_category_id" ).
orderBy($"order_revenue".desc).
limit(5).
withColumn("order_date", to_timestamp($"order_date"))

```

### Export Solution

```

solution.
write.
mode("overwrite").
option("compression", "none").
option("sep", ":").
csv("/user/jsolis/problem7/solution")
```

### Check Solution

```
spark.read.option("sep", ":").csv("/user/jsolis/problem7/solution").show

```
