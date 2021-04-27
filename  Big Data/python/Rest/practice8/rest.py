from app import app
from db import mysql
from flask import jsonify
import logging
from flask import request

logging.basicConfig(level=logging.DEBUG)
# PRETTY
app.config['JSONIFY_PRETTYPRINT_REGULAR'] = True

# id
# name
# key_value
# status


@app.route('/users', methods=['GET'])
def users():
    try:
        connection = mysql.connect()
        cursor = connection.cursor()
        cursor.execute("SELECT * FROM users")
        records = cursor.fetchall()

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


@app.route('/delete/<int:id>', methods=['DELETE'])
def delete_user(id):
    conn = None
    cursor = None
    try:
        connection = mysql.connect()
        cursor = connection.cursor()
        cursor.execute("DELETE FROM users WHERE id=%s", (id,))
        connection.commit()
        resp = jsonify('User deleted successfully!')
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)
    finally:
        cursor.close()
        connection.close()


@app.route('/add', methods=['POST'])
def add_user():
    connection = mysql.connect()
    cursor = connection.cursor()
    try:
        # _json = request.json
        # _id = _json['id']
        # _name = _json['key_value']
        # _key_value = _json['key_value']
        # _status = _json['status']
        # print(_id, _name, _key_value, _status)

        # ----REQUEST-------
        id = request.headers.get('id')
        name = request.headers.get('name')
        key_value = request.headers.get('key_value')
        status = request.headers.get('status')

        # ----INSERT INTO MSQL------
        sql = "INSERT INTO users(id, name, key_value, status) VALUES(%s, %s, %s, %s)"
        data = (id, name, key_value, status)
        cursor.execute(sql, data)
        connection.commit()

        # -----RESPONSE-----------
        response = jsonify('User added successfully!')
        response.status_code = 200
        response.headers['id'] = id
        response.headers['name'] = name
        response.headers['key_value'] = key_value
        response.headers['status'] = status
        print(response)
        return response
    except Exception as e:
        print(e)
    finally:
        cursor.close()
        connection.close()


@app.route('/update', methods=['PUT'])
def update_user():
    connection = mysql.connect()
    cursor = connection.cursor()
    try:
        # ----REQUEST-------
        id = request.headers.get('id')
        name = request.headers.get('name')
        key_value = request.headers.get('key_value')
        status = request.headers.get('status')

        # ----UPDATE------
        sql = "UPDATE users SET name=%s, key_value=%s, status=%s WHERE id=%s"
        data = (name, key_value, status, id)
        connection = mysql.connect()
        cursor = connection.cursor()
        cursor.execute(sql, data)
        connection.commit()

        # --- RESPONSE ----
        response = jsonify('User updated successfully!')
        response.status_code = 200
        return response
    except Exception as e:
        print(e)
    finally:
        cursor.close()
        connection.close()


if __name__ == "__main__":
    app.run()
