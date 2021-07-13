# CCA 175 Real Time Exam Scenario 7 | Read CSV File | Write in HIVE Table

Data Description

1.  All the category records are stored at /user/spark/dataset/retail_db/categories
2.  Data is in text format
3.  Data is comma separated

| Schema                 |        |
| ---------------------- | ------ |
| category_id            | int    |
| category_department_id | int    |
| category_name          | string |

Output Requirement

1.  Save all categories in metastore table categories_replica in default database
2.  Use no compression

## Solution

### Read file

```
val categories = spark.read.option("inferSchema", "true").csv("/user/jsolis/dataset/retail_db/categories").toDF("category_id", "category_department_id", "category_name" )
```

### Write hive table

```
categories.
write.
mode("overwrite").
format("hive").
option("compression", "none").
saveAsTable("ybtrainee.jsolis_categories_replica")
```

### Check solution

```
scala> spark.sql("select * from ybtrainee.jsolis_categories_replica").show
+-----------+----------------------+-------------------+
|category_id|category_department_id|      category_name|
+-----------+----------------------+-------------------+
|          1|                     2|           Football|
|          2|                     2|             Soccer|
|          3|                     2|Baseball & Softball|
|          4|                     2|         Basketball|
|          5|                     2|           Lacrosse|
|          6|                     2|   Tennis & Racquet|
|          7|                     2|             Hockey|
|          8|                     2|        More Sports|
|          9|                     3|   Cardio Equipment|
|         10|                     3|  Strength Training|
|         11|                     3|Fitness Accessories|
|         12|                     3|       Boxing & MMA|
|         13|                     3|        Electronics|
|         14|                     3|     Yoga & Pilates|
|         15|                     3|  Training by Sport|
|         16|                     3|    As Seen on  TV!|
|         17|                     4|             Cleats|
|         18|                     4|     Men's Footwear|
|         19|                     4|   Women's Footwear|
|         20|                     4|     Kids' Footwear|
+-----------+----------------------+-------------------+
only showing top 20 rows
```

```
hive> Describe formatted jsolis_categories_replica;
OK
# col_name              data_type               comment

category_id             int
category_department_id  int
category_name           string

# Detailed Table Information
Database:               ybtrainee
Owner:                  jsolis@YOTABITES.COM
CreateTime:             Mon Jul 12 16:48:33 CDT 2021
LastAccessTime:         UNKNOWN
Protect Mode:           None
Retention:              0
Location:               hdfs://ybolcldrmstr.yotabites.com:8020/user/hive/warehouse/ybtrainee.db/jsolis_categories_replica
Table Type:             MANAGED_TABLE
Table Parameters:
        COLUMN_STATS_ACCURATE   true
        numFiles                1
        spark.sql.create.version        2.3.0.cloudera2
        spark.sql.sources.schema.numParts       1
        spark.sql.sources.schema.part.0 {\"type\":\"struct\",\"fields\":[{\"name\":\"category_id\",\"type\":\"integer\",\"nullable\":true,\"metadata\":{}},{\"name\":\"category_department_id\",\"type\":\"integer\",\"nullable\":true,\"metadata\":{}},{\"name\":\"category_name\",\"type\":\"string\",\"nullable\":true,\"metadata\":{}}]}
        totalSize               1029
        transient_lastDdlTime   1626126523

# Storage Information
SerDe Library:          org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe
InputFormat:            org.apache.hadoop.mapred.TextInputFormat
OutputFormat:           org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat
Compressed:             No
Num Buckets:            -1
Bucket Columns:         []
Sort Columns:           []
Storage Desc Params:
        compression             none
        serialization.format    1
Time taken: 0.112 seconds, Fetched: 35 row(s)
```
