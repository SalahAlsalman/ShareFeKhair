# Share Fe Alkhair
**Share Fe Alkhair** is a free and open source software application for managing the class for any school, university, or other. It helps the teacher and his students to connect with each to share class notes, messages and improve the learning path.

# Project Requirements:

## Tables:

1- class:

| id  | name | teacher_id|
| ------------- | ------------- |------------- |
| null  | null | null|

2- class_sessions:

| session_id  | class_id |
| ------------- | ------------- |
| null  | null |

3- comment:

| id  | message | message_date|user_id|
| ------------- | ------------- |------------- |------------- |
| null  | null | null|null|

4- comments_note:

| comment_id  | note_id |
| ------------- | ------------- |
| null  | null |

5- note:

| id  | message | message_date|user_id|
| ------------- | ------------- |------------- |------------- |
| null  | null | null|null|

6- notes_sessions:

|  note_id | session_id |
| ------------- | ------------- |
| null  | null |

7- session:

| id  |
| ------------- |
| null  |



8- student:

| student_id  |
| ------------- |
| null  |

9- student_class:

|  student_id | class_id |
| ------------- | ------------- |
| null  | null |

10- teacher:

| teacher_id  |
| ------------- |
| null  |

11- user:

| id  | email | password| role| username|
| ------------- | ------------- |------------- |------------- |------------- |
| null  | null | null|null|null|

## Table Relationships:

![Table Relationship image](https://github.com/SalahAlsalman/ShareFeKhair/blob/master/src/main/resources/images/Table%20relationship.png?raw=true)

## Class Diagram:

![class diagram](https://github.com/SalahAlsalman/ShareFeKhair/blob/master/src/main/resources/images/Class%20Diagram.png?raw=true)


## Endpoints
### Auth Controller:
> - Register user with roles (student - teacher).
- Login endpoint that will return a cookie.

### **Class Controller**:
>- Get All Classes.
- Add new class.
- Update class with class_id.
- Delete class.

### **Comment Controller**:
>- Get All comments.
- Add new comment.
- Update comment with comment_id.
- Delete comment with comment_id.

### **Note Controller**:
>- Get All notes.
- Add new notes.
- Update notes with notes_id.
- Delete notes with notes_id.

### **Session Controller**:
>- Get All sessions.
- Add new session.
- Didnt add ( update ) because it wasn't useful.
- Delete session with session_id.

### **Student Controller**:
>- Get All students.
- Add student to class.

### **Teacher Controller**:
>- Get All teachers.
- Add teacher to class.

### **User Controller**:
>- Get All users.
- Update user with user_id.
- Delete user with user_id.

## Dependancies:
>1. Spring web
2. Project lombock
3. Validation
4. JPA
5. Spring security 
6. MySQL



## Documentation
This url contains all endpoints and equipped with body, query strings and path variables:
https://documenter.getpostman.com/view/21199597/Uz5NiYgE
