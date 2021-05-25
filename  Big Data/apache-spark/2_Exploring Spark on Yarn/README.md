# Spark on Yarn 

Yarb is the cluster management component of Hadoop. It manages memory and CPU cores/instances.  
- Specify YARN as the master when running `spark-shell` , along with executor amount and memory size for each executor:  
`spark-shell --master yarn --deploy-mode client --num-executors 4 --executor-memory 4GB` 

- Check aplication Id:  
`sc.applicationId`

- Spark application can be explore in application UI (default at http://localhost:4040)

- In application master, the application status can be tracked (Jobs, Stages, tasks, Storage, Environment, Executors, SQL)  

- Each execution in the spark shell is reflected in application UI