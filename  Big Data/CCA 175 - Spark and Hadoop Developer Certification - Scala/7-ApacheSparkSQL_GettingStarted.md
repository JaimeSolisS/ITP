# Getting Started

Let us get started to get into Spark SQL. In this module we will see how to launch and use Spark SQL.

- Overview of Spark Documentation
- Launching and Using Spark SQL
- Overview of Spark SQL Properties
- Running OS Commands using Spark SQL
- Understanding Warehouse Directory
- Managing Spark Metastore Databases
- Managing Spark Metastore Tables
- Retrieve Metadata of Tables
- Role of Spark or Hive Metastore

## Overview of Spark Documentation

Let us go through the details related to Spark Documentation. It is very important for you to get comfortable with Spark Documentation if you are aspiring for open book certification exams like CCA 175.

- Click [here](https://spark.apache.org/docs/latest/sql-programming-guide.html) to go to latest Spark SQL and Data Frames documentation.
- We typically get documentation for latest version.
- We can replace **latest** in the URL with the version of Spark to get specific version's official documentation.
- Also we have resources provided by **databricks**.

## Launching and Using Spark SQL

Let us understand how to launch Spark SQL CLI.

- Logon to the gateway node of the cluster.
- We have 2 versions of Spark in our labs. One can use `spark-sql` to launch Spark SQL using 1.6.x and `spark2-sql` to launch Spark SQL using 2.3.x.
- Launch Spark SQL CLI using `spark-sql`. In clustered mode we might have to add additional arguments. For example

```
spark-sql  --master yarn  --conf spark.ui.port=0  --conf spark.sql.warehouse.dir=/user/egarcia1/warehouse
```

## Overview of Spark SQL Properties

Let us understand details about Spark SQL properties which control Spark SQL run time environment.

- Spark SQL inherits properties defined for Spark. There are some Spark SQL related properties as well and these are applicable even for Data Frames.
- We can review these properties using Management Tools such as **Ambari** or **Cloudera Manager Web UI**
- Spark run time behavior is controlled by HDFS Properties files, YARN Properties files, Hive Properties files etc in those clusters where Spark is integrated with Hadoop and Hive.
- We can get all the properties using `SET;` in Spark SQL CLI

Let us review some important properties in Spark SQL.

```
spark.sql.warehouse.dir
spark.sql.catalogImplementation
```

- We can review the current value using `SET spark.sql.warehouse.dir;`

```
spark.sql.warehouse.dir /user/egarcia1/warehouse

```

- Properties with default values does not show up as part of `SET` command. But we can check and overwrite the values - for example

```
spark-sql> SET spark.sql.shuffle.partitions;
spark.sql.shuffle.partitions    200
Time taken: 0.021 seconds, Fetched 1 row(s)
```

- We can overwrite property by setting value using the same **SET** command, eg:

```
SET spark.sql.shuffle.partitions=2
```

## Running OS Commands using Spark SQL

Let us understand how to run OS commands using Spark SQL CLI.

- We can run OS commands using **!** at the beginning.
  - Listing local Files `!ls -ltr;`
  - Listing HDFS Files `!hdfs dfs -ls /public/retail_db;`

## Understanding Warehouse Directory

Let us go through the details related to Spark Metastore Warehouse Directory.

- A Database in Spark SQL is nothing but directory in underlying file system like HDFS.
- A Spark Metastore Table is nothing but directory in underlying file systems like HDFS.
- A Partition of Spark Metastore Table is nothing but directory in underlying file systems like HDFS under table.
- Warehouse Directory is the base directory where directories related to databases, tables go by default.
- It is controlled by `spark.sql.warehouse.dir`. You can get the value by saying `SET spark.sql.warehouse.dir;`
  > <span style="color:red">Do not overwrite this property Spark SQL CLI. It will not have any effect.</span>
- Underlying directory for a database will have **.db** extension.

```
spark-sql> show tables;
21/06/27 22:09:27 INFO CodeGenerator: Code generated in 16.209247 ms
default categories_parquet      false
default categories_parquet_1    false
default categories_partitioned  false
default categories_replica      false
default categories_replica1     false
default customer_m      false
default customer_order  false
default customer_orders false
default customer_result false
default customers_hive  false
default customers_hive1 false
default customers_p4_q3 false
default customers_p4_q31        false
default customers_p4_q3_output  false
default customers_p4_q3_output1 false
default employee        false
default example false
default example2        false
default ord_avro        false
default practicetab     false
default product_ranked_new      false
default product_ranked_new1     false
default product_replica false
default product_replica1        false
default rt_cat_parquet  false
default rt_cat_parquet2 false
default rt_cat_partitioned      false
default rt_cat_replica  false
default rt_categories   false
default rt_creditbalance        false
default rt_customer_m   false
default rt_customer_order       false
default rt_customer_result      false
default rt_customers_hive       false
default rt_customers_p4_q3      false
default rt_emplocation  false
default rt_nocreditbalance      false
default rt_order        false
default rt_orders_parquet       false
default rt_product_ranked_new   false
default rt_product_replica      false
default rt_products     false
default sam_employee    false
default sg_ambari_logs_extract  false
default siva_logs6      false
default siva_logs7      false
default siva_logs_normal1       false
default siva_logs_normal2       false
default siva_logs_normal3       false
default siva_logs_normal4       false
default skalidindi_check_filewriting    false
default src_temp        false
default t1      false
Time taken: 0.202 seconds, Fetched 53 row(s)
```

## Managing Spark Metastore Databases

Let us undestand how to manage Spark Metastore Databases.

- Make a habit of reviewing Language Manual.
- We can create database using **CREATE DATABASE** Command.
- For e. g.: `CREATE DATABASE itversity_demo;`
- If the database exists it will fail. If you want to ignore with out throwing error you can use **IF NOT EXISTS**
- e. g.: `CREATE DATABASE IF NOT EXISTS itversity_demo;`
- We can list the databases using `SHOW databases;`
- Spark Metastore is multi tenant database. To switch to a database, you can use **USE** Command. e. g.: `USE itversity_demo;`
- We can drop empty database by using `DROP DATABASE itversity_demo;`.
- Add cascade to drop all the tables and then the database `DROP DATABASE itversity_demo CASCADE;`.
- We can also specify location while creating the database - `CREATE DATABASE itversity_demo LOCATION '/user/itversity/custom/itversity_demo.db'`

* Click [here](https://cwiki.apache.org/confluence/display/HIVE) to go to Apache Hive manual

```
spark-sql> show databases;
21/06/27 22:28:58 INFO CodeGenerator: Code generated in 15.120906 ms
default
sgadade
sgadade_nyse
test
Time taken: 0.072 seconds, Fetched 4 row(s)
```

```
spark-sql> select current_database();
...
default
Time taken: 1.858 seconds, Fetched 1 row(s)
```

```
spark-sql> USE test;
Time taken: 0.034 seconds
spark-sql> select current_database();
...
test
```

### Commands

```
DROP DATABASE IF EXISTS databaseName;

CREATE DATABASE databaseName:

CREATE DATABASE IF NOT EXISTS databaseName;

SHOW databases;

SELECT current_database();

USE databaseName;

CREATE TABLE table_demo (i INT)

DROP DATABASE itversity_demo CASCADE

```

## Managing Spark Metastore Tables

Let us create our first Spark Metastore table. We will also have a look into how to list the tables.

- We will get into details related to DDL Commands at a later point in time.
- For now we will just create our first table. We will get into the details about creating tables as part of subsequent sections.
  > <span style="color:red">Use your OS username as prefix for the databases, if you are using our labs</span>

```
SELECT current_database()

DROP DATABASE itversity_retail CASCADE

CREATE DATABASE itversity_retail

USE itversity_retail

CREATE TABLE orders (
  order_id INT,
  order_date STRING,
  order_customer_id INT,
  order_status STRING
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ','

```

- we can list tables using `SHOW tables;`

* We can also drop the table using `DROP TABLE` command. We will get into more details at a later point in time.
* We can also truncate the managed tables using `TRUNCATE TABLE` command.

## Retrieve Metadata of Tables

As the table is created, let us understand how to get the metadata of a table.

- We can get metadata of Hive Tables using several commands.
  - DESCRIBE - e.g.: `DESCRIBE orders;`
  - DESCRIBE EXTENDED - e.g.: `DESCRIBE EXTENDED orders;`
  - DESCRIBE FORMATTED - e.g.: `DESCRIBE FORMATTED orders;`
- **DESCRIBE** will give only field names and data types.
- **DESCRIBE EXTENDED** will give all the metadata, but not in readable format in Hive. It is same as **DESCRIBE FORMATTED** in Spark SQL.
- **DESCRIBE FORMATTED** will give metadata in readable format.

**As the output is truncated using Jupyter, we will actually see the details using `spark-sql`**

## Role of Spark or Hive Metastore

Let us understand the role of Spark Metastore or Hive Metasore. We need to first understand details related to Metadata generated for Spark Metastore tables.

- When we create a Spark Metastore table, there is metadata associated with it.
  - Table Name
  - Column Names and Data Types
  - Location
  - File Format
  - and more
- This metadata has to be stored some where so that Query Engines such as Spark SQL can access the information to serve our queries.

Let us understand where the metadata is stored.

- Information is typically stored in relational database and it is called as metastore.
- It is extensively used by Hive or Spark SQL engine for syntax and semantics check as well as execution of queries.
- In our case it is stored in MySQL Database. Let us review the details by going through relevant properties.

## Exercise - Getting Started with Spark SQL

Let's do a simple exercise to conclude this section.

- Launch Spark SQL (don't use database) (use spark-sql command). Here is the script for our labs. In other environments, you can skip last line. I have also included commands to launch Spark using Scala or Python (for CCA 175 Certification purpose)

**Using Scala**

```
spark2-shell \
    --master yarn \
    --conf spark.ui.port=0 \
    --conf spark.sql.warehouse.dir=/user/${USER}/warehouse
```

```
spark2-shell \
    --master yarn  \
    --conf spark.ui.port=0  \
    --conf spark.sql.warehouse.dir=/user/egarcia1/warehouse
```

```
scala> spark.sql("SELECT current_database()").show
+------------------+
|current_database()|
+------------------+
|           default|
+------------------+

scala> spark.sql("SHOW databases").show
+--------------------+
|        databaseName|
+--------------------+
|             adhudla|
|              cimran|
|      cimran_archive|
|         cimran_code|
|          cimran_etl|
|           course_db|
|         dashboarddb|
|        dashboarddb1|
|           db_stocks|
|          deequ_test|
|             default|
|         development|
|               docs1|
|         employee_db|
|etarun_retail_db_txt|
|              eureka|
|            eventsim|
|            first_db|
|      hadoopexamtest|
|         impala_kudu|
+--------------------+
only showing top 20 rows

scala> spark.sql("show tables").show()
+--------+------------------+-----------+
|database|         tableName|isTemporary|
+--------+------------------+-----------+
| default|         avro_test|      false|
| default|  ccaparquetimpala|      false|
| default|      cert_company|      false|
| default|       cert_stocks|      false|
| default|cimran_replacement|      false|
| default|       cimran_test|      false|
| default|         customers|      false|
| default|           default|      false|
| default|            depart|      false|
| default|              dept|      false|
| default|               ds1|      false|
| default|               emp|      false|
| default|              emp3|      false|
| default|              emp4|      false|
| default|          emp_dept|      false|
| default|      emp_practice|      false|
| default|           emp_san|      false|
| default|      employee_tbl|      false|
| default|              emps|      false|
| default|        from_mongo|      false|
+--------+------------------+-----------+
only showing top 20 rows

// for multiple line
spark.sql("""
SELECT *
FROM retailorders
""").show
+--------+--------------------+----------------+---------------+
|order_id|          order_date|order_customerid|   order_status|
+--------+--------------------+----------------+---------------+
|       1|2013-07-25 00:00:...|           11599|         CLOSED|
|       2|2013-07-25 00:00:...|             256|PENDING_PAYMENT|
|       3|2013-07-25 00:00:...|           12111|       COMPLETE|
|       4|2013-07-25 00:00:...|            8827|         CLOSED|
|       5|2013-07-25 00:00:...|           11318|       COMPLETE|
|       6|2013-07-25 00:00:...|            7130|       COMPLETE|
|       7|2013-07-25 00:00:...|            4530|       COMPLETE|
|       8|2013-07-25 00:00:...|            2911|     PROCESSING|
|       9|2013-07-25 00:00:...|            5657|PENDING_PAYMENT|
|      10|2013-07-25 00:00:...|            5648|PENDING_PAYMENT|
|      11|2013-07-25 00:00:...|             918| PAYMENT_REVIEW|
|      12|2013-07-25 00:00:...|            1837|         CLOSED|
|      13|2013-07-25 00:00:...|            9149|PENDING_PAYMENT|
|      14|2013-07-25 00:00:...|            9842|     PROCESSING|
|      15|2013-07-25 00:00:...|            2568|       COMPLETE|
|      16|2013-07-25 00:00:...|            7276|PENDING_PAYMENT|
|      17|2013-07-25 00:00:...|            2667|       COMPLETE|
|      18|2013-07-25 00:00:...|            1205|         CLOSED|
|      19|2013-07-25 00:00:...|            9488|PENDING_PAYMENT|
|      20|2013-07-25 00:00:...|            9198|     PROCESSING|
+--------+--------------------+----------------+---------------+
only showing top 20 rows

```

spark.sql("").show
