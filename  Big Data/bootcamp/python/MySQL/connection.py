import mysql.connector


def connectMysql(password="password"):

    mydb = mysql.connector.connect(
        host="127.0.0.1",
        user="root",
        password=password,
        database="PK_ITP"
    )
    print(mydb)
    return mydb


if __name__ == '__main__':
    connectMysql()
