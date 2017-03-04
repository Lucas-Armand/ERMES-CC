# BOMROBOTO

#O Projeto
Fazer um aplicativo que permita pequenas empresas terem acesso a soluções de chatbots para atendimento de maneira simples e automática. A construção do MVP (Minimum Viable Product) deve ser até o final de Fevereiro. Nossa missão é oferecer um serviço de agendamento e pedidos por Bots completo, simples, personalizável e multiplataforma. 

#Informações Básicas

Nossos Bots de Teste:

- Telegran = https://telegram.me/BOMRO_bot  // Token:"292444370:AAGiqsll_zwYbIRMQ9Hg_8pfihj8y1Ig8Ac" 
- Mensager : 
INFORMAÇÕES NOVAS:

ID : 132804990557844           #id do aplicativo no facebook, quem quiser ter acesso é só pedir ao Lucas  
URL de retorno : https://bcd920bf.ngrok.io/    # feita usando o ngrok e rodando na nossa maquina virtual!
Tokens :
   Fundamentos da Matemática :
EAAB4yRJJXpQBAPZC57WEk31t8Ot3dOq40gEZAcXBlN5JPDnJXIu56szS3adjKtXNBTOOyU1CKy5NTwU0hKamFwlLioXEY24hVPZBZCnsdqBFNu0YXPA4twge1XZCtPNvldc6JwzAFZA2DifRoSABSZCPg4nSkltU0iJeZB2AAsxYvgZDZD
   Curso de Fisica Básico :
EAAB4yRJJXpQBAIoktLxF50nSeDO3Rt8MGZChufZAWyx4cGNH2Ejwo0NF8wdGsoRS5oZBTxSyz2dK0riA9fjHniys0Wih7ZCoSNf2JPSEGgdX16YuaVbLiQsA3Mr6un94jYPQv53uqtT4W4E70oGk8BRGKQ6wdANAWFzkxmU3ogZDZD

- Google Plus = *
- Signal = ?
- Viber = ?
- ?????

Estamos usando python 3.5 para programar o bot, segue algumas referências:

 - https://www.codementor.io/garethdwyer/tutorials/building-a-telegram-bot-using-python-part-1-goi5fncay
 - https://core.telegram.org/bots/samples
 - https://apps.worldwritable.com/tutorials/chatbot/ 
 - https://blog.hartleybrody.com/fb-messenger-bot/

#Organização

1) Reuniões semanais a onde é analisado o avanço do projeto e definido os objeetivos para proxima semana.

2) Utilize o GitKraken (https://www.gitkraken.com/) para organizar seu repositório

3) Todos as tarefas (e idéias) devem ser geradas dentro do projeto "BOMROBOTO MVP" dentro da coluna "BACKLOG"

4) Tarefas devem ser clasificadas como BUG, FEATURE ou UPGRADE e com tags como [APP] ou [IA]

5) Ao longo da semana as tarefas caminham pelas colunas, no final de uma semana todas as suas tarefas devem estar em "Done"

6) Caso precise transforme a tarefa em um  "Issues" (tente fazer isso nos 2 primeiros dias do ciclo de trabalho!)

7) Problemas são imprevisíveis! Mantenha sempre o repositório oficial atualizado, fez alguma alteração significativa? Pull request!

#Servidores e VM

Pessoal criei uma virtual Machine pelo Microsoft Azure!
Para entrar você tem que abrir o seu terminal e digitar:

$ ssh bomroboto.brazilsouth.cloudapp.azure.com -l bomroboto
$ password: aa123456789BB

Uma vez que vocẽ entrou você verá um novo usuário e maquina
no terminal:

$ bomroboto@bomrobotoVM:

agora você tem que abrir "tmux" que uma aplicação que permite 
você operar varias janelas do terminal em uma janela só, assim 
digite:

$ tmux

Se tudo der certo, o seu terminal deve ter sido 'reiniciado', 
isso porque agora você esta num novo terminal dentro do "tmux"
agora se você clicar "Ctrl+b" e depos "s" devem aparecer a lista
de terminais existentes, algo como:

(0) + 10: 1 windows (attached)   
(2) + ngrok: 1 windows         
(3) + fb_bot: 1 windows      
(4) + vim: 1 windows                                                                                                                                 
Ai você escolher uma das janelas já criadas ou voltar para janela
que você começou agora, para referencia:
+ ngrok: Janela aonde está sendo executado o nosso servido! 
+ NÃO PODE SER ALTERADA/FECHADA EM HIPÓTESE NEM UMA! 
+ Caso isso seja feito tem que criar um novo link pro servidor
+ e alterar no aplicativo do facebook (o que é chato) 
+ 
+ fb_bot: Janela executando o código python que gera o server
+ local que conversa com a API do facebook, se for fechado basta
+ executar:
+ $ python3 fb_bot.py
+ 
+ vim: é código do fb_bot.py aberto no vim

# BIBLIOGRAFIA

http://man.openbsd.org/OpenBSD-current/man1/tmux.1

