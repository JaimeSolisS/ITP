# Processing Column Data

As part of this module we will explore the functions available under `org.apache.spark.sql.functions` to derive new values from existing column values with in a Data Frame.

## Introduction to Pre-defined Functions

We typically process data in the columns using functions in `org.apache.spark.sql.functions`. Let us understand details about these functions in detail as part of this module.

- Let us recap about Functions or APIs to process Data Frames.
- Projection - `select` or `withColumn`
- Filtering - `filter` or `where`
- Grouping data by key and perform aggregations - `groupBy`
- Sorting data - `sort` or `orderBy`
- We can pass column names or literals or expressions to all the Data Frame APIs.
- Expressions include arithmetic operations, transformations using functions from `org.apache.spark.sql.functions`.
- There are approximately 300 functions under `org.apache.spark.sql.functions`.
- We will talk about some of the important functions used for String Manipulation, Date Manipulation etc.

## Create Dummy Data Frame

Let us go ahead and create dummy data from to explore functions.

```
val l = List("X")
val df = l.toDF("dummy")
```

Once Data Frame is created, we can use to understand how to use functions. For example, to get current date, we can run `df.select(current_date()).show()`.

It is similar to Oracle Query `SELECT sysdate FROM dual`

```
scala> df.select(current_date()).show
+--------------+
|current_date()|
+--------------+
|    2021-06-22|
+--------------+

scala> df.select(current_date.alias("custom_date")).show
+-----------+
|custom_date|
+-----------+
| 2021-06-22|
+-----------+
```

```
val employees = List((1, "Scott", "Tiger", 1000.0,
                      "united states", "+1 123 456 7890", "123 45 6789"
                     ),
                     (2, "Henry", "Ford", 1250.0,
                      "India", "+91 234 567 8901", "456 78 9123"
                     ),
                     (3, "Nick", "Junior", 750.0,
                      "united KINGDOM", "+44 111 111 1111", "222 33 4444"
                     ),
                     (4, "Bill", "Gomes", 1500.0,
                      "AUSTRALIA", "+61 987 654 3210", "789 12 6118"
                     )
                    )

scala> employees.size
res5: Int = 4
```

```
val employeesDF = employees.toDF("employee_id", "first_name", "last_name", "salary", "nationality", "phone_number", "ssn")

employeesDF: org.apache.spark.sql.DataFrame = [employee_id: int, first_name: string ... 5 more fields]

scala> employeesDF.printSchema
root
 |-- employee_id: integer (nullable = false)
 |-- first_name: string (nullable = true)
 |-- last_name: string (nullable = true)
 |-- salary: double (nullable = false)
 |-- nationality: string (nullable = true)
 |-- phone_number: string (nullable = true)
 |-- ssn: string (nullable = true)


scala> employeesDF.show(false)
+-----------+----------+---------+------+--------------+----------------+-----------+
|employee_id|first_name|last_name|salary|nationality   |phone_number    |ssn        |
+-----------+----------+---------+------+--------------+----------------+-----------+
|1          |Scott     |Tiger    |1000.0|united states |+1 123 456 7890 |123 45 6789|
|2          |Henry     |Ford     |1250.0|India         |+91 234 567 8901|456 78 9123|
|3          |Nick      |Junior   |750.0 |united KINGDOM|+44 111 111 1111|222 33 4444|
|4          |Bill      |Gomes    |1500.0|AUSTRALIA     |+61 987 654 3210|789 12 6118|
+-----------+----------+---------+------+--------------+----------------+-----------+
```

## Categories of Functions

There are approximately 300 functions under org.apache.spark.sql.functions. At a higher level they can be grouped into a few categories.

- String Manipulation Functions
- Case Conversion - `lower`, `upper`
- Getting Length - `length`
- Extracting substrings - `substring`, `split`
- Trimming - `trim`, `ltrim`, `rtrim`
- Padding - `lpad`, `rpad`
- Concatenating strings - `concat`
- Date Manipulation Functions
- Getting current date and time - `current_date`, `current_timestamp`
- Date Arithmetic - `date_add`, `date_sub`, `datediff`, `months_between`, `add_months`, `next_day`
- Beginning and Ending Date or Time - `last_day`, `trunc`, `date_trunc`
- Formatting Date - `date_format`
- Extracting Information - `dayofyear`, `dayofmonth`, `dayofweek`, `year`, `month`
- Aggregate Functions
- `count`, `countDistinct`
- `sum`, `avg`
- `min`, `max`
- Other Functions - We will explore depending on the use cases.

## Special Functions - col and lit

Let us understand special functions such as col and lit.

- First let us create Data Frame for demo purposes (use the same as previous section).

```
val employees = List((1, "Scott", "Tiger", 1000.0,
                      "united states", "+1 123 456 7890", "123 45 6789"
                     ),
                     (2, "Henry", "Ford", 1250.0,
                      "India", "+91 234 567 8901", "456 78 9123"
                     ),
                     (3, "Nick", "Junior", 750.0,
                      "united KINGDOM", "+44 111 111 1111", "222 33 4444"
                     ),
                     (4, "Bill", "Gomes", 1500.0,
                      "AUSTRALIA", "+61 987 654 3210", "789 12 6118"
                     )
                    )

val employeesDF = employees.
    toDF("employee_id", "first_name",
         "last_name", "salary",
         "nationality", "phone_number",
         "ssn"
        )
```

- For Data Frame APIs such as `select`, `groupBy`, `orderBy` etc we can pass column names as strings.

```
// to use operators such as $ in place of functions like col
import spark.implicits._

//But I prefer to use col()
```

```
scala> employeesDF.select(col("first_name"), col("last_name")).show
+----------+---------+
|first_name|last_name|
+----------+---------+
|     Scott|    Tiger|
|     Henry|     Ford|
|      Nick|   Junior|
|      Bill|    Gomes|
+----------+---------+
```

```
// Alternative by passing column names as strings.
scala> employeesDF.select("first_name", "last_name").show
+----------+---------+
|first_name|last_name|
+----------+---------+
|     Scott|    Tiger|
|     Henry|     Ford|
|      Nick|   Junior|
|      Bill|    Gomes|
+----------+---------+
```

- If there are no transformations on any column in any function then we should be able to pass all column names as strings.
- If not we need to pass all columns as type column by using col function or its shorthand operator $.

```
scala> employeesDF.groupBy("nationality").count.show
+--------------+-----+
|   nationality|count|
+--------------+-----+
|         India|    1|
|united KINGDOM|    1|
| united states|    1|
|     AUSTRALIA|    1|
+--------------+-----+
scala> employeesDF.groupBy(upper("nationality")).count.show
<console>:26: error: type mismatch;
 found   : String("nationality")
 required: org.apache.spark.sql.Column
       employeesDF.groupBy(upper("nationality")).count.show

scala> employeesDF.groupBy(upper($"nationality")).count.show
+------------------+-----+
|upper(nationality)|count|
+------------------+-----+
|    UNITED KINGDOM|    1|
|             INDIA|    1|
|         AUSTRALIA|    1|
|     UNITED STATES|    1|
+------------------+-----+

scala> employeesDF.orderBy("salary").show
+-----------+----------+---------+------+--------------+----------------+-----------+
|employee_id|first_name|last_name|salary|   nationality|    phone_number|        ssn|
+-----------+----------+---------+------+--------------+----------------+-----------+
|          3|      Nick|   Junior| 750.0|united KINGDOM|+44 111 111 1111|222 33 4444|
|          1|     Scott|    Tiger|1000.0| united states| +1 123 456 7890|123 45 6789|
|          2|     Henry|     Ford|1250.0|         India|+91 234 567 8901|456 78 9123|
|          4|      Bill|    Gomes|1500.0|     AUSTRALIA|+61 987 654 3210|789 12 6118|
+-----------+----------+---------+------+--------------+----------------+-----------+
```

- `col` is the function which will convert column name from string type to **Column** type. We can also refer column names as **Column** type using Data Frame name.

```
// Alternative - we can also refer column names using Data Frame like this
employeesDF.orderBy(upper(employeesDF("nationality"))).show
```

- Sometimes, we want to add a literal to the column values. For example, we might want to concatenate first_name and last_name with separated by comma and space in between.

```
//this will fail ❌
employeesDF.select(concat($"first_name", " ", $"last_name")).show

// this will work ✅
employeesDF.select(concat($"first_name", lit(" "), $"last_name")).show
+--------------------------------+
|concat(first_name,  , last_name)|
+--------------------------------+
|                     Scott Tiger|
|                      Henry Ford|
|                     Nick Junior|
|                      Bill Gomes|
+--------------------------------+
employeesDF.select(
                    concat($"first_name", lit(" "), $"last_name").alias("full_name")
                   ).show
+-----------+
|  full_name|
+-----------+
|Scott Tiger|
| Henry Ford|
|Nick Junior|
| Bill Gomes|
+-----------+
```

## String Manipulation - Case Conversion and Length

Let us check the functions which can convert the case of the column values which are of type string and also get the length.

- Convert all the alphabetic characters in a string to **uppercase** - `upper`
- Convert all the alphabetic characters in a string to **lowercase** - `lower`
- Convert first character in a string to **uppercase** - `initcap`
- Get **number of characters in a string** - `length`
- All the 4 functions take column type argument.

### Tasks

Let us perform tasks to understand the behavior of case conversion functions and length.

- Use employees data and create a Data Frame.
- Apply all 4 functions on **nationality** and see the results.

```
scala> employeesDF.select("employee_id", "nationality").show
+-----------+--------------+
|employee_id|   nationality|
+-----------+--------------+
|          1| united states|
|          2|         India|
|          3|united KINGDOM|
|          4|     AUSTRALIA|
+-----------+--------------+

scala> employeesDF.select("employee_id", "nationality").
            withColumn("nationality_upper", upper(col("nationality"))).
            show
+-----------+--------------+-----------------+
|employee_id|   nationality|nationality_upper|
+-----------+--------------+-----------------+
|          1| united states|    UNITED STATES|
|          2|         India|            INDIA|
|          3|united KINGDOM|   UNITED KINGDOM|
|          4|     AUSTRALIA|        AUSTRALIA|
+-----------+--------------+-----------------+
scala> employeesDF.select("employee_id", "nationality").
            withColumn("nationality_upper", upper(col("nationality"))).
            withColumn("nationality_lower", lower(col("nationality"))).
            withColumn("nationality_initcap", initcap(col("nationality"))).
            withColumn("nationality_length", length(col("nationality"))).
            show
+-----------+--------------+-----------------+-----------------+-------------------+------------------+
|employee_id|   nationality|nationality_upper|nationality_lower|nationality_initcap|nationality_length|
+-----------+--------------+-----------------+-----------------+-------------------+------------------+
|          1| united states|    UNITED STATES|    united states|      United States|                13|
|          2|         India|            INDIA|            india|              India|                 5|
|          3|united KINGDOM|   UNITED KINGDOM|   united kingdom|     United Kingdom|                14|
|          4|     AUSTRALIA|        AUSTRALIA|        australia|          Australia|                 9|
+-----------+--------------+-----------------+-----------------+-------------------+------------------+
```

## String Manipulation - substring

Let us understand how we can extract substrings using function `substring`.

- If we are processing **fixed length columns** then we use `substring` to extract the information.
- Here are some of the examples for **fixed length columns** and the use cases for which we typically extract information..
- 9 Digit Social Security Number. We typically extract last 4 digits and provide it to the tele verification applications..
- 16 Digit Credit Card Number. We typically use first 4 digit number to identify Credit Card Provider and last 4 digits for the purpose of tele verification.
- Data coming from MainFrames systems are quite often fixed length. We might have to extract the information and store in multiple columns.
- `substring` function takes 3 arguments, **column**, **position**, **length**. We can also provide position from the end by passing negative value.

```
scala> val s="Hello World"
s: String = Hello World

scala> s.substring(0,5)
res23: String = Hello

scala> s.substring(1,4)
res24: String = ell
```

### Tasks

Let us perform few tasks to extract information from fixed length strings.

- Create a list for employees with name, ssn and phone_number.
- SSN Format **3 2 4** - Fixed Length with 9 digits
- Phone Number Format - Country Code is variable and remaining phone number have 10 digits:
- Country Code - one to 3 digits
- Area Code - 3 digits
- Phone Number Prefix - 3 digits
- Phone Number Remaining - 4 digits
- All the 4 parts are separated by spaces
- Create a Dataframe with column names name, ssn and phone_number
- Extract last 4 digits from the phone number.
- Extract last 4 digits from SSN.

```
val employees = List((1, "Scott", "Tiger", 1000.0,
                      "united states", "+1 123 456 7890", "123 45 6789"
                     ),
                     (2, "Henry", "Ford", 1250.0,
                      "India", "+91 234 567 8901", "456 78 9123"
                     ),
                     (3, "Nick", "Junior", 750.0,
                      "united KINGDOM", "+44 111 111 1111", "222 33 4444"
                     ),
                     (4, "Bill", "Gomes", 1500.0,
                      "AUSTRALIA", "+61 987 654 3210", "789 12 6118"
                     )
                    )
val employeesDF = employees.
    toDF("employee_id", "first_name",
         "last_name", "salary",
         "nationality", "phone_number",
         "ssn"
        )
```

- lets get the parameter that matter to us

```
scala>  employeesDF.select("employee_id", "phone_number", "ssn").
                    show

+-----------+----------------+-----------+
|employee_id|    phone_number|        ssn|
+-----------+----------------+-----------+
|          1| +1 123 456 7890|123 45 6789|
|          2|+91 234 567 8901|456 78 9123|
|          3|+44 111 111 1111|222 33 4444|
|          4|+61 987 654 3210|789 12 6118|
+-----------+----------------+-----------+

employeesDF.select($"employee_id", $"phone_number", $"ssn").
            withColumn("phone_last4", substring($"phone_number", -4, 4).cast("int")).
             withColumn("ssn_last4", substring($"ssn", -4, 4).cast("int")).
            show

+-----------+----------------+-----------+-----------+---------+
|employee_id|    phone_number|        ssn|phone_last4|ssn_last4|
+-----------+----------------+-----------+-----------+---------+
|          1| +1 123 456 7890|123 45 6789|       7890|     6789|
|          2|+91 234 567 8901|456 78 9123|       8901|     9123|
|          3|+44 111 111 1111|222 33 4444|       1111|     4444|
|          4|+61 987 654 3210|789 12 6118|       3210|     6118|
+-----------+----------------+-----------+-----------+---------+

// With only select
employeesDF.select($"employee_id", $"phone_number", $"ssn",
                    substring($"phone_number", -4, 4).cast("int").alias("phone_last4"),
                    substring($"ssn", -4, 4).cast("int").alias("ssn_last4")).
            show

+-----------+----------------+-----------+-----------+---------+
|employee_id|    phone_number|        ssn|phone_last4|ssn_last4|
+-----------+----------------+-----------+-----------+---------+
|          1| +1 123 456 7890|123 45 6789|       7890|     6789|
|          2|+91 234 567 8901|456 78 9123|       8901|     9123|
|          3|+44 111 111 1111|222 33 4444|       1111|     4444|
|          4|+61 987 654 3210|789 12 6118|       3210|     6118|
+-----------+----------------+-----------+-----------+---------+

```

## String Manipulation - split

Let us understand how we can extract substrings using `split`.

- If we are processing **variable length columns** with **delimiter** then we use `split` to extract the information.
- Here are some of the examples for **variable length columns** and the use cases for which we typically extract information.
- Address where we store House Number, Street Name, City, State and Zip Code comma separated. We might want to extract City and State for demographics reports.
- `split` takes 2 arguments, **column** and **delimiter**.
- `split` convert each string into array and we can access the elements using index.

```
employeesDF.
    select("employee_id", "phone_number", "ssn").
    withColumn("area_code", split($"phone_number", " ")(1).cast("int")).
    withColumn("phone_last4", split($"phone_number", " ")(3).cast("int")).
    withColumn("ssn_last4", split($"ssn", " ")(2).cast("int")).
    show
+-----------+----------------+-----------+---------+-----------+---------+
|employee_id|    phone_number|        ssn|area_code|phone_last4|ssn_last4|
+-----------+----------------+-----------+---------+-----------+---------+
|          1| +1 123 456 7890|123 45 6789|      123|       7890|     6789|
|          2|+91 234 567 8901|456 78 9123|      234|       8901|     9123|
|          3|+44 111 111 1111|222 33 4444|      111|       1111|     4444|
|          4|+61 987 654 3210|789 12 6118|      987|       3210|     6118|
+-----------+----------------+-----------+---------+-----------+---------+
```

- Most of the problems can be solved either by using `substring` or `split`.

## String Manipulation - Concatenating of Strings

Let us understand how to concatenate strings using `concat` function.

- We can pass a variable number of strings to `concat` function.
- It will return one string concatenating all the strings.
- If we have to concatenate literal in between the strings we have to use `lit` function.

**THIS HAS BEEN SEEN PREVIOUSLY**

```
scala> employeesDF.select(concat($"first_name", $"last_name")).show
+-----------------------------+
|concat(first_name, last_name)|
+-----------------------------+
|                   ScottTiger|
|                    HenryFord|
|                   NickJunior|
|                    BillGomes|
+-----------------------------+

scala> employeesDF.select(concat($"first_name", $"last_name").alias("full_name")).show
+----------+
| full_name|
+----------+
|ScottTiger|
| HenryFord|
|NickJunior|
| BillGomes|
+----------+

scala> employeesDF.select(concat($"first_name", lit(", "), $"last_name").alias("full_name")).show
+------------+
|   full_name|
+------------+
|Scott, Tiger|
| Henry, Ford|
|Nick, Junior|
| Bill, Gomes|
+------------+

scala> employeesDF.withColumn("full_name", concat($"first_name", lit(", "), $"last_name")).show
+-----------+----------+---------+------+--------------+----------------+-----------+------------+
|employee_id|first_name|last_name|salary|   nationality|    phone_number|        ssn|   full_name|
+-----------+----------+---------+------+--------------+----------------+-----------+------------+
|          1|     Scott|    Tiger|1000.0| united states| +1 123 456 7890|123 45 6789|Scott, Tiger|
|          2|     Henry|     Ford|1250.0|         India|+91 234 567 8901|456 78 9123| Henry, Ford|
|          3|      Nick|   Junior| 750.0|united KINGDOM|+44 111 111 1111|222 33 4444|Nick, Junior|
|          4|      Bill|    Gomes|1500.0|     AUSTRALIA|+61 987 654 3210|789 12 6118| Bill, Gomes|
+-----------+----------+---------+------+--------------+----------------+-----------+------------+

```

## String Manipulation - Padding

Let us understand how to pad characters at the beginning or at the end of strings.

- We typically pad characters to build fixed length values or records.
- Fixed length values or records are extensively used in Mainframes based systems.
- Length of each and every field in fixed length records is predetermined and if the value of the field is less than the predetermined length then we pad with a standard character.
- In terms of numeric fields we pad with zero on the leading or left side. For non numeric fields, we pad with some standard character on trailing or right side.
- We use `lpad` to pad a string with a specific character on leading or left side and `rpad` to pad on trailing or right side.
- Both lpad and rpad, take 3 arguments - column or expression, desired length and the character need to be padded.

### Task

- Use **pad** functions to convert each of the field into fixed length and concatenate. Here are the details for each of the fields.
- Length of the employee_id should be 5 characters and should be padded with zero.
- Length of first_name and last_name should be 10 characters and should be padded with - on the right side.
- Length of salary should be 10 characters and should be padded with zero.
- Length of the nationality should be 15 characters and should be padded with - on the right side.
- Length of the phone_number should be 17 characters and should be padded with - on the right side.
- Length of the ssn can be left as is. It is 11 characters.

- Create a new Dataframe **empFixedDF** with column name **employee**. Preview the data by disabling truncate.

### Course example:

```
val empFixedDF = employeesDF.select(
    concat(
        lpad($"employee_id", 5, "0"),
        rpad($"first_name", 10, "-"),
        rpad($"last_name", 10, "-"),
        lpad($"salary", 10, "0"),
        rpad($"nationality", 15, "-"),
        rpad($"phone_number", 17, "-"),
        $"ssn"
    ).alias("employee")
)

scala> empFixedDF.show(false)
+------------------------------------------------------------------------------+
|employee                                                                      |
+------------------------------------------------------------------------------+
|00001Scott-----Tiger-----00001000.0united states--+1 123 456 7890--123 45 6789|
|00002Henry-----Ford------00001250.0India----------+91 234 567 8901-456 78 9123|
|00003Nick------Junior----00000750.0united KINGDOM-+44 111 111 1111-222 33 4444|
|00004Bill------Gomes-----00001500.0AUSTRALIA------+61 987 654 3210-789 12 6118|
+------------------------------------------------------------------------------+
```

### Mine

```
scala> employeesDF.select(
     |             lpad($"employee_id", 5, "0").alias("employee_id"),
     |             rpad($"first_name", 10, "-").alias("first_name"),
     |             rpad($"last_name", 10, "-").alias("last_name"),
     |             lpad($"salary", 10, "0").alias("salary"),
     |             rpad($"nationality", 15, "-").alias("nationality"),
     |             rpad($"phone_number", 17, "-").alias("phone_number"),
     |             replace($"ssn", " ", "")
     |             ).
     |             show
+-----------+----------+----------+----------+---------------+-----------------+-----------+
|employee_id|first_name| last_name|    salary|    nationality|     phone_number|        ssn|
+-----------+----------+----------+----------+---------------+-----------------+-----------+
|      00001|Scott-----|Tiger-----|00001000.0|united states--|+1 123 456 7890--|123 45 6789|
|      00002|Henry-----|Ford------|00001250.0|India----------|+91 234 567 8901-|456 78 9123|
|      00003|Nick------|Junior----|00000750.0|united KINGDOM-|+44 111 111 1111-|222 33 4444|
|      00004|Bill------|Gomes-----|00001500.0|AUSTRALIA------|+61 987 654 3210-|789 12 6118|
+-----------+----------+----------+----------+---------------+-----------------+-----------+
```

## String Manipulation - Trimming

Let us understand how to trim unwanted leading and trailing characters around a string.

- We typically use trimming to remove unnecessary characters from fixed length records.
- Fixed length records are extensively used in Mainframes and we might have to process it using Spark.
- As part of processing we might want to remove leading or trailing characters such as 0 in case of numeric types and space or some standard character in case of alphanumeric types.
- As of now Spark trim functions take the column as argument and remove leading or trailing spaces.
- Trim spaces towards left - `ltrim`
- Trim spaces towards right - `rtrim`
- Trim spaces on both sides - `trim`
- We can also trim other characters than spaces using these trim functions.

```
df.withColumn("ltrim", ltrim(col("dummy"))).
    withColumn("rtrim", rtrim(rtrim(col("dummy")), ".")).
    withColumn("trim", trim(trim(col("dummy")), ".")).
    show()
+-------------+----------+--------+-----+
|        dummy|     ltrim|   rtrim| trim|
+-------------+----------+--------+-----+
|   Hello.    |Hello.    |   Hello|Hello|
+-------------+----------+--------+-----+
```

## Date and Time - Overview

Let us get an overview about Date and Time using available functions.

- We can use `current_date` to get today’s server date.
- Date will be returned using **yyyy-MM-dd** format.
- We can use `current_timestamp` to get current server time.
- Timestamp will be returned using **yyyy-MM-dd HH:mm:ss.SSS** format.
- Hours will be by default in 24 hour format.

## Date and Time - Arithmetic

Let us perform Date and Time Arithmetic using relevant functions.

- Adding days to a date or timestamp - `date_add`
- Subtracting days from a date or timestamp - `date_sub`
- Getting difference between 2 dates or timestamps - `datediff`
- Getting a number of months between 2 dates or timestamps - `months_between`
- Adding months to a date or timestamp - `add_months`
- Getting next day from a given date - `next_day`
- All the functions are self explanatory. We can apply these on standard date or timestamp. All the functions return date even when applied on timestamp field.

### Tasks

Let us perform some tasks related to date arithmetic.

- Get help on each and every function first and understand what all arguments need to be passed.
- Create a Dataframe by name datetimesDF with columns date and time.

```
val datetimes = List(("2014-02-28", "2014-02-28 10:00:00.123"),
                     ("2016-02-29", "2016-02-29 08:08:08.999"),
                     ("2017-10-31", "2017-12-31 11:59:59.123"),
                     ("2019-11-30", "2019-08-31 00:00:00.000")
                    )
val datetimesDF = datetimes.toDF("date", "time")

scala> datetimesDF.show(false)
+----------+-----------------------+
|date      |time                   |
+----------+-----------------------+
|2014-02-28|2014-02-28 10:00:00.123|
|2016-02-29|2016-02-29 08:08:08.999|
|2017-10-31|2017-12-31 11:59:59.123|
|2019-11-30|2019-08-31 00:00:00.000|
+----------+-----------------------+

```

- Add 10 days to both date and time values.
- Subtract 10 days from both date and time values.

```
scala> datetimesDF.
     |     withColumn("date_add_date", date_add($"date", 10)).
     |     withColumn("date_add_time", date_add($"time", 10)).
     |     withColumn("date_sub_date", date_sub($"date", 10)).
     |     withColumn("date_sub_time", date_sub($"time", 10)).
     |     show(false)
+----------+-----------------------+-------------+-------------+-------------+-------------+
|date      |time                   |date_add_date|date_add_time|date_sub_date|date_sub_time|
+----------+-----------------------+-------------+-------------+-------------+-------------+
|2014-02-28|2014-02-28 10:00:00.123|2014-03-10   |2014-03-10   |2014-02-18   |2014-02-18   |
|2016-02-29|2016-02-29 08:08:08.999|2016-03-10   |2016-03-10   |2016-02-19   |2016-02-19   |
|2017-10-31|2017-12-31 11:59:59.123|2017-11-10   |2018-01-10   |2017-10-21   |2017-12-21   |
|2019-11-30|2019-08-31 00:00:00.000|2019-12-10   |2019-09-10   |2019-11-20   |2019-08-21   |
+----------+-----------------------+-------------+-------------+-------------+-------------+
```

- Get the difference between current_date and date values as well as current_timestamp and time values.

```
scala> datetimesDF.
     |     withColumn("datediff_date", datediff(current_date, $"date")).
     |     withColumn("datediff_time", datediff(current_timestamp, $"time")).
     |     show(false)
+----------+-----------------------+-------------+-------------+
|date      |time                   |datediff_date|datediff_time|
+----------+-----------------------+-------------+-------------+
|2014-02-28|2014-02-28 10:00:00.123|2671         |2671         |
|2016-02-29|2016-02-29 08:08:08.999|1940         |1940         |
|2017-10-31|2017-12-31 11:59:59.123|1330         |1269         |
|2019-11-30|2019-08-31 00:00:00.000|570          |661          |
+----------+-----------------------+-------------+-------------+
```

- Get the number of months between current_date and date values as well as current_timestamp and time values.
- Add 3 months to both date values as well as time values.

```
scala> datetimesDF.
     |     withColumn("months_between_date", round(months_between(current_date, $"date"), 2)).
     |     withColumn("months_between_time", round(months_between(current_timestamp, $"time"), 2)).
     |     withColumn("add_months_date", add_months($"date", 3)).
     |     withColumn("add_months_time", add_months($"time", 3)).
     |     show(false)
+----------+-----------------------+-------------------+-------------------+---------------+---------------+
|date      |time                   |months_between_date|months_between_time|add_months_date|add_months_time|
+----------+-----------------------+-------------------+-------------------+---------------+---------------+
|2014-02-28|2014-02-28 10:00:00.123|87.81              |87.82              |2014-05-31     |2014-05-31     |
|2016-02-29|2016-02-29 08:08:08.999|63.77              |63.79              |2016-05-31     |2016-05-31     |
|2017-10-31|2017-12-31 11:59:59.123|43.71              |41.72              |2018-01-31     |2018-03-31     |
|2019-11-30|2019-08-31 00:00:00.000|18.74              |21.74              |2020-02-29     |2019-11-30     |
+----------+-----------------------+-------------------+-------------------+---------------+---------------+
```

## Date and Time - trunc and date_trunc

In Data Warehousing we quite often run to date reports such as week to date, month to date, year to date etc.

- We can use `trunc` or `date_trunc` for the same to get the beginning date of the week, month, current year etc by passing date or timestamp to it.
- We can use `trunc` to get beginning date of the month or year by passing date or timestamp to it - for example `trunc(current_date(), "MM")` will give the first of the current month.
- We can use `date_trunc` to get beginning date of the month or year as well as beginning time of the day or hour by passing timestamp to it.
- Get beginning date based on month - `date_trunc("MM", current_timestamp())`
- Get beginning time based on day - `date_trunc("DAY", current_timestamp())`

### Tasks

Let us perform few tasks to understand trunc and date_trunc in detail.

- Create a Dataframe by name datetimesDF with columns date and time.

```
val datetimes = List(("2014-02-28", "2014-02-28 10:00:00.123"),
                     ("2016-02-29", "2016-02-29 08:08:08.999"),
                     ("2017-10-31", "2017-12-31 11:59:59.123"),
                     ("2019-11-30", "2019-08-31 00:00:00.000")
                    )

val datetimesDF = datetimes.toDF("date", "time")

scala> datetimesDF.show(truncate=false)
+----------+-----------------------+
|date      |time                   |
+----------+-----------------------+
|2014-02-28|2014-02-28 10:00:00.123|
|2016-02-29|2016-02-29 08:08:08.999|
|2017-10-31|2017-12-31 11:59:59.123|
|2019-11-30|2019-08-31 00:00:00.000|
+----------+-----------------------+
```

- Get beginning month date using date field and beginning year date using time field.

```
scala> datetimesDF.
     |             withColumn("date_trunc", trunc($"date", "MM")).
     |             withColumn("time_trunc", trunc($"time", "yyyy")).
     |             show
+----------+--------------------+----------+----------+
|      date|                time|date_trunc|time_trunc|
+----------+--------------------+----------+----------+
|2014-02-28|2014-02-28 10:00:...|2014-02-01|2014-01-01|
|2016-02-29|2016-02-29 08:08:...|2016-02-01|2016-01-01|
|2017-10-31|2017-12-31 11:59:...|2017-10-01|2017-01-01|
|2019-11-30|2019-08-31 00:00:...|2019-11-01|2019-01-01|
+----------+--------------------+----------+----------+
```

- Get beginning hour time using date and time field.

```
scala> datetimesDF.
            withColumn("date_dt", date_trunc("HOUR", $"date")).
            withColumn("time_dt", date_trunc("DAY", $"time")).
            show
+----------+--------------------+-------------------+-------------------+
|      date|                time|            date_dt|            time_dt|
+----------+--------------------+-------------------+-------------------+
|2014-02-28|2014-02-28 10:00:...|2014-02-28 00:00:00|2014-02-28 10:00:00|
|2016-02-29|2016-02-29 08:08:...|2016-02-29 00:00:00|2016-02-29 08:00:00|
|2017-10-31|2017-12-31 11:59:...|2017-10-31 00:00:00|2017-12-31 11:00:00|
|2019-11-30|2019-08-31 00:00:...|2019-11-30 00:00:00|2019-08-31 00:00:00|
+----------+--------------------+-------------------+-------------------+
```

## Date and Time - Extracting Information

Let us understand how to extract information from dates or times using functions.

- We can use date_format to extract the required information in a desired format from date or timestamp.
- There are also specific functions to extract year, month, day with in a week, a day with in a month, day with in a year etc.

### Tasks

Let us perform few tasks to extract the information we need from date or timestamp.

- Get year from fields date and time.

```
scala> datetimesDF.
     |     withColumn("date_year", year($"date")).
     |     withColumn("time_year", year($"time")).
     |     show(false)
+----------+-----------------------+---------+---------+
|date      |time                   |date_year|time_year|
+----------+-----------------------+---------+---------+
|2014-02-28|2014-02-28 10:00:00.123|2014     |2014     |
|2016-02-29|2016-02-29 08:08:08.999|2016     |2016     |
|2017-10-31|2017-12-31 11:59:59.123|2017     |2017     |
|2019-11-30|2019-08-31 00:00:00.000|2019     |2019     |
+----------+-----------------------+---------+---------+
```

- Get one or two digit month from fields date and time.

```
scala> datetimesDF.
     |      |     withColumn("date_year", month($"date")).
     |      |     withColumn("time_year", month($"time")).
     |      |     show(false)
+----------+-----------------------+---------+---------+
|date      |time                   |date_year|time_year|
+----------+-----------------------+---------+---------+
|2014-02-28|2014-02-28 10:00:00.123|2        |2        |
|2016-02-29|2016-02-29 08:08:08.999|2        |2        |
|2017-10-31|2017-12-31 11:59:59.123|10       |12       |
|2019-11-30|2019-08-31 00:00:00.000|11       |8        |
+----------+-----------------------+---------+---------+
```

- Get year and month in yyyyMM format from date and time.ç

```
scala> datetimesDF.
     |             withColumn("date_ym", date_format($"date", "yyyyMM")).
     |             show
+----------+--------------------+-------+
|      date|                time|date_ym|
+----------+--------------------+-------+
|2014-02-28|2014-02-28 10:00:...| 201402|
|2016-02-29|2016-02-29 08:08:...| 201602|
|2017-10-31|2017-12-31 11:59:...| 201710|
|2019-11-30|2019-08-31 00:00:...| 201911|
+----------+--------------------+-------+
```

- Get day with in a week, a day with in a month and day within a year from date and time.

```
scala> datetimesDF.
     |         withColumn("date_of_week", dayofweek($"date")).
     |         withColumn("date_of_month", dayofmonth($"date")).
     |         withColumn("date_of_year", dayofyear($"date")).
     |         withColumn("time_of_week", dayofweek($"time")).
     |         withColumn("time_of_month", dayofmonth($"time")).
     |         withColumn("time_of_year", dayofyear($"time")).
     |         show(false)
+----------+-----------------------+------------+-------------+------------+------------+-------------+------------+
|date      |time                   |date_of_week|date_of_month|date_of_year|time_of_week|time_of_month|time_of_year|
+----------+-----------------------+------------+-------------+------------+------------+-------------+------------+
|2014-02-28|2014-02-28 10:00:00.123|6           |28           |59          |6           |28           |59          |
|2016-02-29|2016-02-29 08:08:08.999|2           |29           |60          |2           |29           |60          |
|2017-10-31|2017-12-31 11:59:59.123|3           |31           |304         |1           |31           |365         |
|2019-11-30|2019-08-31 00:00:00.000|7           |30           |334         |7           |31           |243         |
+----------+-----------------------+------------+-------------+------------+------------+-------------+------------+
```

- Get the information from time in yyyyMMddHHmmss format.

```
scala> datetimesDF.
     |         withColumn("time_ts", date_format($"time", "yyyy MM dd HH:mm:ss")).
     |         withColumn("time_ts2", date_format($"time", "yyyy MMM dd HH:mm:ss")).
     |         withColumn("time_ts3", date_format($"time", "yyyy MMMM dd HH:mm:ss")).
     |         show(false)
+----------+-----------------------+-------------------+--------------------+-------------------------+
|date      |time                   |time_ts            |time_ts2            |time_ts3                 |
+----------+-----------------------+-------------------+--------------------+-------------------------+
|2014-02-28|2014-02-28 10:00:00.123|2014 02 28 10:00:00|2014 Feb 28 10:00:00|2014 February 28 10:00:00|
|2016-02-29|2016-02-29 08:08:08.999|2016 02 29 08:08:08|2016 Feb 29 08:08:08|2016 February 29 08:08:08|
|2017-10-31|2017-12-31 11:59:59.123|2017 12 31 11:59:59|2017 Dec 31 11:59:59|2017 December 31 11:59:59|
|2019-11-30|2019-08-31 00:00:00.000|2019 08 31 00:00:00|2019 Aug 31 00:00:00|2019 August 31 00:00:00  |
+----------+-----------------------+-------------------+--------------------+-------------------------+

scala> datetimesDF.
     |     withColumn("date_us", date_format($"date", "MM-dd-yyyy")).
     |     withColumn("date_mx", date_format($"date", "dd-MM-yyyy")).
     |     show(false)
+----------+-----------------------+----------+----------+
|date      |time                   |date_us   |date_mx   |
+----------+-----------------------+----------+----------+
|2014-02-28|2014-02-28 10:00:00.123|02-28-2014|28-02-2014|
|2016-02-29|2016-02-29 08:08:08.999|02-29-2016|29-02-2016|
|2017-10-31|2017-12-31 11:59:59.123|10-31-2017|31-10-2017|
|2019-11-30|2019-08-31 00:00:00.000|11-30-2019|30-11-2019|
+----------+-----------------------+----------+----------+
```

## Dealing with Unix Timestamp

Let us understand how to deal with Unix Timestamp in Spark.

- It is an integer and started from January 1st 1970 Midnight UTC.
- Beginning time is also known as epoch and is incremented by 1 every second.
- We can convert Unix Timestamp to regular date or timestamp and vice versa.
- We can use `unix_timestamp` to convert regular date or timestamp to a unix timestamp value. For example `unix_timestamp(lit("2019-11-19 00:00:00"))`
- We can use `from_unixtime` to convert unix timestamp to regular date or timestamp. For example `from_unixtime(lit(1574101800))`
- We can also pass format to both the functions.

## Conclusion

As part of this module we have gone through list of functions that can be applied on top of columns for row level transformations.

- There are approximately 300 pre-defined functions.
- Functions can be broadly categorized into String Manipulation Functions, Date Manipulation Functions, Numeric Functions etc.
- Typically when we read data from source, we get data in the form of strings and we need to apply functions to apply standardization rules, data type conversion, transformation rules etc.
- Most of these functions can be used while projection using `select`, `selectExpr`, `withColumn` etc as well as part of `filter` or `where`, `groupBy`, `orderBy` or `sort` etc.
- For `selectExpr` we need to use the functions using SQL Style syntax.
- There are special functions such as `col` and `lit`. `col` is used to pass column names as column type for some of the functions while `lit` is used to pass literals as values as part of expressions (eg: `concat($"first_name", lit(", "), $"last_name")`).
