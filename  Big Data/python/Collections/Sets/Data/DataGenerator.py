from Model.Data import Data


class DataGenerator():

    @staticmethod
    def generateData(amount, myList):

        for i in range(amount):
            id = i
            name = "User"+str(i)
            person = Data(id, name)
            myList.append(person)
        return myList
