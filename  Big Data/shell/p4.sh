#Using loops while, for, until and if/else conditions:
#Write shell script for generating pascal triangle

echo "How many levels: \c"
read levels 
for (( i=0; i<$levels; i++ ))
do 
    for (( blanks=1; blanks<$levels-1; blanks++ ))
    do
        echo "*\c"
    done
    for((j = 0; j <= i; j++))
    do  
        if [ $j -eq 0 -o $i -eq 0 ]  
        then 
            coef=1   
        else
        #echo "flag \c"
        coef=`expr $coef \* \( $i - $j + 1 \) / $j`
        fi
    echo "$coef \c" 
    done 
    echo 
done 