## Instructions

    Get the stock tickers from NYSE data for which full name is missing in NYSE symbols data

## Data Description

    NYSE data with "," as delimiter is available in HDFS

## NYSE data information:

**HDFS location:** /public/nyse\
There is no header in the data\
NYSE Symbols data with " " as delimiter is available in HDFS

## NYSE Symbols data information:

**HDFS location:** /public/nyse_symbols\
First line is header and it should be included

## Output Requirements

Get unique stock ticker for which corresponding names are missing in NYSE symbols data\
Save data back to HDFS\
**File Format:** avro\
**Avro dependency details:** groupId -> com.databricks, artifactId -> spark-avro_2.10, version -> 2.0.1\
Place the sorted NYSE data in the HDFS directory **/user/`whoami`/problem15/solution/** \
Replace `whoami` with your OS user name\
End of Problem

## Solution

### Deploy Mode

```
spark2-shell --master yarn --packages com.databricks:spark-avro_2.11:4.0.0
```

### Read Data

```
val stocks =
spark.
read.
option("sep", ",").
option("inferSchema", "true").
csv("/user/egarcia1/data/nyse_all/nyse_data")

val companies  =
spark.
read.
option("sep", "|").
option("inferSchema", "true").
csv("/user/egarcia1/data/nyse_all/nyse_stocks")
```

### Join

```
val solution = stocks.
join(companies, stocks("_c0") === companies("_c0"), "left_anti").
select(stocks("_c0").as("stock_ticker")).
distinct
```

### Export

```
solution.
write.
mode("overwrite").
option("compression", "none").
format("com.databricks.spark.avro").
save("/user/jsolis/problem15/solution")
```

### Check solution

```
spark.
read.
format("com.databricks.spark.avro").
load("/user/jsolis/problem15/solution").
show
```
