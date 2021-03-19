# Using loops while, for, until and if/else conditions:
# Write shell script for finding armstrong numbers
low = int(input("lower number: "))
high = int(input("higher number: "))
print("Armstong numbers: ")

for num in range(low, high):
    sum = 0
    for dig in str(num):
        digit = int(dig)
        sum += digit * digit * digit
    if sum == num:
        print(num)
