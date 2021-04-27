-- load data 
daily = load '../data/NYSE_daily' as (exchange, symbol, date, open, high, low, close, volume, adj_close); 
divs  = load '../data/NYSE_dividends' as (exchange, symbol, date, dividends);
-- join
jnd   = join daily by symbol, divs by symbol;
-- Store jnd
STORE jnd INTO 'output';
-- join with multiple keys
jnd2= join daily by (symbol, date), divs by (symbol, date);
STORE jnd2 INTO 'output2';
-- See just a limited numer of results
first10 = limit jnd 10;
-- Print result
DUMP first10; 
