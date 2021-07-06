## Instructions

    Get NYSE data in ascending order by date and descending order by volume

## Data Description

    NYSE data with "," as delimiter is available in HDFS

## NYSE data information:

**HDFS location:** /public/nyse\
There is no header in the data

## Output Requirements

Save data back to HDFS\
**Column order:** stockticker, transactiondate, openprice, highprice, lowprice, closeprice, volume\
**File Format:** text\
**Delimiter:** ":"\
Place the sorted NYSE data in the HDFS directory
**/user/`whoami`/problem14/solution/**\
Replace `whoami` with your OS user name\
End of Problem

## Solution

### Move file to hdfs

```
//make dir
hdfs dfs -mkdir /user/jsolis/data/nyse

hdfs dfs -copyFromLocal ~/data/prices.csv /user/jsolis/data/nyse
```

### See file

Problem states that there's no header in data, but downloaded data has

```
$ hdfs dfs -cat /user/jsolis/data/nyse/prices.csv | head -5
date,symbol,open,close,low,high,volume
2016-01-05 00:00:00,WLTW,123.43,125.839996,122.309998,126.25,2163600.0
2016-01-06 00:00:00,WLTW,125.239998,119.980003,119.940002,125.540001,2386400.0
2016-01-07 00:00:00,WLTW,116.379997,114.949997,114.93,119.739998,2489500.0
2016-01-08 00:00:00,WLTW,115.480003,116.620003,113.5,117.440002,2006300.0
```

### Read File

```
val df = spark.read.option("header", "true").option("inferSchema","true").csv("/user/jsolis/data/nyse")

df.show
```

### Solve

```
// The alias change to the collumns was unnecesary duh
val solution = df.select($"symbol".as("stockticker"), $"date".as("transactiondate"), $"open".as("openprice"), $"high".as("highprice"), $"low".as("lowprice"),$"close".as("closeprice"), $"volume".cast("Int")).
orderBy($"transactiondate", $"volume".desc).
map(x => x.mkString(":"))

```

#### To make a dataframe compatible to export as text format:

```
df.map(x => x.mkString("delimiter")).write.text("path")
```

### Export

```
solution.
write.
mode("overwrite").
option("compression", "none").
text("/user/jsolis/problem14/solution")
```

### Check Solution

```
scala> spark.read.text("/user/jsolis/problem14/solution").show(false)
+-----------------------------------------------------------------------------+
|value                                                                        |
+-----------------------------------------------------------------------------+
|R:2014-06-13 00:00:00.0:86.269997:86.800003:85.870003:86.639999:366900       |
|SNI:2014-06-13 00:00:00.0:77.169998:77.410004:76.949997:77.389999:361300     |
|MKC:2014-06-13 00:00:00.0:71.800003:72.010002:71.349998:71.709999:347700     |
|PNW:2014-06-13 00:00:00.0:54.060001:54.43:53.610001:54.220001:346700         |
|NWS:2014-06-13 00:00:00.0:16.91:16.91:16.639999:16.73:343200                 |
|ORLY:2014-06-13 00:00:00.0:149.070007:149.639999:148.009995:149.539993:341100|
|BLK:2014-06-13 00:00:00.0:309.640015:311.0:308.089996:309.450012:340700      |
|COL:2014-06-13 00:00:00.0:78.879997:79.43:78.790001:79.370003:334200         |
|GPC:2014-06-13 00:00:00.0:85.169998:85.599998:84.919998:85.099998:329600     |
|MAA:2014-06-13 00:00:00.0:71.0:71.300003:70.360001:71.160004:302000          |
|DNB:2014-06-13 00:00:00.0:103.18:104.220001:102.809998:103.940002:292200     |
|CTAS:2014-06-13 00:00:00.0:63.16:63.43:62.970001:63.110001:290000            |
|IFF:2014-06-13 00:00:00.0:101.139999:101.290001:100.629997:100.790001:270800 |
|SHW:2014-06-13 00:00:00.0:202.839996:203.279999:201.660004:202.729996:261000 |
|AMG:2014-06-13 00:00:00.0:198.820007:198.919998:196.289993:196.970001:260300 |
|IDXX:2014-06-13 00:00:00.0:131.029999:131.759995:130.160004:130.979996:243200|
|FRT:2014-06-13 00:00:00.0:119.139999:119.760002:118.459999:119.720001:237100 |
|EFX:2014-06-13 00:00:00.0:71.279999:71.779999:70.870003:71.68:230900         |
|AYI:2014-06-13 00:00:00.0:128.610001:129.449997:127.809998:128.929993:227900 |
|AZO:2014-06-13 00:00:00.0:523.150024:525.23999:520.25:523.099976:192500      |
+-----------------------------------------------------------------------------+
only showing top 20 rows
```
