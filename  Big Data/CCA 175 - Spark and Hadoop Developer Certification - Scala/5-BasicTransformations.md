# Basic Transformations

## Sumary

## Overview of Basic Transformations

We will cover filtering, aggregations and sorting as part of this module and look into joins and ranking in subsequent modules.

Let us define problem statements and come up with solutions to learn more about Data Frame APIs.

- Get total number of flights as well as number of flights which are delayed in departure and number of flights delayed in arrival.
- Output should contain 3 columns - **FlightCount**, **DepDelayedCount**, **ArrDelayedCount**
- Get number of flights which are delayed in departure and number of flights delayed in arrival for each day along with number of flights departed for each day.
- Output should contain 4 columns - **FlightDate**, **FlightCount**, **DepDelayedCount**, **ArrDelayedCount**
- **FlightDate** should be of **YYYY-MM-dd** format.
- Data should be **sorted** in ascending order by **flightDate**

## Overview of Filtering

Let us understand few important details related to filtering before we get into the solution

```
scala> "hdfs dfs -ls -r /user/egarcia1/data/flightmonth=200801/flightmonth=200801"!
warning: there was one feature warning; re-run with -feature for details
Found 1 items
-rw-r--r--   3 egarcia1 egarcia1   14666683 2021-06-16 17:57 /user/egarcia1/data/flightmonth=200801/flightmonth=200801/part-00252-9f6f50c3-9ea4-459e-8b66-c0f1699e7475.c000.snappy.parquet
res12: Int = 0
```

Because it is a parquet file, now we know how to read the file

```
val airlines_path = "/user/egarcia1/data/flightmonth=200801"

val airlines_all = spark.read.parquet(airlines_path)

airlines.show
+----+-----+----------+---------+-------+----------+-------+----------+-------------+---------+-------+-----------------+--------------+-------+--------+--------+------+----+--------+------+-------+---------+----------------+--------+------------+------------+--------+-------------+-----------------+------------+------------+-----------+
|Year|Month|DayofMonth|DayOfWeek|DepTime|CRSDepTime|ArrTime|CRSArrTime|UniqueCarrier|FlightNum|TailNum|ActualElapsedTime|CRSElapsedTime|AirTime|ArrDelay|DepDelay|Origin|Dest|Distance|TaxiIn|TaxiOut|Cancelled|CancellationCode|Diverted|CarrierDelay|WeatherDelay|NASDelay|SecurityDelay|LateAircraftDelay|IsArrDelayed|IsDepDelayed|flightmonth|
+----+-----+----------+---------+-------+----------+-------+----------+-------------+---------+-------+-----------------+--------------+-------+--------+--------+------+----+--------+------+-------+---------+----------------+--------+------------+------------+--------+-------------+-----------------+------------+------------+-----------+
|2008|    1|        16|        3|    952|       955|   1109|      1115|           WN|      709| N230WN|               77|            80|     62|      -6|      -3|   MCI| MDW|     405|     4|     11|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|          NO|          NO|     200801|
|2008|    1|        16|        3|   1851|      1850|   2051|      2125|           WN|     3146| N716SW|              240|           275|    222|     -34|       1|   MDW| LAX|    1750|     8|     10|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|          NO|         YES|     200801|
|2008|    1|        16|        3|   1056|      1050|   1208|      1210|           WN|     1854| N513SW|               72|            80|     56|      -2|       6|   PHL| MHT|     290|     3|     13|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|          NO|         YES|     200801|
|2008|    1|        16|        3|    743|       745|    900|       900|           WN|     1767| N513SW|               77|            75|     45|       0|      -2|   PHL| PIT|     267|     7|     25|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|          NO|          NO|     200801|
|2008|    1|        16|        3|   1723|      1725|   1835|      1905|           WN|     1417| N389SW|               72|           100|     60|     -30|      -2|   PHL| RDU|     336|     2|     10|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|          NO|          NO|     200801|
|2008|    1|        16|        3|    632|       635|    642|       650|           WN|      495| N673AA|               70|            75|     55|      -8|      -3|   PHX| ONT|     325|     5|     10|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|          NO|          NO|     200801|
|2008|    1|        16|        3|   1110|      1110|   1451|      1505|           WN|     1210| N385SW|              161|           175|    146|     -14|       0|   PHX| STL|    1262|     3|     12|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|          NO|          NO|     200801|
|2008|    1|        16|        3|    914|       910|   1007|      1010|           WN|     3394| N794SW|               53|            60|     42|      -3|       4|   RNO| SJC|     188|     3|      8|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|          NO|         YES|     200801|
|2008|    1|        16|        3|   1012|      1015|   1227|      1240|           WN|     1758| N456WN|              135|           145|    116|     -13|      -3|   SEA| LAS|     866|     6|     13|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|          NO|          NO|     200801|
|2008|    1|        17|        4|   1951|      1920|   2052|      2020|           WN|      209| N236WN|               61|            60|     49|      32|      31|   BWI| PIT|     210|     3|      9|        0|            null|       0|          16|           0|       1|            0|               15|         YES|         YES|     200801|
|2008|    1|        17|        4|   2113|      2015|   2320|      2230|           WN|     1250| N550WN|              127|           135|    114|      50|      58|   CMH| TPA|     829|     4|      9|        0|            null|       0|           3|           0|       0|            0|               47|         YES|         YES|     200801|
|2008|    1|        17|        4|   1127|      1125|   1417|      1425|           WN|       15| N601WN|              110|           120|     96|      -8|       2|   HOU| TPA|     781|     3|     11|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|          NO|         YES|     200801|
|2008|    1|        17|        4|   1018|      1020|   1124|      1120|           WN|     1114| N732SW|              126|           120|    103|       4|      -2|   IAD| MDW|     577|     4|     19|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|         YES|          NO|     200801|
|2008|    1|        17|        4|   1252|      1255|   1406|      1415|           WN|      791| N388SW|               74|            80|     56|      -9|      -3|   LAS| RNO|     345|     4|     14|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|          NO|          NO|     200801|
|2008|    1|        17|        4|   1045|      1045|   1205|      1210|           WN|      441| N204WN|               80|            85|     65|      -5|       0|   LAS| SJC|     386|     3|     12|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|          NO|          NO|     200801|
|2008|    1|        17|        4|   1303|      1255|   1348|      1355|           WN|     1433| N274WN|              105|           120|     96|      -7|       8|   MCO| BNA|     616|     3|      6|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|          NO|         YES|     200801|
|2008|    1|        17|        4|   1318|      1310|   1520|      1525|           WN|     3397| N230WN|              122|           135|    109|      -5|       8|   MCO| PIT|     834|     6|      7|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|          NO|         YES|     200801|
|2008|    1|        17|        4|   1916|      1915|   2043|      2035|           WN|     3918| N275WN|               87|            80|     67|       8|       1|   OAK| LAS|     407|    11|      9|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|         YES|         YES|     200801|
|2008|    1|        17|        4|   1309|      1305|   1431|      1430|           WN|     1196| N468WN|               82|            85|     62|       1|       4|   OAK| SAN|     446|     2|     18|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|         YES|         YES|     200801|
|2008|    1|        17|        4|   1220|      1225|   1319|      1315|           WN|     3586| N785SW|               59|            50|     47|       4|      -5|   PBI| TPA|     174|     3|      9|        0|            null|       0|          NA|          NA|      NA|           NA|               NA|         YES|          NO|     200801|
+----+-----+----------+---------+-------+----------+-------+----------+-------------+---------+-------+-----------------+--------------+-------+--------+--------+------+----+--------+------+-------+---------+----------------+--------+------------+------------+--------+-------------+-----------------+------------+------------+-----------+
only showing top 20 rows
```

- Filtering can be done either by using `filter` or `where`. These are like synonyms to each other.
- When it comes to the condition, we can either pass it in **SQL Style** or **Data Frame Style**.
- Example for SQL Style - `airlines.filter("IsArrDelayed = 'YES'").show() or airlines.where("IsArrDelayed = 'YES'").show()`
- Example for Data Frame Style - `airlines.filter(airlines("IsArrDelayed") === "YES").show()` or `airlines.filter($"IsArrDelayed" === "YES").show()`. We can also use where instead of filter.
- Here are the other operations we can perform to filter the data - `!=`, `>`, `<`, `>=`, `<=`, `LIKE`, `BETWEEN` with `AND`
- If we have to validate against multiple columns then we need to use boolean operations such as `AND` and `OR`.
- If we have to compare each column value with multiple values then we can use the `IN` operator.

### Tasks

Let us perform some tasks to understand filtering in detail. Solve all the problems by passing conditions using both SQL Style as well as API Style.

- Read the data for the month of 2008 January.

```
val airlines = airlines_all.
            select("Year", "Month", "DayOfMonth",
           "DepDelay", "ArrDelay", "UniqueCarrier",
           "FlightNum", "IsArrDelayed", "IsDepDelayed"
            )
scala> airlines.show(5)
+----+-----+----------+--------+--------+-------------+---------+------------+------------+
|Year|Month|DayOfMonth|DepDelay|ArrDelay|UniqueCarrier|FlightNum|IsArrDelayed|IsDepDelayed|
+----+-----+----------+--------+--------+-------------+---------+------------+------------+
|2008|    1|        16|      -3|      -6|           WN|      709|          NO|          NO|
|2008|    1|        16|       1|     -34|           WN|     3146|          NO|         YES|
|2008|    1|        16|       6|      -2|           WN|     1854|          NO|         YES|
|2008|    1|        16|      -2|       0|           WN|     1767|          NO|          NO|
|2008|    1|        16|      -2|     -30|           WN|     1417|          NO|          NO|
+----+-----+----------+--------+--------+-------------+---------+------------+------------+
only showing top 5 rows
```

```
scala> airlines.select($"Year").distinct.show
+----+
|Year|
+----+
|2008|
+----+


scala> airlines.select($"Month").distinct.show
+-----+
|Month|
+-----+
|    1|
+-----+
```

#### Get count of flights which are departed late at origin and reach destination early or on time.

- SQL Style

```
scala> airlines.filter("IsDepDelayed = 'YES' AND IsArrDelayed = 'NO'").count
res26: Long = 54233
```

- API Style

```
scala> airlines.filter($"IsDepDelayed" === "YES" and $"IsArrDelayed" === "NO").count
res28: Long = 54233
```

- To get autocompletion with tab:

```
scala> airlines.filter(col("IsDepDelayed").
!=    <=      asInstanceOf      desc_nulls_first   formatted      isin       notifyAll      unary_-
!==   <=>     asc               desc_nulls_last    geq            leq        or             wait
##    =!=     asc_nulls_first   divide             getClass       like       otherwise      when
%     ==      asc_nulls_last    endsWith           getField       lt         over           ||
&&    ===     between           ensuring           getItem        minus      plus           ?
*     >       bitwiseAND        eq                 gt             mod        rlike
+     >=      bitwiseOR         eqNullSafe         hashCode       multiply   startsWith
-     alias   bitwiseXOR        equalTo            isInstanceOf   name       substr
->    and     cast              equals             isNaN          ne         synchronized
/     apply   contains          explain            isNotNull      notEqual   toString
<     as      desc              expr               isNull         notify     unary_!

```

#### Get count of flights which are departed late from origin by more than 60 minutes.

- SQL style

```
scala> airlines.filter("DepDelay > 60").count
res1: Long = 40104
```

- API Style

```
scala> airlines.filter(col("DepDelay").
!=    >=                 desc_nulls_last   isInstanceOf   otherwise
!==   alias              divide            isNaN          over
##    and                endsWith          isNotNull      plus
%     apply              ensuring          isNull         rlike
&&    as                 eq                isin           startsWith
*     asInstanceOf       eqNullSafe        leq            substr
+     asc                equalTo           like           synchronized
-     asc_nulls_first    equals            lt             toString
->    asc_nulls_last     explain           minus          unary_!
/     between            expr              mod            unary_-
<     bitwiseAND         formatted         multiply       wait
<=    bitwiseOR          geq               name           when
<=>   bitwiseXOR         getClass          ne             ||
=!=   cast               getField          notEqual       ?
==    contains           getItem           notify
===   desc               gt                notifyAll
>     desc_nulls_first   hashCode          or

scala> airlines.filter(col("DepDelay") > 60).count
[Stage 1:>                                                          (0 + [Stage 1:>                                                          (0 + [Stage 1:>                                                          (0 + [Stage 1:=============================>                             (1 +                                                                          res2: Long = 40104
```

#### Get count of flights which are departed early or on time but arrive late by at least 15 minutes.

- SQL style

```
scala> airlines.filter("IsDepDelayed = 'NO' AND ArrDelay >= 15").count
res4: Long = 20705
```

- API style

```

scala> airlines.filter($"IsDepDelayed" === "NO" and $"ArrDelay" >= 15).count
res5: Long = 20705
```

#### Get count of flights departed from following major airports - ORD, DFW, ATL, LAX, SFO.

- SQL style

```
scala> airlines_all.filter("Origin IN ('ORD', 'DFW', 'ATL', 'LAX', 'SFO')").count
res7: Long = 118212
```

- API style

```
scala> airlines_all.filter($"Origin" isin ("ORD", "DFW", "ATL", "LAX", "SFO")).count
res8: Long = 118212
```

#### Get count of flights departed late between 2008 January 1st to January 9th using FlightDate.

- Date should be of `yyyyMMdd` format.

* SQL style
  scala> airlines.
  | withColumn("FlightDate", concat($"Year", lpad($"Month", 2, "0"),lpad($"DayOfMonth", 2, "0"))).
  | filter("FlightDate BETWEEN '20080101' AND '20080109' AND IsDepDelayed = 'YES'").
  | count
  res6: Long = 91045

* API style

```
airlines.
        withColumn("FlightDate", concat($"Year", lpad($"Month", 2, "0"),lpad($"DayOfMonth", 2, "0"))).
              filter($"FlightDate" between("20080101", "20080109") and $"IsDepDelayed" === "YES").
              count
res9: Long = 91045
```

#### Get number of flights departed late on Sundays.

```
airlines.
  | withColumn("FlightDate", concat($"Year", lpad($"Month", 2, "0"),lpad($"DayOfMonth", 2, "0"))).
    withColumn("DayOfWeek", date_format(to_date($"FlightDate", "yyyyMMdd"), "EEEE")).
    show
```

- SQL style

```
 airlines.
  | withColumn("FlightDate", concat($"Year", lpad($"Month", 2, "0"),lpad($"DayOfMonth", 2, "0"))).
    withColumn("DayOfWeek", date_format(to_date($"FlightDate", "yyyyMMdd"), "EEEE")).
    filter("DayOfWeek = 'Sunday' AND IsDepDelayed = 'YES'").
    count
res20: Long = 34708
```

- API style

```
airlines.
  | withColumn("FlightDate", concat($"Year", lpad($"Month", 2, "0"),lpad($"DayOfMonth", 2, "0"))).
    withColumn("DayOfWeek", date_format(to_date($"FlightDate", "yyyyMMdd"), "EEEE")).
    filter($"DayOfWeek" === "Sunday" and $"IsDepDelayed" === "YES").
    count
res22: Long = 34708
```

## Overview of Aggregations

Let us go through the details related to aggregations using Spark.

- We can perform total aggregations directly on Dataframe or we can perform aggregations after grouping by a key(s).
- Here are the APIs which we typically use to group the data using a key.
- `groupBy`
- `rollup`
- `cube`
- Here are the functions which we typically use to perform aggregations.
- `count`
- `sum`, `avg`
- `min`, `max`
- If we want to provide aliases to the aggregated fields then we have to use `agg` after `groupBy`.
- Let us get the count of flights for each day for the month of 200801.

```
scala> airlines.groupBy(concat($"Year", lpad($"Month", 2, "0"), lpad($"DayOfMonth", 2, "0")).alias("FlightDate")).count.show
+----------+-----+
|FlightDate|count|
+----------+-----+
|  20080120|18653|
|  20080130|19766|
|  20080115|19503|
|  20080118|20347|
|  20080122|19504|
|  20080104|20929|
|  20080125|20313|
|  20080102|20953|
|  20080105|18066|
|  20080111|20349|
|  20080109|19820|
|  20080127|18903|
|  20080101|19175|
|  20080128|20147|
|  20080119|16249|
|  20080106|19893|
|  20080123|19769|
|  20080117|20273|
|  20080116|19764|
|  20080112|16572|
+----------+-----+
only showing top 20
```

```
scala> airlines.groupBy(concat($"Year", lpad($"Month", 2, "0"), lpad($"DayOfMonth", 2, "0")).alias("FlightDate")).agg(count(lit(1)).alias("FlightCount")).show
+----------+-----------+
|FlightDate|FlightCount|
+----------+-----------+
|  20080120|      18653|
|  20080130|      19766|
|  20080115|      19503|
|  20080118|      20347|
|  20080122|      19504|
|  20080104|      20929|
|  20080125|      20313|
|  20080102|      20953|
|  20080105|      18066|
|  20080111|      20349|
|  20080109|      19820|
|  20080127|      18903|
|  20080101|      19175|
|  20080128|      20147|
|  20080119|      16249|
|  20080106|      19893|
|  20080123|      19769|
|  20080117|      20273|
|  20080116|      19764|
|  20080112|      16572|
+----------+-----------+
only showing top 20 rows
```

## Overview of Sorting

Let us understand how to sort the data in a Data Frame.

- We can use `orderBy` or `sort` to sort the data.
- We can perform composite sorting by passing multiple columns or expressions.
- By default data is sorted in ascending order, we can change it to descending by applying `desc()` function on the column or expression.
- Let us sort the Flight Count for each day for the month of 2008 January in descending order by count

```
val flightCountDaily = airlines.groupBy(concat($"Year", lpad($"Month", 2, "0"), lpad($"DayOfMonth", 2, "0")).alias("FlightDate")).agg(count(lit(1)).alias("FlightCount"))

scala> flightCountDaily.orderBy("FlightCount").show
+----------+-----------+
|FlightDate|FlightCount|
+----------+-----------+
|  20080119|      16249|
|  20080126|      16276|
|  20080112|      16572|
|  20080105|      18066|
|  20080120|      18653|
|  20080127|      18903|
|  20080113|      18946|
|  20080101|      19175|
|  20080129|      19485|
|  20080115|      19503|
|  20080122|      19504|
|  20080108|      19603|
|  20080116|      19764|
|  20080130|      19766|
|  20080123|      19769|
|  20080109|      19820|
|  20080106|      19893|
|  20080121|      20133|
|  20080128|      20147|
|  20080114|      20176|
+----------+-----------+
only showing top 20 rows

scala> flightCountDaily.orderBy($"FlightCount".desc).show
+----------+-----------+
|FlightDate|FlightCount|
+----------+-----------+
|  20080102|      20953|
|  20080103|      20937|
|  20080104|      20929|
|  20080111|      20349|
|  20080118|      20347|
|  20080107|      20341|
|  20080125|      20313|
|  20080110|      20297|
|  20080117|      20273|
|  20080131|      20260|
|  20080124|      20257|
|  20080114|      20176|
|  20080128|      20147|
|  20080121|      20133|
|  20080106|      19893|
|  20080109|      19820|
|  20080123|      19769|
|  20080130|      19766|
|  20080116|      19764|
|  20080108|      19603|
+----------+-----------+
only showing top 20 rows
```

## Solutions - Problem 1

Get total number of flights as well as number of flights which are delayed in departure and number of flights delayed in arrival.

- Output should contain 3 columns - **FlightCount**, **DepDelayedCount**, **ArrDelayedCount**

### My Solution

```
val airlines = airlines_all.select("IsDepDelayed", "IsArrDelayed")

// 1
scala> airlines.withColumn("FlightCount", lit(1)).
     |         withColumn("IsDepDelayedCount", when($"IsDepDelayed" === "YES", 1).otherwise(0)).
     |         withColumn("ArrDelayedCount", when($"IsArrDelayed" === "YES", 1).otherwise(0)).
     |         agg(sum("FlightCount").alias("FlightCount"), sum("IsDepDelayedCount").alias("IsDepDelayedCount"), sum("ArrDelayedCount").alias("ArrDelayedCount")).
     |         show
+-----------+-----------------+---------------+
|FlightCount|IsDepDelayedCount|ArrDelayedCount|
+-----------+-----------------+---------------+
|     605659|           265198|         297956|
+-----------+-----------------+---------------+

// 2
scala> airlines.agg(count(lit(1)).alias("FlightCount"),
                    count(when($"IsDepDelayed" === "YES", 1)).alias("DepDelayedCount"),
                    count(when($"IsArrDelayed" === "YES", 1)).alias("ArrDelayedCount")
                    ).show
+-----------+---------------+---------------+
|FlightCount|DepDelayedCount|ArrDelayedCount|
+-----------+---------------+---------------+
|     605659|         265198|         297956|
+-----------+---------------+---------------+
```

### Video Solution

```
airlines.agg(count(lit(1)).alias("FlightCount"),
             sum(expr("CASE WHEN IsDepDelayed = 'YES' THEN 1 ELSE 0 END ")).alias("IsDepDelayedCount"),
             sum(expr("CASE WHEN IsArrDelayed = 'YES' THEN 1 ELSE 0 END ")).alias("ArrDelayedCount")
            ).show

+-----------+------------+---------------+
|FlightCount|IsDepDelayedCount|ArrDelayedCount|
+-----------+------------+---------------+
|     605659|      265198|         297956|
+-----------+------------+---------------+
```

## Solutions - Problem 2

Get number of flights which are delayed in departure and number of flights delayed in arrival for each day along with number of flights departed for each day.

- Output should contain 4 columns - **FlightDate**, **FlightCount**, **DepDelayedCount**, **ArrDelayedCount**
- **FlightDate** should be of **yyyy-MM-dd** format.
- Data should be **sorted** in ascending order by **flightDate**

### Video Solution

```
val airlines = spark.read.format("parquet").load("/user/egarcia1/data/flightmonth=200801")

airlines.select("Year", "Month", "DayOfMonth", "IsArrDelayed", "IsDepDelayed")

airlines.
select("Year", "Month", "DayOfMonth", "IsArrDelayed", "IsDepDelayed").
withColumn("FlightDate", concat($"Year", lit("-"), lpad($"Month", 2, "0"), lit("-"), lpad($"DayOfMonth", 2, "0") )).
show


airlines.
groupBy(concat($"Year", lit("-"), lpad($"Month", 2, "0"), lit("-"), lpad($"DayOfMonth", 2, "0") ).as("FlightDate")).
//agg(count(lit(1)).as("FlightCount")).
show

airlines.
groupBy(concat($"Year", lit("-"), lpad($"Month", 2, "0"), lit("-"), lpad($"DayOfMonth", 2, "0")).as("FlightDate")).
agg(count(lit(1)).as("FlightCount"),
    sum(expr("CASE WHEN IsDepDelayed = 'YES' THEN 1 ELSE 0 END").as("DepDelayedCount")).as("ArrDelayedCount"),
    sum(expr("CASE WHEN IsArrDelayed = 'YES' THEN 1 ELSE 0 END").as("ArrDelayedCount")).as("ArrDelayedCount")
  ).
orderBy("FlightDate").
show

```

### Other Solution

```
val airlines = spark.read.format("parquet").load("/user/egarcia1/data/flightmonth=200801")

airlines.select("Year", "Month", "DayOfMonth", "IsArrDelayed", "IsDepDelayed")

airlines.
select("Year", "Month", "DayOfMonth", "IsArrDelayed", "IsDepDelayed").
withColumn("FlightDate", concat($"Year", lit("-"), lpad($"Month", 2, "0"), lit("-"), lpad($"DayOfMonth", 2, "0") )).
show


airlines.
groupBy(concat($"Year", lit("-"), lpad($"Month", 2, "0"), lit("-"), lpad($"DayOfMonth", 2, "0") ).as("FlightDate")).
agg(count(lit(1)).as("FlightCount"),
      count(when($"IsDepDelayed" === "YES", 1)).as("DepDelayedCount"),
      count(when($"IsArrDelayed" === "YES", 1)).as("ArrDelayedCount")
      ).
orderBy("FlightDate").
show

+----------+-----------+---------------+---------------+
|FlightDate|FlightCount|DepDelayedCount|ArrDelayedCount|
+----------+-----------+---------------+---------------+
|2008-01-01|      19175|          11053|          11725|
|2008-01-02|      20953|          13805|          14260|
|2008-01-03|      20937|          12294|          12488|
|2008-01-04|      20929|          10175|          10593|
|2008-01-05|      18066|           9507|           9801|
|2008-01-06|      19893|          11225|          11388|
|2008-01-07|      20341|           8701|           9262|
|2008-01-08|      19603|           7946|           9401|
|2008-01-09|      19820|           6339|           7234|
|2008-01-10|      20297|           7374|           8906|
|2008-01-11|      20349|           7779|           8688|
|2008-01-12|      16572|           4128|           4304|
|2008-01-13|      18946|           6993|           7832|
|2008-01-14|      20176|           6830|           8013|
|2008-01-15|      19503|           5603|           6688|
|2008-01-16|      19764|           5634|           6760|
|2008-01-17|      20273|          10507|          12101|
|2008-01-18|      20347|          10268|          11090|
|2008-01-19|      16249|           7275|           7686|
|2008-01-20|      18653|           6947|           7252|
+----------+-----------+---------------+---------------+
only showing top 20 rows

```
