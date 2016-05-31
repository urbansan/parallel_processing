#!/usr/local/bin/python

# run_parallel_v1.1
#
# Written by Krzysztof Urbanczyk [krzysztof.urbanczyk@accenture.com]
#
# Script/Class runs processes simultaneously. There are many simple tools to accomplish this task, 
# but unfortunately where not available on SunOS at the time e.g. xargs -max_procs, multiprocessing package for python etc.
#
#backlog:
#   v1.1 - Added options to turn off logs

import os, sys, time


class MotherProcess:

    def __init__(self, proc_max_count, COMMAND_LIST, childlog = False, motherlog = False):
        self.COMMAND_LIST = COMMAND_LIST
        self.proc_max_count = proc_max_count
        for line in sys.stdin:
            self.COMMAND_LIST.append(line)
        self.motherlog = motherlog
        self.childlog = childlog
        
        if childlog:
            self.log_name = 'childlog_' + str(os.getpid()) + '.txt'
            open(self.log_name, 'w').close()
        if motherlog:
            self.mother_name = 'motherlog_' + str(os.getpid()) + '.txt'
            open(self.mother_name, 'w').close()

    def log_(self, txt):
        if childlog:
            log_file = open(self.log_name, 'a')
            log_file.write(time.ctime() + ', ' + str(os.getpid()) + ', ' + txt.strip() + '\n')
            log_file.close()

    def log_m(self, txt):
        if motherlog:
            mother_file =  open(self.mother_name, 'a')
            mother_file.write(time.ctime() + ', ' + str(os.getpid()) + ', ' + txt.strip() + '\n')
            mother_file.close()

    def child_proc(self, cmd):
        os.system(cmd)

    def run(self):
        self.log_m('MOTHER STARTED, number of parallel processes: ' + str(self.proc_max_count))

        #PARALLEL FEEDING PROCESS STARTS:
        proc_count = 0
        child_list = []
        for cmd in self.COMMAND_LIST:
            if proc_count < self.proc_max_count:
                processid = os.fork()
                if processid == 0:
                    self.log_(cmd)
                    self.child_proc(cmd)
                    self.log_(cmd)
                    sys.exit(0)

                else:
                    child_list.append(processid)
                    self.log_m('PID: ' + str(processid) + ' started')
                    proc_count += 1
            else:

                coz = os.wait()
                self.log_m('PID: ' + str(coz[0]) + ' finished')
                child_list.remove(coz[0])

                processid = os.fork()
                if processid == 0:
                    self.log_(cmd)
                    self.child_proc(cmd)
                    self.log_(cmd)
                    sys.exit(0)
                else:
                    child_list.append(processid)
                    self.log_m('PID: ' + str(processid) + ' started')

        while child_list:
            coz = os.wait()
            self.log_m('PID: ' + str(coz[0]) + ' finished')
            child_list.remove(coz[0])

        self.log_m('MOTHER FINISHED')



if __name__ == '__main__':

    help_txt = '''
        run_parallel.py [max_procs] [logs]

        (ver.1.1) Simple parallel processing tool. Equivalent of "xargs -max_procs"

        [max_procs] - max processes that will run simultaneously

        [logs] - Decide which logs you want to create.

            Childlog (recommended) is a log from the children processes perspective and shows the commands which have been run.

            Motherlog is a log of PID's that start and end from the mother process perspective.

            Values:
                logs - create motherlog and childlog
                motherlog - create only motherlog_<motherPID>.txt
                childlog - create only childlog_<motherPID>.txt
'''
    try:
        proc_max_count = int(sys.argv[1])
    except:
        print help_txt
        sys.exit(-1)
    
    motherlog = False
    childlog = False

    if len(sys.argv) > 2:
        if sys.argv[2] == 'logs' or sys.argv[2] == 'childlog':
            childlog = True
        if sys.argv[2] == 'logs' or sys.argv[2] == 'motherlog':
            motherlog = True
        if not (childlog or motherlog):
            print help_txt
            sys.exit(-1)
            
    COMMAND_LIST = []
    for line in sys.stdin:
        COMMAND_LIST.append(line)

    if COMMAND_LIST:
        mother_process = MotherProcess(proc_max_count, COMMAND_LIST, childlog, motherlog)
        mother_process.run()

sys.exit(0)
