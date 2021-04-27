from Model.Data import Data
import names


class DataGenerator():

    @staticmethod
    def generateData(amount, myList):

        for i in range(amount):
            id = i
            name = names.get_first_name()
            person = Data(id, name)

            myList.append(person.id)
            myList.append(person.name)
            myList.append(person.key)
            myList.append(person.status)
        return myList
