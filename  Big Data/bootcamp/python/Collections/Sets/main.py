from Controller.Methods import createSet, createRandomSet, searchSet, updateSet, removeSet


def main():

    table = [["Set", "1k (ms)", "10k (ms)", "100k (ms)"]]

    createTimes = ["Create"]
    list1k = createSet(1000, createTimes)
    list10k = createSet(10000, createTimes)
    list100k = createSet(100000, createTimes)
    table.append(createTimes)

    random1k = createRandomSet(10, list1k)
    random10k = createRandomSet(10, list10k)
    random100k = createRandomSet(10, list100k)

    searchTimes = ["Search"]
    searchSet(list1k, random1k, searchTimes)
    searchSet(list10k, random10k, searchTimes)
    searchSet(list100k, random100k, searchTimes)
    table.append(searchTimes)

    updateTimes = ["Update"]
    updateSet(list1k, random1k, updateTimes)
    updateSet(list10k, random10k, updateTimes)
    updateSet(list100k, random100k, updateTimes)
    table.append(updateTimes)

    removeTimes = ["Remove"]
    removeSet(list1k, random1k, removeTimes)
    removeSet(list10k, random10k, removeTimes)
    removeSet(list100k, random100k, removeTimes)
    table.append(removeTimes)

    mx = max((len(str(ele)) for sub in table for ele in sub))
    for row in table:
        print(" ".join(["{:<{mx}}".format(ele, mx=mx) for ele in row]))


if __name__ == '__main__':
    main()
