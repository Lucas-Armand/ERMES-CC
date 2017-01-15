# -*- coding: utf-8 -*-
"""
Created on Sun Jan 15 02:55:01 2017

@author: armand
"""

import datetime
import os
import csv
import requests
from bs4 import BeautifulSoup



file=open('links.txt','r')
lines=file.readlines()

Text = ''
for link in lines:    
    
     print link
        
     r    =requests.get(link) #Example = 09142015")
     soup =BeautifulSoup(r.content)
     result = soup('span',class_='f irregular')
     result = [str(x)[26:-7] for x in result]
     print soup
     print result
     
     verb_0 = link.strip('https://www.conjugacao.com.br/verbo-').strip('/')
#    
     Text += verb_0
     for verb in result:
         Text += '\t' + verb  
     Text +='\n'
     
print Text

CSV=open("irregular.csv",'w')
CSV.write(Text)
CSV.close()
 