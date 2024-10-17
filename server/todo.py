from flask import Flask, jsonify, request, abort
import sqlite3

app = Flask(__name__)

# Connect to the SQLite database
def get_db_connection():
    conn = sqlite3.connect('todo.db')
    conn.row_factory = sqlite3.Row
    return conn

# Initialize database with a table for ToDo items
def init_db():
    conn = get_db_connection()
    conn.execute('''
        CREATE TABLE IF NOT EXISTS todos (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            userId INTEGER NOT NULL,
            title TEXT NOT NULL,
            completed BOOLEAN NOT NULL
        )
    ''')
    conn.commit()
    conn.close()

# GET /todos - retrieve all to-do items
@app.route('/todos', methods=['GET'])
def get_todo_list():
    conn = get_db_connection()
    todos = conn.execute('SELECT * FROM todos').fetchall()
    conn.close()
    
    todo_list = [dict(todo) for todo in todos]
    return jsonify(todo_list)

# POST /todos - add a new to-do item
@app.route('/todos', methods=['POST'])
def add_todo_item():
    if not request.json or 'title' not in request.json:
        abort(400, 'Title is required')

    new_task = {
        'userId': request.json.get('userId', 1),  # default userId to 1
        'title': request.json['title'],
        'completed': request.json.get('completed', False)
    }
    
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute(
        'INSERT INTO todos (userId, title, completed) VALUES (?, ?, ?)',
        (new_task['userId'], new_task['title'], new_task['completed'])
    )
    conn.commit()
    new_task['id'] = cursor.lastrowid
    conn.close()

    return jsonify(new_task), 201

# PUT /todos/{id} - update an existing to-do item
@app.route('/todos/<int:id>', methods=['PUT'])
def update_todo_item(id):
    conn = get_db_connection()
    todo = conn.execute('SELECT * FROM todos WHERE id = ?', (id,)).fetchone()
    
    if todo is None:
        abort(404)

    if not request.json:
        abort(400)
    
    updated_task = {
        'title': request.json.get('title', todo['title']),
        'completed': request.json.get('completed', todo['completed'])
    }
    
    conn.execute(
        'UPDATE todos SET title = ?, completed = ? WHERE id = ?',
        (updated_task['title'], updated_task['completed'], id)
    )
    conn.commit()
    conn.close()

    updated_task['id'] = id
    updated_task['userId'] = todo['userId']
    return jsonify(updated_task)

# DELETE /todos/{id} - delete a to-do item
@app.route('/todos/<int:id>', methods=['DELETE'])
def delete_todo_item(id):
    conn = get_db_connection()
    todo = conn.execute('SELECT * FROM todos WHERE id = ?', (id,)).fetchone()
    
    if todo is None:
        abort(404)
    
    conn.execute('DELETE FROM todos WHERE id = ?', (id,))
    conn.commit()
    conn.close()
    
    return '', 204

if __name__ == '__main__':
    init_db()  # Initialize the database
    #app.run(debug=True)
    app.run(host='0.0.0.0', port=5000, debug=True)
