#Using loops while, for, until and if/else conditions:z
#Write shell script for generating prime numbers in a range
echo "low num: \c"
read low
echo "top num: \c"
read top
echo "Sequence: \c"
until [ $low -ge $top ]
do 
    div=2
    until [ $div -eq $low ]
    do 
        mod=`expr $low % $div`
        if [ $mod -eq 0 ]
        then 
            low=`expr $low + 1`
            div=2
        else 
            div=`expr $div + 1`
        fi
    done 
    echo "$low, \c"
    low=`expr $low + 1`
done

