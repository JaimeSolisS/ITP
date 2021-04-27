from app import app
from db import mysql
from flask import jsonify
import logging
from flask import request

logging.basicConfig(level=logging.DEBUG)
# PRETTY
app.config['JSONIFY_PRETTYPRINT_REGULAR'] = True


@app.route('/users', methods=['GET'])
def users():
    try:
        connection = mysql.connect()
        cursor = connection.cursor()
        cursor.execute("SELECT * FROM users")
        records = cursor.fetchall()
        # app.logger.info()

        # Query Params
        print("Query Params:")
        key1 = request.args.get('key1')
        key2 = request.args.get('key2')
        print(key1)
        print(key2)

        # Request
        print("Request Headers:")
        header_1 = request.headers.get('Header_from_request_1')
        header_2 = request.headers.get('Header_from_request_2')
        print(header_1)
        print(header_2)
        print(request.headers)

        # Response
        response = jsonify(records)
        response.status_code = 200
        response.headers['Response Header 1'] = 'This is One Custume Header from response'
        response.headers['Response Header 2'] = 'This is Another Custume Header from response'
        print(response.headers)
        return response

    except Exception as e:
        print(e)

    finally:
        cursor.close()
        connection.close()


@app.route('/users/<int:id>')
def user(id):
    connection = None
    cursor = None
    try:
        connection = mysql.connect()
        cursor = connection.cursor()
        cursor.execute(
            "SELECT * FROM users WHERE id=%s", id)
        row = cursor.fetchone()
        response = jsonify(row)
        response.status_code = 200
        return response
    except Exception as e:
        print(e)
    finally:
        cursor.close()
        connection.close()


if __name__ == "__main__":
    app.run()
