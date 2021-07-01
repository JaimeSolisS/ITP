## Instructions

**Convert NYSE data into parquet**

**NYSE data Description: Data is available in local file system under /data/NYSE (ls -ltr /data/NYSE)**

### NYSE Data information:

> **\*Fields:** (stockticker:string, transactiondate:string, openprice:float, highprice:float, lowprice:float, closeprice:float, volume:bigint)\*

### Output Requirements:

> **\*Column Names:** stockticker, transactiondate, openprice, highprice, lowprice, closeprice, volume  
> **Convert file format to parquet**  
> **Place the output file in the HDFS directory:** /user/`whoami`/problem4/solution/  
> Replace `whoami` with your OS user name\*

### End of Problem

Since I don't have the NYSE data, I'll use Weather.csv

## Solution

### Load Fille

```
val w = spark.read.option("header", "true").csv("/user/jsolis/data/weather.csv")
```

### Export File

```
w.
write.
mode("overwrite").
option("compression", "none" ).
format("parquet").
save("/user/jsolis/problem4/solution")
```

### Check Solution

```
$ hdfs dfs -ls /user/jsolis/problem4/solution

Found 3 items
-rw-r--r--   3 jsolis jsolis          0 2021-06-29 22:06 /user/jsolis/problem4/solution/_SUCCESS
-rw-r--r--   3 jsolis jsolis    2422980 2021-06-29 22:06 /user/jsolis/problem4/solution/part-00000-48850bea-ed75-4419-9d0a-329b153f62f1-c000.parquet
-rw-r--r--   3 jsolis jsolis    1190755 2021-06-29 22:06 /user/jsolis/problem4/solution/part-00001-48850bea-ed75-4419-9d0a-329b153f62f1-c000.parquet
```
