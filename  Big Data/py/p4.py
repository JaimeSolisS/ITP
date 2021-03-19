# Using loops while, for, until and if/else conditions:
# Write shell script for generating pascal triangle

levels = int(input("Levels: "))
for i in range(1, levels+1):
    for j in range(0, levels-i+1):
        print(" ", end="")
    coef = 1
    for j in range(1, i+1):
        print(" ", coef, sep="", end="")
        coef = coef * (i - j) // j
    print()
