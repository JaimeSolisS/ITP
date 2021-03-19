#Using loops while, for, until and if/else conditions:
#Write shell script for generating fibonacci sequence
echo How many elements in the sequence
read elements
if [ $elements -eq 0 ]
then 
    echo 0
elif  [ $elements -eq 1 ]
then
    echo 0, 1
else 
    x=0
    y=1
    counter=2

    echo "0, 1, \c"
    while [ $counter -lt $elements ]
    do 
        fib=`expr $x + $y`
        echo "$fib, \c"
        x=$y
        y=$fib
        counter=`expr $counter + 1`
    done 
fi
