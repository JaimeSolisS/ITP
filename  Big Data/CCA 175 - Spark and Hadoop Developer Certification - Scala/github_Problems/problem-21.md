## Problem:

- Details - Duration 40 minutes
- Data set URL 825
- Choose language of your choice Python or Scala
- Data is available in HDFS file system under /public/crime/csv
- You can check properties of files using hadoop fs -ls -h /public/crime/csv
- Structure of data (ID,Case Number,Date,Block,IUCR,Primary Type,Description,Location Description,Arrest,Domestic,Beat,District,Ward,Community Area,FBI Code,X Coordinate,Y Coordinate,Year,Updated On,Latitude,Longitude,Location)
- **File format** - text file
- **Delimiter** - “,”
- Get monthly count of primary crime type, sorted by month in ascending and number of crimes per type in descending order
- Store the result in HDFS path **/user/<YOUR_USER_ID>/solutions/solution01/crimes_by_type_by_month**
- **Output File** Format: TEXT
- **Output Columns:** Month in YYYYMM format, crime count, crime type
- **Output Delimiter:** \t (tab delimited)
- **Output Compression:** gzip

## Solution

### Load file

```
val crime =  spark.read.option("header", "true").option("inferSchema", "true").csv("/user/jsolis/data/crime")
```

### Transformations and Agg

```
val crime2 = crime.select("Date", "Primary Type")

val solution = crime2.
withColumn("Date", substring($"Date", 0, 10)).
withColumn("Date", to_date($"Date", "MM/dd/yyyy")).
withColumn("Month", date_format($"Date", "yyyyMM")).
groupBy("Month", "Primary Type").
agg(count(lit(1)).as("crime count")).
orderBy($"Month", $"crime count".desc).
select($"Month", $"crime count", $"Primary Type".as("crime type"))

scala>solution.show
+------+-----------+--------------------+
| Month|crime count|          crime type|
+------+-----------+--------------------+
|200101|       7866|               THEFT|
|200101|       6525|             BATTERY|
|200101|       4714|           NARCOTICS|
|200101|       3966|     CRIMINAL DAMAGE|
|200101|       2800|       OTHER OFFENSE|
|200101|       2123|             ASSAULT|
|200101|       2096| MOTOR VEHICLE THEFT|
|200101|       1934|            BURGLARY|
|200101|       1396|             ROBBERY|
|200101|       1395|  DECEPTIVE PRACTICE|
|200101|       1192|   CRIMINAL TRESPASS|
|200101|        563|        PROSTITUTION|
|200101|        337|   WEAPONS VIOLATION|
|200101|        243|OFFENSE INVOLVING...|
|200101|        218|         SEX OFFENSE|
|200101|        162| CRIM SEXUAL ASSAULT|
|200101|        161|PUBLIC PEACE VIOL...|
|200101|        101|LIQUOR LAW VIOLATION|
|200101|         75|          KIDNAPPING|
|200101|         67|               ARSON|
+------+-----------+--------------------+
only showing top 20 rows

```

### Export Solution

```
solution.
write.
mode("overwrite").
option("sep", "\t").
option("compression", "gzip").
csv("/user/jsolis/solutions/solution01/crimes_by_type_by_month")
```

### Check Solution

```
spark.read.option("sep", "\t").csv("/user/jsolis/solutions/solution01/crimes_by_type_by_month").show

+------+----+--------------------+
|   _c0| _c1|                 _c2|
+------+----+--------------------+
|201702|  13|          KIDNAPPING|
|201702|  11|CRIMINAL SEXUAL A...|
|201702|   9|            GAMBLING|
|201702|   4|           OBSCENITY|
|201702|   3|CONCEALED CARRY L...|
|201702|   1|        NON-CRIMINAL|
|201702|   1|NON-CRIMINAL (SUB...|
|201702|   1|    PUBLIC INDECENCY|
|201703|4501|               THEFT|
|201703|3859|             BATTERY|
|201703|2238|     CRIMINAL DAMAGE|
|201703|1644|  DECEPTIVE PRACTICE|
|201703|1524|       OTHER OFFENSE|
|201703|1486|             ASSAULT|
|201703|1126|           NARCOTICS|
|201703| 931|            BURGLARY|
|201703| 801| MOTOR VEHICLE THEFT|
|201703| 783|             ROBBERY|
|201703| 548|   CRIMINAL TRESPASS|
|201703| 307|   WEAPONS VIOLATION|
+------+----+--------------------+
only showing top 20 rows
```
