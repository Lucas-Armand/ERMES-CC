import json
import time
import re
import csv
import requests
import datetime
from datetime import date

# dictionaries:
import dictRequest
import dictTime
import dictDate

tok_buffer = ''
dictTag = {}
concatenationList = ['bom', 'boa', 'Bom', 'Boa']
TOKEN = "292444370:AAGiqsll_zwYbIRMQ9Hg_8pfihj8y1Ig8Ac"
URL = "https://api.telegram.org/bot{}/".format(TOKEN)

#
# c_KEYWORDS = ("Olá","Ola","ola","olá","Oi", "bom dia", "bom tarde",
#           "boa noite","Tudo bem?","Tudo bem","tudo bem","tudo bem")
# SCHEDULLING_KEYWORDS = ("marcar","combinar","definir","sondar",
#           "planejar","decidir","acertar","casar","horário")


GREETING_RESPONSES = ["Olá, tudo bem com você?", "Oi,tudo bem com você? "]


def get_url(url):
    response = requests.get(url)
    content = response.content.decode("utf8")
    return content


def get_json_from_url(url):
    content = get_url(url)
    js = json.loads(content)
    return js


def get_updates():
    url = URL + "getUpdates"
    js = get_json_from_url(url)
    return js


def get_last_chat_id_name_and_text(updates):
    num_updates = len(updates["result"])
    last_update = num_updates - 1
    text = updates["result"][last_update]["message"]["text"]
    chat_id = updates["result"][last_update]["message"]["chat"]["id"]
    name = (updates["result"][last_update]["message"]["chat"]["first_name"])

    return (text, chat_id, name)


def send_message(text, chat_id):
    url = URL + "sendMessage?text={}&chat_id={}".format(text, chat_id)
    get_url(url)
    print(text)


def cleaner(sentence, markers):

    clean = ''.join(c for c in sentence if c not in markers)
    return clean


def tokenTest(token):

    if token.isdigit() : return True                                            #test if token is a number
    if re.search(r'\d{2}:\d{2}', token): return True                             #test if token is in time format
    if re.search(r'\d{1}º', token) or re.search(r'\d{1}ª',token): return True    #test if token is a ordinal number
    if len(token)>4: return True                                                #test if token is big enought
    if len(token)==0: return False
    if token[0].isupper(): return True                                          #test if token cappital letter start
    if token =='dia': return True                                               #test if token is 'dia'
    if token in  concatenationList : return True

    return False


def tokenConcatenation(tokens):

    tok_buffer = ''
    new_tokens = []
    for tok in tokens:
        if tok_buffer != '':
            new_tok = tok_buffer+' '+tok
            new_tokens.append(new_tok)
            tok_buffer = ''
        else:
            if  tok in concatenationList:
                tok_buffer = tok
            else:
                new_tokens.append(tok)
    return new_tokens

def tokenization(text):

    cleanSentence = cleaner(text,'?!;)([],.')
    pre_tokens = cleanSentence.split(' ')
    tokens = [token for token in pre_tokens if tokenTest(token)]
    tokens = tokenConcatenation(tokens)

    print(tokens)
    return tokens

def tagRequestTest(tok):

    if tok in dictRequest.dictKeys: return True

def tagDateTest(tok):

    if tok in dictDate.dictKeys : return True

def tagTimeTest(tok):

    if tok in dictTime.dictKeys: return True

def requestConstruct(tok):

    if tok in dictRequest.schedule.keys(): return dictRequest.schedule[tok]
    if tok in dictRequest.reschedule.keys(): return dictRequest.reschedule[tok]
    if tok in dictRequest.cancelation.keys(): return dictRequest.cancelation[tok]
    if tok in dictRequest.information.keys(): return dictRequest.information[tok]

def dateConstruct(tok):

    tdy = date.today()

    if tok in dictDate.month.keys():

        month_day = dictDate.month[tok]
        dt = date(tdy.year,tdy.month,month_day)
        return dt.isoformat()

    if tok in dictDate.week.keys():

        week_day = dictDate.week[tok]
        delta = tdy.weekday()-week_day
        dt = tdy + datetime.timedelta(days=delta)
        return dt.isoformat()

    if tok in dictDate.relative.keys():
        relative_day = dictDate.relative[tok]
        dt = tdy +datetime.timedelta(days=relative_day)
        return dt.isoformat()

def timeConstruct(tok):

    if tok in dictTime.time.keys():

        if tok == '*':
            now = datetime.datetime.now()
            t = now.time()
            return t.isoformat()
        else:
            time_str = dictTime.time[tok]
            time_lst = time_str.split(':')
            t = datetime.time(int(time_lst[0]),int(time_lst[1]))
            return t.isoformat()
    else:
        time_str = tok
        time_lst = time_str.split(':')
        t = datetime.time(time_lst[0],time_lst[1])
        return t.isoformat()


def findTags(toks,chat):

    tags = {'user':'','request':'','date':'','time':'','confirm':'','job':'','lastcall':''}

    tags['last_call'] = datetime.datetime.now().isoformat()
    tags['user'] = str(chat)

    for tok in toks:
        if tagRequestTest(tok): tags['request'] = requestConstruct(tok)
        if tagDateTest(tok): tags['date'] = dateConstruct(tok)
        if tagTimeTest(tok): tags['time'] = timeConstruct(tok)
        if re.search(r'\d{2}:\d{2}',tok): tags['time'] = timeConstruct(tok)
    return tags
#
#    features = [str(tok) for tok in tokens if len(str(tok))>2]
#
#    features = [str(tok) for tok in tokens if len(str(tok))>2]
def tagnization(v):

    tag = {}
    tag['user'] = v[0]
    tag['request'] = v[1]
    tag['date'] = v[2]
    tag['time'] = v[3]
    tag['schedule'] = v[4]
    tag['job'] = v[5]
    tag['lastcall'] = v[6]
    return(tag)

def knowTags(name,chat):
    CSV = open(name,'r')
    data = CSV.read()
    data = data.split('\n')
    data = [row.split(';') for row in data ]
    for i in range(len(data)):
        if data[i][0] == str(chat):
            return (tagnization(data[i]))

    return (None)


def mergerTags(nTag,oTag):

    mTag ={}
    for k in nTag.keys():
        mTag[k] =  nTag[k] if nTag[k]!='' else oTag[k]
    return mTag


def saveTags(chat,tags,name):
    CSV = open(name,'r')
    data = CSV.read()
    data = data.split('\n')
    data = [row.split(';') for row in data ]
    tst  = None
    for i in range(len(data)):
        if data[i][0] == str(chat):
            data [i][1] = tag['request']
            data [i][2] = tag['date']
            data [i][3] = tag['time']
            data [i][4] = tag['schedule']
            data [i][5] = tag['job']
            data [i][6] = tag['lastcall']
            tst = 1
            break
    if not tst:
        v = [tag['user'], tag['request'], tag['date'],
             tag['time'], tag['schedule'], tag['job'], tag['lastcall']]
        data.append(v)

    return ('')



def main():
    last_textchat = (None, None)
    while True:
        text, chat,name = get_last_chat_id_name_and_text(get_updates())
        if (text, chat) != last_textchat:
            toks = tokenization(text)
            new_tags = findTags(toks,chat)
            old_tags = knowTags('users.csv',chat)
            print( old_tags)
            tags = mergerTags(new_tags,old_tags)
#            resp = answer(tags,feat)
#            send_message(resp, chat)
            send_message(str(new_tags),chat)

#            if tags['schedule']=='confirmed':
#                shedule(name,tags['request'],tags['date'],tags['time'],'schedule.csv')
            save_tags(chat,tags,'user.csv')
            last_textchat = (text, chat)



#            print (name+' '+str(chat) +'  '+text)
#            send_message(text, chat)
#            last_textchat = (text, chat)
        time.sleep(0.5)


if __name__ == '__main__':
    main()
