from Data.DataGenerator import DataGenerator
import time
import random
import datetime


def createDic(amount=10, times=None):
    start = time.time()
    newList = DataGenerator.generateData(amount, [])
    dictionary = {item.id: item for item in newList}
    end = time.time()
    miliseconds = round((end-start)*10**3, 3)
    times.append(miliseconds)
    #print("Create Dictionary Duration with ", len(newList), " items:", miliseconds, "ms")
    return dictionary


def createRandomDic(amount=10, original=None):
    randomDic = {}
    #print("Random elements from Dict with ", len(original), " elements")
    for i in range(amount):
        key = random.randint(0, len(original)-1)
        randomDic[key] = original[key]
    return randomDic


def searchDic(original=None, random=None, times=None):
    start = time.time()
    keys = random.keys()
    for key in keys:
        if key in original:
            # print(key, "found")
            pass
        else:
            print(key, " not found")
    end = time.time()
    miliseconds = round((end-start)*10**3, 3)
    times.append(miliseconds)
    #print("Search Item in Dict duration with ", len(original), " items:", miliseconds, "ms")


def updateDic(original=None, random=None, times=None):
    start = time.time()
    keys = random.keys()
    for key in keys:
        if key in original:
            original[key].status = datetime.datetime.now().strftime("%H:%M:%S")
        else:
            print(key, " not found")
    end = time.time()
    miliseconds = round((end-start)*10**3, 3)
    times.append(miliseconds)
    #print("Update Item in Dict duration with ", len( original), " items:", miliseconds, "ms")


def removeDic(original=None, random=None, times=None):
    start = time.time()
    keys = random.keys()
    for key in keys:
        if key in original:
            del original[key]
        else:
            print(key, " not found")
    end = time.time()
    miliseconds = round((end-start)*10**3, 3)
    times.append(miliseconds)
    #print("Delete Item in Dict duration with ", len(original), " items:", miliseconds, "ms")


if __name__ == '__main__':
    createDic()
    createRandomDic()
    searchDic()
    updateDic()
    removeDic()
