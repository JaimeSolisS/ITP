# Joining Data Sets

Let us understand how to join multiple Data Sets using Spark based APIs.
• Prepare Datasets for Joins
• Starting Spark Context
• Analyze Datasets for Joins
• Problem Statements
• Overview of Joins
• Solutions - Problem 1
• Solutions - Problem 2
• Solutions - Problem 3
• Solutions - Problem 4
• Solutions - Problem 5
• Solutions - Problem 6

## Prepare Datasets for Joins

Let us prepare datasets to join.
• Make sure airport-codes is in HDFS.
• We will also use airlines data for the month of January 2008. We have used that data set in the past as well.

```
import sys.process._

scala> "hdfs dfs -ls /user/egarcia1/data/flightmonth=200801/flightmonth=200801" !
warning: there was one feature warning; re-run with -feature for details
Found 1 items
-rw-r--r--   3 egarcia1 egarcia1   14666683 2021-06-16 17:57 /user/egarcia1/data/flightmonth=200801/flightmonth=200801/part-00252-9f6f50c3-9ea4-459e-8b66-c0f1699e7475.c000.snappy.parquet

scala> "hdfs dfs -ls /user/egarcia1/data/airport-codes/airport-codes"!
warning: there was one feature warning; re-run with -feature for details
Found 1 items
-rw-r--r--   3 egarcia1 egarcia1      11411 2021-06-16 18:25 /user/egarcia1/data/airport-codes/airport-codes/airport-codes-na.txt
```

## Analyze Datasets for Joins

Let us analyze data sets that are going to be used for joins.
• We will use January 2008 airlines data which have all relevant flight details.
• Let us read and review the airlines data quickly

```
val airlines = spark.read.format("parquet").load("/user/egarcia1/data/flightmonth=200801")

scala> airlines.printSchema
root
 |-- Year: integer (nullable = true)
 |-- Month: integer (nullable = true)
 |-- DayofMonth: integer (nullable = true)
 |-- DayOfWeek: integer (nullable = true)
 |-- DepTime: string (nullable = true)
 |-- CRSDepTime: integer (nullable = true)
 |-- ArrTime: string (nullable = true)
 |-- CRSArrTime: integer (nullable = true)
 |-- UniqueCarrier: string (nullable = true)
 |-- FlightNum: integer (nullable = true)
 |-- TailNum: string (nullable = true)
 |-- ActualElapsedTime: string (nullable = true)
 |-- CRSElapsedTime: integer (nullable = true)
 |-- AirTime: string (nullable = true)
 |-- ArrDelay: string (nullable = true)
 |-- DepDelay: string (nullable = true)
 |-- Origin: string (nullable = true)
 |-- Dest: string (nullable = true)
 |-- Distance: string (nullable = true)
 |-- TaxiIn: string (nullable = true)
 |-- TaxiOut: string (nullable = true)
 |-- Cancelled: integer (nullable = true)
 |-- CancellationCode: string (nullable = true)
 |-- Diverted: integer (nullable = true)
 |-- CarrierDelay: string (nullable = true)
 |-- WeatherDelay: string (nullable = true)
 |-- NASDelay: string (nullable = true)
 |-- SecurityDelay: string (nullable = true)
 |-- LateAircraftDelay: string (nullable = true)
 |-- IsArrDelayed: string (nullable = true)
 |-- IsDepDelayed: string (nullable = true)
 |-- flightmonth: integer (nullable = true)
```

• We will be using another data set to get details about airports. Details include information such as State, City etc for a given airport code.
• Let us analyze the Dataset to confirm if there is header and also how the data is structured.

```
val airportCodes = spark.read.format("text").load("/user/egarcia1/data/airport-codes/airport-codes")

airportCodes.show(false)
```

• Data is tab separated.
• There is header for the data set.
• Dataset have 4 fields - Country, State, City, IATA
Create DataFrame airport_codes applying appropriate Schema.

```
val airportCodes = spark.read.
                    option("sep", "\t").
                    option("header", true).
                    option("inferSchema", true).
                    format("csv").
                    load("/user/egarcia1/data/airport-codes/airport-codes")

+-----------+-----+-------+----+
|City       |State|Country|IATA|
+-----------+-----+-------+----+
|Abbotsford |BC   |Canada |YXX |
|Aberdeen   |SD   |USA    |ABR |
|Abilene    |TX   |USA    |ABI |
|Akron      |OH   |USA    |CAK |
|Alamosa    |CO   |USA    |ALS |
|Albany     |GA   |USA    |ABY |
|Albany     |NY   |USA    |ALB |
|Albuquerque|NM   |USA    |ABQ |
|Alexandria |LA   |USA    |AEX |
|Allentown  |PA   |USA    |ABE |
|Alliance   |NE   |USA    |AIA |
|Alpena     |MI   |USA    |APN |
|Altoona    |PA   |USA    |AOO |
|Amarillo   |TX   |USA    |AMA |
|Anahim Lake|BC   |Canada |YAA |
|Anchorage  |AK   |USA    |ANC |
|Appleton   |WI   |USA    |ATW |
|Arviat     |NWT  |Canada |YEK |
|Asheville  |NC   |USA    |AVL |
|Aspen      |CO   |USA    |ASE |
+-----------+-----+-------+----+
only showing top 20 rows
```

• Preview and Understand the data.

• Get schema of airport_codes.

```
airportCodes.printSchema
root
 |-- City: string (nullable = true)
 |-- State: string (nullable = true)
 |-- Country: string (nullable = true)
 |-- IATA: string (nullable = true)
```

• Get the count of records

```
airportCodes.count
res12: Long = 526
```

• Get the count of unique records and see if it is the same as total count.

```
airportCodes.select("IATA").distinct.count
res13: Long = 524
```

• If they are not equal, analyze the data and identify IATA codes which are repeated more than once.

```
airportCodes.
groupBy("IATA").
agg(count(lit(1)).as("iata_count")).
filter("iata_count > 1").
show

+----+----------+
|IATA|iata_count|
+----+----------+
| Big|         3|
+----+----------+

```

• Filter out the duplicates using the most appropriate one and discard others.

```
airportCodes.
filter("IATA = 'Big'").
show

airportCodes.
filter("!(State = 'Hawaii' AND IATA = 'Big')").
show

+-----------+-----+-------+----+
|       City|State|Country|IATA|
+-----------+-----+-------+----+
| Abbotsford|   BC| Canada| YXX|
|   Aberdeen|   SD|    USA| ABR|
|    Abilene|   TX|    USA| ABI|
|      Akron|   OH|    USA| CAK|
|    Alamosa|   CO|    USA| ALS|
|     Albany|   GA|    USA| ABY|
|     Albany|   NY|    USA| ALB|
|Albuquerque|   NM|    USA| ABQ|
| Alexandria|   LA|    USA| AEX|
|  Allentown|   PA|    USA| ABE|
|   Alliance|   NE|    USA| AIA|
|     Alpena|   MI|    USA| APN|
|    Altoona|   PA|    USA| AOO|
|   Amarillo|   TX|    USA| AMA|
|Anahim Lake|   BC| Canada| YAA|
|  Anchorage|   AK|    USA| ANC|
|   Appleton|   WI|    USA| ATW|
|     Arviat|  NWT| Canada| YEK|
|  Asheville|   NC|    USA| AVL|
|      Aspen|   CO|    USA| ASE|
+-----------+-----+-------+----+
only showing top 20 rows

```

• Get number of airports (IATA Codes) for each state in the US. Sort the data in descending order by count.

```
val airportCodes = spark.read.
                    option("sep", "\t").
                    option("header", true).
                    option("inferSchema", true).
                    format("csv").
                    load("/user/egarcia1/data/airport-codes/airport-codes").
                    filter("!(State = 'Hawaii' AND IATA = 'Big') AND Country = 'USA'")

airportCodes.count
res0: Long = 443

val airportCountByState = airportCodes.
groupBy("Country", "State").
agg(count(lit(1)).as("IATACount")).
orderBy($"IATACount".desc)

scala> airportCountByState.count
res9: Long = 51

airportCountByState.show(51)

scala> airportCountByState.show(51)
+-------+-----+---------+
|Country|State|IATACount|
+-------+-----+---------+
|    USA|   CA|       29|
|    USA|   TX|       26|
|    USA|   AK|       25|
|    USA|   MI|       18|
|    USA|   NY|       18|
|    USA|   FL|       18|
|    USA|   MT|       14|
|    USA|   PA|       13|
|    USA|   IL|       12|
|    USA|   CO|       12|
|    USA|   NC|       10|
|    USA|   WY|       10|
|    USA|   WI|        9|
|    USA|   GA|        9|
|    USA|   NE|        9|
|    USA|   KS|        9|
|    USA|   WA|        9|
|    USA|   HI|        9|
|    USA|   NM|        9|
|    USA|   WV|        8|
|    USA|   AZ|        8|
|    USA|   IA|        8|
|    USA|   MO|        8|
|    USA|   MN|        8|
|    USA|   MA|        8|
|    USA|   ND|        8|
|    USA|   AR|        8|
|    USA|   MS|        7|
|    USA|   VA|        7|
|    USA|   SD|        7|
|    USA|   LA|        7|
|    USA|   OR|        7|
|    USA|   ME|        7|
|    USA|   TN|        6|
|    USA|   ID|        6|
|    USA|   IN|        6|
|    USA|   OH|        6|
|    USA|   SC|        6|
|    USA|   AL|        6|
|    USA|   OK|        5|
|    USA|   KY|        4|
|    USA|   NV|        3|
|    USA|   NJ|        3|
|    USA|   VT|        3|
|    USA|   MD|        3|
|    USA| null|        3|
|    USA|   NH|        3|
|    USA|   UT|        2|
|    USA|   CT|        2|
|    USA|   RI|        1|
|    USA|   DE|        1|
+-------+-----+---------+
```

## Problem Statements

Let us understand how to join Data Frames by using some problem statements. We will use 2008 January airlines data along with Airport Codes.

• Get number of flights departed from each of the US airport.
• Get number of flights departed from each of the state.
• Get the list of airports in the US from which flights are not departed.
• Check if there are any origins in airlines data which do not have record in airport-codes.
• Get the total number of flights from the airports that do not contain entries in airport-codes.
• Get the total number of flights per airport that do not contain entries in airport-codes.

## Overview of Joins

Let us get an overview of joining Data Frames.

- Our data cannot be stored in one table. It will be stored in multiple tables and the tables might be related.
  - When it comes to transactional systems, we typically define tables based on Normalization Principles.
  - When it comes to data warehousing applications, we typically define tables using Dimensional Modeling.
  - Either of the approach data is scattered into multiple tables and relationships are defined.
  - Typically tables are related with one to one, one to many, many to many relationships.
- When we have 2 Data Sets that are related based on a common key we typically perform join.
- There are different types of joins. - INNER JOIN - OUTER JOIN (LEFT or RIGHT) - FULL OUTER JOIN (a LEFT OUTER JOIN b UNION a RIGHT OUTER JOIN b)
  ## Solutions - Problem 1
  Get number of flights departed from each of the US airport in the month of 2008 January.
  • We have to use airport codes to determine US airport.
  • We need to use airlines data to get departure details.
  • To solve this problem we have to perform inner join.

```
val airlines = spark.read.format("parquet").load("/user/egarcia1/data/flightmonth=200801")

airlines.select("Year", "Month", "DayOfMonth", "Origin", "Dest", "CRSDepTime").show(10)
+----+-----+----------+------+----+----------+
|Year|Month|DayOfMonth|Origin|Dest|CRSDepTime|
+----+-----+----------+------+----+----------+
|2008|    1|        16|   MCI| MDW|       955|
|2008|    1|        16|   MDW| LAX|      1850|
|2008|    1|        16|   PHL| MHT|      1050|
|2008|    1|        16|   PHL| PIT|       745|
|2008|    1|        16|   PHL| RDU|      1725|
|2008|    1|        16|   PHX| ONT|       635|
|2008|    1|        16|   PHX| STL|      1110|
|2008|    1|        16|   RNO| SJC|       910|
|2008|    1|        16|   SEA| LAS|      1015|
|2008|    1|        17|   BWI| PIT|      1920|
+----+-----+----------+------+----+----------+
only showing top 10 rows

airlines.
join(airportCodes, $"IATA" === $"Origin").
select($"Year", $"Month", $"DayOfMonth", airportCodes("*"), $"CRSDepTime").
show
+----+-----+----------+---------------+-----+-------+----+----------+
|Year|Month|DayOfMonth|           City|State|Country|IATA|CRSDepTime|
+----+-----+----------+---------------+-----+-------+----+----------+
|2008|    1|        16|    Kansas City|   MO|    USA| MCI|       955|
|2008|    1|        16|        Chicago|   IL|    USA| MDW|      1850|
|2008|    1|        16|   Philadelphia|   PA|    USA| PHL|      1050|
|2008|    1|        16|   Philadelphia|   PA|    USA| PHL|       745|
|2008|    1|        16|   Philadelphia|   PA|    USA| PHL|      1725|
|2008|    1|        16|        Phoenix|   AZ|    USA| PHX|       635|
|2008|    1|        16|        Phoenix|   AZ|    USA| PHX|      1110|
|2008|    1|        16|           Reno|   NV|    USA| RNO|       910|
|2008|    1|        16|        Seattle|   WA|    USA| SEA|      1015|
|2008|    1|        17|      Baltimore|   MD|    USA| BWI|      1920|
|2008|    1|        17|       Columbus|   OH|    USA| CMH|      2015|
|2008|    1|        17|        Houston|   TX|    USA| HOU|      1125|
|2008|    1|        17|  Washington DC| null|    USA| IAD|      1020|
|2008|    1|        17|      Las Vegas|   NV|    USA| LAS|      1255|
|2008|    1|        17|      Las Vegas|   NV|    USA| LAS|      1045|
|2008|    1|        17|        Orlando|   FL|    USA| MCO|      1255|
|2008|    1|        17|        Orlando|   FL|    USA| MCO|      1310|
|2008|    1|        17|        Oakland|   CA|    USA| OAK|      1915|
|2008|    1|        17|        Oakland|   CA|    USA| OAK|      1305|
|2008|    1|        17|West Palm Beach|   FL|    USA| PBI|      1225|
+----+-----+----------+---------------+-----+-------+----+----------+
only showing top 20 rows

airlines.
join(airportCodes, $"IATA" === $"Origin").
select($"Year", $"Month", $"DayOfMonth", airportCodes("*"), $"CRSDepTime").
show

```

**If both tables have the same column name, there will be conflicts, so we have to specify the _DataFrameName_("ColumnName") Ex:**

```
airlines.
join(airportCodes, airlines("Origin") === airportCodes("IATA")).
groupBy("Origin").
agg(count(lit(1)).as("FlightCount")).
show
+------+-----------+
|Origin|FlightCount|
+------+-----------+
|   BGM|         62|
|   MSY|       3453|
|   GEG|       1373|
|   BUR|       2797|
|   SNA|       4273|
|   GTF|        217|
|   GRB|        675|
|   IDA|        300|
|   GRR|       1273|
|   EUG|        552|
|   PVD|       1983|
|   GSO|       1083|
|   MYR|        360|
|   OAK|       5932|
|   COD|         93|
|   MSN|       1102|
|   FSM|        240|
|   FAR|        402|
|   MQT|         85|
|   BTM|         62|
+------+-----------+

airlines.
join(airportCodes, airlines("Origin") === airportCodes("IATA")).
groupBy("Origin").
agg(count(lit(1)).as("FlightCount")).
orderBy($"FlightCount".desc).
show
```

+------+-----------+  
|Origin|FlightCount|
+------+-----------+
| ATL| 33897|
| ORD| 29936|
| DFW| 23861|
| DEN| 19477|
| LAX| 18945|
| PHX| 17695|
| IAH| 15531|
| LAS| 15292|
| DTW| 14357|
| EWR| 12467|
| SLC| 12401|
| MSP| 11800|
| SFO| 11573|
| MCO| 11070|
| CLT| 10752|
| LGA| 10300|
| JFK| 10023|
| BOS| 9717|
| BWI| 8883|
| CVG| 8659|
+------+-----------+
only showing top 20 rows

## Solutions - Problem 2

Get number of flights departed from each of the US state in the month of 2008 January.
• We have to use airport codes to determine state of each of the US airport.
• We need to use airlines data to get departure details.
• To solve this problem we have to perform inner join.

```
airlines.
join(airportCodes, airlines("Origin") === airportCodes("IATA")).
groupBy($"State").
agg(count(lit(1)).as("FlightCount")).
orderBy($"FlightCount".desc).
show

+-----+-----------+
|State|FlightCount|
+-----+-----------+
|   CA|      72853|
|   TX|      63930|
|   FL|      41042|
|   IL|      39812|
|   GA|      35527|
|   NY|      28414|
|   CO|      23288|
|   AZ|      20768|
|   OH|      19209|
|   NC|      17942|
|   MI|      17824|
|   NV|      17763|
| null|      14090|
|   TN|      13549|
|   PA|      13491|
|   UT|      12709|
|   NJ|      12498|
|   MN|      12357|
|   MO|      11808|
|   WA|      10210|
+-----+-----------+
only showing top 20 rows

airlines.
join(airportCodes.filter("State IS NOT NULL"), airlines("Origin") === airportCodes("IATA")).
groupBy($"State").
agg(count(lit(1)).as("FlightCount")).
orderBy($"FlightCount".desc).
show

+-----+-----------+
|State|FlightCount|
+-----+-----------+
|   CA|      72853|
|   TX|      63930|
|   FL|      41042|
|   IL|      39812|
|   GA|      35527|
|   NY|      28414|
|   CO|      23288|
|   AZ|      20768|
|   OH|      19209|
|   NC|      17942|
|   MI|      17824|
|   NV|      17763|
|   TN|      13549|
|   PA|      13491|
|   UT|      12709|
|   NJ|      12498|
|   MN|      12357|
|   MO|      11808|
|   WA|      10210|
|   MA|       9717|
+-----+-----------+
only showing top 20 rows
```

## Solutions - Problem 3

Get the list of airports in the US from which flights are not departed in the month of 2008 January.
• This is an example for outer join.
• We need to get those airports which are in airport codes but not in 2008 January airlines data set.
• Based on the side of the airport codes data set, we can say left or right. We will be using airport codes as the driving data set and hence we will use left outer join.
val airlinesPath = "/public/airlines_all/airlines-part/flightmonth=200801"

```
airlines.
filter("Origin IS NULL").
join(airportCodes, airlines("Origin") === airportCodes("IATA"), "right").
select(airportCodes("*"), airlines("Origin")).
show

+-------------+-----+-------+----+------+
|         City|State|Country|IATA|Origin|
+-------------+-----+-------+----+------+
|     Aberdeen|   SD|    USA| ABR|  null|
|      Abilene|   TX|    USA| ABI|  null|
|        Akron|   OH|    USA| CAK|  null|
|      Alamosa|   CO|    USA| ALS|  null|
|       Albany|   GA|    USA| ABY|  null|
|       Albany|   NY|    USA| ALB|  null|
|  Albuquerque|   NM|    USA| ABQ|  null|
|   Alexandria|   LA|    USA| AEX|  null|
|    Allentown|   PA|    USA| ABE|  null|
|     Alliance|   NE|    USA| AIA|  null|
|       Alpena|   MI|    USA| APN|  null|
|      Altoona|   PA|    USA| AOO|  null|
|     Amarillo|   TX|    USA| AMA|  null|
|    Anchorage|   AK|    USA| ANC|  null|
|     Appleton|   WI|    USA| ATW|  null|
|    Asheville|   NC|    USA| AVL|  null|
|        Aspen|   CO|    USA| ASE|  null|
|       Athens|   GA|    USA| AHN|  null|
|      Atlanta|   GA|    USA| ATL|  null|
|Atlantic City|   NJ|    USA| ACY|  null|
+-------------+-----+-------+----+------+
only showing top 20 rows
```
