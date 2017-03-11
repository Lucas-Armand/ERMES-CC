from flask import Flask, request
import requests
import json

BOOK = []
app = Flask(__name__)

ACCESS_TOKEN = "EAAB4yRJJXpQBAPZC57WEk31t8Ot3dOq40gEZAcXBlN5JPDnJXIu56szS3adjKtXNBTOOyU1CKy5NTwU0hKamFwlLioXEY24hVPZBZCnsdqBFNu0YXPA4twge1XZCtPNvldc6JwzAFZA2DifRoSABSZCPg4nSkltU0iJeZB2AAsxYvgZDZD"
VERIFY_TOKEN = "secret"


def fb_reply(user_id, msg):
    data = {
        "recipient": {"id": user_id},
        "message": {"text": msg}
    }
    resp = requests.post("https://graph.facebook.com/v2.6/me/messages?access_token=" + ACCESS_TOKEN, json=data)
    print(resp.content)


def handle_verification(args):
    print(request.url)
    if request.args['hub.verify_token'] == VERIFY_TOKEN:
        return request.args['hub.challenge']
    else:
        return "Invalid verification token"


def resetBook():
    if BOOK != []:
        tr = int(BOOK[-1]['time']) # time of reference
        for dic in BOOK: 
            if tr - int(dic['time']) < 600000:
                break
            else:
                BOOK.remove(dic)


def handle_communication(args):
    resetBook()
    data = json.dumps(BOOK)
    return data


def handle_facebook_message(data):
    timemsg = data['entry'][0]['messaging'][0]['timestamp']
    send_ID = data['entry'][0]['messaging'][0]['sender']['id']
    page_ID = data['entry'][0]['id']
    message = data['entry'][0]['messaging'][0]['message']['text']

    BOOK.append({
        'time':timemsg,
        'sdID':send_ID,
        'pgID':page_ID,
        'msg':message})

def handle_bot_message(data):
    msg_type= data['type']
    text    = data['text']
    chat_id = data['chatid']
    if msg_type == 'facebook_msg':
        answer = fb_reply(chat_id,text)
    return answer

@app.route('/', methods=['GET'])
def handle_get():
    args = request.args
    if request.args['hub.mode'] == 'subscribe':
        return handle_verification(args)
    elif request.args['hub.mode'] == 'communication':
        return handle_communication(args)
    else:
        return "Invalid request"


@app.route('/', methods=['POST'])
def handle_incoming_messages():
    data = request.json
    if data["object"] == "page":
        # this means that we have a facebook msg
        handle_facebook_message(data)

    elif data ["object"] == "bot":
        # this means thet we have a bot msg
        handle_bot_message(data)
    return "ok"


if __name__ == '__main__':
    app.run(debug=True)

####
# algumas observações:
# se você entrar com seguinte url:
# https://bcd920bf.ngrok.io/?hub.mode=subscribe&hub.challenge=980636180&hub.verify_token=secret
#
# você tera o seguinte resultado:
# 980636180
#
#
# os dados enviados do facebook são no formato:
#{
#   "entry": [
#       {
#           "id": "424316070951952",
#           "messaging": [
#               {
#                   "message": {
#                       "mid": "mid.1488637736684:66fb343571",
#                       "seq": 110029,
#                       "text": "teste"
#                   },
#                   "recipient": {
#                       "id": "424316070951952"
#                  },
#                   "sender": {
#                       "id": "1272971316150819"
#                   },
#                   "timestamp": 1488637736684
#               }
#           ],
#           "time": 1488637736758
#       }
#   ],
#   "object": "page"
#}
#
# A seguir todos os parametros do "requests" quendo GET e POST:
# POST:
#{'shallow': False,
#'url_rule': <Rule '/' (OPTIONS, POST) -> handle_incoming_messages>,
#'view_args': {},
#'environ': {'wsgi.run_once': False,
#'wsgi.url_scheme': 'http',
#'werkzeug.request': <Request 'http://bcd920bf.ngrok.io/' [POST]>,
#'SERVER_SOFTWARE': 'Werkzeug/0.11.15',
#'wsgi.errors': <_io.TextIOWrapper name='<stderr>' mode='w' encoding='UTF-8'>,
#'HTTP_ACCEPT': '*/*',
#'werkzeug.server.shutdown': <function WSGIRequestHandler.make_environ.<locals>.shutdown_server at 0x7fac84adbe18>,
#'SERVER_PORT': '5000',
#'HTTP_X_FORWARDED_FOR': '173.252.123.144',
#'wsgi.multithread': False,
#'HTTP_X_FORWARDED_PROTO': 'https',
#'SERVER_PROTOCOL': 'HTTP/1.1',
#'SCRIPT_NAME': '',
#'REQUEST_METHOD': 'POST',
#'HTTP_X_HUB_SIGNATURE': 'sha1=5b3f428bdfe88cd181c8adb13e20456f9e667298',
#'QUERY_STRING': '',
#'CONTENT_LENGTH': '262',
#'HTTP_ACCEPT_ENCODING': 'deflate, gzip',
#'PATH_INFO': '/',
#'wsgi.version': (1, 0),
#'wsgi.input': <_io.BufferedReader name=5>,
#'HTTP_HOST': 'bcd920bf.ngrok.io',
#'SERVER_NAME': '127.0.0.1',
#'wsgi.multiprocess': False,
#'CONTENT_TYPE': 'application/json',
#'REMOTE_PORT': 57462,
#'REMOTE_ADDR': '127.0.0.1'},
#'url': 'http://bcd920bf.ngrok.io/'}
#
# GET:
#{'args': ImmutableMultiDict([('test',
#'12345'), ('hub.mode',
#'communication')]),
#'url_rule': <Rule '/' (HEAD, OPTIONS, GET) -> handle_get>,
#'view_args': {},
#'shallow': False,
#'environ': {'REQUEST_METHOD': 'GET',
#'HTTP_USER_AGENT': 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36',
#'werkzeug.request': <Request 'http://bcd920bf.ngrok.io/?hub.mode=communication&test=12345' [GET]>,
#'HTTP_X_FORWARDED_PROTO': 'https',
#'QUERY_STRING': 'hub.mode=communication&test=12345',
#'wsgi.multithread': False,
#'SCRIPT_NAME': '',
#'wsgi.url_scheme': 'http',
#'werkzeug.server.shutdown': <function WSGIRequestHandler.make_environ.<locals>.shutdown_server at 0x7fd5c23c2e18>,
#'wsgi.multiprocess': False,
#'SERVER_PROTOCOL': 'HTTP/1.1',
#'HTTP_UPGRADE_INSECURE_REQUESTS': '1',
#'SERVER_NAME': '127.0.0.1',
#'HTTP_ACCEPT_ENCODING': 'gzip, deflate, sdch, br',
#'wsgi.input': <_io.BufferedReader name=5>,
#'HTTP_ACCEPT_LANGUAGE': 'en-US,en;q=0.8,pt-BR;q=0.6,pt;q=0.4',
#'SERVER_SOFTWARE': 'Werkzeug/0.11.15',
#'SERVER_PORT': '5000',
#'CONTENT_TYPE': '',
#'wsgi.errors': <_io.TextIOWrapper name='<stderr>' mode='w' encoding='UTF-8'>,
#'HTTP_X_FORWARDED_FOR': '179.158.216.2',
#'wsgi.run_once': False,
#'CONTENT_LENGTH': '',
#'HTTP_HOST': 'bcd920bf.ngrok.io',
#'REMOTE_PORT': 57582,
#'PATH_INFO': '/',
#'REMOTE_ADDR': '127.0.0.1',
#'HTTP_ACCEPT': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
#'wsgi.version': (1, 0)}}
#
# Aqui um lista de tempos gerados com mensagens enviadas com +- 5 seg de dif.:
# 'time": 1488651779521,
# 'time": 1488651784926,
# 'time": 1488651789552,
# 'time": 1488651794825,
# 'time": 1488651799533,
#
# Logo isso seguinifica:
# 'time": 1488651779521, = 1 488 651 779,521 segundos


