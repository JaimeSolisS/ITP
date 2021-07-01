## Instructions

**Get top 3 crime types based on number of incidents in RESIDENCE area using "Location Description"**

**Data Description: Data is available in HDFS under /public/crime/csv**

### crime data information:

> **\*Structure of data:** (ID, Case Number, Date, Block, IUCR, Primary Type, Description, Location Description, Arrst, Domestic, Beat, District, Ward, Community Area, FBI Code, X Coordinate, Y Coordinate, Year, Updated on, Latitude, Longitude, Location)  
> **File format:** text file  
> **Delimiter:** ","\*

    (use regex while splitting split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1), as there are some fields with comma and enclosed using double quotes.

### Output Requirements:

> **\*Output Fields:** crime_type, incident_count  
> **Output File Format:** JSON  
> **Delimiter:** N/A  
> **Compression:** No  
> **Place the output file in the HDFS directory:** /user/`whoami`/problem3/solution/  
> Replace `whoami` with your OS user name\*

### End of Problem

## Solution

[Data Link](https://data.cityofchicago.org/Public-Safety/Crimes-2001-to-Present/ijzp-q8t2)

### See first lines of files

```
$ head -5 Crimes_-_2001_to_Present.csv

ID,Case Number,Date,Block,IUCR,Primary Type,Description,Location Description,Arrest,Domestic,Beat,District,Ward,Community Area,FBI Code,X Coordinate,Y Coordinate,Year,Updated On,Latitude,Longitude,Location
10224738,HY411648,09/05/2015 01:30:00 PM,043XX S WOOD ST,0486,BATTERY,DOMESTIC BATTERY SIMPLE,RESIDENCE,false,true,0924,009,12,61,08B,1165074,1875917,2015,02/10/2018 03:50:01 PM,41.815117282,-87.669999562,"(41.815117282, -87.669999562)"
10224739,HY411615,09/04/2015 11:30:00 AM,008XX N CENTRAL AVE,0870,THEFT,POCKET-PICKING,CTA BUS,false,false,1511,015,29,25,06,1138875,1904869,2015,02/10/2018 03:50:01 PM,41.895080471,-87.765400451,"(41.895080471, -87.765400451)"
11646166,JC213529,09/01/2018 12:01:00 AM,082XX S INGLESIDE AVE,0810,THEFT,OVER $500,RESIDENCE,false,true,0631,006,8,44,06,,,2018,04/06/2019 04:04:43 PM,,,
10224740,HY411595,09/05/2015 12:45:00 PM,035XX W BARRY AVE,2023,NARCOTICS,POSS: HEROIN(BRN/TAN),SIDEWALK,true,false,1412,014,35,21,18,1152037,1920384,2015,02/10/2018 03:50:01 PM,41.937405765,-87.716649687,"(41.937405765, -87.716649687)"
```

### Pass file to HDFS

```
hdfs dfs -copyFromLocal ~/data/crime/ /user/jsolis/data/
```

### Check File in HDFS

```
$hdfs dfs -cat /user/jsolis/data/crime/Crimes_-_2001_to_Present.csv | head -5

ID,Case Number,Date,Block,IUCR,Primary Type,Description,Location Description,Arrest,Domestic,Beat,District,Ward,Community Area,FBI Code,X Coordinate,Y Coordinate,Year,Updated On,Latitude,Longitude,Location
10224738,HY411648,09/05/2015 01:30:00 PM,043XX S WOOD ST,0486,BATTERY,DOMESTIC BATTERY SIMPLE,RESIDENCE,false,true,0924,009,12,61,08B,1165074,1875917,2015,02/10/2018 03:50:01 PM,41.815117282,-87.669999562,"(41.815117282, -87.669999562)"
10224739,HY411615,09/04/2015 11:30:00 AM,008XX N CENTRAL AVE,0870,THEFT,POCKET-PICKING,CTA BUS,false,false,1511,015,29,25,06,1138875,1904869,2015,02/10/2018 03:50:01 PM,41.895080471,-87.765400451,"(41.895080471, -87.765400451)"
11646166,JC213529,09/01/2018 12:01:00 AM,082XX S INGLESIDE AVE,0810,THEFT,OVER $500,RESIDENCE,false,true,0631,006,8,44,06,,,2018,04/06/2019 04:04:43 PM,,,
10224740,HY411595,09/05/2015 12:45:00 PM,035XX W BARRY AVE,2023,NARCOTICS,POSS: HEROIN(BRN/TAN),SIDEWALK,true,false,1412,014,35,21,18,1152037,1920384,2015,02/10/2018 03:50:01 PM,41.937405765,-87.716649687,"(41.937405765, -87.716649687)"
```

## Read File

```
val crimes = spark.read.option("header", "true").csv("/user/jsolis/data/crime")

scala> crimes.show(5, false)
[Stage 4:>                                                          (                                                                     +--------+-----------+----------------------+---------------------+----+------------+-----------------------+--------------------+------+--------+----+--------+----+--------------+--------+------------+------------+----+----------------------+------------+-------------+-----------------------------+
|ID      |Case Number|Date                  |Block                |IUCR|Primary Type|Description            |Location Description|Arrest|Domestic|Beat|District|Ward|Community Area|FBI Code|X Coordinate|Y Coordinate|Year|Updated On            |Latitude    |Longitude    |Location                     |
+--------+-----------+----------------------+---------------------+----+------------+-----------------------+--------------------+------+--------+----+--------+----+--------------+--------+------------+------------+----+----------------------+------------+-------------+-----------------------------+
|10224738|HY411648   |09/05/2015 01:30:00 PM|043XX S WOOD ST      |0486|BATTERY     |DOMESTIC BATTERY SIMPLE|RESIDENCE           |false |true    |0924|009     |12  |61            |08B     |1165074     |1875917     |2015|02/10/2018 03:50:01 PM|41.815117282|-87.669999562|(41.815117282, -87.669999562)|
|10224739|HY411615   |09/04/2015 11:30:00 AM|008XX N CENTRAL AVE  |0870|THEFT       |POCKET-PICKING         |CTA BUS             |false |false   |1511|015     |29  |25            |06      |1138875     |1904869     |2015|02/10/2018 03:50:01 PM|41.895080471|-87.765400451|(41.895080471, -87.765400451)|
|11646166|JC213529   |09/01/2018 12:01:00 AM|082XX S INGLESIDE AVE|0810|THEFT       |OVER $500              |RESIDENCE           |false |true    |0631|006     |8   |44            |06      |null        |null        |2018|04/06/2019 04:04:43 PM|null        |null         |null                         |
|10224740|HY411595   |09/05/2015 12:45:00 PM|035XX W BARRY AVE    |2023|NARCOTICS   |POSS: HEROIN(BRN/TAN)  |SIDEWALK            |true  |false   |1412|014     |35  |21            |18      |1152037     |1920384     |2015|02/10/2018 03:50:01 PM|41.937405765|-87.716649687|(41.937405765, -87.716649687)|
|10224741|HY411610   |09/05/2015 01:00:00 PM|0000X N LARAMIE AVE  |0560|ASSAULT     |SIMPLE                 |APARTMENT           |false |true    |1522|015     |28  |25            |08A     |1141706     |1900086     |2015|02/10/2018 03:50:01 PM|41.881903443|-87.755121152|(41.881903443, -87.755121152)|
+--------+-----------+----------------------+---------------------+----+------------+-----------------------+--------------------+------+--------+----+--------+----+--------------+--------+------------+------------+----+----------------------+------------+-------------+-----------------------------+
only showing top 5 rows
```

###

```

val solution = crimes.
filter($"Location Description" === "RESIDENCE").
groupBy($"Primary Type".as("crime_type")).
agg(count(lit(1)).as("incident_count")).
orderBy($"incident_count".desc).
limit(3)

```

### Export Solution

```
solution.
write.
mode("overwrite").
option("compression", "none").
format("json").
save("/user/jsolis/problem3/solution/")
```

### Check Answer

```
spark.read.json("/user/jsolis/problem3/solution/").show

+-------------+--------------+
|   crime_type|incident_count|
+-------------+--------------+
|      BATTERY|        281820|
|OTHER OFFENSE|        206428|
|        THEFT|        160601|
+-------------+--------------+
```
