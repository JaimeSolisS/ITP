from Data.DataGenerator import DataGenerator
import time
import random
import datetime


def createTuple(amount=10, times=None):
    start = time.time()
    newList = DataGenerator.generateData(amount, [])
    newTuple = tuple(newList)
    end = time.time()
    miliseconds = round((end-start)*10**3, 3)
    times.append(miliseconds)
    #print("Create Tuple Duration with ", len(newList), " items:", miliseconds, "ms")
    return newTuple


def createRandomTuple(amount=10, original=None):
    randomList = []
   # print("Random elements from Tuple with ", len(original), " elements")
    for i in range(amount):
        randomList.append(original[random.randint(0, len(original)-1)])
        # print(randomList[i])
    return randomList


def searchTuple(original=None, random=None, times=None):
    start = time.time()
    for item in random:
        if item in original:
            #print(item, "found")
            pass
        else:
            print(item.name, " not found")
    end = time.time()
    miliseconds = round((end-start)*10**3, 3)
    times.append(miliseconds)
    #print("-- Tuple Search Item duration with ",len(original), " items:", miliseconds, "ms")


def updateTuple(original=None, random=None, times=None):
    start = time.time()
    listFromTuple = list(original)
    for item in random:
        if item in original:
            index = listFromTuple.index(item)
            listFromTuple[index].status = datetime.datetime.now().strftime(
                "%H:%M:%S")
        else:
            print(item.name, " not found")
    original = tuple(listFromTuple)
    end = time.time()
    miliseconds = round((end-start)*10**3, 3)
    times.append(miliseconds)
    #print("--Update Item in Tuple duration with ", len(original), " items:", miliseconds, "ms")


def removeTuple(original=None, random=None, times=None):
    start = time.time()
    listFromTuple = list(original)
    for item in random:
        if item in listFromTuple:
            listFromTuple.remove(item)
        else:
            print(item.name, " not found")
    original = tuple(listFromTuple)
    end = time.time()
    miliseconds = round((end-start)*10**3, 3)
    times.append(miliseconds)
   #print("--Modify Item in Tuple duration with ",len(original), " items:", miliseconds, "ms")
    return original


if __name__ == '__main__':
    createTuple()
    createRandomTuple()
    searchTuple()
    updateTuple()
    removeTuple()
