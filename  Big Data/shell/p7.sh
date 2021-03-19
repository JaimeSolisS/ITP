#Find out a way where you can use substring like feature in bash scripting and use that.
#Example for this is print all the files with same extension O/P should look like
#.txt - one, two, three
#pdf - four, five. six
echo "what type of files do you want: \c"
read type
#find . -type f -name "*.$type"
#ls *.$type
#ls *.$type | sed 's/\.[a-z]*//g'
list=$(ls *.$type | sed 's/\.[a-z]*//g')
echo "$type - \c"
for i in $list
do echo "$i, \c"
done
echo "\b"