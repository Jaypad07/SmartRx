  Feature: Successful user registration
    Scenario:
     Given a user has a unique email
     When they enter their email and password
     Then the password and their information is stored in database