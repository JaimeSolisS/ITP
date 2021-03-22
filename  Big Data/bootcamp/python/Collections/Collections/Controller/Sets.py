from Data.DataGenerator import DataGenerator
import time
import random
import datetime


def createSet(amount=10, times=None):
    start = time.time()
    newList = DataGenerator.generateData(amount, [])
    newSet = set(newList)
    end = time.time()
    miliseconds = round((end-start)*10**3, 3)
    times.append(miliseconds)
    #print("Create Set Duration with ", len(newList), " items:", miliseconds, "ms")
    return newSet


def createRandomSet(amount=10, original=None):
    randomList = []
    #print("Random elements from Set with ", len(original), " elements")
    for i in range(amount):
        # random.choice(tuple(myset)), because it's faster and arguably cleaner looking
        randomList.append(random.choice(tuple(original)))
    return randomList


def searchSet(original=None, random=None, times=None):
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
    #print("Search Item in Set duration with ", len(original), " items:", miliseconds, "ms")


def updateSet(original=None, random=None, times=None):
    start = time.time()
    myList = list(original)
    for item in random:
        if item in myList:
            index = myList.index(item)
            myList[index].status = datetime.datetime.now().strftime("%H:%M:%S")
        else:
            print(item.name, " not found")
    original = set(myList)
    end = time.time()
    miliseconds = round((end-start)*10**3, 3)
    times.append(miliseconds)
    #print("Update Item in Set duration with ", len(original), " items:", miliseconds, "ms")


def removeSet(original=None, random=None, times=None):
    start = time.time()
    for item in random:
        if item in original:
            original.remove(item)
        else:
            print(item.name, " not found")
    end = time.time()
    miliseconds = round((end-start)*10**3, 3)
    times.append(miliseconds)
    #print("Delete Item in Set duration with ", len(original), " items:", miliseconds, "ms")


if __name__ == '__main__':
    createSet()
    createRandomSet()
    searchSet()
    updateSet()
    removeSet()
