# Menu Cost Calculator 
- The following project was built for a contracting company's interview process and required it to be built using TDD.

# quick-start 
To run the server and client.
- import existing maven project ( I used Eclipse) 
- run as maven build... goals: mvn spring-boot:run

Navigate to localhost:8080/ to utilize a UI that allows for use of the following APIs shown below. 


# APIs

## /menu
  Is a GET request returning Tacoloco's menu.
```
[
	{
		"id":1,
		"name":"Veggie Taco",
		"cost":2.50
	},
	{
		"id":2,
		"name":"Chicken Taco",
		"cost":3.00
	},
	{
		"id":3,
		"name":"Beef Taco",
		"cost":3.00
	},
	{
		"id":4,
		"name":"Chorizo Taco",
		"cost":3.50
	}
]
```

## /order
  Is a POST request requiring an array of objects, each object requires an id (associated to a menu item) and quantity (of that menu item ordered).
  - 'id' can range from 1-4. Note: Any Ids out of range or if the request has an menu item object with a duplicate id, an error will be thrown.
  - 'quantity' can be a positive integer between 1-100. Note: Any negative integers or integers greater than 100 will throw an error.
  - The api will only take an array of 1-4 items, the max amount of items is equal to the amount items on the menu - which is 4.

```

  [
	{
	"id": 1,
	"quantity": 1
	},
	{
	"id": 2,
	"quantity": 1
	},
	{
	"id": 3,
	"quantity": 1
	},
	{
	"id" : 4,
	"quantity" : 1
	}
  ]

```
returns a JSON with a totalCost field. Note: Discount applied on orders containing 4 or more items.

```
{
  "totalCost": 9.60
}
```
