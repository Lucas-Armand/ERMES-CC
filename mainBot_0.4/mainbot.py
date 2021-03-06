import json
import time
import re
import random
import requests
import datetime
from datetime import date

# dictionaries:
import dictRequest
import dictTime
import dictDate
import dictGreeting

tok_buffer = ''
dictTa = {}
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
    try:
        response = requests.get(url)
        content = response.content.decode("utf8")
        return content
    except:
        return (None,None)

def get_json_from_url(url):
    content = get_url(url)
    if content != (None,None):
        js = json.loads(content)
    else:
        js = content
    return js


def get_updates():
    url = URL + "getUpdates"
    js = get_json_from_url(url)
    return js


def get_last_chat_id_name_and_text(updates):
    if updates!=(None,None):
        num_updates = len(updates["result"])
        if num_updates >0:
            last_update = num_updates - 1
            text = updates["result"][last_update]["message"]["text"]
            chat_id = updates["result"][last_update]["message"]["chat"]["id"]
            name = (updates["result"][last_update]["message"]["chat"]["first_name"])

            return (text, chat_id, name)
    return (None,None,None)

def send_message(text, chat_id):
    url = URL + "sendMessage?text={}&chat_id={}".format(text, chat_id)
    get_url(url)
    print(text)


def cleaner(sentence, markers):

    clean = ''.join(c for c in sentence if c not in markers)
    return clean


def tokenTest(token):

    #
    littleWordsList = ['dia','oi','ola','olá','bem','bom','tudo','como','vai','boa']
    #
    if token.isdigit() : return True                                            #test if token is a number
    if re.search(r'\d{2}:\d{2}', token): return True                             #test if token is in time format
    if re.search(r'\d{1}º', token) or re.search(r'\d{1}ª',token): return True    #test if token is a ordinal number
    if len(token)>4: return True                                                #test if token is big enought
    if len(token)==0: return False
    if token[0].isupper(): return True                                          #test if token cappital letter start
    if token in littleWordsList: return True

    return False


def tokenConcatenation(tokens):

    #
    concatenationList = ['bom', 'boa','dia','tudo','como']
    #
    tok_buffer = ''
    new_tokens = []
    for tok in tokens:
        if tok_buffer != '':
            new_tok = tok_buffer+' '+tok
            new_tokens.append(new_tok)
            tok_buffer = ''
        else:
            if tok in concatenationList:
                tok_buffer = tok
            else:
                new_tokens.append(tok)
    return new_tokens


def tokenization(text):

    cleanSentence = cleaner(text, '?!;)([],.')
    pre_tokens = cleanSentence.split(' ')
    pre_tokens = [w[0].lower()+w[1:] for w in pre_tokens]   # lower first letter
    tokens = [token for token in pre_tokens if tokenTest(token)]
    tokens = tokenConcatenation(tokens)

    print(tokens)
    return tokens


def tagRequestTest(tok):

    if tok in dictRequest.dictKeys:
        return True


def tagDateTest(tok):

    if tok in dictDate.dictKeys:
        return True


def tagTimeTest(tok):

    if tok in dictTime.dictKeys:
        return True


def tagGreetTest(tok):

    if tok in dictGreeting.dictKeys:
        return True

def requestTagConstruct(tok):
    if tok in dictRequest.schedule.keys():
        result = dictRequest.schedule[tok]
    if tok in dictRequest.reschedule.keys():
        return dictRequest.reschedule[tok]
    if tok in dictRequest.cancelation.keys():
        return dictRequest.cancelation[tok]
    if tok in dictRequest.information.keys():
        return dictRequest.information[tok]
    return(result)


def dateTagConstruct(tok):

    tdy = date.today()

    if tok in dictDate.month.keys():

        month_day = dictDate.month[tok]
        dt = date(tdy.year, tdy.month, month_day)
        return dt.isoformat()

    if tok in dictDate.week.keys():

        print(tok)

        week_day = dictDate.week[tok]
        if week_day < tdy.weekday():
            week_day+=7
        delta = week_day-tdy.weekday()
        dt = tdy + datetime.timedelta(days=delta)
        return dt.isoformat()

    if tok in dictDate.relative.keys():
        relative_day = dictDate.relative[tok]
        dt = tdy +datetime.timedelta(days = relative_day)
        return dt.isoformat()


def timeTagConstruct(tok):

    if tok in dictTime.time.keys():

        if tok == '*':
            now = datetime.datetime.now()
            t = now.time()
            return t.strftime('%H:%M')
        else:
            time_str = dictTime.time[tok]
            return time_str
    else:
        time_str = tok
        return time_str


def greetTagConstruct(tok):

    tag = dictGreeting.greeting[tok]
    return tag


def findTags(toks, chat):

    tags = {'user': '', 'request': '', 'date': '', 'time': '',
            'confirm': '', 'job': '', 'lastcall': '', 'greeting': []}

    tags['job'] = 'consulta'
    tags['lastcall'] = datetime.datetime.now().day
    tags['user'] = str(chat)

    for tok in toks:
        if tagRequestTest(tok): tags['request'] = requestTagConstruct(tok)
        if tagDateTest(tok): tags['date'] = dateTagConstruct(tok)
        if tagTimeTest(tok): tags['time'] = timeTagConstruct(tok)
        if re.search(r'\d{2}:\d{2}',tok): tags['time'] = timeTagConstruct(tok)
        if tagGreetTest(tok): tags['greeting'].append(greetTagConstruct(tok))
    return tags
#
#    features = [str(tok) for tok in tokens if len(str(tok))>2]
#
#    features = [str(tok) for tok in tokens if len(str(tok))>2]


def knowTags(users,chatID):
    if str(chatID) in users.keys():
        tags = users[str(chatID)]
        tags['user'] = str(chatID)
        return tags
    else:
        return None


def mergerTags(nTag, oTag):
    mTag ={}
    for k in nTag.keys():
        mTag[k] =  nTag[k] if nTag[k]!='' else oTag[k]
    return mTag


def saveTags(chatID, tags, users, name):

    if str(chatID) in users.keys():
        del users[str(chatID)]
    del tags['user']
    users[str(chatID)] = tags
    post_jsonTable(name, users)
    print (users)
    print (tags)
    return(users)


def boaAswr(gTags):

    # this feature generate a greeting that vary (time of day of or the sentence
    # of the interlocutor

    # first we will analyze tags:

    if 'bom dia' in gTags:
        return ('bom dia')
    if 'boa tarde' in gTags:
        return ('boa tarde')
    if 'boa noite' in gTags:
        return ('boa noite')

    #
    # now i will answer based on the time of day

    now = datetime.datetime.now()
    now_time = now.time()
    time = datetime.time
    if now_time >= time(5, 00) and now_time <= time(12, 00):  # morning
        return ('bom dia')

    elif now_time <= time(18, 00):  # afternoon
        return ('boa tarde')

    else:  # afternom
        return ('boa noite')


def greetAnswerConstruct(tags,name):

    # this feature construct the initial part of conversation if user (greet)

    gTags = tags['greeting']

    aswr = ''
    oiAswr = ['oi', 'olá']
    tdAswr = ['tudo bem', 'tudo bom']
    boaFlag = False
    tdFlag = False
    if 'oi' in gTags:   # if the client say 'oi' the bot answer 'oi'
        aswr += random.choice(oiAswr)
        gTags[:] = [tag for tag in gTags if tag != 'oi']
    else:
        rdm = random.random()
        if rdm <= 0.2:  # 20% = Oi / 80% = Boa noite
            aswr += random.choice(oiAswr)
            gTags[:] = [tag for tag in gTags if tag != 'oi']
        else:
            aswr = boaAswr()
            gTags[:] = [tag for tag in gTags if tag != 'bom dia' and
                        tag != 'boa tarde' and tag != 'boa noite']
            boaFlag = True

    # add the possibility of say the client name
    rdm = random.random()
    if rdm <= 0.5:
        aswr += ', '
        aswr += name

    # if client greet 'tudo bem?' the bot answer
    if 'tudo bem?' in gTags:
        aswr += ', '
        aswr += random.choice(tdAswr)
        gTags[:] = [tag for tag in gTags if tag != 'tudo bem?']
        tdFlag = True

    # if i don't answer wet the 'Bom dia' of client
    if 'bom dia' in gTags or 'boa tarde' in gTags or 'boa noite' in gTags:
        aswr += ', '
        aswr += boaAswr()

    if not boaFlag and not tdFlag:
        rdm = random.random()
        if rdm <= 0.5:
            aswr += ', '
            aswr += boaAswr()

    # final adjustments
    aswr = aswr[0].capitalize() + aswr[1:]
    aswr += '. '
    return(aswr)


def validDate(date, data, n):

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
    # this feature merge the overlapping blocks of time

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


def answer(tags, dataTable, schdTable, name):

    # this feature analyze the tags and the schedule and make a answer for the
    # client.


    if tags['greeting']:
        answer = greetAnswerConstruct(tags,name)
    else:
        now = datetime.datetime.now()
        tdy = now.day
        lastcall_day = int(tags['lastcall'])
        delta = lastcall_day - tdy
        if delta:
            answer = greetAnswerConstruct(tags,name)
        else:
            answer = ''

    if not tags['request']:

        answer += 'O que posso fazer por você hoje ?'
        options = ['Marcar um horário',
                   'Remarcar meu horário',
                   'Desmarcar meu horário',
                   'Informações a respeito do atendimento']
        return (answer, options, None)

    elif tags['request'] == 'information':

        answer += 'Information:'
        options = ['information']
        return (answer, options, None)

    if not tags['date']:

        answer += 'Qual data seria melhor para marcarmos?'
        options = ['segunda', 'terça', 'quarta', 'quinta', 'sexta']
        return (answer, options, None)

    else:
        dates = validDate(tags['date'], dataTable, 7)
        if dates[0] != tags['date']:
            answer += 'Infelizmente, não temos disponibilidade no dia solicitados, as datas para agendamento mais proximas são:'
            options = list(dates) + ['Escolher outra data',
                                     'Informações sobre horários de atendimento do consultório']

            return(answer, options, None)

    if not tags['time']:

        answer += 'Qual horário você tem interesse :'
        options = avaibleTime(dataTable, schdTable, tags, tags['date'],
                              business_ID, employer_ID, 0.5)
        return (answer, options, None)
    else:

        answer += 'Horário confirmado:'
        options = [tags['date'], tags['time']]
        return (answer, options, True)


def scheduleCreator(t0, tags, chatID, dataTable):

    #
    print ('scheduleCreator')
    #

    jobs = dataTable[business_ID]['services']
    for job in jobs:
        if job['name'] == tags['job']:
            duration = job['duration']
            hoursDuration = float(duration)
            delta = datetime.timedelta(hours=hoursDuration)
            break
    tf = t0+delta

    t0 = t0.strftime('%H:%M')
    tf = tf.strftime('%H:%M')

    schd = {}
    schd['time'] = [t0, tf]
    schd['job'] = tags['job']
    schd['contactID'] = chatID
    return schd


def schedule(tags, chatID, dataTable, schdTable):

    # this feature make anew schedule on the scheduleTable

    time = datetime.datetime.strptime(tags['time'], '%H:%M')
    new_schd = scheduleCreator(time, tags, chatID, dataTable)

    schdEmployer = schdTable[business_ID]['employers'][employer_ID]['schedules']
    if tags['date'] in schdEmployer.keys():  # test is i have same one schdule
                                             # at 'this' day

        # if ys i need to organize my schedules cronologic in a list
        # (schdDayList)

        schdDayList = schdEmployer[tags['date']]
        for schd in schdDayList:

            schdTime = datetime.datetime.strptime(schd['time'][0], '#H:#M')
            if time > schdTime:
                i = schdDayList.index(schd) + 1
                break
            else:
                i = len(schdDayList)

        schdDayList.insert(i, new_schd)
    else:
        # if not, i need to generate a new list for 'this day' ans save the new
        # schdule on this list:

        schdEmployer[tags['date']] = [new_schd]

    return schdTable


def get_jsonTable(jsonName):

    with open(jsonName) as data_file:
            data = json.load(data_file)
    return data


def post_jsonTable(jsonName, data):

    with open(jsonName, 'w') as outfile:
            json.dump(data, outfile)


def main():
    dataTable = get_jsonTable('data.json')
    schdTable = get_jsonTable('schedule.json')
    userTable = get_jsonTable('users.json')
    last_textchat = (None, None)
    while True:
        text, chat, name = get_last_chat_id_name_and_text(get_updates())
        if (text, chat) != last_textchat:
            toks = tokenization(text)
            new_tags = findTags(toks, chat)
            old_tags = knowTags(userTable, chat)
            if old_tags is not None:
                tags = mergerTags(new_tags, old_tags)
            else:
                tags = new_tags
            resp, opts, tags['confirm'] = answer(tags, dataTable, schdTable, name)
            send_message(resp, chat)
            for opt in opts:
                send_message(opt, chat)
            userTable = saveTags(chat, tags, userTable, 'user.json')

            last_textchat = (text, chat)

            print(tags['confirm'])

            if tags['confirm'] == True:
                schdTable = schedule(tags, chat, dataTable, schdTable)
                post_jsonTable('schedule.json', schdTable)
                tags = findTags([],chat)
                userTable = saveTags(chat, tags, userTable, 'user.json')
#            print (name+' '+str(chat) +'  '+text)
#            send_message(text, chat)
#            last_textchat = (text, chat)
        time.sleep(0.5)



if __name__ == '__main__':
    main()
