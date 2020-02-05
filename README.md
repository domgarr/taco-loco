# taco-loco


# quick-start 
- import as an "Existing Maven Project"
- mvn spring-boot:run

or

- mvn package
- cd target
- java -jar *.jar


# api

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
  Is a POST request requring an array of objects, each object has an id to a menu item and the quantity ordered.
  - 'id' can range from 1-4. Note: Any Ids out of range will throw an error.
  - 'quantity' can be any positive integer. Note: Any negative integers will throw an error. Max quantity of an item is 100.

```
{
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
}
```
returns a JSON with a totalCost field. Note: Discount applied on orders containing 4 or more items.

```
{
  "totalCost": 9.60
}
```
