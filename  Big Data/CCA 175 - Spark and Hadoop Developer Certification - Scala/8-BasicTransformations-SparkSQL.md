# Basic Transformations

As part of this section we will see basic transformations we can perform on top of Data Frames such as filtering, aggregations, joins etc using SQL. We will build end to end solution by taking a simple problem statement.

- Spark SQL – Overview
- Define Problem Statement
- Preparing Tables
- Projecting Data
- Filtering Data
- Joining Tables - Inner
- Joining Tables - Outer
- Perform Aggregations
- Sorting Data
- Conclusion - Final Solution

If you are going to use CLIs, you can use Spark SQL using one of the 3 approaches.

**Using Spark SQL**

```
spark-sql \
    --master yarn \
    --conf spark.ui.port=0 \
    --conf spark.sql.warehouse.dir=/user/${USER}/warehouse
```

**Using Scala**

```
spark2-shell \
    --master yarn \
    --conf spark.ui.port=0 \
    --conf spark.sql.warehouse.dir=/user/${USER}/warehouse
```

## Spark SQL – Overview

Let us get an overview of Spark SQL.

Here are the standard operations which we typically perform as part of processing the data. In Spark we can perform these using Data Frame APIs or **Spark SQL**.

- Selection or Projection – select clause
  - It is also called as row level transformations.
  - Apply standardization rules (convert names and addresses to upper case).
  - Mask partial data (SSN and Date of births).
- Filtering data – where clause
  - Get orders based on date or product or category.
- Joins – join (supports outer join as well)
  - Join multiple data sets.
- Aggregations – group by and aggregations with support of functions such as sum, avg, min, max etc
  - Get revenue for a given order
  - Get revenue for each order
  - Get daily revenue
- Sorting – order by
  - Sort the final output by date.
  - Sort the final output by date, then by revenue in descending order.
  - Sort the final output by state or province, then by revenue in descending order.
- Analytics Functions – aggregations, ranking and windowing functions

  - Get top 5 stores by revenue for each state.
  - Get top 5 products by revenue in each category.

  ## Define Problem Statement

Let us define problemt statement to get an overview of basic transformations using Spark SQL.

- Get Daily Product Revenue using orders and order_items data set.
- We have following fields in **orders**.
  - order_id
  - order_date
  - order_customer_id
  - order_status
- We have following fields in **order_items**.
  - order_item_id
  - order_item_order_id
  - order_item_product_id
  - order_item_quantity
  - order_item_subtotal
  - order_item_product_price
- We have one to many relationship between orders and order_items.
- **orders.order_id** is **primary key** and **order_items.order_item_order_id** is foreign key to **orders.order_id**.
- By the end of this module we will explore all standard transformation and get daily product revenue using following fields.
  - **orders.order_date**
  - **order_items.order_item_product_id**
  - **order_items.order_item_subtotal** (aggregated using date and product_id).
- We will consider only **COMPLETE** or **CLOSED** orders.

## Preparing Tables

Let us prepare the tables to solve the problem.

- Make sure database is created.
- Create **orders** table.
- Load data from local path **/data/retail_db/orders** into newly created **orders** table.
- Preview data and get count from **orders**
- Create **order_items** table.
- Load data from local path **/data/retail_db/order_items** into newly created **orders** table.
- Preview data and get count from **order_items**

As tables and data are ready let us get into how to write queries against tables to perform basic transformation.

Spark.sql:

```
CREATE DATABASE IF NOT EXISTS retailDB;

USE retailDB

SHOW tables

CREATE TABLE orders (
    order_id INT,
    order_date STRING,
    order_customer_id INT,
    order_status STRING
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ','

LOAD DATA LOCAL INPATH '/data/retail_db/orders' INTO TABLE orders

SELECT * FROM orders LIMIT 10

CREATE TABLE order_items (
    order_item_id INT,
    order_item_order_id INT,
    order_item_product_id INT,
    order_item_quantity INT,
    order_item_subtotal FLOAT,
    order_item_product_price FLOAT
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ','

LOAD DATA LOCAL INPATH '/data/retail_db/order_items' INTO TABLE order_items

SELECT * FROM order_items LIMIT 10

```

For spark2-shell, use the same queries inside `spark.sql(" query_here ")`

## Projecting Data

Let us understand different aspects of projecting data. We primarily using `SELECT` to project the data.

- We can project all columns using `*` or some columns using column names.
- We can provide aliases to a column or expression using `AS` in `SELECT` clause.
- `DISTINCT` can be used to get the distinct records from selected columns. We can also use `DISTINCT *` to get unique records using all the columns.
- As of now **Spark SQL** does not support projecting all but one or few columns. It is supported in Hive. Following will work in hive and it will project all the columns from orders except for order_id.

```
SET hive.support.quoted.identifiers=none;
SELECT `(order_id)?+.+` FROM orders;
```

Spark-Sql:

```
SELECT * FROM orders LIMIT 10

DESCRIBE orders

SELECT order_customer_id, order_date, order_status FROM orders LIMIT 10

SELECT DISTINCT order_status FROM orders

SELECT DISTINCT * FROM orders LIMIT 10

```

## Filtering Data

Let us understand how we can filter the data in Spark SQL.

- We use `WHERE` clause to filter the data.
- All comparison operators such as `=`, `!=`, `>`, `<`, etc can be used to compare a column or expression or literal with another column or expression or literal.
- We can use operators such as LIKE with % and regexp_like for pattern matching.
- Boolan OR and AND can be performed when we want to apply multiple conditions.
  - Get all orders with order_status equals to COMPLETE or CLOSED. We can also use IN operator.
  - Get all orders from month 2014 January with order_status equals to COMPLETE or CLOSED
- We need to use `IS NULL` and `IS NOT NULL` to compare against null values.

Spark-sql:

```
SELECT * FROM orders WHERE order_status = 'COMPLETE' LIMIT 10

SELECT count(1) FROM orders WHERE order_status = 'COMPLETE'

SELECT * FROM orders WHERE order_status IN ('COMPLETE', 'CLOSED') LIMIT 10

SELECT * FROM orders
WHERE order_status IN ('COMPLETE', 'CLOSED')
    AND order_date LIKE '2014-01%'
LIMIT 10

SELECT * FROM orders
WHERE order_status IN ('COMPLETE', 'CLOSED')
    AND date_format(order_date, 'yyyy-MM') = '2014-01'
LIMIT 10

```

- Comparison against null can be done with `IS NULL` and `IS NOT NULL`. Below query will not work even though we have one record with phone_numbers as null.

## Joining Tables - Inner

Let us understand how to join data from multiple tables.

- **Spark SQL** supports ASCII style join (**JOIN with ON**).
- There are different types of joins.
  - INNER JOIN - Get all the records from both the datasets which satisfies JOIN condition.
  - OUTER JOIN - We will get into the details as part of the next topic
- Example for INNER JOIN

```
SELECT o.order_id,
    o.order_date,
    o.order_status,
    oi.order_item_subtotal
FROM orders o JOIN order_items oi
    ON o.order_id = oi.order_item_order_id
LIMIT 10
```

- We can join more than 2 tables in one query. Here is how it will look like.

```
SELECT o.order_id,
    o.order_date,
    o.order_status,
    oi.order_item_subtotal
FROM orders o JOIN order_items oi
    ON o.order_id = oi.order_item_order_id
    JOIN products p
    ON p.product_id = oi.order_item_product_id
LIMIT 10
```

- If we have to apply additional filters, it is recommended to use WHERE clause. ON clause should only have join conditions.
- We can have non equal join conditions as well, but they are not used that often.
- Here are some of the examples for INNER JOIN:
  - Get order id, date, status and item revenue for all order items.
  - Get order id, date, status and item revenue for all order items for all orders where order status is either COMPLETE or CLOSED.
  - Get order id, date, status and item revenue for all order items for all orders where order status is either COMPLETE or CLOSED for the orders that are placed in the month of 2014 January.

Spark-sql:

```
SELECT o.order_id,
    o.order_date,
    o.order_status,
    oi.order_item_subtotal
FROM orders o
JOIN order_items oi
    ON o.order_id = oi.order_item_order_id
LIMIT 10

SELECT count(1)
FROM orders o
JOIN order_items oi
    ON o.order_id = oi.order_item_order_id

SELECT o.order_id,
    o.order_date,
    o.order_status,
    oi.order_item_subtotal
FROM orders o
JOIN order_items oi
    ON o.order_id = oi.order_item_order_id
WHERE o.order_status IN ('COMPLETE', 'CLOSED')
LIMIT 10

SELECT o.order_id,
    o.order_date,
    o.order_status,
    oi.order_item_subtotal
FROM orders o
JOIN order_items oi
    ON o.order_id = oi.order_item_order_id
WHERE o.order_status IN ('COMPLETE', 'CLOSED')
    AND date_format(order_date, 'yyyy-MM') = '2014-01'
LIMIT 10
```

## Joining Tables - Outer

Let us understand how to perform outer joins using Spark SQL. There are 3 different types of outer joins.

- LEFT OUTER JOIN (default) - Get all the records from both the datasets which satisfies JOIN condition along with those records which are in the left side table but not in the right side table.
- RIGHT OUTER JOIN - Get all the records from both the datasets which satisfies JOIN condition along with those records which are in the right side table but not in the left side table.
- FULL OUTER JOIN - left union right
- When we perform the outer join (lets say left outer join), we will see this.
  - Get all the values from both the tables when join condition satisfies.
  - If there are rows on left side tables for which there are no corresponding values in right side table, all the projected column values for right side table will be null.
- Here are some of the examples for outer join.
  - Get all the orders where there are no corresponding order items.
  - Get all the order items where there are no corresponding orders.

```
SELECT o.order_id,
    o.order_date,
    o.order_status,
    oi.order_item_order_id,
    oi.order_item_subtotal
FROM orders o LEFT OUTER JOIN order_items oi
    ON o.order_id = oi.order_item_order_id
LIMIT 10
```

## Aggregating Data

Let us understand how to aggregate the data.

- We can perform global aggregations as well as aggregations by key.
- Global Aggregations
  - Get total number of orders.
  - Get revenue for a given order id.
  - Get number of records with order_status either COMPLETED or CLOSED.
- Aggregations by key - using `GROUP BY`
  - Get number of orders by date or status.
  - Get revenue for each order_id.
  - Get daily product revenue (using order date and product id as keys).
- We can also use `HAVING` clause to apply filtering on top of aggregated data.
  - Get daily product revenue where revenue is greater than $500 (using order date and product id as keys).
- Rules while using `GROUP BY`.
  - We can have the columns which are specified as part of `GROUP BY` in `SELECT` clause.
  - On top of those, we can have derived columns using aggregate functions.
  - We cannot have any other columns that are not used as part of `GROUP BY` on derived column using non aggregate functions.
  - We will not be able to use aggregate functions or aliases used in the select clause as part of the where clause.
  - If we want to filter based on aggregated results, then we can leverage `HAVING` on top of `GROUP BY` (specifying `WHERE` is not an option)
- Typical query execution - FROM -> WHERE -> GROUP BY -> SELECT

```
SELECT count(order_id) FROM orders

SELECT count(DISTINCT order_date) FROM orders

SELECT round(sum(order_item_subtotal), 2) AS order_revenue
FROM order_items
WHERE order_item_order_id = 2

SELECT count(1)
FROM orders
WHERE order_status IN ('COMPLETE', 'CLOSED')

SELECT order_status,
    count(1) AS status_count
FROM orders
GROUP BY order_status

SELECT order_item_order_id,
    round(sum(order_item_subtotal), 2) AS order_revenue
FROM order_items
GROUP BY order_item_order_id LIMIT 10


SELECT o.order_date,
    oi.order_item_product_id,
    round(sum(oi.order_item_subtotal), 2) AS revenue
FROM orders o JOIN order_items oi
    ON o.order_id = oi.order_item_order_id
WHERE o.order_status IN ('COMPLETE', 'CLOSED')
    AND revenue >= 500 //Error
GROUP BY o.order_date,
    oi.order_item_product_id
LIMIT 10

SELECT o.order_date,
    oi.order_item_product_id,
    round(sum(oi.order_item_subtotal), 2) AS revenue
FROM orders o JOIN order_items oi
    ON o.order_id = oi.order_item_order_id
WHERE o.order_status IN ('COMPLETE', 'CLOSED')
GROUP BY o.order_date,
    oi.order_item_product_id
HAVING revenue >= 500
LIMIT 10

```

## Sorting Data

Let us understand how to sort the data using **Spark SQL**.

- We typically perform sorting as final step.
- Sorting can be done either by using one field or multiple fields.
- We can sort the data either in ascending order or descending order by using column or expression.
- By default, the sorting order is ascendig and we can change it to descending by using `DESC`.

```
SELECT * FROM orders
ORDER BY order_customer_id
LIMIT 10

SELECT * FROM orders
ORDER BY order_customer_id,
    order_date
LIMIT 10

SELECT * FROM orders
ORDER BY order_customer_id,
    order_date DESC
LIMIT 10

SELECT o.order_date,
    oi.order_item_product_id,
    round(sum(oi.order_item_subtotal), 2) AS revenue
FROM orders o JOIN order_items oi
    ON o.order_id = oi.order_item_order_id
WHERE o.order_status IN ('COMPLETE', 'CLOSED')
GROUP BY o.order_date,
    oi.order_item_product_id
ORDER BY o.order_date,
    revenue DESC
LIMIT 10
```

## Conclusion - Final Solution

Let us review the Final Solution for our problem statement **daily_product_revenue**.

- Prepare tables
  - Create tables
  - Load the data into tables
- We need to project the fields which we are interested in.
  - order_date
  - order_item_product_id
  - product_revenue
- As we have fields from multiple tables, we need to perform join after which we have to filter for COMPLETE or CLOSED orders.
- We have to group the data by order_date and order_item_product_id, then we have to perform aggregation on order_item_subtotal to get product_revenue.

### Create Database

```
CREATE DATABASE IF NOT EXISTS retaildb
```

### Create Tables

```
CREATE TABLE orders (
    order_id INT,
    order_date STRING,
    order_customer_id INT,
    order_status STRING
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ','

CREATE TABLE order_items (
    order_item_id INT,
    order_item_order_id INT,
    order_item_product_id INT,
    order_item_quantity INT,
    order_item_subtotal FLOAT,
    order_item_product_price FLOAT
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
```

### Load Tables

```
LOAD DATA LOCAL INPATH '/data/retail_db/orders' INTO TABLE orders
LOAD DATA LOCAL INPATH '/data/retail_db/order_items' INTO TABLE order_items
```

### Query

```
SELECT o.order_date,
    oi.order_item_product_id,
    round(sum(oi.order_item_subtotal), 2) AS product_revenue
FROM orders o JOIN order_items oi
    ON o.order_id = oi.order_item_order_id
WHERE o.order_status IN ('COMPLETE', 'CLOSED')
GROUP BY o.order_date,
    oi.order_item_product_id
ORDER BY o.order_date,
    product_revenue DESC
```
