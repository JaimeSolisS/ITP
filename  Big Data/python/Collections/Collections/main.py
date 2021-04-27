from Controller.Lists import createList, createRandomList, searchList, updateList, removeList
from Controller.Tuples import createTuple, createRandomTuple, searchTuple, updateTuple, removeTuple
from Controller.Sets import createSet, createRandomSet, searchSet, updateSet, removeSet
from Controller.Dics import createDic, createRandomDic, searchDic, updateDic, removeDic


def main():

    # ------------------ CREATE COLLECTIONS ------------------
    table = [["CREATE", "1k (ms)", "10k (ms)", "100k (ms)"]]
    createTimes = ["List"]
    list1k = createList(1000, createTimes)
    list10k = createList(10000, createTimes)
    list100k = createList(100000, createTimes)
    table.append(createTimes)
    createTimes = ["Tuple"]
    tuple1k = createTuple(1000, createTimes)
    tuple10k = createTuple(10000, createTimes)
    tuple100k = createTuple(100000, createTimes)
    table.append(createTimes)
    createTimes = ["Set"]
    set1k = createSet(1000, createTimes)
    set10k = createSet(10000, createTimes)
    set100k = createSet(100000, createTimes)
    table.append(createTimes)
    createTimes = ["Dictionary"]
    dic1k = createDic(1000, createTimes)
    dic10k = createDic(10000, createTimes)
    dic100k = createDic(100000, createTimes)
    table.append(createTimes)
    table.append(["-----", "-----", "-----", "------"])

    # ------------------ RANDOM ITEMS ------------------
    randomList1k = createRandomList(10, list1k)
    randomList10k = createRandomList(10, list10k)
    randomList100k = createRandomList(10, list100k)
    randomTuple1k = createRandomTuple(10, tuple1k)
    randomTuple10k = createRandomTuple(10, tuple10k)
    randomTuple100k = createRandomTuple(10, tuple100k)
    randomSet1k = createRandomSet(10, set1k)
    randomSet10k = createRandomSet(10, set10k)
    randomSet100k = createRandomSet(10, set100k)
    randomDic1k = createRandomDic(10, dic1k)
    randomDic10k = createRandomDic(10, dic10k)
    randomDic100k = createRandomDic(10, dic100k)

    # ------------------ SEARCH ITEMS ------------------
    table.append(["SEARCH", "1k (ms)", "10k (ms)", "100k (ms)"])
    searchTimes = ["List"]
    searchList(list1k, randomList1k, searchTimes)
    searchList(list10k, randomList10k, searchTimes)
    searchList(list100k, randomList100k, searchTimes)
    table.append(searchTimes)
    searchTimes = ["Tuple"]
    searchTuple(tuple1k, randomTuple1k, searchTimes)
    searchTuple(tuple10k, randomTuple10k, searchTimes)
    searchTuple(tuple100k, randomTuple100k, searchTimes)
    table.append(searchTimes)
    searchTimes = ["Set"]
    searchSet(set1k, randomSet1k, searchTimes)
    searchSet(set10k, randomSet10k, searchTimes)
    searchSet(set100k, randomSet100k, searchTimes)
    table.append(searchTimes)
    searchTimes = ["Dicitionary"]
    searchDic(dic1k, randomDic1k, searchTimes)
    searchDic(dic10k, randomDic10k, searchTimes)
    searchDic(dic100k, randomDic100k, searchTimes)
    table.append(searchTimes)
    table.append(["-----", "-----", "-----", "------"])

    # ------------------UPDATE ITEMS ------------------
    table.append(["UPDATE", "1k (ms)", "10k (ms)", "100k (ms)"])
    updateTimes = ["List"]
    updateList(list1k, randomList1k, updateTimes)
    updateList(list10k, randomList10k, updateTimes)
    updateList(list100k, randomList100k, updateTimes)
    table.append(updateTimes)
    updateTimes = ["Tuple"]
    updateTuple(tuple1k, randomTuple1k, updateTimes)
    updateTuple(tuple10k, randomTuple10k, updateTimes)
    updateTuple(tuple100k, randomTuple100k, updateTimes)
    table.append(updateTimes)
    updateTimes = ["Set"]
    updateSet(set1k, randomSet1k, updateTimes)
    updateSet(set10k, randomSet10k, updateTimes)
    updateSet(set100k, randomSet100k, updateTimes)
    table.append(updateTimes)
    updateTimes = ["Dictionary"]
    updateDic(dic1k, randomDic1k, updateTimes)
    updateDic(dic10k, randomDic10k, updateTimes)
    updateDic(dic100k, randomDic100k, updateTimes)
    table.append(updateTimes)
    table.append(["-----", "-----", "-----", "------"])

    # ------------------ REMOVE ITEMS ------------------
    table.append(["REMOVE", "1k (ms)", "10k (ms)", "100k (ms)"])
    removeTimes = ["List"]
    removeList(list1k, randomList1k, removeTimes)
    removeList(list10k, randomList10k, removeTimes)
    removeList(list100k, randomList100k, removeTimes)
    table.append(removeTimes)
    removeTimes = ["Tuple"]
    removeTuple(tuple1k, randomTuple1k, removeTimes)
    removeTuple(tuple10k, randomTuple10k, removeTimes)
    removeTuple(tuple100k, randomTuple100k, removeTimes)
    table.append(removeTimes)
    removeTimes = ["Set"]
    removeSet(set1k, randomSet1k, removeTimes)
    removeSet(set10k, randomSet10k, removeTimes)
    removeSet(set100k, randomSet100k, removeTimes)
    table.append(removeTimes)
    removeTimes = ["Dictionary"]
    removeDic(dic1k, randomDic1k, removeTimes)
    removeDic(dic10k, randomDic10k, removeTimes)
    removeDic(dic100k, randomDic100k, removeTimes)
    table.append(removeTimes)

    mx = max((len(str(ele)) for sub in table for ele in sub))
    for row in table:
        print(" ".join(["{:<{mx}}".format(ele, mx=mx) for ele in row]))

    print()


if __name__ == '__main__':
    main()
