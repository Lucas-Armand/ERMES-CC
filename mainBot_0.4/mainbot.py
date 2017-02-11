import json
import time
import re
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

business_ID = 0
employer_ID = 0



#
# c_KEYWORDS = ("Olá","Ola","ola","olá","Oi", "bom dia", "bom tarde",
#           "boa noite","Tudo bem?","Tudo bem","tudo bem","tudo bem")
# SCHEDULLING_KEYWORDS = ("marcar","combinar","definir","sondar",
#           "planejar","decidir","acertar","casar","horário")

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
    if tok in dictRequest.schedule.keys(): result = dictRequest.schedule[tok]
    if tok in dictRequest.reschedule.keys(): return dictRequest.reschedule[tok]
    if tok in dictRequest.cancelation.keys(): return dictRequest.cancelation[tok]
    if tok in dictRequest.information.keys(): return dictRequest.information[tok]
    return(result)

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
        t = datetime.time(int(time_lst[0]),int(time_lst[1]))
        return t.isoformat()


def findTags(toks,chat):

    tags = {'user':'','request':'','date':'','time':'','confirm':'','job':'','lastcall':''}

    tags['job'] = 'consulta'
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
    tag['confirm'] = v[4]
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


def csvConstruct(matrix):
    text = ''
    for vect in matrix:
        line = ''
        for elem in vect:
            line+= elem+';'
        line = line[0:-1]+'\n'
        text += line
    return(text)


def saveTags(chat,tags,name):
    CSV = open(name,'r')
    data = CSV.read()
    CSV.close()
    data = data.split('\n')
    data = [row.split(';') for row in data ]
    tst  = None
    for i in range(len(data)):
        if data[i][0] == str(chat):
            data [i][1] = tags['request']
            data [i][2] = tags['date']
            data [i][3] = tags['time']
            data [i][4] = tags['confirm']
            data [i][5] = tags['job']
            data [i][6] = tags['lastcall']
            tst = 1
            break
    if not tst:
        v = [tags['user'], tags['request'], tags['date'],
             tags['time'], tags['confirm'], tags['job'], tags['lastcall']]
        data.append(v)

    csvDados = csvConstruct(data)
    CSV = open(name,'w')
    CSV.write(csvDados)
    CSV.close()

    return ()


def validDate(date,data,n):
    # this feature find the valid days in the nexts 'n' days to schedule
    timeAvaible = data[business_ID]['employers'][employer_ID]['time']
    d = date.split('-')
    dayChose = datetime.datetime(int(d[0]),int(d[1]),int(d[2]))
    nDays = [ dayChose+datetime.timedelta(days=i) for i in range(n) ]
    validDays = [day.date().isoformat() for day in nDays if timeAvaible[str(day.weekday())][0]]
    return validDays
#
#    if tok in dictDate.week.keys():
#
#        week_day = dictDate.week[tok]
#        delta = tdy.weekday()-week_day
#        dt = tdy + datetime.timedelta(days=delta)
#        return dt.isoformat()


def expandTB(tB, timeExpand):

    # a timeBlock is period that you can't schedule a new service
    # this feature increase a tB changin yours beginning and/or finish:

    n_tB = []
    for t, dt in zip(tB, timeExpand):
        FMT = '#H:#M'
        time = datetime.datetime.strptime(t, FMT)
        delta_time = datetime.timedelta(hours=dt)
        n_time = time + delta_time
        n_tB.append(str(n_time.hour)+':'+str(n_time.minute))

    return n_tB[0]


def mergerBlocks(tBlocks):

    n = len(tBlocks)
    if n == 1:
        return tBlocks

    FMT = '#H:#M'
    timeBlocks = [[datetime.datetime.strptime(t[0], FMT),
                   datetime.datetime.strptime(t[1], FMT)] for t in tBlocks]

    new_tBlocks = [[tBlocks[0][0]]]
    for i in range(n-1):
        if timeBlocks[i+1][0] > timeBlocks[i][1]:
            t = timeBlocks[i][1].strftime('%H:%M')
            t_ = timeBlocks[i+1][1].strftime('%H:%M')
            new_tBlocks[-1].append(t)
            new_tBlocks.append([t_])
    new_tBlocks[-1].append(tBlocks[-1][1])

    return (new_tBlocks)


def TimeGrid(business_ID, employer_ID, data, d, delta):
    # this feature generate a grid of schedule possibilities with a delta time
    # distance:

    timesAvaibles = data[business_ID]['employers'][employer_ID]['time']
    d_l = [int(e) for e in d.split('-')]    # d format is like '2017-03-12'
    date = datetime.datetime(d_l[0], d_l[1], d_l[2])
    w = date.weekday()
    timeList = timesAvaibles[str(w)]

    grid = []
    for t in timeList:
        time = t.split(' ~ ')
        FMT = '%H:%M'
        startTime = datetime.datetime.strptime(time[0], FMT)
        finishTime = datetime.datetime.strptime(time[-1], FMT)
        avaibleTime = startTime
        while avaibleTime.time() < finishTime.time():
            grid.append(avaibleTime.strftime('%H:%M'))
            avaibleTime = avaibleTime + datetime.timedelta(hours=delta)

    return(grid)


def gridArrange(tGrid, tBlocks):
    # this festure merge the timeGrid with timeBlocks in a avaible timeGrid

    FMT = '#H:#M'
    timeGrid = [datetime.datetime.strpt(t, FMT) for t in tGrid]
    timeBlocks = [[datetime.datetime.strptime(t[0], FMT),
                   datetime.datetime.strptime(t[1], FMT)] for t in tBlocks]
    i = 0
    j = 0

    n = len(timeGrid)

    newGrid = []
    while not i == n:
        time = timeGrid[i]
        if time < timeBlocks[j][0]:
            newGrid.append(time.strftime('%H:%M'))
            i += 1
        elif time < timeBlocks[j][1]:
            i += 1
        else:
            newGrid.append(timeBlocks[j][0].strftime('%H:%M'))
            newGrid.append(timeBlocks[j][1].strftime('%H:%M'))
            j += 1
    return(newGrid)


def avaibleTime(data, sched, tag, date, business_ID, employer_ID, delta):

    # this module return the avaible time(s) to schedule
    # for a employer or 'None':

    jobList = data[business_ID]['services']
    for job in jobList:
        if job['name'] == tag['job']:
            # test to find the right service:

            timeService = job['duration']
            print (sched[business_ID]['employers'][employer_ID]['schedules'].keys())
            if date in sched[business_ID]['employers'][employer_ID]['schedules'].keys():
                # if there is same shedule already done at this date
                # i need to show a list of possibles shedule times:
                print ('ola ola ola ')
                services = sched[business_ID][employer_ID]['schedules'][date]
                timeBlocks = [expandTB(tB, [timeService, 0]) for tB in services]
                timeBlocks = mergerBlocks(timeBlocks)
                timeGrid = TimeGrid(business_ID, employer_ID, data, date, delta)
                timeGrid = gridArrange(timeGrid, timeBlocks)
                return timeGrid
            else:
                timeGrid = TimeGrid(business_ID, employer_ID, data, date, delta)
                return timeGrid
        else:
            print (tag['request'])


def answer(tags, dataTable, schdTable):

    if not tags['request']:

        answer = 'O que posso fazer por você hoje ?'
        options = ['Marcar um horário',
                   'Remarcar meu horário',
                   'Dismarcar meu horário',
                   'Informações a respeito do atendimento']
        return (answer, options)

    elif tags['request'] == 'information':

        answer = 'Information:'
        options = ['information']
        return (answer, options)

    if not tags['date']:

        answer = 'Qual data seria melhor para marcar?'
        options = ['seg', 'ter', 'qua', 'qui', 'sex']
        return (answer, options)

    else:
        dates = validDate(tags['date'], dataTable, 7)
        if dates[0] != tags['date']:
            answer = 'Infelizmente, não temos disponibilidade no dia solicitados, as datas para agendamento mais proximas são:'
            options =  list(dates) + ['Escolher outra data',
                                      'Informações sobre horários de atendimento do consultório']

            return(answer, options)

    if not tags['time']:

        answer = 'Qual horário você tem interesse :'
        options = avaibleTime(dataTable, schdTable, tags, tags['date'],
                              business_ID, employer_ID, 0.5)
        return (answer, options)
    else:

        answer = 'Horário confirmado:'
        options = [tags['date'], tags['time']]
        return (answer, options)

def get_jsonTable(jsonName):

    with open(jsonName) as data_file:
            data = json.load(data_file)
    return data


def main():
    dataTable = get_jsonTable('data.json')
    schdTable = get_jsonTable('schedule.json')

    last_textchat = (None, None)
    while True:
        text, chat, name = get_last_chat_id_name_and_text(get_updates())
        if (text, chat) != last_textchat:
            toks = tokenization(text)
            new_tags = findTags(toks, chat)
            old_tags = knowTags('users.csv', chat)
#            print( old_tags)
            tags = mergerTags(new_tags, old_tags)
#            tags = new_tags
            resp, opts = answer(tags, dataTable, schdTable)
#            send_message(resp, chat)
            opts_ = [opt for opt in opts]
            send_message(resp, chat)
            for opt in opts_:
                send_message(opt, chat)

#            if tags['confirm']=='confirmed':
#                shedule(name,tags['request'],tags['date'],tags['time'],'confirm.csv')
            saveTags(chat, tags, 'users.csv')
            last_textchat = (text, chat)

#            print (name+' '+str(chat) +'  '+text)
#            send_message(text, chat)
#            last_textchat = (text, chat)
        time.sleep(0.5)



if __name__ == '__main__':
    main()
