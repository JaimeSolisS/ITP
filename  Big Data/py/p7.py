# Find out a way where you can use substring like feature in bash scripting and use that.
# Example for this is print all the files with same extension O/P should look like
# .txt - one, two, three
#pdf - four, five. six

import os
from os.path import splitext
filelist = os.listdir()
# print(filelist)
dictionary = {}
for file in filelist:
    file_name, extension = splitext(file)
    key = extension
    values = dictionary.get(key, [])
    values.append(file_name)
    dictionary[key] = values
# print(dictionary)
for ext, name in dictionary.items():
    print(ext, " - ", end="")
    print(*name, sep=",")
