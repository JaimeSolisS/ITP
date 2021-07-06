## Instructions

        Get the name of stocks displayed along with other information

## Data Description

        NYSE data with "," as delimiter is available in HDFS

## NYSE data information:

**HDFS location:** /public/nyse\
There is no header in the data\
NYSE Symbols data with tab character (\t) as delimiter is available in HDFS\

## NYSE Symbols data information:

**HDFS location:** /public/nyse_symbols\
First line is header and it should be included\

## Output Requirements

Get all NYSE details along with stock name if exists, if not stockname should be empty\
**Column Order:** stockticker, stockname, transactiondate, openprice, highprice, lowprice, closeprice, volume\
**Delimiter:** ","\
**File Format:** text\
Place the data in the HDFS directory
**/user/`whoami`/problem18/solution/**\
Replace `whoami` with your OS user name\
End of Problem

## Solution

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
join(companies, stocks("_c0") === companies("_c0"), "left").
select(stocks("_c0").as("stockticker"), companies("_c1").as("stockname"), stocks("_c1").as("transactiondate"), stocks("_c2").as("openprice"), stocks("_c3").as("highprice"),stocks("_c4").as("lowprice"),stocks("_c5").as("closeprice"), stocks("_c6").as("volume")).
na.fill("")
```

### Export

```
solution.
write.
mode("overwrite").
csv("/user/jsolis/problem16/solution/")
```
