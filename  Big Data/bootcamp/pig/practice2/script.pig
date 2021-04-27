
log = LOAD 'excite-smallComplete.log' AS (user, timestamp, query); 
grpd = GROUP log BY user;
cntd = FOREACH grpd GENERATE group, COUNT(log) as cnt;
STORE cntd INTO 'output';
DUMP cntd;
fltrd = FILTER cntd BY cnt > 3;
STORE fltrd INTO 'output1';
DUMP fltrd; 
srtd = ORDER fltrd BY cnt desc;
STORE srtd INTO 'output2';
DUMP srtd;
