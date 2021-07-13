# CCA 175 Real Time Exam Scenario 1 | Read Tab Delimited File | Write as CSV in HDFS

Data Description

1.  All the customer records are stored in the HDFS directory
    /user/spark/dataset/retail_db/customers-tab-delimited
2.  Data is in Text format
3.  Data is Tab delimited

| Schema            |        |
| ----------------- | ------ |
| customer_id       | int    |
| customer_fname    | string |
| customer_lname    | string |
| customer_email    | string |
| customer_password | string |
| customer_street   | string |
| customer_city     | string |
| customer_state    | string |
| customer_zipcode  | string |

Output Requirement

1.  Output all the customers who live in California
2.  Use text format for the output files
3.  Place the result data in /user/spark/dataset/result/scenario1/solution
4.  Result should only contain records that have state value as "CA“
5.  Output should only contain customer's full name
    Example: Robert Hudson

## Solution

### Read file

```
val customers = spark.read.option("sep", "\t").format("csv").load("/user/jsolis/dataset/retail_db/customers-tab-delimited")
```

### filter

```
val res = customers.filter($"_c7" === "CA").select(concat($"_c1", lit(" "), $"_c2"))
```

### write

```
res.write.
mode("overwrite").
format("text").
save("/user/jsolis/dataset/result/scenario1/solution")
```

### Check Solution

```
scala> spark.read.format("text").load("/user/jsolis/dataset/result/scenario1/solution").show
+---------------+
|          value|
+---------------+
|     Mary Jones|
|Katherine Smith|
|      Jane Luna|
|   Robert Smith|
|Margaret Wright|
|     Mary Smith|
|   Howard Smith|
|       Mary Kim|
|  Douglas James|
|   Mary Simmons|
|Frank Gillespie|
|   Joseph Young|
|     Sean Smith|
| Lauren Freeman|
|   Alice Warner|
|     Mary Smith|
| Mary Gallagher|
| Daniel Maxwell|
|Shirley Mcclain|
|     Mary Smith|
+---------------+
```

```
hdfs dfs -ls /user/jsolis/dataset/result/scenario1/solution
Found 2 items
-rw-r--r--   3 jsolis jsolis          0 2021-07-12 17:51 /user/jsolis/dataset/result/scenario1/solution/_SUCCESS
-rw-r--r--   3 jsolis jsolis      26096 2021-07-12 17:51 /user/jsolis/dataset/result/scenario1/solution/part-00000-05932c9f-cb2a-42f6-b81d-7df04e22fa80-c000.txt
```
