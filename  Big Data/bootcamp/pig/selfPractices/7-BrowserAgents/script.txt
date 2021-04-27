--Browser Agents
data = load '../data/apache_logs.log' using PigStorage('"') as (f0,f1,f2,f3,f4,f5);
browser = foreach data generate f5;
dump browser; 
browserD = distinct browser;
Store browserD into 'Browsers';
dump browserD; 
