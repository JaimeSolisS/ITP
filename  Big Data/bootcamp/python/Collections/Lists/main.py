from Controller.Methods import createList, createRandomList, searchList, updateList, removeList


def main():

    table = [["List", "1k (ms)", "10k (ms)", "100k (ms)"]]

    createTimes = ["Create"]
    list1k = createList(1000, createTimes)
    list10k = createList(10000, createTimes)
    list100k = createList(100000, createTimes)
    table.append(createTimes)

    random1k = createRandomList(10, list1k)
    random10k = createRandomList(10, list10k)
    random100k = createRandomList(10, list100k)

    searchTimes = ["Search"]
    searchList(list1k, random1k, searchTimes)
    searchList(list10k, random10k, searchTimes)
    searchList(list100k, random100k, searchTimes)
    table.append(searchTimes)

    updateTimes = ["Update"]
    updateList(list1k, random1k, updateTimes)
    updateList(list10k, random10k, updateTimes)
    updateList(list100k, random100k, updateTimes)
    table.append(updateTimes)

    removeTimes = ["Remove"]
    removeList(list1k, random1k, removeTimes)
    removeList(list10k, random10k, removeTimes)
    removeList(list100k, random100k, removeTimes)
    table.append(removeTimes)

    mx = max((len(str(ele)) for sub in table for ele in sub))
    for row in table:
        print(" ".join(["{:<{mx}}".format(ele, mx=mx) for ele in row]))


if __name__ == '__main__':
    main()
