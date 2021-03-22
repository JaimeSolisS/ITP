from Controller.Methods import createDic,  createRandomDic, searchDic, updateDic, removeDic


def main():

    table = [["Dictionary", "1k (ms)", "10k (ms)", "100k (ms)"]]

    createTimes = ["Create"]
    list1k = createDic(1000, createTimes)
    list10k = createDic(10000, createTimes)
    list100k = createDic(100000, createTimes)
    table.append(createTimes)

    random1k = createRandomDic(10, list1k)
    random10k = createRandomDic(10, list10k)
    random100k = createRandomDic(10, list100k)

    searchTimes = ["Search"]
    searchDic(list1k, random1k, searchTimes)
    searchDic(list10k, random10k, searchTimes)
    searchDic(list100k, random100k, searchTimes)
    table.append(searchTimes)

    updateTimes = ["Update"]
    updateDic(list1k, random1k, updateTimes)
    updateDic(list10k, random10k, updateTimes)
    updateDic(list100k, random100k, updateTimes)
    table.append(updateTimes)

    removeTimes = ["Remove"]
    removeDic(list1k, random1k, removeTimes)
    removeDic(list10k, random10k, removeTimes)
    removeDic(list100k, random100k, removeTimes)
    table.append(removeTimes)

    mx = max((len(str(ele)) for sub in table for ele in sub))
    for row in table:
        print(" ".join(["{:<{mx}}".format(ele, mx=mx) for ele in row]))


if __name__ == '__main__':
    main()
