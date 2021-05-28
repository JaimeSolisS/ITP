# Spark SQL

Spark SQL is Spark module of structured data processing. It provides more information on the data and the computation being performed.

Spark SQL is used to execute SQL queries and read from a SQL database.

## Why isn't RDD enough?

RDD is not enough because it can't express complex transformations to compute the data. 

- Computation is Opaque
- Data is Opaque 
- API is Verbose (complex operationes can be expressed with a single line of SQL).  

## Why Spark SQL?
- Perform Data Operations, withput specifying how to execute operations

- Provides
    - High-Level API's SQL, Dataframe, Dataset
    - Efficient optimizer
    
## Spark SQL Overview 
![Overview](https://image.slidesharecdn.com/sparksummiteast-sparksql-170209223852/95/sparksql-a-compiler-from-queries-to-rdds-10-638.jpg?cb=1486680061)

- **Query Plan**: Map a SQL query to a Spark Method  
- **Logical Plan**: Describes computation withpud defining how to perform it   
- **Physical plan**: Logical instruction on how to execute the logical plan  
- **Evaluate expressions**: Simplify expressions ex. `1+2+t1.value => 3+t1.value`  
- **Predicate push down**: Filter the data first and then join  
- **Column Pruning**: Selecting only the required columns necessary to perform the computation, instead of grabbing the whole table  
- **Optimized query plan**: The result of all the optimizations

## Dataframe 

A Dataframe is a distributed collection of data organized into named columns (a table in Mysql)

- Construct DataFrame via: 
    - Transforming existing DataFrame
    - Files in storage system (e.g. HDFS, Parquet in S3)
    
## Dataset 
 
A Dataset is a strongly-typed, immutable collection of objects that are mapped to a relational schema
- Extension of a Dataframe API
    - Type-safe
    - Object Oriente Programming principles
- Data in memory is encoded using Spark's Tungsten Binary Format
- Uses Catalyst Optimizer

- Best of both worlds (Dataframe & RDD)
- Use less memory 

## Spark Session

- It is the entry point to use Spark 

## DF from RDD

- Create an RDD from text file
`scala> val data = sc.textFile("hdfs://localhost:54310/user/hadoop/ml-100k/u.data")`
- Create case class (this is the schema  
`case class Ratings(userId: Int, itemID: Int, rating: Int, timestmap:String)`
- Convert to Dataframe by mapping the data to the class  
`scala> val rating = data.map(_.split("\t")).map(p => ratings(p(0).trim.toInt,  p(1).trim.toInt, p(2).trim.toInt, p(3))).toDF()`
- Create the dataframe from the json data
`scala> val movie = sqlContext.jsonFile("hdfs://localhost:54310/user/hadoop/ml-100k/movies.json")`

## Operations 

### printSchema()
`scala> ratint.printSchema()`
### Select
- Select Column 
`scala> rating.select("userId").show()
- Condition Select
`scala> rating.select(rating("itemID")>100).show()`
### filter
`scala> rating.filter(rating("rating")>3).show()`
`scala> rating.filter(rating("rating")>3).select("userID","itemID").show()`
`rating.filter("userID">500).select(avg("rating"),max("rating"),sum("rating")).show()`

### Group by
Group by need to be done with the aggregate functions
- avg
- max
- min
- mean
- sum
- User defined Function

`scala> rating.groupBy("userId").count().show()`
`scala> rating.groupBy("userId").agg("rating"->"avg","userID" -> "count").show()`
`scala> rating.groupBy("userId").count().sort("count","userID").show()`

### Join 
- Basic Join Operation 
`scala> rating.join(movie, $"itemID" === $"movieID", "inner").show()
- Join supports: 
    - inner, outer, left_outer, right_outer, semijoin
    
### Registering Temp Table

`scala> rating.registerTempTable("rating_table"

- SQL Operation
`scala> sqlContext.sql("SELECT userID FROM rating_table").show()

## Save Data
- Save()
`rating.select("itemID").save("hdfs://localhost:54310/test2.json","json")`

- Save supports
    - saveAsParquetFile
    - saveAsTable(Hive Table)
    - Avro File
    - Any De-limited format 

## Write SQL
If you want to use more SQL then just use SQL queries through the sqlContext object.

`sqlContext.sql(“select * from ratings“).show()`
`sqlContext.sql(“select item, avg(rating) from ratings group by item“`

## Mixed expression 
You can do SQL queries and return a DF and then do operations over that DF using Spark SQL methods. You don't have to worry about what happens behind the scenes,  Catalyst takes care of that.

`df = sqlContext.sql(“select * from ratings”)`
`df.filter(“ratings < 5”).groupBy(“item”).count().show()`

## User Define Function

- register the new function to the sqlContext object`

```
from pyspark.sql.functions import udf 
from pyspark.sql.types import *
sqlContext.registerFunction("hash", lambda x: hash(x), LongType())
sqlContext.sql(“select hash(item) from ratings”)
```

## Data Types

- Numeric types
- String type
- Binary type
- Boolean type
- Datetime type
- TimestampType: Represents values comprising values of fields year, month, day, hour, minute, and second.
- Date Type: Represents values comprising values of fields year, month, day.
- Complex types

## Joins 

- Basic Joins
    - Shuffle Hash Join 
        - troubleshooting
    - Broadcast Hash Join
    - Cartesian Join

The Catalyst optimizer selects the best option

## Reading from JDBC

```
%spark
val jdbcDF = spark.read
    .formta("jdbc")
    .option("url", "")
    .option("dbtable", "screen")
    .option("user", "")
    .option("password", "")
    .load()
```

## Data Formats

- Delimited Text
- Json
- Avro
- Parquet 
- ORC

Criteria of selection:  

- Schema Evolution 
- Splitability 
- Compression 
- Serialization / Deserialization 
    - How fast we can read the data format?
    
### JSON
- Serialization format for HTTP & Javascript
- Text-format with many parsers
- Schema integrated with data
- Row major format 

### Avro
- Cross Language File Format for Hadoop
- Supports Schema Evolution 
- Schema seggregated from Data
- Row major format 
- Dynamic Typing 

### Parquet 
- Designed based on Google's dremel paper
- Schema segregated into footer
- Column major format
- All data pushed to leaves of the tree
- Integrated compression and indexes

### ORC
- Schema segregated into footer
- Column major format
- Integrated 
    - Compression
    - Indexes & stats
- Rich type model, stored top-down

### Which one should I choose?

It depends on the data you are using and what you need



