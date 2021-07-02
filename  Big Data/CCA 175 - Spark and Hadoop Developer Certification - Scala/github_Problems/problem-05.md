## Instructions

**Get word count for the input data using space as delimiter (for each word, we need to get how many times it is repeated in the entire input data set)**

**\*Data Description:** Data is available in HDFS /public/randomtextwriter\*

### word count data information:

> **Number of executors should be 10  
> Executor memory should be 3 GB  
> Executor cores should be 20 in total (2 per executor)  
> Number of output files should be 8  
> Avro dependency details: groupId -> com.databricks, artifactId -> spark-avro_2.10, version -> 2.0.1**

### Output Requirements

> **\*Output File format:** Avro  
> **Output fields:** word, count  
> **Compression:** Uncompressed  
> **Place the customer files in the HDFS directory:** /user/`whoami`/problem5/solution/  
> Replace `whoami` with your OS username\*

### End of Problem

Since I don't have a file, I used this [site](http://www.randomtextgenerator.com)

## Solution

### Move file to HDFS

```
hdfs dfs -copyFromLocal randomText.txt /user/jsolis/data/
```

### Start Spark Shell

```
spark2-shell --master yarn --num-executors 10 --executor-memory 3GB --executor-cores 20 --packages com.databricks:spark-avro_2.11:4.0.0

```

## Read File

```
val text = spark.read.format("text").load("/user/jsolis/data/randomText.txt")
scala> text.show
+--------------------+
|               value|
+--------------------+
|Much evil soon hi...|
|                    |
|Led ask possible ...|
|                    |
|Mind what no by k...|
|                    |
|Throwing consider...|
|                    |
|Had strictly mrs ...|
|                    |
|Carried nothing o...|
|                    |
|Six started far p...|
|                    |
|Consider now prov...|
|                    |
|Imagine was you r...|
|                    |
|It allowance prev...|
+--------------------+
```

### Resolve

```
val words = text.explode("value","word")((line: String) => line.split(" "))
words.show
```

```
val solution = words.groupBy("word").count.orderBy($"count".desc, $"word")
solution.show
```

### Export

```
solution.
coalesce(8).
write.
mode("overwrite").
option("compression","none").
format("com.databricks.spark.avro").
save("/user/jsolis/problem5/solution/")
```
