
## API Reference

## Demo on Oracle cloud
I built this java project as docker container, this way we can have a good reliability and it can be easily scalable.

To test this point, i created a new linux virtual machine on oracle cloud, so i've run that container and expose on 8080 port.

http://155.248.226.38:8080/api/v1/cancun/rooms?checkIn=2023-02-01&checkOut=2023-02-03

#### Get all available rooms <<<<

```http
  GET /api/v1/cancun/rooms
```

| Query Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `checkIn` | `string` | **Required**. Your checkIn date yyy-MM-dd |
| `checkIn` | `string` | **Required**. Your checkOut date yyy-MM-dd |

...................................................................................................................
#### Place a reservation <<<<

```http
  POST /api/v1/cancun/reserve
```

| Request Body | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `{"room":{"id":1}, "guests": "Max Verstappen", "checkIn": "2023/01/16 22:59:59", "checkOut": "2023/01/17 22:59:59"}`      | `json` | **Required**.|

...................................................................................................................
#### Update a reservation <<<<
```http
  PUT /api/v1/cancun/reserve/{id}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `integer` | **Required**. Your reservation id|

| Request Body | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `{"room":{"id":1}, "guests": "Max Verstappen", "checkIn": "2023/01/16 22:59:59", "checkOut": "2023/01/17 22:59:59"}`      | `json` | **Required**.|
...................................................................................................................

#### Delete a reservation <<<<
```http
  DELETE /api/v1/cancun/reserve/{id}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `integer` | **Required**. Your reservation id|

