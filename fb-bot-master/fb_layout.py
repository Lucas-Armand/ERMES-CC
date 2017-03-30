# esse arquivo é uma biblioteca com o layout de mensagens para a comunicação 
# pelo facebook

def buttons(text,buttTextList):
    L = []
    for butt in buttTextList:
        B = {
                "type":"postback",
                "title":butt,
                "payload":butt
                }
        L.append(B)

    msg = {
    "attachment":{
        "type":"template",
        "payload":{
            "template_type":"button",
            "text":text,
            "buttons":L
            }
        }
    }
    return msg

def picture(url):
  msg = {
    "attachment":{
      "type":"image",
      "payload":{
        "url":url
      }
    }
    return msg

###
### Referencia butuões:
#curl -X POST -H "Content-Type: application/json" -d '{
#  "recipient":{
#    "id":"1272971316150819"
#  },
#  "message":{
#    "attachment":{
#      "type":"template",
#      "payload":{
#        "template_type":"button",
#        "text":"What do you want to do next?",
#        "buttons":[
#          {
#            "type":"web_url",
#            "url":"https://petersapparel.parseapp.com",
#            "title":"Show Website"
#          },
#          {
#            "type":"postback",
#            "title":"Start Chatting",
#            "payload":"USER_DEFINED_PAYLOAD"
#          }
#        ]
#      }
#    }
# }
#}' "https://graph.facebook.com/v2.6/me/messages?access_token=EAAB4yRJJXpQBAPZC57WEk31t8Ot3dOq40gEZAcXBlN5JPDnJXIu56szS3adjKtXNBTOOyU1CKy5NTwU0hKamFwlLioXEY24hVPZBZCnsdqBFNu0YXPA4twge1XZCtPNvldc6JwzAFZA2DifRoSABSZCPg4nSkltU0iJeZB2AAsxYvgZDZD"    


###
#### Referência Imagens
#sudo curl -X POST -H "Content-Type: application/json" -d '{
#  "recipient":{
#    "id":"1272971316150819"
#  },
#  "message":{
#    "attachment":{
#      "type":"image",
#      "payload":{
#        "url":"http://i.imgur.com/faWzu.png"
#      }
#    }
#  }
#}'  "https://graph.facebook.com/v2.6/me/messages?access_token=EAAB4yRJJXpQBAIoktLxF50nSeDO3Rt8MGZChufZAWyx4cGNH2Ejwo0NF8wdGsoRS5oZBTxSyz2dK0riA9fjHniys0Wih7ZCoSNf2JPSEGgdX16YuaVbLiQsA3Mr6un94jYPQv53uqtT4W4E70oGk8BRGKQ6wdANAWFzkxmU3ogZDZD"
