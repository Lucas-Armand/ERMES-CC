# ERMES

#O Projeto
Fazer um aplicativo que permita pequenas empresas terem acesso a soluções de chatbots para atendimento de maneira simples e automática. A construção do MVP (Minimum Viable Product) deve ser até o final de Fevereiro. Nossa missão é oferecer um serviço de agendamento e pedidos por Bots completo, simples, personalizável e multiplataforma. 

#Informações Básicas


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

# Servidores e VM

## Microsoft Azure VM

Pessoal criei uma virtual Machine pelo Microsoft Azure!
Para entrar você tem que abrir o seu terminal e digitar:

$ ssh ermes.brazilsouth.cloudapp.azure.com -l ermes

$ password: ********
Uma vez que vocẽ entrou você verá um novo usuário e maquina
no terminal:

$ ermes@ermesVM:

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
 NÃO PODE SER ALTERADA/FECHADA EM HIPÓTESE NEM UMA! 
 Caso isso seja feito tem que criar um novo link pro servidor
 e alterar no aplicativo do facebook (o que é chato) 
 
+ fb_bot: Janela executando o código python que gera o server
 local que conversa com a API do facebook, se for fechado basta
 executar:
 $ python3 fb_bot.py
 
+ vim: é código do fb_bot.py aberto no vim

## Amazon VM

Para acessar pela primeira vez a VM na amazon baixe os arquivos da pasta
"Amazon" que estão no Drive na pasta "ERMES".

Depois você deve mover o arquivo "ermes-amazon-key.pem" para o diretório 
"~/.ssh"

```
mv /onde/vc/baixo/ermes-amazon-key.pem ~/.ssh
```
OU
```
cp /onde/vc/baixo/ermes-amazon-key.pem ~/.ssh
```

Depois siga o tutorial a partir do terceiro item:
https://aws.amazon.com/pt/getting-started/tutorials/launch-a-virtual-machine/

Para acessar o VM no dia-a-dia basta executar:
```
 sudo ssh -i ~/.ssh/ermes-amazon-key.pem ubuntu@52.26.167.85
```

# Como configurar uma nova VM

1) Instalar o pip:

```
sudo apt-get install python3-setuptools
sudo easy_install3 pip
```

2) Instalar ngrok:

```
sudo apt-get update
wget https://bin.equinox.io/c/4VmDzA7iaHb/ngrok-stable-linux-amd64.zip
sudo apt install unzip
unzip ngrok-stable-linux-amd64.zip
```

3) Instalar requests:

```
sudo pip install requests
```

4)  Instalar Flask:

```
sudo pip install flask
```

5) Instalar git:

```
sudo apt-get install git
```

6) Clonar repositório:
 
```
git clone https://github.com/Lucas-Armand/BOMROBOTO.git
```

6) Instalar VIM:
 
```
cp -i BOMROBOTO/vim/.vimrc ./.vimrc
mkdir -p ~/.vim/autoload ~/.vim/bundle && \
curl -LSso ~/.vim/autoload/pathogen.vim https://tpo.pe/pathogen.vim
```


7) Executar o ngrok:
 
```
tmux
ngrok http 8080
Ctrl+B $ ngrok 
Ctrl+B D
```


8) Executar o server:
 
```
tmux
cd BOMROBOTO/fb-master
python3 fb_bot.py
Ctrl+B $ fb_bot
Ctrl+B d
```

8) Executar o BOT:
 
```
tmux
cd BOMROBOTO/main_botXX
python3 mainbot.py
Ctrl+B $ bot
Ctrl+B d
```

9) Ativar portugues:

```
echo 'export LANG=C' >>~/.bashrc
echo 'export LANG=en_US.UTF-8' >>~/.bashrc
source ~/.bashrc
```
OU	
	
```
sudo locale-gen et_EE et_EE.UTF-8 en_US en_US.UTF-8
sudo dpkg-reconfigure locales

```

# INSCRIÇÃO LEAN STARTUP

Nome do grupo ou empresa *

```
   ERMES Conversitional Commerce 
```

Descrição resumida da empresa ou grupo (até 50 palavras) *

```
   Somos três jovens amigos, desenvolvedores, com a vontade de transformar o modo como a comunicação entre pessoas e empresas acontece atualmente. O ERMES (É um Robô Marcando Esse Serviço?) teve inicio no ano de 2017, atravéz de um protótipo testado nas redes sociais. Nosso próximo desafio é valida-lo no mercado.
```

Público alvo atual?*

```   
   Nosso público alvo é composto de pequenas e médias empresas cujos serviços prestados precisam ser agendados atravéz de um contato entre o fornecedor e o clientes. Visamos principalmente o mercado de empresas de delivery, consultórios médicos, salões de beleza, veterinários & petshop , etc. Em média, nos últimos anos,segundo dados oficiais o somatório destes mercados movimentou alguma coisa em torno de R$ 40 bilhões por ano. Nossa idéia é oferecer uma solução, ainda que não completamente customizada, mas que possa atender as necessidades da maioria dos empreendedores deste público alvo.
```

Qual problema busca resolver para o público alvo atualmente?*

```
   A baixa eficiência dos mecanismos de agendamento de serviços existentes dentro desse público alvo, que contribuem para a dificultar a acessibilidade, por parte do cliente, e o baixo aproveitamento efetivo do tempo de trabalho, por parte do fornecedor. 

	A pesar do atendimento ao cliente ser fundamental para pequenas e médias empresas a maior parte do agendamento ou do suporte com dúvidas é um processo mecânico, ou seja, pode ser automatizado sem perda de qualidade. O chatbot permitira maior  acessibilidade, com atendimento 24 horas, o que aumenta o número de potenciais clientes do nosso público alvo. Além disso, acreditamos que podemos retirar toda a preocupação, com agendamento, do gestor de uma pequena e média empresa, que muita das vezes se divide entre apoio ao cliente e a realização da atividade fim.
```

Empresa ja existe? Se sim, qual a faixa de faturamento? *

```
A empresa ainda não tem faturamento
```

A empresa ou grupo faz uso, ou pretende fazer uso, de alguma tecnologia para entregar sua oferta? Se sim, qual tipo de tecnologia? *

```
Pretendemos utilizar a tecnologia de chatbots integrada as redes sociais do nosso cliente (b2b), que realizará o atendimento/agendamentamento e sincronizará essas informações através de um único aplicativo. Utilizaremos,tambem, Machine Learning para aprimorar a nossa plataforma.

Nossa Protótipo para referência: https://www.facebook.com/VaterinariaChatbot/
```  

O grupo ou empresa está vinculado a alguma instituição de apoio (Universidade, Laboratório, Incubadora, Parque Tecnológico, Aceleradora, Outros)? Se sim, aponte qual a instituição. *

```
   Atualmente não temos nenhuma ligaçõa direta com instituições de apoio (mais gostariamos!)


# BIBLIOGRAFIA

http://masnun.com/2016/05/22/building-a-facebook-messenger-bot-with-python.html
http://flask.pocoo.org/
http://askubuntu.com/questions/8653/how-to-keep-processes-running-after-ending-ssh-session
http://man.openbsd.org/OpenBSD-current/man1/tmux.1

