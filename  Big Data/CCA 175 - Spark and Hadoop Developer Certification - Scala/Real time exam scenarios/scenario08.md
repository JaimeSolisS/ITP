# CCA 175 Real Time Exam Scenario 8 | Read CSV File | Write in HIVE Table with PARQUET File Format

Data Description

1.  All the category records are stored at /user/spark/dataset/retail_db/categories
2.  Data is in text format comma separated

| Schema                 |        |
| ---------------------- | ------ |
| category_id            | int    |
| category_department_id | int    |
| category_name          | string |

Output Requirement

1.  create a metastore table named 'categories_parquet'
2.  Table should only contain category_id, category_name
3.  Save all categories in metastore table categories_parquet
4.  Use parquet format for the output files

## Solution

### Read file

```
val categories = spark.read.csv("/user/jsolis/dataset/retail_db/categories").toDF("category_id", "category_department_id", "category_name")
```

### Select columns

```
val res = categories.select("category_id", "category_name")
```

### Write hive table

```
res.
write.
mode("overwrite").
format("hive").
option("fileFormat", "parquet").
option("compression", "none").
saveAsTable("ybtrainee.jsolis_categories_parquet")
```

### Check Solution

```
scala> spark.sql("select * from ybtrainee.jsolis_categories_parquet").show
+-----------+-------------------+
|category_id|      category_name|
+-----------+-------------------+
|          1|           Football|
|          2|             Soccer|
|          3|Baseball & Softball|
|          4|         Basketball|
|          5|           Lacrosse|
|          6|   Tennis & Racquet|
|          7|             Hockey|
|          8|        More Sports|
|          9|   Cardio Equipment|
|         10|  Strength Training|
|         11|Fitness Accessories|
|         12|       Boxing & MMA|
|         13|        Electronics|
|         14|     Yoga & Pilates|
|         15|  Training by Sport|
|         16|    As Seen on  TV!|
|         17|             Cleats|
|         18|     Men's Footwear|
|         19|   Women's Footwear|
|         20|     Kids' Footwear|
+-----------+-------------------+
only showing top 20 rows
```

```
describe formatted ybtrainee.jsolis_categories_parquet;

hive> describe formatted ybtrainee.jsolis_categories_parquet;
OK
# col_name              data_type               comment

category_id             string
category_name           string

# Detailed Table Information
Database:               ybtrainee
Owner:                  jsolis@YOTABITES.COM
CreateTime:             Mon Jul 12 17:05:44 CDT 2021
LastAccessTime:         UNKNOWN
Protect Mode:           None
Retention:              0
Location:               hdfs://ybolcldrmstr.yotabites.com:8020/user/hive/warehouse/ybtrainee.db/jsolis_categories_parquet
Table Type:             MANAGED_TABLE
Table Parameters:
        COLUMN_STATS_ACCURATE   true
        numFiles                1
        spark.sql.create.version        2.3.0.cloudera2
        spark.sql.sources.schema.numParts       1
        spark.sql.sources.schema.part.0 {\"type\":\"struct\",\"fields\":[{\"name\":\"category_id\",\"type\":\"string\",\"nullable\":true,\"metadata\":{}},{\"name\":\"category_name\",\"type\":\"string\",\"nullable\":true,\"metadata\":{}}]}
        totalSize               1623
        transient_lastDdlTime   1626127555

# Storage Information
SerDe Library:          org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe
InputFormat:            org.apache.hadoop.hive.ql.io.parquet.MapredParquetInputFormat
OutputFormat:           org.apache.hadoop.hive.ql.io.parquet.MapredParquetOutputFormat
Compressed:             No
Num Buckets:            -1
Bucket Columns:         []
Sort Columns:           []
Storage Desc Params:
        compression             none
        serialization.format    1
Time taken: 0.086 seconds, Fetched: 34 row(s)
```
