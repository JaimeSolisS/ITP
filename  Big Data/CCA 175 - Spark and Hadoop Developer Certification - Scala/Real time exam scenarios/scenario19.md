# CCA 175 Real Time Exam Scenario 19 | Read CSV | AGGREGATE | RANK | Save as TEXT Pipe Delimited

Data Description
All the product records are stored at /user/spark/dataset/retail_db/products
All the category records are stored at /user/spark/dataset/retail_db/categories
All the Order Items records are stored at /user/spark/dataset/retail_db/order_items
Data is in text format

Output Requirement
Get top five best selling product in "Accessories" category
Place the result data in HDFS directory /user/spark/dataset/result/scenario19/solution
Save the output as text file
Use "|" as field separator
Output data should contain columns category_name,product_name,product_revenue

## Solution

### Read

```
val products = spark.read.csv("/user/jsolis/dataset/retail_db/products").
select("_c0", "_c1", "_c2", "_c4").
toDF("product_id", "product_category_id", "product_name", "product_price")

val category = spark.read.csv("/user/jsolis/dataset/retail_db/categories").toDF("category_id", "department_id", "category_name")

val order_items = spark.read.csv("/user/jsolis/dataset/retail_db/order_items").toDF("order_item_id", "order_item_order_id", "order_item_product_id", "order_item_quantity", "order_item_subtotal", "order_item_product_price")
```

### Joins & Agg

```
val join =
products.
join(category, products("product_category_id") === category("category_id")).
join(order_items, products("product_id") === order_items("order_item_product_id"))

val res = join.
filter($"category_name" ==="Accessories").
select("category_name", "product_id", "order_item_subtotal").
groupBy("category_name", "product_id").
agg(sum("order_item_subtotal").as("product_revenue")).
orderBy($"product_revenue".desc)

val res2 =
res.
join(products, res("product_id") === products("product_id")).
select("category_name", "product_name", "product_revenue").
map(x => x.mkString("|"))

```

### Write

```
res2.
write.
mode("overwrite").
option("compression", "none").
format("text").
save("/user/jsolis/dataset/result/scenario19/solution")
```

### Check Solution

```
spark.read.option("sep", "|").text("/user/jsolis/dataset/result/scenario19/solution").show(false)
+-------------------------------------------------------------------------+
|value                                                                    |
+-------------------------------------------------------------------------+
|Accessories|Team Golf San Francisco Giants Putter Grip|21766.290000000005|
|Accessories|Team Golf New England Patriots Putter Grip|20566.769999999982|
|Accessories|Team Golf St. Louis Cardinals Putter Grip|23940.42000000001  |
|Accessories|Team Golf Texas Longhorns Putter Grip|22366.04999999998      |
|Accessories|Team Golf Tennessee Volunteers Putter Grip|22865.85          |
|Accessories|Team Golf Pittsburgh Steelers Putter Grip|22166.13           |
```

```
hdfs dfs -ls /user/jsolis/dataset/result/scenario19/solution

Found 7 items
-rw-r--r--   3 jsolis jsolis          0 2021-07-15 22:22 /user/jsolis/dataset/result/scenario19/solution/_SUCCESS
-rw-r--r--   3 jsolis jsolis         72 2021-07-15 22:22 /user/jsolis/dataset/result/scenario19/solution/part-00000-95a8a7cf-cac3-4565-9a1d-76286ec18149-c000.txt
-rw-r--r--   3 jsolis jsolis         64 2021-07-15 22:22 /user/jsolis/dataset/result/scenario19/solution/part-00001-95a8a7cf-cac3-4565-9a1d-76286ec18149-c000.txt
-rw-r--r--   3 jsolis jsolis         68 2021-07-15 22:22 /user/jsolis/dataset/result/scenario19/solution/part-00002-95a8a7cf-cac3-4565-9a1d-76286ec18149-c000.txt
-rw-r--r--   3 jsolis jsolis         63 2021-07-15 22:22 /user/jsolis/dataset/result/scenario19/solution/part-00003-95a8a7cf-cac3-4565-9a1d-76286ec18149-c000.txt
-rw-r--r--   3 jsolis jsolis         74 2021-07-15 22:22 /user/jsolis/dataset/result/scenario19/solution/part-00004-95a8a7cf-cac3-4565-9a1d-76286ec18149-c000.txt
-rw-r--r--   3 jsolis jsolis         74 2021-07-15 22:22 /user/jsolis/dataset/result/scenario19/solution/part-00005-95a8a7cf-cac3-4565-9a1d-76286ec18149-c000.txt
```
