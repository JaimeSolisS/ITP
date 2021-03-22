from Controller.Methods import createTuple, createRandomTuple, searchTuple, updateTuple, removeTuple


def main():

    table = [["Tuple", "1k (ms)", "10k (ms)", "100k (ms)"]]

    createTimes = ["Create"]
    list1k = createTuple(1000, createTimes)
    list10k = createTuple(10000, createTimes)
    list100k = createTuple(100000, createTimes)
    table.append(createTimes)

    random1k = createRandomTuple(10, list1k)
    random10k = createRandomTuple(10, list10k)
    random100k = createRandomTuple(10, list100k)

    searchTimes = ["Search"]
    searchTuple(list1k, random1k, searchTimes)
    searchTuple(list10k, random10k, searchTimes)
    searchTuple(list100k, random100k, searchTimes)
    table.append(searchTimes)

    updateTimes = ["Update"]
    updateTuple(list1k, random1k, updateTimes)
    updateTuple(list10k, random10k, updateTimes)
    updateTuple(list100k, random100k, updateTimes)
    table.append(updateTimes)

    removeTimes = ["Remove"]
    removeTuple(list1k, random1k, removeTimes)
    removeTuple(list10k, random10k, removeTimes)
    removeTuple(list100k, random100k, removeTimes)
    table.append(removeTimes)

    mx = max((len(str(ele)) for sub in table for ele in sub))
    for row in table:
        print(" ".join(["{:<{mx}}".format(ele, mx=mx) for ele in row]))


if __name__ == '__main__':
    main()
