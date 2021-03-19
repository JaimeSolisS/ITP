# Using loops while, for, until and if/else conditions:
# Write shell script for string palindrome
string = input("Write a string: ")
string2 = string[::-1]

if string == string2:
    print("Is a palindrome")
else:
    print("It is NOT a palindrome")
