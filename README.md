# Sales Taxes

## What does the system do?
Returns details of a fictitious purchase with tax rates calculated on the products.

## What do I need to do to run the system?
- Clone this repository on the computer where the system will run
- Import the cloned project into the eclipse IDE
- Make sure that no services are using port 8080 on the computer where the system will run
- Run the main class located on package `com.jean.salestax.SalestaxApplication`

## How to send products for the system to calculate?
*   Perform all of the previous steps
*   Use an application to test Rest APIs of your choice (Postman, insomnia, ...)
* 	Perform a POST to the address `http://localhost:8080/api/products/purchase_summary`
* 	Paste the code example below into the request body (text json)


```
[
	{
		"origin": "NATIONAL",
		"price": 12.49,
		"quantity": 1,
		"type": "BOOKS",
		"name": "livro"
	},
	{
		"origin": "NATIONAL",
		"price": 14.99,
		"quantity": 1,
		"type": "OTHER",
		"name": "cd"
	},
	{
		"origin": "NATIONAL",
		"price": 0.85,
		"quantity": 1,
		"type": "FOODS",
		"name": "chocolate"
	}
]
```
* 	Run the request, if everything is correct, the request was made and you will be able to observe the calculated product data on the return

## Architecture overview

The project was designed so that each part of the system had the minimum responsibilities, the main are:
- com.jean.salestax.controller.PurchaseController: Receives the requisition, invokes the imputer validator and invokes the person responsible for assembling the purchase summary
- com.jean.salestax.service.impl.PurchaseServiceImpl: Responsible for executing codes necessary for assembling the purchase summary
- com.jean.salestax.factories.ProductFactory: Responsible for making a list of products
- com.jean.salestax.service.impl.CalculatorImpl: Responsible for calculating / rounding values

## Technical system details
- The system uses version 2.3.10 of Spring Boot
- The system was built and tested using java 11.0
- The lombok library was extensively used in the project