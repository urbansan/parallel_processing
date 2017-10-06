#!/usr//bin/python

# run_parallel_v1.2
#
# Written by Krzysztof Urbanczyk [krzysztof.urbanczyk@accenture.com]
#
# Script/Class runs processes simultaneously. There are many simple tools to accomplish this task, 
# but unfortunately where not available on SunOS at the time e.g. xargs -max_procs, multiprocessing package for python etc.
#
#backlog:
#   v1.2 - simplified logs, check return codes and logs their status
#   v1.1 - Added options to turn off logs

import os, sys, time
from datetime import datetime as dt

class UserException(Exception):
    pass


class MotherProcess:
    """
        multiprocessing file=[command_file] max_proc=[number_of_processes]

        (ver.1.2) Simple parallel processing tool. Equivalent of "xargs -max_procs". Program generates a log file

        [file] - file containing shell commands

        [max_procs] - max processes that will run simultaneously
"""

    def __init__(self, argv):
        source_file = None
        self.proc_max_count = None
        self.return_code = 0
        self.COMMAND_LIST = None

        if len(sys.argv) == 3:
            for arg in sys.argv[1:]:
                if arg.startswith('file='):
                    source_file = arg.split('=')[1]
                    if not os.path.exists(source_file) or not os.path.isfile(source_file):
                        raise UserException('File does not exist or a folder has been given')
                if arg.startswith('max_proc='):
                    try:
                        self.proc_max_count = int(arg.split('=')[1])
                    except ValueError, e:
                        raise UserException('Incorrect value for amount of processes')
            if self.proc_max_count and source_file:

                fd = open(source_file)
                self.COMMAND_LIST = [line for line in fd]
                fd.close()
                if not self.COMMAND_LIST:
                    raise UserException('command list is empty')
            else:
                raise UserException('You have given invalid arguments e.g. wrong value for processes or wrong file path')
        else:
            raise UserException('Wrong number of arguments')

        self.log_file = 'multiprocessing_log_' + str(os.getpid()) + '.txt'
        log_file = open(self.log_file, 'w')
        self.log_formatter = 'status: %7s | pid: %7s | start: %8s | stop: %8s | command: %s'
        log_file.writelines([self.log_formatter % ('waiting', 'none', 'none', 'none', cmd) for cmd in self.COMMAND_LIST])
        log_file.close()

    def log_to_file(self):
        log_file = open(self.log_file, 'r')
        log_data = log_file.readlines()
        log_file.close()

        log_data[self.index] = self.log_formatter % (self.status, self.pid, self.start, self.stop, self.cmd)

        log_file = open(self.log_file, 'w')
        log_data = log_file.writelines(log_data)
        log_file.close()

    def start_child_process(self, index, cmd):
        self.index = index
        self.status = 'running'
        self.cmd = cmd
        self.pid = str(os.getpid())
        self.start = dt.now().strftime('%H:%M:%S')
        self.stop = 'none'
        self.log_to_file()

        return_code = 1 if os.system(self.cmd) else 0

        self.status = 'error' if return_code else 'done'
        self.stop = dt.now().strftime('%H:%M:%S')
        self.log_to_file()

        return return_code

    def run(self):
        """Main function for running parallel tasks"""
        proc_count = 0
        child_list = []
        for index, cmd in enumerate(self.COMMAND_LIST):
            if proc_count < self.proc_max_count:
                processid = os.fork()
                if processid == 0:
                    sys.exit(self.start_child_process(index, cmd))
                else:
                    child_list.append(processid)
                    proc_count += 1
            else:

                coz = os.wait()
                child_list.remove(coz[0])
                self.return_code = 1 if coz[1] else self.return_code

                processid = os.fork()
                if processid == 0:
                    sys.exit(self.start_child_process(index, cmd))
                else:
                    child_list.append(processid)

        while child_list:
            coz = os.wait()
            self.return_code = 1 if coz[1] else self.return_code
            child_list.remove(coz[0])

        return self.return_code


if __name__ == '__main__':
    return_code = 0
    if len(sys.argv) < 2:
        print MotherProcess.__doc__
    else:
        try:
            mother_process = MotherProcess(sys.argv)
        except UserException, e:
            print e
            print MotherProcess.__doc__
            sys.exit(-1)
        return_code = mother_process.run()

    sys.exit(return_code)