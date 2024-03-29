# ContactManagement Web Management

# Table Of Content
* [Introduction](#Introduction)
* [SetUp](#Set-Up)
* [Features](#features)
* [End_Point](#end-point)

# Introduction
This contact management application is an application which helps you to organize your contact into a group with their name and phone number and can be found and the aim of this contact mangemeant is to aid any firm in storing and retrieving all information about a pre-existing client in a more robust and efficient manner
This application is built using maven framework (spring-boot) and java for the backend application.

# Set-Up
* install git
* Install mySql for proper database functions
* Clone the project by following the cloning process
* Ensure your project is on the right server port
* install jdk version 21
* Click on this link (https://github.com/Oluwatobiloba-ojo/ContactManagementApp.git)
* Open your Post man Application, paste the accurate url on the given url space
* Ensure all dependencies in the above project are well injected in your pom.xml

# Features

* RegisterRequest
* LoginRequest 
* AddContactRequest 
* EditContactRequest 
* EditProfileRequest 
* ViewAllContacts 
* ViewAContactRequest 
* ViewUser 
* ResetPasswordRequest 
* ResetEmailRequest 
* DeleteContactRequest 
* DeleteAllContactRequest 
* DeleteUserAccountRequest 
* BlockContactRequest 
* UnBlockContactRequest


# End-Point

## RegisterRequest End-Point

_Register request is a request used in by the user that want to register into the contact management application and it collect from the user first name, last name, email, phonenumber, and password and it returns the user id (identification) and returns message and the data are saved in the data base._ 

# Request
* Url : https://localhost:9065/api/request/users
* Method: Post
* Header: 
  * Content-type: application\json
* Body :
```
JSON:
{
    "firstName":"tope",
    "lastName":"ojo",
    "email": "topeOjo23@gmail.com",
    "password": "Oluwatobi@5",
    "phoneNumber": "08129810794"
}
```
* Fields
  * `firstName`: _The first name of the user in string_
  * `lastName`: _The last name of the user in string_
  * `email`: _the email of the user in string_
  * `password`: _This is the password used when registering and it must begin with capital letter contain minimum of 6 with digit number and a special characater in string_
  * `phoneNumber`: _This is the phone number of the user_

# Response 1

_successful request and response_
* Status code: `201 Created`
* Body
```
JSON:
    {"response":{
                   "userId":49,
                   "message":"Account has been created"
    },
    "successful": true
    }
```

# Response 2
_unsuccessful request due to that the user exist already_

* Status code: `400 BadRequest`
* Body :
```
JSON
{
    "response": "User already exist",
    "successful": false
}
```

# Response 3

_unsuccessful request due to invalid format of the email_

* Status code `400 Bad request`
* Body
```
JSON
{
    "response": "Invalid email",
    "successful": false
}
```

# Response 4
_unsuccessful request due to invalid format of phone number_

* Status code `400 Bad Request`
* Body:
```
JSON
{
    "response": "invalid phone number phone format is +(country code),(national number)",
    "successful": false
}
```

# Response 5

_unsuccessful due to weak password given_

* Status code ``400 Bad request``
* Body:
```
JSON
{
    "response": "Weak password",
    "successful": false
}
```


## LoginRequest End-Point
_Login request include that the user that want to login into an account with contact management must already have an account to ensure successfully. it collect the id given to them during registeration and password of the account and set the isLocked to false and return a message that it has already login_

# Request
* Url: http://localhost:9065/api/request/user/2?password=Oluwatobi@5
* Method: Post
* Header: 
  * Content-type application/json
* Parameter:
  * userId: Long as Path variable
  * password: String as Required parameter

# Response 1
_successful request_

* Status code: `202 Accepted`
* Body
```
JSON:
    {
    "response": {
                "message": "You na don Login !!!!!!"
    },
    "successful": true
    }
```
# Response 2
_unsuccessful request due to invaiid user and password_

* Status code `400 Bad request`
* Body
```
JSON
{
    "response": {
                    "message": "Invalid Details"
    },
    "successful": false
}
```

# Response 3
_unsuccessful request due to that the user has already login and want to login also

* Status code:  `400 Bad Request`
* Body
```
JSON
{
    "response": {
                   "message": "User has login already"
    },
    "successful": false
}
```


# AddContactRequest End-Point
_Add contact request is used to create a contact which you want this app to save for a user which already have an account on this application to add a contact provided you give us the userId and the profile of the contact to be added (Contact name, and phoneNumber)._

# Request
* Url: http://localhost:9065/api/request/contact
* Method: POST
* Header: 
  * Content-type application/json
* Body:
```
JSON
{
    "userId": 2,
    "name": "BigTobi",
    "phoneNumber": "+234804578903"
}
```
* Fields:
  * `userId`: The id of the user required is a string 
  * `name`: The name of the contact you want to add 
  * `phoneNumber`: The phone number of the contact name

# Response 1
_successful request_
* Status code: 200 OK
* Body
```
JSON
{
    "response": {
                "message": "Contact already created"
    },
    "successful": true
}
```

# Response 2
_unsuccessful request due to that the user does not exist_

* Status code: `400 Bad request`
* Body
```
JSON
{
    "response": {
                "message": "Invalid Details"
    },
    "successful": false
}
```
# Response 3
_unsuccessful request due to that the contact already with the name_

* Status code: `400 Bad request`
* Body
```
JSON
{
    "response": {
                "message": "Contact Already Exist"
    },
    "successful": false
}
```

# Response 4
_unsuccessful request due to that the user have not login into account_

* Status code: `400 Bad request`
* Body
```
JSON
{
    "response": {
                "message": "Have not logIn"
    },
    "successful": false
}
```

# Response 5
_unsuccessful request due to that phone number given is invalid_

* Status code: `400 Bad request`
* Body
```
JSON
{
    "response": {
                    "message": "Invalid format for phone number"
    },
    "successful": false
}
```


}

# EditContactRequest End-Point

_Edit contact request is when you want to edit a particular contact phone-number with a new phone number_

Request
* Url: https://localhost:9065/api/request/contact
* Method: PUT
* Header: 
  * Content-type application/json
* Body
```
JSON:
{
    "userId": 1,
    "name":"smallTobi",
    "newContactNumber":"+2348056799373"
}
```
* Fields
  * userId: The userId of the user required a string
  * name: The contact name you want to edit required a string
  * newContactNumber: The new contact phone number required a string

# Response 1
_successful request_

* Status code: ``200 OK``
* Body:
```
JSON:
{
    "response": {
                   "message": "Contact Has been Updated"
    },
    "successful": true
}
```

# Response 2
_unsuccessful request due to invalid user in the data base oo_

* Status code: `400 Bad request`
* Body
```
JSON
{
    "response": {
                   "message": "User does not exist"
    },
    "successful": false
}
```
# Response 3
_unsuccessful request due to invalid contact with the name_

* Status code: `400 Bad request`
* Body
```
JSON
{
    "response": {
                    "message": "Contact does not exist"
    },
    "successful": false
}
```
# Response 4
_unsuccessful request due to invalid format of phone number_

* Status code: `400 Bad request`
* Body
```
JSON
{
    "response": {
                   "message": "Invalid format for phone number"
    },
    "successful": false
}
```
# EditProfileRequest End-Point

_This request is used in editing the user account profile by collecting the userId and what ever they want to edit about the first name and last name._

## Request
* Url: http://localhost:9065/api/request/user
* Method: PUT
* Header: 
  * Content-type application/json
* Body:
```
JSON
{
    "userId": 1,
    "lastName": "martins",
    "firstName": "ope"
}
```
# Response 1
* Status code: 200 OK
* Body
```
JSON
{
    "response": {
                   "message": "Profile Of User Has been updated"
    },
    "successful": true
}
```
# Response 2
_unsuccessful request due to that the user does not exist in the data base_

* Status code: `400 Bad request`
* Body
```
JSON: 
{
    "response": {
                    "message": "User does not exist"
    },
    "successful": false
}
```


# View-All-Contact End-Point
_View all contact of the user request is a request used in viewing all contact beloging to a user_

## Request
* Url: https://localhost:9065/api/request/contacts/1
* Method: GET
* Header: 
  * Content-type application/json
* Param:
  * `userId`: Long (The id of the user which you want to view contacts)

# Response 1
_successful request_

* Status code: `200 OK`
* Body
```
JSON
"response": {
           "contact": [
                            {
                                  "id": 28,
                                  "userId": 49,
                                  "phoneNumber": "+234804578903",
                                  "name": "BigTobi",
                                  "block": false
                            },
                            {
                                    "id": 29,
                                    "userId": 49,
                                    "phoneNumber": "+23480457890",
                                    "name": "BigTobis",
                                    "block": false
                            },
                            {
                                    "id": 30,
                                    "userId": 49,
                                    "phoneNumber": "+2348056799373",
                                    "name": "Olashile",
                                    "block": false
                            }
           ]
        },
            "successful": true
}
```
# Response 2
_unsuccessful request due to that the user has not login_

* Status code: `400 Bad request`
* Body:
```
JSON
{
        "response": "User Have not login",
        "successful": false
}
```

# Response 3
_unsuccessful request due to invalid user_

* Status code: `400 Bad request`
* Body:
```
JSON:
{
        "response": "User does not exist",
        "successful": false
}
```


# View-A-Contact-Request End-Point
_View a particular contact with the user Id and name of the contact_

# Request
* Url: http://localhost:9065/api/request/contact/1?contactName=smallTobi
* Method: GET
* Header: 
  * Content-type application/json
* Param:
  * path variable: userId: Long 
  * required param: contactName : String

# Response 1
_successful request_

* Status code: 200 OK
* Body
```
JSON
{
    "response": {
                   "contact": {
                                "id": 30,
                                "userId": 49,
                                "phoneNumber": "+2348056799373",
                                "name": "Olashile",
                                "block": false
                   }
    },
    "successful": true
}
```

# Response 2
_unsuccessful request due to that the user id is invalid_

* Status code: `400 Bad request`
* Body
```
JSON
{
    "response": "User does not exist",
    "successful": false
}
```
# Response 3
_unsuccessful request due to that the contact name does not exist_

* Status code: `400 Bad request`
* Body
```
JSON
{
    "response": "Contact does not exist",
    "successful": false
}
```

# ViewUser End-Point 
_This request is used in viewing the profile of the user_

# Request
* Url: https://localhost:9065/api/request/user/49
* Method: GET
* Header: 
  * Content-type application/json
* Param:
  * Path Variable: userId: Long (The id of the userId to view)

# Response 1:
* Status code: `200 OK`
* Body
```
JSON
{
        "response": {
                       "user": {
                                "id": 49,
                                "firstName": "ope",
                                "lastName": "martins",
                                "email": "topeOjo23@gmail.com",
                                "password": "F83r8/3dPsvwHDPB4sXelVIdehIwX1d3aSRfARaMPVs=JAQD0C1O4S9HHBRC1J970QQCMR3P3A",
                                "phoneNumber": "08129810794",
                                "logOut": false
                       }
        },
        "successful": true
}
```
# Response 2
_unsuccessful due to that user have not login into account_

* Status code: `400 Bad request`
* Body :
``` 
{
  "response": "User has not login into application",
  "successful": false
}
```

Response 3
_unsuccessful due to that user id is invalid_

* Status code: `400 Bad request`
* Body
```
JSON:
{
    "response": "User does not exist",
    "successful": false
}
```

## Reset-Password-Request End-Point
_This request allows the user to reset the password of the user provided you know the id of the user and also the old password_

# Request
* Url: https://localhost:9065/api/request/password/1
* Method: PUT
* Header: 
  * Content-type application/json 
* Param
* Path Variable: 
  * userId: Long 
* Body
```
JSON
{
    "oldPassword": "Oluwatobi@5",
    "newPassword": "Opeoluwa@12"
}
```
* Fields: 
  * `oldPassword`: the old password of the user (required a type String)
  * `newPassword`: The new password to change the user password to required String

# Response 1
_successful request_

* Status code : `200 OK`
* Body:
```
JSON
{
        "response": {
                    "message": "Password has been updated"
        },
        "successful": true
}
```
# Response 2
_unsuccessful request due to that the user does not exist_

* Status code: `400 Bad request`
* Body
```
JSON
{
            "response": {
                            "message": "User does not exist"
            },
            "successful": false
}
```

# Response 3
_unsuccessful request due to that is an incorrect details_

* Status code: `400 Bad request`
* Body
```
JSON
{
        "response": {
                        "message": "Password verification was wrong"
    },
"successful": false
}
```
# Response 4
_unsuccessful request due to that the new password is weak_

* Status code: `400 Bad request`
* Body
```
JSON
{
    "response": {
                    "message": "Password is weak"
    },
    "successful": false
}
```


## ResetEmailRequest End-Point
_This request is used in reseting the email of the user provided you know the old email and the id of the user_

# Request
* Url: http://localhost:9065/api/request/email/1
* Method: PUT
* Header: 
  * Content-type application/json 
* Param
  * Path variable: userId: Long 
* Body
```
JSON
{
    "oldEmail": "topeOjo23@gmail.com",
    "newEmail": "Ogungbeniopeoluwa@gmail.com"
}
```
* Fields
  * oldEmail: The old email of that account you provided id required String
  * newEmail: The new email of that account you want to reset

# Response 1
_successful request_

* Status code: `200 OK`
* Body
```
JSON
{
    "response": {
                "message": "Email has been reset"
    },
    "successful": true
}
```

# Response 2
_unsuccessful request due to invalid user detail_

* Status code: 400 Bad request 
* Body
```
JSON
{
"response": {
"message": "User does not exist"
},
"successful": false
}
```

# Response 3
_unsuccessful request due to that the old email was wrong_

* Status code: `400 Bad request`
* Body:
```
  {
    "response": "Email verification went wrong",
    "successful": false
  }
```



##  Delete-Contact End-Point
_This request is used in to delete a contact with the contact name._

# Request
* Url: localhost:9065/api/request/contact/1?contactName=smallTobi
* Method: DELETE
* Header: 
  * Content-Type application/json
* Parameter:
  * userID: (required) Long

# Response 1
_successful request_
* Status Code: `200 OK`
* Body :
```
JSON:
{
    "response": "Olashiles has been deleted from your account",
    "successful": true
}
```

# Response 2
_unsuccessful request due to that the contact does not exist in the user contacts_
* Status Code: `400 Bad Request`
* Body
```
JSON
{
    "response": "Contact does not exist",
    "successful": false
}
```

# Response 3
_unsuccessful due to that the user have not login into application._
* Status code: `400 Bad Request`
* Body :
```
JSON
{
  "response": "User have not login into application",
  "successful": false
}
```


## DeleteAllContactRequest
_This is a request that is used to delete all contact of this person_

# Request
* Url : `localhost:9065/api/request/contacts/1`
* Method : DELETE
* Header: 
  * Content-Type application/json
* Parameter:
  * `userID`: (required) Long

# Response 1
*successful request*
* Status code: `200 OK`
* Body:
```
JSON:
{
  "response": "All Contact Deleted successfully",
  "successful": true
}
```

# Response 2
_unsuccessful request due to that the user does not have any contact saved_
* Status code: `400 Bad Request`
* Body
```
JSON
{
  "response": "No Contact yet",
  "successful": false
}
```

# Response 3
_unsuccessful request due to that the user have not login_
* Status code: `400 Bad request`
* Body
```
JSON:
{
  "response": "User have not login into application",
  "successful": false
}
```



## DeleteAccountRequest
_This end point is used to delete the account of the user._
# Request
* Url : `localhost:9065/api/request/user/1`
* Method : DELETE
* Header : 
  * Content-Type application/json
* Parameter :
  * userID : (required) Long


# Response 1
_successful request_
* Status Code: `200 OK`
* Body
```
JSON:
{
  "response": "Account has been deleted......",
  "successful": true
}
```

# Response 2
_unsuccessful reques due to that user with the id does not exist_
* Status code : `400 Bad Request`
* Body
```
JSON:
{
  "response": "User does not exist",
  "successful": false
}
```
# Response 3
_unsuccessful request due to that the user has not login into application_
* Status code: `400 Bad Request`
* Body
```
JSON:
{
  "response": "User have not login",
  "successful": false
}
```



## BlockContactRequest
_This end point is used to block contact from the user contacts_
# Request
* Url: `localhost:9065/api/request/contact/2?contactName=BigTobi`
* Method: PATCH
* Header: 
  * Content-type application/json
* Parameter:
  * userID (required) Long
  * contactName (required) String


# Response 1
_successful request_
* Status Code: `200 OK`
* Body:
```
JSON:
{
"response": "Olashiles is blocked",
"successful": true
}
```

# Response 2
_unsuccessful request due to that the contact does not exist_
* Status Code: `400 Bad Request`
* Body
```
JSON:
{
"response": "Contact does not exist",
"successful": false
}
```

# Response 3
_unsuccessful request due to that user with the id does not exist_
* Status Code: 400 Bad Request
* Body
```
JSON:
{
"response": "User does not exist",
"successful": false
}
```

# Response 4
_unsuccessful request due to that the user have not login into application_
* Status Code: `400 Bad Request`
* Body
```
JSON:
{
"response": "User have not login",
"successful": false
}
```

# Response 5
_unsuccessful request if the contact is blocked already_
* Status Code: `400 Bad Request`
* Body
```
JSON:
{
"response": "The contact has been blocked already",
"successful": false
}
```





## UnBlock-Contact-Request
_This endpoint is used to unblock contact from the user contacts which implies that if they want to view their contacts they wont see the contact_

# Request:
* Url: `localhost:9065/api/request/contact/2?contactName=BigTobi`
* Method: POST
* Header: 
  * Content-type application/json
* Parameter :
  * userID: (required) Long
  * username: (required) String


# Response 1:
_successful request_
* Status Code: `200 OK`
* Body:
```
JSON:
{
"response": "Olashiles has been unblocked",
"successful": true
}
```

# Response 2
_unsuccessful request due to that the user have not login into application_
* Status Code: `400 Bad Request`
* Body
```
JSON:
{
"response": "User have not login",
"successful": false
}
```

# Response 3
_unsuccessful request due to that the contact does not exist_
* Status Code: 400 Bad Request
* Body
```
JSON:
{
  "response": "Contact does not exist",
  "successful": false
}
```

# Response 4
_unsuccessful request due to that the contact have not be block already ooo_
* Status Code: `400 Bad Request`
* Body
```
JSON:
{
"response": "The contact is not blocked",
"successful": false
}
```

# Response 5
_unsuccessful request due to that the user does not exist_
* Status Code: 400 Bad Request
* Body
```
JSON
{
"response": "User does not exist",
"successful": false
}
```

