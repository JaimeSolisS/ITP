from app import app
from db import mysql
from flask import jsonify

# PRETTY
app.config['JSONIFY_PRETTYPRINT_REGULAR'] = True


@app.route('/users')
def users():
    try:
        connection = mysql.connect()
        cursor = connection.cursor()
        cursor.execute("SELECT * FROM users")
        records = cursor.fetchall()

        response = jsonify(records)
        response.status_code = 200
        return response

    except Exception as e:
        print(e)

    finally:
        cursor.close()
        connection.close()


if __name__ == "__main__":
    app.run()
