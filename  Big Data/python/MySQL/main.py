from connection import connectMysql
import getpass
from Utils.create import createList
import mysql.connector


def main():

    # CONECT TO MYSQL
    """  password = getpass.getpass()
     mydb = connectMysql(password)
     mycursor = mydb.cursor() """

    # CREATE DATABASE
    """ mycursor.execute("CREATE DATABASE PK_ITP") """

    # SHOW DATABASES
    """ mycursor.execute("SHOW DATABASES")
    for x in mycursor:
        print(x) """

    # CREATE TABLE
    """ mycursor.execute(
        "CREATE TABLE users (id INT PRIMARY KEY, name VARCHAR(255), key_value VARCHAR(255), status VARCHAR(255))")

    mycursor.execute("SHOW TABLES")
    for x in mycursor:
        print(x) """

    try:

        password = getpass.getpass()
        connection = connectMysql(password)
        cursor = connection.cursor()

        amount = int(input("How many elements insert to DB: "))
        users = createList(amount)

        # Recieves List, convert to LIST OF TUPLES
        # Create an iterator with 4 copies
        it = [iter(users)] * 4
        # Unpack the copies of the iterator, and pass them as parameters to zip
        users = list(zip(*it))
        # print(myList)

        insert_query = "INSERT INTO users (id, name, key_value, status) VALUES (%s, %s, %s, %s)"
        records_to_insert = users

        cursor = connection.cursor()
        cursor.executemany(insert_query, records_to_insert)
        connection.commit()

        print(cursor.rowcount, "Record inserted successfully into users table")

    except mysql.connector.Error as error:
        print("Failed to insert record into MySQL table {}".format(error))

    finally:
        if connection.is_connected():
            cursor.close()
            connection.close()
            print("MySQL connection is closed")


if __name__ == '__main__':
    main()
