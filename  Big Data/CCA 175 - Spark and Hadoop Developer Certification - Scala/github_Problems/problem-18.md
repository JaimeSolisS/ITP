## Problem:

- Read data from hive and perform transformation and save it back in HDFS
- Read table orders
- Produce output in this format (2 fields) , sort by order count in descending

            ORDER_STATUS      ORDER_COUNT
            COMPLETE          54
            CANCELLED         89
            INPROGRESS        23

- Save above output in avro snappy compression in avro format in hdfs \
  **location:** /user/yourusername/problem18

### Solution

### Deploy Mode

```
spark2-shell --master yarn --packages com.databricks:spark-avro_2.11:4.0.0
```

### DB

```
spark.sql("select current_database()").show
+------------------+
|current_database()|
+------------------+
|           default|
+------------------+

spark.sql("show databases").show
+--------------------+
|        databaseName|
+--------------------+
|             adhudla|
|              cimran|
|      cimran_archive|
|         cimran_code|
|          cimran_etl|
|           course_db|
|         dashboarddb|
|        dashboarddb1|
|           db_stocks|
|          deequ_test|
|             default|
|         development|
|               docs1|
|         employee_db|
|etarun_retail_db_txt|
|              eureka|
|            eventsim|
|            first_db|
|      hadoopexamtest|
|         impala_kudu|
|                iris|
|         kafkasample|
| mitchell_repository|
|          mitchelldb|
|           movielens|
|               muthu|
|              poc_db|
|               pocdb|
|            poshamza|
|         prashanthdb|
|          pratap_mix|
|       pratapgitrepo|
|        ps_retail_db|
|    ps_retail_db_orc|
|      ps_retail_demo|
|              psr_hr|
|       psr_retail_db|
|      psri_retail_db|
|             ram_orc|
|           reshma_db|
|          retail_db1|
|             retails|
|       retailshop_db|
|             rp_test|
|        rramani_demo|
|        rramani_nyse|
|      rramani_nysedb|
|     rramanii_retail|
|         sample_ruby|
|                san1|
|              sandy1|
|         schimata_db|
|        shanthy_test|
|       sri_course_db|
|    ssharumathi_demo|
|ssharumathi_retai...|
|ssharumathi_retai...|
|     streamsetssqoop|
|                test|
|              test23|
|        test_vino_ui|
|            ui_table|
|       ui_table_vino|
|          university|
|            usertest|
|    vidya1_course_db|
|     vidya_course_db|
|          vidya_demo|
|   vidya_employee_db|
|     vidya_stocks_db|
|            vidyadb1|
|             ybdata1|
|     ybkeralatrainee|
|              ybkudu|
|           ybtrainee|
|     ybtraineekerala|
+--------------------+

spark.sql("use ybtrainee")

spark.sql("select current_database()").show
+------------------+
|current_database()|
+------------------+
|         ybtrainee|
+------------------+

spark.sql("show tables").show
+---------+--------------------+-----------+
| database|           tableName|isTemporary|
+---------+--------------------+-----------+
|ybtrainee|aarbikesharingsfotbl|      false|
|ybtrainee|   aarbikesharingtbl|      false|
|ybtrainee|         aaremployee|      false|
|ybtrainee|              aartbl|      false|
|ybtrainee|        artists_hive|      false|
|ybtrainee|          avro_test1|      false|
|ybtrainee|          avro_test2|      false|
|ybtrainee|            avrotest|      false|
|ybtrainee|          bevbrancha|      false|
|ybtrainee|          bevbranchb|      false|
|ybtrainee|          bevbranchc|      false|
|ybtrainee|           bevcounta|      false|
|ybtrainee|           bevcountb|      false|
|ybtrainee|           bevcountc|      false|
|ybtrainee|bike_sharing_stat...|      false|
|ybtrainee|bike_sharing_station|      false|
|ybtrainee|bike_sharing_stat...|      false|
|ybtrainee|          bike_sspoc|      false|
|ybtrainee|      bucket_student|      false|
|ybtrainee|bucketed_partitio...|      false|
+---------+--------------------+-----------+
only showing top 20 rows
```

### Read table

```
val orders = spark.sql("SELECT * from orders")
```

### Agregate

```
val solution = orders.
groupBy("order_status").
agg(count(lit(1)).as("order_count")).
orderBy($"order_count".desc)

solution.show
```

### Export Solution

```
solution.
write.
mode("overwrite").
option("compression", "snappy").
format("com.databricks.spark.avro").
save("/user/jsolis/problem18/")
```

### Check Answer

spark.read.format("com.databricks.spark.avro").load("/user/jsolis/problem18/").show
