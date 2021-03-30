from app import app
from db import mysql
from flask import jsonify
import logging
from flask import request
import secrets

logging.basicConfig(level=logging.DEBUG)
# PRETTY
app.config['JSONIFY_PRETTYPRINT_REGULAR'] = True

# id
# name
# key_value
# status

token = 'bearer ' + secrets.token_urlsafe(16)
print(token)


@app.before_request
def before_request_func():

    if request.headers.get('Auth'):
        reqtoken = request.headers.get('Auth')
        if reqtoken == token:
            pass
        else:
            response = jsonify('Wrong token')
            response.status_code = 401
            return response
    else:
        response = jsonify('No token found')
        response.status_code = 401
        return response


@app.route('/users', methods=['GET'])
def users():
    try:
        connection = mysql.connect()
        cursor = connection.cursor()
        cursor.execute("SELECT * FROM users")
        records = cursor.fetchall()
        # Response
        response = jsonify(records)
        response.status_code = 200
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
    connection = mysql.connect()
    cursor = connection.cursor()
    try:
        cursor.execute("DELETE FROM users WHERE id=%s", (id,))
        connection.commit()
        response = jsonify('User deleted successfully!')
        response.status_code = 200
        return response
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
