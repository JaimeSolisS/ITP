#Using loops while, for, until and if/else conditions:
#Write shell script for string palindrome
echo "write a string: \c"
read string 
palindrome=`expr $string | rev`

if [ $string = $palindrome ]
then 
    echo "It is a palindrome"
else 
    echo "It is NOT a palindrome"
fi
