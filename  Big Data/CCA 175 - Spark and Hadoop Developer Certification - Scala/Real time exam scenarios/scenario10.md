# CCA 175 Real Time Exam Scenario 10 | Read CSV File | Write in HIVE Table

Data Description

1.  All the category records are stored at /user/spark/dataset/retail_db/categories
2.  Data is in text format comma separated

| Schema                 |        |
| ---------------------- | ------ |
| category_id            | int    |
| category_department_id | int    |
| category_name          | string |

Output Requirement

1.  Create a metastore table named 'categories_partitioned' by category_department_id
2.  Table should only contain category_id, category_name
3.  Save all categories in metastore table categories_partitioned

## Solution

### Read file

```
val categories = spark.read.option("inferSchema", "true").csv("/user/jsolis/dataset/retail_db/categories").toDF("category_id", "category_department_id", "category_name")
```

### Set Config

```
spark.sqlContext.setConf("hive.exec.dynamic.partition", "true")

spark.sqlContext.setConf("hive.exec.dynamic.partition.mode", "nonstrict")
```

### Write Solution

```
categories.
write.
mode("overwrite").
partitionBy("category_department_id").
format("hive").
saveAsTable("ybtrainee.jsolis_categories_partitioned")
```

### Check Solution

```
scala> spark.read.table("ybtrainee.jsolis_categories_partitioned").show
+-----------+--------------------+----------------------+
|category_id|       category_name|category_department_id|
+-----------+--------------------+----------------------+
|         42|   Bike & Skate Shop|                     7|
|         43|    Camping & Hiking|                     7|
|         44|  Hunting & Shooting|                     7|
|         45|             Fishing|                     7|
|         46|Indoor/Outdoor Games|                     7|
|         47|             Boating|                     7|
|         48|        Water Sports|                     7|
|          9|    Cardio Equipment|                     3|
|         10|   Strength Training|                     3|
|         11| Fitness Accessories|                     3|
|         12|        Boxing & MMA|                     3|
|         13|         Electronics|                     3|
|         14|      Yoga & Pilates|                     3|
|         15|   Training by Sport|                     3|
|         16|     As Seen on  TV!|                     3|
|          1|            Football|                     2|
|          2|              Soccer|                     2|
|          3| Baseball & Softball|                     2|
|          4|          Basketball|                     2|
|          5|            Lacrosse|                     2|
+-----------+--------------------+----------------------+
only showing top 20 rows
```

```
hive> describe formatted ybtrainee.jsolis_categories_partitioned;
OK
# col_name              data_type               comment

category_id             int
category_name           string

# Partition Information
# col_name              data_type               comment

category_department_id  int

# Detailed Table Information
Database:               ybtrainee
Owner:                  jsolis@YOTABITES.COM
CreateTime:             Mon Jul 12 17:46:00 CDT 2021
LastAccessTime:         UNKNOWN
Protect Mode:           None
Retention:              0
Location:               hdfs://ybolcldrmstr.yotabites.com:8020/user/hive/warehouse/ybtrainee.db/jsolis_categories_partitioned
Table Type:             MANAGED_TABLE
Table Parameters:
        numPartitions           7
        spark.sql.create.version        2.3.0.cloudera2
        spark.sql.sources.schema.numPartCols    1
        spark.sql.sources.schema.numParts       1
        spark.sql.sources.schema.part.0 {\"type\":\"struct\",\"fields\":[{\"name\":\"category_id\",\"type\":\"integer\",\"nullable\":true,\"metadata\":{}},{\"name\":\"category_name\",\"type\":\"string\",\"nullable\":true,\"metadata\":{}},{\"name\":\"category_department_id\",\"type\":\"integer\",\"nullable\":true,\"metadata\":{}}]}
        spark.sql.sources.schema.partCol.0      category_department_id
        transient_lastDdlTime   1626129960

# Storage Information
SerDe Library:          org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe
InputFormat:            org.apache.hadoop.mapred.TextInputFormat
OutputFormat:           org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat
Compressed:             No
Num Buckets:            -1
Bucket Columns:         []
Sort Columns:           []
Storage Desc Params:
        serialization.format    1
Time taken: 0.075 seconds, Fetched: 38 row(s)
```

```
hive> show partitions ybtrainee.jsolis_categories_partitioned;
OK
category_department_id=2
category_department_id=3
category_department_id=4
category_department_id=5
category_department_id=6
category_department_id=7
category_department_id=8
Time taken: 0.068 seconds, Fetched: 7 row(s)
```
