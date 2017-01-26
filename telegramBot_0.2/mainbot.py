import json 
import time
import re
import requests
import random
import datetime

# dictionaries:
import dictRequest
import dictTime
import dictDate

tok_buffer =''
dictTag = {}
concatenationList = ['bom','boa','Bom','Boa']
TOKEN = "292444370:AAGiqsll_zwYbIRMQ9Hg_8pfihj8y1Ig8Ac"
URL = "https://api.telegram.org/bot{}/".format(TOKEN)


c_KEYWORDS = ("Olá","Ola","ola","olá","Oi", "bom dia", "bom tarde","boa noite","Tudo bem?","Tudo bem","tudo bem","tudo bem")
SCHEDULLING_KEYWORDS = ("marcar","combinar","definir","sondar","planejar","decidir","acertar","casar","horário")
 

GREETING_RESPONSES = ["Olá, tudo bem com você?","Oi,tudo bem com você? "]



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

    return (text, chat_id,name)



def send_message(text, chat_id):
    url = URL + "sendMessage?text={}&chat_id={}".format(text, chat_id)
    get_url(url)
    print(text)
    


#def cleaner(sentence,markers):
#    
#    clean = ''.join( c for c in sentence if  c not in markers )
#    
#    return clean
#    
#    
#def check_for_asking(features):
#    """If any of the words in the user's input was a greeting, return a greeting response"""
#    for feat in features:
#        if feat in SCHEDULLING_KEYWORDS:
#            return shedulling_anwser(features)
#
#def shedulling_anwser(features):
#
#    for feat in features:
#        if feat in date.keys():
#            return 'Você gostaria de marcar na '+date[feat]+'?'
##            
##        if feat in TIME_KEYWORD:
##            None
##    
#
#def check_for_greeting(features):
#    """If any of the words in the user's input was a greeting, return a greeting response"""
#    for feat in features:
#        if feat in GREETING_KEYWORDS:
#            return random.choice(GREETING_RESPONSES)  
#
#def respond(sentence):  
#    
#    cleanSentence = cleaner(sentence,'?:!;)([],.')
#    
#    tokens = cleanSentence.split(' ')   
#    
#    features = [str(tok) for tok in tokens if len(str(tok))>2]
#    
#    resp=''    
#    
#    hello = check_for_greeting(features)
#    if hello:
#        resp+=hello
#    
#    check = check_for_asking(features)
#    
#    if check:
#        
#        resp+=check
#        
#    if not resp:
#        #
#        # If we didn't override the final sentence, try to construct a new one:
##        if not pronoun:
##            resp = random.choice(NONE_RESPONSES)
##        elif pronoun == 'I' and not verb:
##            resp = random.choice(COMMENTS_ABOUT_SELF)
##        else:
##            resp = construct_response(pronoun, noun, verb)
#            resp = sentence
#    
#            
#    # If we got through all that with nothing, use a random response
##    if not resp:
#            #        resp = random.choice(NONE_RESPONSES)
##        
##    logger.info("Returning phrase '%s'", resp)
##    # Check that we're not going to say anything obviously offensive
##    filter_response(resp)
#    
#    return resp
#
def cleaner(sentence,markers):
    
    clean = ''.join( c for c in sentence if  c not in markers )
    return clean
      
def tokenTest(token):

    if token.isdigit() : return True                                            #test if token is a number
    if re.search(r'\d{2}:\d{2}',token): return True                             #test if token is in time format
    if re.search(r'\d{1}º',token) or re.search(r'\d{1}ª',token): return True    #test if token is a ordinal number
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

        month_day = dicDate.month[tok]
        dt = date(tdy.year,td.month,month_day)
        return dt.isoformat()

    if tok in dictDate.week.keys():

        week_day = dicDate.week[tok]
        delta = tdy.weekday()-week_day
        dt = tdy + datetime.timedelta(days=delta)
        return dt.isoformat()
        
    if tok in dictDate.relative.keys():
        relative_day = dicDate.relative[tok]
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
            
    
def findTags(toks):
    
    tags = {'user':None,'request':None,'date':None,'time':None,'confirm':None,'job':None,'last_call':None}

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
def main():
    last_textchat = (None, None)
    while True:
        text, chat,name = get_last_chat_id_name_and_text(get_updates())
        if (text, chat) != last_textchat:
            toks = tokenization(text)
            new_tags = findTags(toks)
#            old_tags = knowTags(chat)
#            tags = mergerTags(new_tags,old_tags)
#            resp = answer(tags,feat)
#            send_message(resp, chat)
            send_message(str(new_tags),chat)

#            if tags['schedule']=='confirmed':
#                shedule(name,tags['request'],tags['date'],tags['time'],'schedule.csv')
#            save_tags(chat,tags,'user.csv')
            last_textchat = (text, chat)
            
            
            
#            print (name+' '+str(chat) +'  '+text)
#            send_message(text, chat)
#            last_textchat = (text, chat)
        time.sleep(0.5)


if __name__ == '__main__':
    main()

