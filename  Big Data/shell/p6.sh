#Using loops while, for, until and if/else conditions:
#Write shell script for finding armstrong numbers
echo "low num: \c"
read low
echo "top num: \c"
read top
echo "Armstrong Numbers: "
until [ $low -ge $top ]
do 
    sum=0
    num=$low
    for((;num>0; num /=10))
    do
        digit=`expr $num % 10`
        sum=`expr $sum + $digit \* $digit \* $digit`
    done

    if [ $sum -eq $low ]
    then 
        echo $low
    fi

    low=`expr $low + 1`
done