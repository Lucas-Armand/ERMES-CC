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
import dictConfirm

tok_buffer = '' # é utilizado dentro de função;
dictTag = {}
concatenationList = ['bom', 'boa', 'Bom', 'Boa'] # é utilizado dentro de função;
TOKEN = "292444370:AAGiqsll_zwYbIRMQ9Hg_8pfihj8y1Ig8Ac"
URL = "http://localhost:5000"
business_ID = 0
employer_ID = 0

#1
# c_KEYWORDS = ("Olá","Ola","ola","olá","Oi", "bom dia", "bom tarde",
#           "boa noite","Tudo bem?","Tudo bem","tudo bem","tudo bem")
# SCHEDULLING_KEYWORDS = ("marcar","combinar","definir","sondar",
#           "planejar","decidir","acertar","casar","horário")

def get_url(url):
    response = requests.get(url,{'hub.mode':'communication'})
    content = response.content.decode("utf8")
    return content


def get_json_from_url(url):
    content = get_url(url)
    js = json.loads(content)
    return js


def get_updates():
    url=URL
    js = get_json_from_url(url)
    return js


def get_last_chat_id_name_and_text(updates):
    # facebook msg:
    # [{'msg': 'olá testando 123', 'pgID': '424316070951952', 'time':
    # 1489033287081, 'sdID': '1272971316150819'}]

    if len(updates)>0:
        if "result" in updates[0].keys():
            # if yes than telegram

            num_updates = len(updates["result"])
            last_update = num_updates - 1
            text = updates["result"][last_update]["message"]["text"]
            chat_id = updates["result"][last_update]["message"]["chat"]["id"]
            name = (updates["result"][last_update]["message"]["chat"]["first_name"])

        else:
            # if not tha facebook
            num_updates = len(updates)
            last_update = num_updates - 1
            text = updates[last_update]['msg']
            chat_id = updates[last_update]['sdID']
            name = '{}'
    else:
        text, chat_id,name = None,None,None

    return (text, chat_id, name)


def send_message(text, chat_id, msgtype, *butts):
    data = {'object':'bot', 'sdrtype':'facebook_msg','msgtype':msgtype, 'text':text, 'chatid':chat_id}
    if msgtype == 'buttons':
        data['bttlist'] = butts[0]
    r = requests.post(URL, json = data)


def send_image(chat_id,img_tag):
    data = {'object':'bot', 'sdrtype':'facebook_msg','msgtype':'image', 'text':img_tag, 'chatid':chat_id}
    r = requests.post(URL, json = data)


def cleaner(sentence, markers):

     # retorna uma nova sentença em que os marcadores de pontuação não estão presentes;

    clean = ''.join(c for c in sentence if c not in markers)
    return clean


def tokenTest(token):

    # função que recebe uma string e retorna valor verdadeiro se a string for considerada uma token;

    #
    littleWordsList = ['dia','oi','ola','olá','bem','bom','tudo','como','vai','boa','meu','mais','cão','gato','cães','ave']
    #
    if token.isdigit() : return True                                            #test if token is a number
    if re.search(r'\d{1}º', token) or re.search(r'\d{1}ª',token): return True    #test if token is a ordinal number
    if len(token)>4: return True                                                #test if token is big enought
    if len(token)==0: return False
    if token[0].isupper(): return True                                          #test if token cappital letter start
    if token in littleWordsList: return True

    return False


def tokenConcatenation(tokens):

    # Não entendi porque essa função está sendo utilizada;

    #
    concatenationList = ['bom', 'boa','dia','tudo','como','meu','mais']
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

    # função que quebra o texto em tokens;

    cleanSentence = cleaner(text, '?!;)([],.')
    pre_tokens = cleanSentence.split(' ')
    pre_tokens = [pt for pt in pre_tokens if pt!=''] # se houver espaços no início ou no final do texto, são retirados;
    pre_tokens = [w[0].lower()+w[1:] for w in pre_tokens]   # lower first letter
    tokens = [token for token in pre_tokens if tokenTest(token)] # para cada palavra no pre_tokens que é de fato token, adiciona-se a lista token;
    tokens = tokenConcatenation(tokens) # não entendi essa função

    return tokens


def tagRequestTest(tok):

    # retorna verdadeiro se a string tok for uma das chaves (marcar, agendar, remarcar ...) do dicionario dictRequest

    if tok in dictRequest.dictKeys:
        return True

def tagDateTest(tok):
    
    # retorna verdadeiro se a string tok for uma das chaves (dia 20, amanhã, quarta ...) do dicionario dictDate
    if tok in dictDate.dictKeys:
        return True
    elif re.search(r'\d{2}/\d{2}', tok):
        return True
    elif re.search(r'\d{4}[-/]\d{2}[-/]\d{2}', tok):
        return True

def tagTimeTest(tok):

    # retorna verdadeiro se a string tok for uma das chaves (manhã, tarde e noite) do dicionario dictTime; ok, mas não é essencial na minha visão;

    if tok in dictTime.dictKeys:
        return True


def tagGreetTest(tok):

    # mesmo raciocionio das funcoes acima;

    if tok in dictGreeting.dictKeys:
        return True

def tagConfirmTest(tok):

    if tok in dictConfirm.dictKeys:
        return True

def requestTagConstruct(tok):

    # funcao que verificar se o tok e um dos comandos de agendar, remarcar ou cancelar, dentre os varios possiveis dessas tres classes. E se nao for nenhum deles?
    if tok in dictRequest.schedule.keys():
        result = dictRequest.schedule[tok]      # result recebe o valor correspondente a chave tok no dictRequest.schedule;
    if tok in dictRequest.reschedule.keys():
        return dictRequest.reschedule[tok]      # result recebe o valor correspondente a chave tok no dictRequest.reschedule;
    if tok in dictRequest.cancelation.keys():
        return dictRequest.cancelation[tok]
    if tok in dictRequest.information.keys():   # mesmo raciocionio acima para cancelamento;
        return dictRequest.information[tok]
    if tok in dictRequest.restart.keys():       # mesmo raciocionio acima para retornar ao inicio to atendimento;
        return dictRequest.restart[tok]
    return (result)


def dateTagConstruct(tok):

    # funcao que retorna a data considerando as varias maneiras que o usuario pode entrar com ela;
    # considera apenas o mês presente; não verificar se o dia selecionado ja passou como faz com os dias da semana;
    # fazer somente para o mês seguinte;
    # verificar se tok está no formato /;

    tdy = date.today()
    

    if '-' in tok and tok not in dictDate.week.keys():
        toklista = tok.split('-')
        if len(toklista) == 2:
            dt = date(tdy.year, int(toklista[1]), int(toklista[0]))
            return dt.isoformat()
        elif len(toklista) == 3:
            dt = date(int(toklista[0]), int(toklista[1]), int(toklista[2]))
            return dt.isoformat()

    if '/' in tok:
        toklista = tok.split('/')
        if len(toklista) == 2:
            dt = date(tdy.year, int(toklista[1]), int(toklista[0]))
            return dt.isoformat()
        elif len(toklista) == 3:
            dt = date(int(toklista[2]), int(toklista[1]), int(toklista[0]))
            return dt.isoformat()
    
    elif tok in dictDate.month.keys():

        month_day = dictDate.month[tok]
        dt = date(tdy.year, tdy.month, month_day)
        return dt.isoformat()
    
    elif tok in dictDate.week.keys():

        week_day = dictDate.week[tok]
        if week_day < tdy.weekday():
            week_day+=7
        delta = week_day-tdy.weekday()
        dt = tdy + datetime.timedelta(days=delta)
        return dt.isoformat()

    elif tok in dictDate.relative.keys():
        relative_day = dictDate.relative[tok]
        dt = tdy +datetime.timedelta(days = relative_day)
        return dt.isoformat()


def timeTagConstruct(tok):

    # na minha visão esta incompleta; Não acho que dar a opção de marcar na hora seja válido;

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

    # para responder com o mesmo greeting ou para interpretar o greeting?

    tag = dictGreeting.greeting[tok]
    return tag

def findTags(toks, chat):

    tags = {'user': '', 'request': '', 'date': '', 'time': '',
            'confirm': '', 'job': '', 'lastcall': '', 'greeting': [],'pet':''}

    tags['job'] = 'consulta'
    tags['lastcall'] = datetime.datetime.now().day
    tags['user'] = str(chat)

    for tok in toks:
        if tagRequestTest(tok): tags['request'] = requestTagConstruct(tok) # se for uma tag de request, pega-se o valor correspondente a chave e coloca em tags - padronizaçao
        if tagDateTest(tok): tags['date'] = dateTagConstruct(tok) # mesma coisa se for uma tag de data
        if tagTimeTest(tok): tags['time'] = timeTagConstruct(tok) # mesma coisa se for uma tag de hora
        if re.search(r'\d{2}:\d{2}',tok): tags['time'] = timeTagConstruct(tok) # mesma coisa se for hora;
        if tagGreetTest(tok): tags['greeting'].append(greetTagConstruct(tok)) # nao entendo a necessidade disso;
        if tagConfirmTest(tok):
            tags['confirm'] = True
    return tags
#
#    features = [str(tok) for tok in tokens if len(str(tok))>2]
#
#    features = [str(tok) for tok in tokens if len(str(tok))>2]


def knowTags(users,chatID):

    # cada user tem uma tag (função acima); no arquivo users tem o número direto e na tag na função acim tem user também. Não entendi;

    if str(chatID) in users.keys():
        tags = users[str(chatID)]
        tags['user'] = str(chatID)
        return tags
    else:
        return None


def mergerTags(nTag, oTag, chat):

    # nao entendi;
    if nTag['request'] != 'restart':
        mTag ={}
        for k in nTag.keys():
            mTag[k] =  nTag[k] if nTag[k]!='' else oTag[k]
        return mTag
    else:
        mTags = findTags([],chat)
        return mTags


def saveTags(chatID, tags, users, name):

    # nao entendi;

    if str(chatID) in users.keys():
        del users[str(chatID)]
    del tags['user']
    users[str(chatID)] = tags
    post_jsonTable(name, users)
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
            aswr = boaAswr(gTags)
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
        aswr += boaAswr(gTags)

    if not boaFlag and not tdFlag:
        rdm = random.random()
        if rdm <= 0.5:
            aswr += ', '
            aswr += boaAswr(gTags)

    # final adjustments
    aswr = aswr[0].capitalize() + aswr[1:]
    aswr += '. '
    return(aswr)


def validDate(date, data, n):

    # this feature find the valid days in the nexts 'n' days to schedule

    timeAvaible = data[business_ID]['employers'][employer_ID]['time'] # retorna um dicionario de dias da semana e horarios;
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
        FMT = '%H:%M'
        time = datetime.datetime.strptime(t, FMT)
        delta_time = datetime.timedelta(hours=dt)
        n_time = time - delta_time
        n_tB.append(str(n_time.hour)+':'+str(n_time.minute))


    return n_tB


def mergerBlocks(tBlocks):
    # this feature merge the overlapping blocks of time

    n = len(tBlocks)
    if n == 1:
        return tBlocks

    FMT = '%H:%M'
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

    FMT = '%H:%M'
    timeGrid = [datetime.datetime.strptime(t, FMT) for t in tGrid]

    timeBlocks = [[datetime.datetime.strptime(t[0], FMT),
                   datetime.datetime.strptime(t[1], FMT)] for t in tBlocks]
    i = 0
    j = 0

    n = len(timeGrid)

    newGrid = []
    #while not i == n:
    for i in range(n):
        time = timeGrid[i]
        for j in range(len(timeBlocks)):
            if time <= timeBlocks[j][0] or time >= timeBlocks[j][1]:
                 newGrid.append(time.strftime('%H:%M'))

    return(newGrid)

def avaibleTime(data, sched, tag, date, business_ID, employer_ID, delta):

    # this module return the avaible time(s) to schedule
    # for a employer or 'None':

    jobList = data[business_ID]['services']
    for job in jobList:
        if job['name'] == tag['job']:
            # test to find the right service:

            timeService = job['duration']
            #if date in sched[business_ID]['employers'][employer_ID]['schedules'].keys():
                # if there is same shedule already done at this date
                # i need to show a list of possibles shedule times
                #print ('ola ola ola ')
                #services = sched[business_ID]['employers'][employer_ID]['schedules'][date]
                #print ('services')
                #print (services)
                #timeBlocks=[]
                #for tB in services:
                #    print (tB['time'])
                #    timeBlocks.append(expandTB(tB['time'],[timeService,0]))
                #print ('timeBlocks')
                #print (timeBlocks)
                #timeBlocks = mergerBlocks(timeBlocks)
                #timeGrid = TimeGrid(business_ID, employer_ID, data, date, delta)
                #timeGrid = gridArrange(timeGrid, timeBlocks)
                #return timeGrid
            #else:
                #timeGrid = TimeGrid(business_ID, employer_ID, data, date, delta)
                #return timeGrid
            timeGrid = TimeGrid(business_ID, employer_ID, data, date, delta)
            return timeGrid
        else:
            pass


def getMyInformation(tags, schdTable, business_ID, employer_ID, contact_ID):
    job = tags['job']
    schds = schdTable[business_ID]['employers'][employer_ID]['schedules']

    test = False
    for date in schds.keys():
        schdDay = schds[date]
        n = len(schdDay)
        for i in range(n):
            schd = schdDay[i]
            if schd['contactID'] == contact_ID:
                test = True
                time = schd['time']
                return date, job, time, test, i

    date = None
    job = None
    time = None
    return date, job, time, test, None


def constructMyInformation(tags, schdTable, business_ID, employer_ID, contact_ID):
    date, job, time, test, schdIndex = getMyInformation(tags, schdTable, business_ID, employer_ID, contact_ID)
    if test == True:
        answer = ''
        answer += 'Você tem um(a) '+ job+' marcado em:\n'
        answer += date+'\n'
        answer += time[0]+'\n'
        options = ['Outras informações','Retornar ao menu inicial']
    else:
        answer = 'Você, aparentemente, não tem nenhum horário marcado.'
        options = ['Outras informações','Retornar ao menu inicial']
    return answer, options


def getAddress(business_ID, dataTable):

    address = dataTable[business_ID]['address']
    return address


def constructAddress(business_ID, dataTable):
    address = getAddress(business_ID, dataTable)
    answer = "O nosso endereço é:\n"
    answer += address
    options = ['Outras informações','Retornar ao menu inicial']
    return answer, options

def constructHealthPlans(dataTable, business_ID):

    plans = dataTable[business_ID]['healthplans']
    answer = 'Nós trabalhos com os seguintes planos:\n'
    for plan in plans:
        answer += plan+'\n'
    options = ['Outras informações','Retornar ao menu inicial']
    return answer, options

def constructServices(dataTable, business_ID):

    services = dataTable[business_ID]['services']
    if len(services)>1:
        answer = 'Em nosso consultório nós realizamos:\n'
        for serv in services:
            answer += serv['name']+', pelo valor de  R$'
            answer += str(serv['price'])+' no particular.\n'
    else:
        answer = 'Nosso consultório só realiza\n'
        for serv in services:
            answer += serv['name']+', pelo valor de  R$'
            answer += str(serv['price'].__truc__)+',00 no particular.\n'

    options = ['Outras informações','Retornar ao menu inicial']
    return answer, options

def constructCancelation(tags, schdTable, business_ID, employer_ID, contact_ID):

    date, job, time, test, schdIndex = getMyInformation(tags, schdTable, business_ID, employer_ID, contact_ID)

    if test == True:

        answer = ''
        answer += 'Você tem um(a) '+ job+' marcado em:\n'
        answer += date+'\n'
        answer += time[0]+'\n'
        answer += 'Tem certeza que deseja desmarcar ?'
        options = ['Confirmar','Outras informações','Retornar ao menu inicial']
    else:
        answer = 'Você, aparentemente, não tem nenhum horário marcado.'
        options = ['Outras informações','Retornar ao menu inicial']
    return answer, options

def answer(tags, dataTable, schdTable, name, chat):

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

        answer += 'O que posso fazer por você hoje?'
        options = ['Marcar uma consulta',
                   #'Remarcar meu horário',
                   'Desmarcar agendamento',
                   'Informações']
        return (answer, options, None)

    elif tags['request'] == 'information':

        answer += 'Qual informação você precisa?'
        options = ['Meu horário',
                   'Endereço',
                   'Mais informações']

        return (answer, options, None)

    elif tags['request'] == 'myinformation' :

        answer,options = constructMyInformation(tags, schdTable, business_ID, employer_ID, chat)
        return (answer, options, None)

    elif tags['request'] == 'address':

        answer, options = constructAddress(business_ID, dataTable)
        return (answer, options, None)

    elif tags['request'] == 'plus':

        answer = 'O que mais eu posso fazer por voce?'
        options = ['Planos de Saude',
                   'Tabela de Serviços',
                   'Retornar ao menu inicial']
        return (answer, options, None)

    elif tags['request'] == 'healthplans':

        answer,options = constructHealthPlans(dataTable, business_ID)
        return (answer, options, None)

    elif tags['request'] == 'services':

        answer,options = constructServices(dataTable, business_ID)
        return (answer, options, None)

    elif tags['request'] == 'schedule':

        answer += 'Qual é o seu bichinho de estimação?'
        options = ['Cão','Gato','Porquinho da India']
        return (answer, options, None)

    elif tags['request'] == 'cancelation':
        if not tags['confirm']:
            answer, options = constructCancelation(tags, schdTable, business_ID, employer_ID, chat)
            return (answer, options, None)
        else:
            answer += 'Muito obrigado por testar nosso roboto!'
            options = None
            return (answer, options, True)

    if not tags['date']:

        answer += 'Qual data seria melhor para marcarmos?\n\n'
        answer += '(Obs: Nosso roboto entende "datas" escritas de forma natural.'

        answer += ' Sinta-se à vontade para escrever sua data como: "sexta-feira", "quinta", "13/04", "amanhã", "dia 6")'

        #options = ['segunda', 'terça', 'quarta', 'quinta', 'sexta']
        options = None
        return (answer, options, None)

    else:
        dates = validDate(tags['date'], dataTable, 7)
        if dates[0] != tags['date']:
            r =''
            for i in range(len(dates)):
                r = r+str(dates[i])+'\n'

            answer += 'Infelizmente, não temos disponibilidade no dia solicitado. As datas para agendamento mais próximas estão abaixo. Sinta-se a vontade em digitar ou utilizar os botões para agendar um novo horário.\n\n' + r

            options = ['Cancelar e retornar',
                                     'Informações sobre horários de atendimento do consultório']
            return(answer, options, None)


    if not tags['time']:

        options2 = avaibleTime(dataTable, schdTable, tags, tags['date'], business_ID, employer_ID, 0.5)
        options = None

        r = ''
        for i in range(len(options2)):
            r = r+str(options2[i])+'\n'
        answer += 'Os horários disponíveis para esse dia são'+'\n\n'+r+'\n'+'Por favor, digite o horário.'
        return (answer, options, None)
    else:
        options2 = avaibleTime(dataTable, schdTable, tags, tags['date'], business_ID, employer_ID, 0.5)
        options = None

        verificador = 0
        for i in range(len(options2)):
            if options2[i] == tags['time']:
                verificador = verificador+1

        if verificador == 0:
            r = ''
            for i in range(len(options2)):
                r = r+str(options2[i])+'\n'
            answer += 'Esse não é um horário válido. Os horários disponíveis para esse dia são'+'\n\n'+r+'\n'+'Por favor, digite novamente o horário.'
            return (answer,options,None)


    if not tags['confirm']:

        answer += 'Você gostaria de realizar um agendamento em:'+'\n'+tags['date']+'\n'+tags['time']
        options = ['Confirmar agendamento',
                   'Cancelar e retornar']
        return (answer, options, None)
    else:
        answer += 'Muito obrigado por testar nosso roboto!'
        options = None
        return (answer, options, True)




def scheduleCreator(t0, tags, chatID, dataTable):

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
    print('Teste 1')
    time = datetime.datetime.strptime(tags['time'], '%H:%M')
    new_schd = scheduleCreator(time, tags, chatID, dataTable)

    schdEmployer = schdTable[business_ID]['employers'][employer_ID]['schedules']
    if tags['date'] in schdEmployer.keys():  # test is i have same one schdule
                                             # at 'this' day

        print('Teste 2')
        # if ys i need to organize my schedules cronologic in a list
        # (schdDayList)

        schdDayList = schdEmployer[tags['date']]
        for schd in schdDayList:

            schdTime = datetime.datetime.strptime(schd['time'][0], '%H:%M')
            if time > schdTime:
                i = schdDayList.index(schd) + 1
                break
            else:
                i = len(schdDayList)

        schdDayList.insert(i, new_schd)
    else:
        # if not, i need to generate a new list for 'this day' ans save the new
        # schdule on this list:

        print('Teste 3')
        schdEmployer[tags['date']] = [new_schd]

    return schdTable


def cancelation(tags, contact_ID, business_ID, employer_ID, schdTable):

    date, job, time, test, schdIndex = getMyInformation(tags, schdTable, business_ID, employer_ID, contact_ID)
    schdDay = schdTable[business_ID]['employers'][employer_ID]['schedules'][date]
    schdDay.pop(schdIndex)

    return schdTable


def get_jsonTable(jsonName):

    with open(jsonName) as data_file:
            data = json.load(data_file)
    return data


def post_jsonTable(jsonName, data):

    with open(jsonName, 'w') as outfile:
            json.dump(data, outfile, indent=4, sort_keys=True)


def atendimento(testMode):
    dataTable = get_jsonTable('data.json')
    schdTable = get_jsonTable('schedule.json')
    userTable = get_jsonTable('users.json')
    last_textchat = (None, None)
    while True:
        #Aqui eu recebo as informações iniciais (chat = UserID , text = UserMsg, name = UserName)
        if testMode == True:
            #
            text = input('')
            chat = '1234'
            name = 'Lucas'
            #
        else:
            text, chat, name = get_last_chat_id_name_and_text(get_updates())

        print ('ChaID:'+str(chat)+'     Text:'+str(text))

        if (text, chat) != last_textchat:
            toks = tokenization(text)                           #quebrar o "text" in "toks"
            new_tags = findTags(toks, chat)                     #apartir dos tokens eu vou achar as tags do usuário
            old_tags = knowTags(userTable, chat)                #aqui eu relembro as tags que ele ja tem

            #aqui eu vou inserir as novas tags nas antigas
            if old_tags is not None:
                tags = mergerTags(new_tags, old_tags, chat)
            else:
                tags = new_tags


            #aqui eu uso "answer" que gera uma resposta a partir das tags do usuário
            resp, opts, tags['confirm'] = answer(tags, dataTable, schdTable, name, chat)

            # essa parte é meio confusa por causa da questão do "testMode" mas
            # basicamente se eu estou no modo de teste então eu vou "printar" as
            # resposta no terminal, caso não eu tenho que envialas para o
            # webhook que vai mandar para o facebook
            #Cabeçalho
            print ('')
            print ('Nome :      ' + name)
            print ('ChatId :    ' + chat)
            print ('Input :     ' + text)
            print ('Tokens :    ' + str(toks))
            print ('Tags :      '+ str(tags))
            print ('')
            print ('BotAnswer :')
            print (resp)
            if opts:
                print ('')
                for opt in opts:
                    print (opt)
                print('')
                print('')
            if testMode == False:
                if opts:
                    send_message(resp,chat,'buttons',opts)
                else:
                    send_message(resp, chat,'answer')

            #se o atendimento é bem sucedido eu devo inviar uma image
            if tags['confirm'] == True: #Last msg
                if tags['request'] != 'cancelation':
                    if testMode == True:
                        print ('Imagem Request = '+tags['request'])
                        print ('')
                        print ('')
                    else:
                        send_image(chat,tags['request'])

                    #se o atendimento for bem sucedido eu tbm devo salvar em 'scdTable'
                    schdTable = schedule(tags, chat, dataTable, schdTable)
                    post_jsonTable('schedule.json', schdTable)
                elif tags ['request'] == 'cancelation':
                    #se a pessoa quiser cancelar o atendimento então devo tirar de 'schdTable'
                    schdTable = cancelation(tags, chat, business_ID, employer_ID, schdTable)
                    post_jsonTable('schedule.json', schdTable)
                tags = findTags([],chat)

            userTable = saveTags(chat, tags, userTable, 'user.json')

            last_textchat = (text, chat)
        time.sleep(0.5)

def main():
    try:
        atendimento(testMode = False)
    except:
        #test
        time.sleep(0.1)


if  __name__ == '__main__':

    main()
