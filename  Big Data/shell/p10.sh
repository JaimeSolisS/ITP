#Find a file which has unseen line feeds or space feeds or tab feeds hidden in a line or in a word. Write a script where you eliminate them and print the original string.
#cat pra10.txt | sed "s/\t\t*/ /g"
cat pra10.txt | tr -s [:space:] ' '
# -s Squeeze multiple occurrences of the characters listed in the last operand (either string1 or string2) in the input into a single instance of the character.  This occurs after all deletion and translation is completed.
#sed $'s/[^[:print:]\t]//g' pra10.txt
echo '\b'