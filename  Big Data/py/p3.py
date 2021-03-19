# Using loops while, for, until and if/else conditions:z
# Write shell script for generating prime numbers in a range

low = int(input("lower number: "))
high = int(input("higher number: "))
for num in range(low, high + 1):
    prime = True
    for i in range(2, num):
        if (num % i) == 0:
            prime = False
    if prime:
        print(num, end=",")
