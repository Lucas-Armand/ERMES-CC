**ERMES API**
----
**Demorou, mas chegou! :D**
Mas por que demorou? Foram necessárias várias otimizações para a API alcançar a simplicidade que ela possui agora sem perder a eficiência, e isso custou bastante tempo e pesquisas (mas é claro que eu acabei perdendo tempo com outras coisas como jogos e horário de sono desregulado). 

Mas enfim... rsrs

A nossa API faz basicamente o que todas fazem: servir de intermediária entre as aplicações e o banco de dados. Por enquanto ela permite apenas que você insira e pegue informações do banco, mas em breve ela terá funções para evitar o overwork das aplicações.
Ela vai ficar executando 24h no meu ambiente local e vocês poderão acessá-la pela internet através do link que o Ngrok vai gerar.

> URL: _http://{ngrok-link}/api/_ 
> (atualizado em ??/04/2017)

**Observação**: Eu sugiro a vocês utilizarem o Insomnia ou o Postman (clients para testar requisições em APIs). É bem mais fácil e intuitivo do que usar o cURL puro, por exemplo.

# Autenticação

Para acessar qualquer URL da API o client precisa ser autenticado com um login de um dos usuários que estão no banco. Algumas URLs precisam que o usuário tenha permissões de administrador. Por isso, por padrão, a API cria dois usuários de acesso:

> ADMIN
E-mail: admin@email.com
Password: 12345

> USER
E-mail: user@email.com
Password: 12345

# Exemplo com cURL:

    curl -u admin@email.com:12345 http://{ngrok-link}/api/

> GET /api/
> Host: {ngrok-link}
> Authorization: Basic dXNlckBlbWFpbC5jb206MTIzNDU=
> Content-Length: 0
> Content-Type: application/x-www-form-urlencoded

Returns:

> HTTP/1.1 200 
> Content-Type: text/plain;charset=UTF-8
> Content-Length: 9
> Date: Thu, 06 Apr 2017 21:44:13 GMT

    ERMES API

# Requests

## As entidades funcionam com o mesmo padrão de requests.
Tudo será melhor especificado na próxima atualização da API.

**Listar bisnetos**
GET/pais/{paiId}/filhos/{filhoId}/bisnetos

**Criar bisneto** (com bisneto especificado em JSON no corpo do request)
POST /pais/{paiId}/filhos/{filhoId}/bisnetos

**Selecionar bisneto**
GET /pais/{paiId}/filhos/{filhoId}/bisnetos/{bisnetoId}

**Atualizar bisneto** (com bisneto especificado em JSON no corpo do request)
PUT /pais/{paiId}/filhos/{filhoId}/bisnetos/{bisnetoId}

**Deletar bisneto**
DELETE /pais/{paiId}/filhos/{filhoId}/bisnetos/{bisnetoId}

## Exemplos de requests

## Business

### Retrieves a list of businesses

> GET /api/businesses
> Host: {ngrok-link}
> Authorization: Basic YWRtaW5AZW1haWwuY29tOjEyMzQ1
> Content-Type: application/x-www-form-urlencoded
> Content-Length: 0

Returns:

> GET /api/businesses
> Host: {ngrok-link}
> Authorization: Basic YWRtaW5AZW1haWwuY29tOjEyMzQ1

```json
[
	{
		"id": 3,
		"name": "Multi Oral",
		"openingTime": [
			8,
			0
		],
		"closingTime": [
			20,
			0
		],
		"fieldOfActivity": "Saúde",
		"services": [
			{
				"id": 1
			},
			{
				"id": 2
			},
			{
				"id": 3
			}
		],
		"employees": [
			{
				"id": 1
			},
			{
				"id": 2
			},
			{
				"id": 3
			}
		],
		"customers": [
			{
				"id": 1
			},
			{
				"id": 2
			},
			{
				"id": 3
			}
		],
		"appointments": [
			{
				"id": 1
			},
			{
				"id": 2
			}
		]
	},
	{
        "id": 4,
        "name": "Oficina do Doc",
        ...
    }
]
```

#### Creates a new business

> POST /api/businesses
> Host: {ngrok-link}
> Authorization: Basic YWRtaW5AZW1haWwuY29tOjEyMzQ1
> Content-Type: application/json
> Content-Length: 123
```json
{
	"name": "PetShop Snoopy",
	"openingTime": [
		8,
		0
	],
	"closingTime": [
		20,
		0
	],
	"fieldOfActivity": "Animais"
}
```

Returns:

> HTTP/1.1 201 
> Content-Length: 0
> Date: Thu, 06 Apr 2017 20:18:38 GMT

### Retrieves a specific business

> GET /api/businesses/{id}
> Host: {ngrok-link}
> Authorization: Basic dXNlckBlbWFpbC5jb206MTIzNDU=
> Content-Length: 0
> Content-Type: application/x-www-form-urlencoded

Returns:

> HTTP/1.1 200 
> Content-Type: application/json;charset=UTF-8
> Date: Thu, 06 Apr 2017 22:51:19 GMT
```json
{
	"id": 4,
	"name": "Oficina do Doc",
	"openingTime": [
		8,
		0
	],
	"closingTime": [
		20,
		0
	],
	"fieldOfActivity": "Automotiva",
	"services": [
		{
			"id": 4
		},
		{
			"id": 5
		}
	],
	"employees": [
		{
			"id": 4
		},
		{
			"id": 5
		}
	],
	"customers": [
		{
			"id": 4
		},
		{
			"id": 5
		}
	],
	"appointments": [
		{
			"id": 3
		},
		{
			"id": 4
		}
	]
}
```

### Updates a specific business

> PUT /api/businesses/{id}
> Host: {ngrok-link}
> Authorization: Basic dXNlckBlbWFpbC5jb206MTIzNDU=
> Content-Type: application/json
> Content-Length: 136
```json
{
	"id": 4,
	"name": "Oficina do Doc",
	"openingTime": [
		8,
		0
	],
	"closingTime": [
		22,                 // Linha alterada
		0
	],
	"fieldOfActivity": "Automotiva"
}
```

Returns:

> HTTP/1.1 200 
> Content-Length: 0
> Date: Fri, 07 Apr 2017 01:16:57 GMT

## Service

### Retrieves the services of a business

> GET /api/businesses/3/services
> Host: {ngrok-link}
> Authorization: Basic YWRtaW5AZW1haWwuY29tOjEyMzQ1

Returns:

> HTTP/1.1 200 
> Content-Type: application/json;charset=UTF-8
> Date: Tue, 18 Apr 2017 10:56:44 GMT
```json
[
    {
		"id": 1,
		"name": "Consulta",
		"description": "Análise dos dentes e tecidos moles.",
		"duration": 30,
		"price": 50.0
	},
	{
		"id": 2,
		"name": "Clareamento",
		...
	}
]
```

#### Creates a new service for a business

> POST /api/businesses/3/services
> Host: {ngrok-link}
> Authorization: Basic YWRtaW5AZW1haWwuY29tOjEyMzQ1
> Content-Type: application/json
> Content-Length: 161
```json
{
	"name": "Implante",
	"description": "Reposição de um ou mais dentes sem comprometer os dentes adjacentes.",
	"duration": 60.0,	
	"price": 500.0
}
```

Returns:

> HTTP/1.1 201 
> Content-Length: 0
> Date: Tue, 18 Apr 2017 11:00:47 GMT

### Retrieves a specific service of business

> GET /api/businesses/3/services/6
> Host: {ngrok-link}
> Authorization: Basic YWRtaW5AZW1haWwuY29tOjEyMzQ1

Returns:

> HTTP/1.1 200 
> Content-Type: application/json;charset=UTF-8
> Date: Tue, 18 Apr 2017 11:01:17 GMT
```json
{
	"id": 6,
	"name": "Implante",
	"description": "Reposição de um ou mais dentes sem comprometer os dentes adjacentes.",
	"duration": 60.0,	
	"price": 500.0
}
```

### Updates a specific service of business

> PUT /api/businesses/3/services/6
> Host: {ngrok-link}
> Authorization: Basic YWRtaW5AZW1haWwuY29tOjEyMzQ1
> Content-Type: application/json
> Content-Length: 161
```json
{
	"name": "Implante",
	"description": "Reposição de um ou mais dentes sem comprometer os dentes adjacentes.",
	"duration": 45.0,           // Linha alterada
	"price": 500.0
}
```

Returns:

> HTTP/1.1 200 
> Content-Length: 0
> Date: Tue, 18 Apr 2017 11:01:26 GMT

### Deletes a specific service of a business

> DELETE /api/businesses/3/services/6
> Host: {ngrok-link}
> Authorization: Basic YWRtaW5AZW1haWwuY29tOjEyMzQ1

Returns:

> HTTP/1.1 204 
> Date: Tue, 18 Apr 2017 11:01:40 GMT