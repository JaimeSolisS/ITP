# CCA 175 Real Time Exam Scenario 4 | Read CSV file | Write as TSV in HDFS with LZ4 Compression

Data Description

1.  All the categories records are stored at
    /user/spark/dataset/retail_db/categories
2.  Data is in text format
3.  Data is comma separated

Output Requirement

1. Convert data into tab delimited file
2. Use text format for the output files
3. Place the result data in HDFS directory /user/spark/dataset/result/scenario4/solution
4. Compress the output using lz4 compression

## Solution

### Read file

```
val categories = spark.read.csv("/user/jsolis/dataset/retail_db/categories")
```

### 1 column df

```
val res = categories.map(x => x.mkString("\t"))
```

### Export

```
res.
write.
mode("overwrite").
option("compression", "lz4").
format("text").
save("/user/jsolis/dataset/result/scenario4/solution")
```

### Check Solution

```
scala> spark.read.option("sep", "\t").text("/user/jsolis/dataset/result/scenario4/solution").show
                                                                          +--------------------+
|               value|
+--------------------+
|        1      2       Football|
|          2    2       Soccer|
|3      2       Baseball & So...|
|      4        2       Basketball|
|        5      2       Lacrosse|
|6      2       Tennis & Racquet|
|          7    2       Hockey|
|     8 2       More Sports|
|9      3       Cardio Equipment|
|10     3       Strength Tra...|
|11     3       Fitness Acce...|
|   12  3       Boxing & MMA|
|    13 3       Electronics|
| 14    3       Yoga & Pilates|
|15     3       Training by ...|
|16     3       As Seen on  TV!|
|         17    4       Cleats|
| 18    4       Men's Footwear|
|19     4       Women's Foot...|
| 20    4       Kids' Footwear|
+--------------------+
only showing top 20 rows
```

```
hdfs dfs -ls /user/jsolis/dataset/result/scenario4/solution
Found 2 items
-rw-r--r--   3 jsolis jsolis          0 2021-07-11 22:26 /user/jsolis/dataset/result/scenario4/solution/_SUCCESS
-rw-r--r--   3 jsolis jsolis        835 2021-07-11 22:26 /user/jsolis/dataset/result/scenario4/solution/part-00000-5d2be0ed-8345-49b1-8e04-53dc71b09f94-c000.txt.lz4
```
