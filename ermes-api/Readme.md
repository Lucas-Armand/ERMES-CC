<!DOCTYPE html><html><head><meta charset="utf-8"></head><body id="preview">
<h2><a id="ERMES_API_0"></a><strong>ERMES API</strong></h2>
<p><strong>Demorou, mas chegou! :D</strong><br>
Mas por que demorou? Foram necessárias várias otimizações para a API alcançar a simplicidade que ela possui agora sem perder a eficiência, e isso custou bastante tempo e pesquisas (mas é claro que eu acabei perdendo tempo com outras coisas como jogos e horário de sono desregulado).</p>
<p>Mas enfim… rsrs</p>
<p>A nossa API faz basicamente o que todas fazem: servir de intermediária entre as aplicações e o banco de dados. Por enquanto ela permite apenas que você insira e pegue informações do banco, mas em breve ela terá funções para evitar o overwork das aplicações.<br>
Ela vai ficar executando 24h no meu ambiente local e vocês poderão acessá-la pela internet através do link que o Ngrok vai gerar.</p>

<p><h3><strong>URL: <em>http://4eaa7f75.ngrok.io/api/</em></strong><br>
(atualizado em 21/04/2017)</h3></p>

<p><strong>Observação</strong>: Eu sugiro a vocês utilizarem o Insomnia ou o Postman (clients para testar requisições em APIs). É bem mais fácil e intuitivo do que usar o cURL puro, por exemplo.</p>
<h1><a id="Autenticao_15"></a>Autenticação</h1>
<p>Para acessar qualquer URL da API o client precisa ser autenticado com um login de um dos usuários que estão no banco. Algumas URLs precisam que o usuário tenha permissões de administrador. Por isso, por padrão, a API cria dois usuários de acesso:</p>
<blockquote>
<p>ADMIN<br>
E-mail: <a href="mailto:admin@email.com">admin@email.com</a><br>
Password: 12345</p>
</blockquote>
<blockquote>
<p>USER<br>
E-mail: <a href="mailto:user@email.com">user@email.com</a><br>
Password: 12345</p>
</blockquote>
<h1><a id="Exemplo_com_cURL_27"></a>Exemplo com cURL:</h1>
<pre><code>curl -u admin@email.com:12345 http://{ngrok-link}/api/
</code></pre>
<blockquote>
<p>GET /api/<br>
Host: {ngrok-link}<br>
Authorization: Basic dXNlckBlbWFpbC5jb206MTIzNDU=<br>
Content-Length: 0<br>
Content-Type: application/x-www-form-urlencoded</p>
</blockquote>
<p>Returns:</p>
<blockquote>
<p>HTTP/1.1 200<br>
Content-Type: text/plain;charset=UTF-8<br>
Content-Length: 9<br>
Date: Thu, 06 Apr 2017 21:44:13 GMT</p>
</blockquote>
<pre><code>ERMES API
</code></pre>
<h1><a id="Requests_46"></a>Requests</h1>
<h2><a id="As_entidades_funcionam_com_o_mesmo_padro_de_requests_48"></a>As entidades funcionam com o mesmo padrão de requests.</h2>
<p>Tudo será melhor especificado na próxima atualização da API.</p>
<p><strong>Listar netos</strong><br>
GET/pais/{paiId}/filhos/{filhoId}/netos</p>
<p><strong>Criar neto</strong> (com bisneto especificado em JSON no corpo do request)<br>
POST /pais/{paiId}/filhos/{filhoId}/netos</p>
<p><strong>Selecionar neto</strong><br>
GET /pais/{paiId}/filhos/{filhoId}/netos/{netoId}</p>
<p><strong>Atualizar neto</strong> (com bisneto especificado em JSON no corpo do request)<br>
PUT /pais/{paiId}/filhos/{filhoId}/netos/{netoId}</p>
<p><strong>Deletar neto</strong><br>
DELETE /pais/{paiId}/filhos/{filhoId}/netos/{netoId}</p>
<h2><a id="Exemplos_de_requests_66"></a>Exemplos de requests</h2>
<h2><a id="Business_68"></a>Business</h2>
<h3><a id="Retrieves_a_list_of_businesses_70"></a>Retrieves a list of businesses</h3>
<blockquote>
<p>GET /api/businesses<br>
Host: {ngrok-link}<br>
Authorization: Basic YWRtaW5AZW1haWwuY29tOjEyMzQ1<br>
Content-Type: application/x-www-form-urlencoded<br>
Content-Length: 0</p>
</blockquote>
<p>Returns:</p>
<blockquote>
<p>GET /api/businesses<br>
Host: {ngrok-link}<br>
Authorization: Basic YWRtaW5AZW1haWwuY29tOjEyMzQ1</p>
</blockquote>
<pre><code class="language-json">[
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
</code></pre>
<h4><a id="Creates_a_new_business_148"></a>Creates a new business</h4>
<blockquote>
<p>POST /api/businesses<br>
Host: {ngrok-link}<br>
Authorization: Basic YWRtaW5AZW1haWwuY29tOjEyMzQ1<br>
Content-Type: application/json<br>
Content-Length: 123</p>
</blockquote>
<pre><code class="language-json">{
    "<span class="hljs-attribute">name</span>": <span class="hljs-value"><span class="hljs-string">"PetShop Snoopy"</span></span>,
    "<span class="hljs-attribute">openingTime</span>": <span class="hljs-value">[
        <span class="hljs-number">8</span>,
        <span class="hljs-number">0</span>
    ]</span>,
    "<span class="hljs-attribute">closingTime</span>": <span class="hljs-value">[
        <span class="hljs-number">20</span>,
        <span class="hljs-number">0</span>
    ]</span>,
    "<span class="hljs-attribute">fieldOfActivity</span>": <span class="hljs-value"><span class="hljs-string">"Animais"</span>
</span>}
</code></pre>
<p>Returns:</p>
<blockquote>
<p>HTTP/1.1 201<br>
Content-Length: 0<br>
Date: Thu, 06 Apr 2017 20:18:38 GMT</p>
</blockquote>
<h3><a id="Retrieves_a_specific_business_176"></a>Retrieves a specific business</h3>
<blockquote>
<p>GET /api/businesses/{businessId}<br>
Host: {ngrok-link}<br>
Authorization: Basic dXNlckBlbWFpbC5jb206MTIzNDU=<br>
Content-Length: 0<br>
Content-Type: application/x-www-form-urlencoded</p>
</blockquote>
<p>Returns:</p>
<blockquote>
<p>HTTP/1.1 200<br>
Content-Type: application/json;charset=UTF-8<br>
Date: Thu, 06 Apr 2017 22:51:19 GMT</p>
</blockquote>
<pre><code class="language-json">{
    "<span class="hljs-attribute">id</span>": <span class="hljs-value"><span class="hljs-number">4</span></span>,
    "<span class="hljs-attribute">name</span>": <span class="hljs-value"><span class="hljs-string">"Oficina do Doc"</span></span>,
    "<span class="hljs-attribute">openingTime</span>": <span class="hljs-value">[
        <span class="hljs-number">8</span>,
        <span class="hljs-number">0</span>
    ]</span>,
    "<span class="hljs-attribute">closingTime</span>": <span class="hljs-value">[
        <span class="hljs-number">20</span>,
        <span class="hljs-number">0</span>
    ]</span>,
    "<span class="hljs-attribute">fieldOfActivity</span>": <span class="hljs-value"><span class="hljs-string">"Automotiva"</span></span>,
    "<span class="hljs-attribute">services</span>": <span class="hljs-value">[
        {
            "<span class="hljs-attribute">id</span>": <span class="hljs-value"><span class="hljs-number">4</span>
        </span>},
        {
            "<span class="hljs-attribute">id</span>": <span class="hljs-value"><span class="hljs-number">5</span>
        </span>}
    ]</span>,
    "<span class="hljs-attribute">employees</span>": <span class="hljs-value">[
        {
            "<span class="hljs-attribute">id</span>": <span class="hljs-value"><span class="hljs-number">4</span>
        </span>},
        {
            "<span class="hljs-attribute">id</span>": <span class="hljs-value"><span class="hljs-number">5</span>
        </span>}
    ]</span>,
    "<span class="hljs-attribute">customers</span>": <span class="hljs-value">[
        {
            "<span class="hljs-attribute">id</span>": <span class="hljs-value"><span class="hljs-number">4</span>
        </span>},
        {
            "<span class="hljs-attribute">id</span>": <span class="hljs-value"><span class="hljs-number">5</span>
        </span>}
    ]</span>,
    "<span class="hljs-attribute">appointments</span>": <span class="hljs-value">[
        {
            "<span class="hljs-attribute">id</span>": <span class="hljs-value"><span class="hljs-number">3</span>
        </span>},
        {
            "<span class="hljs-attribute">id</span>": <span class="hljs-value"><span class="hljs-number">4</span>
        </span>}
    ]
</span>}
</code></pre>
<h3><a id="Updates_a_specific_business_237"></a>Updates a specific business</h3>
<blockquote>
<p>PUT /api/businesses/{businessId}<br>
Host: {ngrok-link}<br>
Authorization: Basic dXNlckBlbWFpbC5jb206MTIzNDU=<br>
Content-Type: application/json<br>
Content-Length: 136</p>
</blockquote>
<pre><code class="language-json">{
    "<span class="hljs-attribute">id</span>": <span class="hljs-value"><span class="hljs-number">4</span></span>,
    "<span class="hljs-attribute">name</span>": <span class="hljs-value"><span class="hljs-string">"Oficina do Doc"</span></span>,
    "<span class="hljs-attribute">openingTime</span>": <span class="hljs-value">[
        <span class="hljs-number">8</span>,
        <span class="hljs-number">0</span>
    ]</span>,
    "<span class="hljs-attribute">closingTime</span>": <span class="hljs-value">[
        <span class="hljs-number">22</span>,                 // Linha alterada
        <span class="hljs-number">0</span>
    ]</span>,
    "<span class="hljs-attribute">fieldOfActivity</span>": <span class="hljs-value"><span class="hljs-string">"Automotiva"</span>
</span>}
</code></pre>
<p>Returns:</p>
<blockquote>
<p>HTTP/1.1 200<br>
Content-Length: 0<br>
Date: Fri, 07 Apr 2017 01:16:57 GMT</p>
</blockquote>
<h2><a id="Service_266"></a>Service</h2>
<h3><a id="Retrieves_the_services_of_a_business_268"></a>Retrieves the services of a business</h3>
<blockquote>
<p>GET /api/businesses/{businessId}/services<br>
Host: {ngrok-link}<br>
Authorization: Basic YWRtaW5AZW1haWwuY29tOjEyMzQ1</p>
</blockquote>
<p>Returns:</p>
<blockquote>
<p>HTTP/1.1 200<br>
Content-Type: application/json;charset=UTF-8<br>
Date: Tue, 18 Apr 2017 10:56:44 GMT</p>
</blockquote>
<pre><code class="language-json">[
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
</code></pre>
<h4><a id="Creates_a_new_service_for_a_business_296"></a>Creates a new service for a business</h4>
<blockquote>
<p>POST /api/businesses/{businessId}/services<br>
Host: {ngrok-link}<br>
Authorization: Basic YWRtaW5AZW1haWwuY29tOjEyMzQ1<br>
Content-Type: application/json<br>
Content-Length: 161</p>
</blockquote>
<pre><code class="language-json">{
    "<span class="hljs-attribute">name</span>": <span class="hljs-value"><span class="hljs-string">"Implante"</span></span>,
    "<span class="hljs-attribute">description</span>": <span class="hljs-value"><span class="hljs-string">"Reposição de um ou mais dentes sem comprometer os dentes adjacentes."</span></span>,
    "<span class="hljs-attribute">duration</span>": <span class="hljs-value"><span class="hljs-number">60.0</span></span>,   
    "<span class="hljs-attribute">price</span>": <span class="hljs-value"><span class="hljs-number">500.0</span>
</span>}
</code></pre>
<p>Returns:</p>
<blockquote>
<p>HTTP/1.1 201<br>
Content-Length: 0<br>
Date: Tue, 18 Apr 2017 11:00:47 GMT</p>
</blockquote>
<h3><a id="Retrieves_a_specific_service_of_business_318"></a>Retrieves a specific service of business</h3>
<blockquote>
<p>GET /api/businesses/{businessId}/services/{serviceId}<br>
Host: {ngrok-link}<br>
Authorization: Basic YWRtaW5AZW1haWwuY29tOjEyMzQ1</p>
</blockquote>
<p>Returns:</p>
<blockquote>
<p>HTTP/1.1 200<br>
Content-Type: application/json;charset=UTF-8<br>
Date: Tue, 18 Apr 2017 11:01:17 GMT</p>
</blockquote>
<pre><code class="language-json">{
    "<span class="hljs-attribute">id</span>": <span class="hljs-value"><span class="hljs-number">6</span></span>,
    "<span class="hljs-attribute">name</span>": <span class="hljs-value"><span class="hljs-string">"Implante"</span></span>,
    "<span class="hljs-attribute">description</span>": <span class="hljs-value"><span class="hljs-string">"Reposição de um ou mais dentes sem comprometer os dentes adjacentes."</span></span>,
    "<span class="hljs-attribute">duration</span>": <span class="hljs-value"><span class="hljs-number">60.0</span></span>,   
    "<span class="hljs-attribute">price</span>": <span class="hljs-value"><span class="hljs-number">500.0</span>
</span>}
</code></pre>
<h3><a id="Updates_a_specific_service_of_business_339"></a>Updates a specific service of business</h3>
<blockquote>
<p>PUT /api/businesses/{businessId}/services/{serviceId}<br>
Host: {ngrok-link}<br>
Authorization: Basic YWRtaW5AZW1haWwuY29tOjEyMzQ1<br>
Content-Type: application/json<br>
Content-Length: 161</p>
</blockquote>
<pre><code class="language-json">{
    "name": "Implante",
    "description": "Reposição de um ou mais dentes sem comprometer os dentes adjacentes.",
    "duration": 45.0,           // Linha alterada
    "price": 500.0
}
</code></pre>
<p>Returns:</p>
<blockquote>
<p>HTTP/1.1 200<br>
Content-Length: 0<br>
Date: Tue, 18 Apr 2017 11:01:26 GMT</p>
</blockquote>
<h3><a id="Deletes_a_specific_service_of_a_business_361"></a>Deletes a specific service of a business</h3>
<blockquote>
<p>DELETE /api/businesses/{businessId}/services/{serviceId}<br>
Host: {ngrok-link}<br>
Authorization: Basic YWRtaW5AZW1haWwuY29tOjEyMzQ1</p>
</blockquote>
<p>Returns:</p>
<blockquote>
<p>HTTP/1.1 204<br>
Date: Tue, 18 Apr 2017 11:01:40 GMT</p>
</blockquote>

</body></html>
