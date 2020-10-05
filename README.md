# EluxTestCommander

 Electrolux test task:
 
The task is to design and implement a backend service to control an appliance such as a wash machine or an oven. The API should be REST based and the state of the appliance should be persisted to any form of persistent storage. There is no need for any frontend but we expect there to be a README.md file with build directions and examples of how to invoke the REST API (e.g. curl).
The project should be implemented using Java. Feel free to use any 3rd party library that you are comfortable with. Unit tests are expected and the assignment will be assessed based on good programming practices and design.
Please use GitHub or Bitbucket to publish your source code.


## API

You can use swagger: 

```sh
http://localhost:9090/swagger-ui.html
```

### Examples of usage:

Send command to appliance
```sh
curl -XPOST -H "Content-type: application/json" -d '{ "applianceId": "946910-785-99", "command":"Start"}' 'http://localhost:9090/execute'
```

Register appliance
```sh
curl -XPOST -H "Content-type: application/json" -d '{ "applianceId": "9116910-785-99", "applianceType": "Owen"}' 'http://localhost:9090/appliances'
```

Get all appliances
```sh
curl -XGET -H "Content-type: application/json"  'http://localhost:9090/appliances'
```

Get appliance
```sh
curl -XGET -H "Content-type: application/json" 'http://localhost:9090/appliances/946910-785-99'
```

Unregister appliance
```sh
curl -X DELETE -H "Content-type: application/json" 'http://localhost:9090/appliances/946910-785-99'
```
