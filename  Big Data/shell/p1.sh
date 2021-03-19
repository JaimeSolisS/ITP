#Write scripts where you pass arguments to the bash scrips
$1 #file name
echo "Please add a new name for the copy"
read newName
cp $1 $newName