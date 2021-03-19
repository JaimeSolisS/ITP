#Write a shell script where you read a certain set of commands from a file and execute them, using loops

#source commands.txt
i=1
for line in `cat p8.txt`
do 
    echo "command $i: "
    $line; 
    i=`expr $i + 1` 
done