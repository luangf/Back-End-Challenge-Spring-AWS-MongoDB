# 🛍️ Product Catalog Management System

## Project Overview 📖

#### Back end project that I carried out for learning with the help of a tutorial by Fernanda Kipper about a programming challenge.

#### Main functionality of the application: Product catalog management system in a marketplace application.

## Technologies: 🛠️
* Java 17
* Spring Boot 3.3.5
* RESTful APIs
* Postman
* MongoDB Database on local machine
    * MongoDB Compass as a visual interface
* Design Patterns
* AWS (Amazon Web Services) Cloud;
    * SNS (Simple Notification Service)
    * SQS (Simple Queue Service)
    * S3 (Simple Storage Service)
    * Lambda
    * CloudWatch (logs)
    * IAM (Identity and Access Management)
    * KMS (Key Management Service)
* IDE used: VSCode

## AWS Flow Overview: ☁️
1. Customer sends request to API;

2. API:
* Performs operations with the database;
* Post in SNS topic (only when some product or category is created or updated for now);

3. SNS topic send the message to SQS (so messages can be handled asynchronously, on a queue);

3. SQS send to Lambda (serverless function);

4. Lambda is triggered when a message arrives in SQS, and save/update on S3;

5. S3 bucket (to store the JSON of the products/categories).