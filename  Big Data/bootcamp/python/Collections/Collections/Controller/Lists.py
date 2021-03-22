from Data.DataGenerator import DataGenerator
import time
import random
import datetime


def createList(amount=10, times=None):
    start = time.time()
    newList = DataGenerator.generateData(amount, [])
    end = time.time()
    miliseconds = round((end-start)*10**3, 3)
    times.append(miliseconds)
    # print("Create List Duration with ", len(newList), " items:", miliseconds, "ms")
    return newList


def createRandomList(amount=10, originalList=None):
    randomList = []
   # print("Random elements from list with ", len(originalList), " elements")
    for i in range(amount):
        randomList.append(originalList[random.randint(0, len(originalList)-1)])
        # print(randomList[i])
    return randomList


def searchList(original=None, random=None, times=None):
    start = time.time()
    for item in random:
        if item in original:
            # print(item, "found")
            pass
        else:
            print(item.name, " not found")
    end = time.time()
    miliseconds = round((end-start)*10**3, 3)
    times.append(miliseconds)
    # print("--Search Item in List duration with ",len(original), " items:", miliseconds, "ms")


def updateList(original=None, random=None, times=None):
    start = time.time()
    for item in random:
        if item in original:
            index = original.index(item)
            original[index].status = datetime.datetime.now().strftime(
                "%H:%M:%S")
        else:
            print(item.name, " not found")
    end = time.time()
    miliseconds = round((end-start)*10**3, 3)
    times.append(miliseconds)
    # print("Update Item in List duration with ", len(original), " items:", miliseconds, "ms")


def removeList(original=None, random=None, times=None):
    size = len(original)
    start = time.time()
    for item in random:
        if item in original:
            original.remove(item)
            # print(item.name,  " removed")
        else:
            print(item.name, " doesn't exist")
    end = time.time()
    miliseconds = round((end-start)*10**3, 3)
    times.append(miliseconds)
    # print("--Remove Item in List duration with ", size, " items:", miliseconds, "ms")


if __name__ == '__main__':
    createList()
    createRandomList()
    searchList()
    updateList()
    removeList()
