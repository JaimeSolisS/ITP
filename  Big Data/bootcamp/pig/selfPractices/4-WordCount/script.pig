-- Count words in file

--load data
text = LOAD '../data/sherk.txt' USING TextLoader AS (line:CHARARRAY);

-- Clean the input by removing all punctuation and control characters and making everything lowercase
words = FOREACH text GENERATE FLATTEN(TOKENIZE(REPLACE(LOWER(TRIM(line)),'[\\p{Punct},\\p{Cntrl}]','')));

grpd = GROUP words BY $0;
cntd = FOREACH grpd GENERATE $0, COUNT($1);

-- Order the output by count with the highest counts first
list = ORDER cntd BY $1 DESC, $0 ASC;
DUMP list; 
STORE list into 'WordCount'; 
