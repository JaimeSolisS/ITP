from app import app
from db import mysql
from flask import jsonify
import logging
from flask import request, g
import secrets

logging.basicConfig(level=logging.DEBUG)
# PRETTY
app.config['JSONIFY_PRETTYPRINT_REGULAR'] = True
token = 'bearer ' + secrets.token_urlsafe(16)
print(token)

@app.before_request
def before_request_func():

    if request.headers.get('Auth'):
        reqtoken = request.headers.get('Auth')
        if reqtoken == token:
             # ------Open Connection----
            g.connection = mysql.connect()
            g.cursor = g.connection.cursor()
        else:
            response = jsonify('Invalid token')
            response.status_code = 401
            return response
    else:
        response = jsonify('No Authorization header was found')
        response.status_code = 401
        return response

# @app.after_request
# def after_request_func():
#    g.cursor.close()
#    g.connection.close()

@app.route('/users', methods=['GET'])
def users():
    try:
        g.cursor.execute("SELECT * FROM users")
        records = cursor.fetchall()
        # Response
        response = jsonify(records)
        response.status_code = 200
        return response

    except Exception as e:
        print(e)

    finally:
        g.cursor.close()
        g.connection.close()

@app.route('/users/<int:id>')
def user(id):
    try:
        g.cursor.execute(
            "SELECT * FROM users WHERE id=%s", id)
        row = cursor.fetchone()
        response = jsonify(row)
        response.status_code = 200
        return response
    except Exception as e:
        print(e)
    finally:
        g.cursor.close()
        g.connection.close()


@app.route('/delete/<int:id>', methods=['DELETE'])
def delete_user(id):
    try:
        g.cursor.execute("DELETE FROM users WHERE id=%s", (id,))
        g.connection.commit()
        response = jsonify('User deleted successfully!')
        response.status_code = 200
        return response
    except Exception as e:
        print(e)
    finally:
        g.cursor.close()
        g.connection.close()


@app.route('/add', methods=['POST'])
def add_user():
    try:
        # ----REQUEST-------
        _json = request.json
        _id = _json['id']
        _name = _json['name']
        _key_value = _json['key_value']
        _status = _json['status']

        # ----INSERT INTO MSQL------
        sql = "INSERT INTO users(id, name, key_value, status) VALUES(%s, %s, %s, %s)"
        data = (id, name, key_value, status)
        g.cursor.execute(sql, data)
        g.connection.commit()

        # -----RESPONSE-----------
        response = jsonify('User added successfully!')
        response.status_code = 200

        return response
    except Exception as e:
        print(e)
    finally:
        g.cursor.close()
        g.connection.close()


@app.route('/update', methods=['PUT'])
def update_user():
    try:
        # ----REQUEST-------
        _json = request.json
        _id = _json['id']
        _name = _json['name']
        _key_value = _json['key_value']
        _status = _json['status']

        # ----UPDATE------
        sql = "UPDATE users SET name=%s, key_value=%s, status=%s WHERE id=%s"
        data = (name, key_value, status, id)
        g.cursor.execute(sql, data)
        g.connection.commit()

        # --- RESPONSE ----
        response = jsonify('User updated successfully!')
        response.status_code = 200
        return response
    except Exception as e:
        print(e)
    finally:
        g.cursor.close()
        g.connection.close()

if __name__ == "__main__":
    app.run()
