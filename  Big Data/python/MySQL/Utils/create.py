import time
from Data.DataGenerator import DataGenerator


def createList(amount=10):
    start = time.time()
    newList = DataGenerator.generateData(amount, [])
    end = time.time()
    miliseconds = round((end-start)*10**3, 3)
    # print("Create List Duration with ", len(newList), " items:", miliseconds, "ms")
    return newList


if __name__ == '__main__':
    createList()
