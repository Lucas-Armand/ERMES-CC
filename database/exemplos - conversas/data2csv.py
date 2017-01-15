# -*- coding: utf-8 -*-
"""
Created on Sat Jan 14 23:45:26 2017

@author: armand
"""
import inspect, os
import csv

mypath = os.path.dirname(os.path.abspath(inspect.getfile(inspect.currentframe())))

fileList = ['Base de Dados/'+name for name in os.listdir(mypath+'/Base de Dados/')]


subText = ''

for fileName  in fileList:
    
    file = open(fileName,'r')
    lines = file.readlines()
    
    for line in lines:
        line = line.replace(', ',' \t ')
        line = line.replace(' - ',' \t ')
        line = line.replace(': ',' \t ')
        subText += line
        
    subText += '\n---\n\n'
    
CSV=open("Dados.csv",'w')
CSV.write(subText)
CSV.close()
 

    

def csv_read(name):	#Metodo de leitura, transforma um arquivo CSV em  um vetor 

	CSV=open(name,'r')
	dados=CSV.read()
	dados=dados.replace(',','.')
	dados=dados.replace(';',',')
	CSV.close()
	
	CSV=open("temp.csv",'w')
	CSV.write(dados)
	CSV.close()
	
	CSV=open("temp.csv",'r')
	dados=csv.reader(CSV)
	v=[]
	for i in dados:
		v.append(map(float,i))
	CSV.close()
	os.remove("temp.csv")
	return (v)
