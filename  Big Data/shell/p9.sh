#For the above script execute a script where you ignore the lines commented(or prefixed by #) and ignore Case sensitivity(use something that ignores the case of letters)

#text=`grep -i -v '^\#' p9.txt | tr A-Z a-z`
grep -i -v '^\#' p9.txt | tr A-Z a-z | while read -r line  
do
    echo "command $line: "
    $line
    echo " "
done


