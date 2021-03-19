f = open("file3.txt", "r")
lines = []
for x in f:
    if len(x.strip()) == 0:
        continue
    # The split() method splits a string into a list. The join() method takes all items in an iterable and joins them into one string.
    string = " ".join(x.split())
    # print(string)
    lines.append(string)
print(" ".join(lines))
