-- URI Accesed 
data = load '../data/apache_logs.log' using PigStorage('"') as (f0,f1,f2,f3,f4,f5);
uri = foreach data generate f1;
uriD = distinct uri; 
dump uriD; 
STORE uriD into 'URI Accessed'; 
