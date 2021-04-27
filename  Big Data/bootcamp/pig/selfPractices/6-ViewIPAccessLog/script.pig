--View IP list from where access is made
data = load '../data/apache_logs.log' using PigStorage('-') as (f0,f1,f2,f3,f4);
ipList = foreach data generate f0;
ipDistinct = distinct ipList;
Store ipDistinct into 'IPs'
