from flask import Flask, request
import requests
import mainbot
import json

app = Flask(__name__)

ACCESS_TOKEN = "EAAB4yRJJXpQBAM7ht2lC5xqmDDsX8lBkKMPqVKZBO8ZAxJpHak61Sc6cDIpqpxGksZA13aLbRskSAOhI4ZCqP66GPZBzU8ZCjE1WOylfZC7npM0C5lwZCdtM69ll4gjBUqIhi44XXLn2vD1NJn9pxfWHkQx855mZAC9VZAslaOONAY6wZDZD"

@app.route('/', methods=['GET'])
def handle_verification():
    if request.args['hub.verify_token'] == VERIFY_TOKEN:
        return request.args['hub.challenge']
    else:
        return "Invalid verification token"

def reply(user_id, msg):

    data = {
        "recipient": {"id": user_id},
        "message": {"text": msg}
    }

    resp = requests.post("https://graph.facebook.com/v2.6/me/messages?access_token=" + ACCESS_TOKEN, json=data)
    print(resp.content)


@app.route('/', methods=['POST'])
def handle_incoming_messages():
    data = request.json
    sender = data['entry'][0]['messaging'][0]['sender']['id']
    message = data['entry'][0]['messaging'][0]['message']['text']
    reply(sender, message[::-1])

    return "ok"

if __name__ == '__main__':
    app.run(debug=True)

