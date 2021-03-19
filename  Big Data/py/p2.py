# Using loops while, for, until and if/else conditions:
# Write shell script for generating fibonacci sequence

num = int(input("How many elements: "))
x = 0
y = 1
if num < 1:
    print("Choose a valid number")
elif num == 1:
    print(x)
elif num == 2:
    print(x, ",", y)
else:
    print("Sequence: ", x, ",", y, end=",")
    for i in range(num):
        fib = x + y
        print(fib, end=",")
        x = y
        y = fib
