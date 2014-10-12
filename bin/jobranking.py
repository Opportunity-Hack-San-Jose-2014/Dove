#!/usr/bin/env python

from Queue import Queue
from threading import Thread
from optparse import OptionParser
import subprocess
import difflib 
import csv
import sys 
import urllib
import json
from pyswitch import Switch   


myswitch = Switch()
keywords= {}

@myswitch.case(range(0,10000))
def caseRange5(value):
    return 20

@myswitch.case(range(10000,20000))
def caseRange10(value):
    return 15

@myswitch.case(range(20000,40000))
def caseRange20(value):
    return 10

@myswitch.case(range(40000,80000))
def caseRange40(value):
    return 5

@myswitch.case(range(80000,1000000))
def caseRange80(value):
    return 0

def createJSON(stagea, stageb,  packages):
   #print "Prepare the front-end"
   # create data dictionary
   data = {}
   
   # create package list
   componentList = {}
   for c in packages:
       cdbdiff = [] 
       for f in c.cdbdiff:
          entries = []
          for e in f.patch:
            entries.append({"cdb_key": e.name, "stage1": e.value1, "stage2":e.value2})
          cdbdiff.append({"filename":f.filename, "diff":entries})
       if componentList.get(c.name):
          componentList[c.name].append({"configuration":cdbdiff})
       else:
          componentList[c.name] = []
          componentList[c.name].append({"configuration":cdbdiff})
   data["response"] = componentList; 
   return data

def process(candidate, jobs):
    #response = urllib2.urlopen(candidate)
    #jobs_res= urllib2.urlopen(candidate)
    #data = json.load(response)
    #jobs = json.load(jobs_res)
    data = json.loads(open('./candidate.json').read())
    jobs = json.loads(open('./jobs.json').read())

    jobs_bio_data= [] 
    jobs_scores = {}

    for job in jobs["jobs"]:
       address = data["candidate"]["address"]
       job_address = data["candidate"]["address"]
   #    if (datetime.datetime.strptime(job["jobdate"]["from"] >   )
       d_score = distance(address,job["jobaddress"])
       i_score = matching(data.get("candidate").get("skills"), normalize(job.get("skills"),keywords)) * 10
       i_score = 35 if i_score > 35 else i_score
   
       s_score = matching(data["candidate"]["interests"], normalize(job["skills"],keywords)) * 10
       s_score = 35 if i_score > 35 else i_score
        
       jobs_scores[job["jobname"]]=d_score + i_score + s_score
       entrydata = []
       entrydata.append(job["jobname"]) 
       entrydata.append(data["candidate"]["bio"]+"<***>"+job["jobdescription"]) 
       jobs_bio_data.append(entrydata)
    with open('score.csv', 'w') as fp:
        job_bio = csv.writer(fp, delimiter=',')
        job_bio.writerows(jobs_bio_data)

    p = subprocess.Popen("/x/home/liufang/tools/jobranking/bin/pig -x local -4 /x/home/liufang/tools/jobranking/bin/nolog.conf -param input=/x/home/liufang/tools/jobranking/bin/score.csv matching.pig > score.out",
	 shell=True, stdout=subprocess.PIPE)
    out, err = p.communicate()
    hdoopf = open("score.out",'r')
    for hline in  hdoopf.readlines():
        title = hline.split(",")[0].translate(None, '(')
        score = hline.split(",")[-1].translate(None, ')')
        jobs_scores[title] =  jobs_scores[title] + float(score)
        print  jobs_scores[title] 

def normalize(tags, keys):
    tag_n = {}
    for t in tags:
       tag_n[t]=t
       for k in keys:
           if t in keys[k]:
              tag_n[t].extend(keys[k])
           if t == k:
              tag_n[t].append(k)
    return tag_n




def matching(tags, keys):
    times = 0
    for t in tags:
       for k in keys:
           if t in keys[k]:
              times = 1 + times
           if t == k:
              times = 1 + times
         
    return 4 if  times > 3 else times 

def loadKeyWords():
    print 'load data'
    infile = open('keywords.txt','r')
    lines = infile.readlines()
    for i in lines:
        k1, key2= [a.strip() for a in i.split(',')]
        keywords.setdefault(k1, []).append(key2)  

def distance(address1, address2):
    #url= "https://maps.googleapis.com/maps/api/directions/json?origin="+address1+"&destination="+address2+"&key=AIzaSyAim206L20YgvzwtxYnTP57kWJpupObiNc&departure_time=1412866800&mode=transit"
    url= "https://maps.googleapis.com/maps/api/directions/json?origin="+address1+"&destination="+address2+"&key=AIzaSyAim206L20YgvzwtxYnTP57kWJpupObiNc"
    response = urllib.urlopen(url)
    data = json.load(response)
    dis = data["routes"][0]["legs"][0]["distance"]["value"]
    return myswitch(dis)

def returnwithstatus(status, callback,msg, diffmsg):
    params = {}
    params['status'] =status 
    params['msg']  = msg
    params['details'] = diffmsg
    jsonp = json.dumps(params, ensure_ascii=False)
    #jsonp = jsonp.replace('\'', "\"");
    print jsonp
    #params = urllib.urlencode(params)
    #f = urllib.urlopen(callback, jsonp)
    #print f.read()
    #exit(status)


parser = OptionParser()
parser.add_option("-c", "--candidate", dest="profile", metavar="string",
		  help="candidate profile restful api link")
parser.add_option("-j", "--job", dest="job", metavar="string",
                  help="job restful api link")
parser.add_option("-r", "--result", dest="result", metavar="string",
                  help="result callback restful api link")
(options, args) = parser.parse_args()

candidate=options.profile
jobs=options.job
callback=options.result

if not candidate: 
     parser.print_help()
     returnwithstatus(2, callback, "stage1 hostname is empty", "")
if not jobs: 
     parser.print_help()
     returnwithstatus(2, callback, "stage2 hostname is empty", "")
if not callback: 
     parser.print_help()
     returnwithstatus(2, callback, "components list is empty", "")

class Candidate:
  def __init__(self, skills, interests, address):
     self.skills = skills
     self.interests = interests 
     self.address = address

loadKeyWords()
process(candidate, jobs)


