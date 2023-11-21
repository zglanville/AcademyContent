import sys
from cal_setup import get_calendar_service
from event_management.attend_slot import attend_event
from event_management.delete_slot import delete_event
from event_management.host_slot import host_event 
from user.user_login import login
from user.user_logout import logout
from user.user_get import get_user
from output.cal_output import cal_output
from datetime import datetime
from write_to_file import write_to_file, return_seven_days

def run_login():
    """Executes Login Function"""

    login()

def run_logout():
    """Executes Logout Function"""

    logout()

def run_calendar():
    """Executes Calendar Output Function"""

    cal_output()

def validation_host_attend(date_string, start_time, args):
    """Checks Formatting and Validity of Date, Time and Arguments"""
    
    if not args:
        raise SystemExit(f"""\nError: Missing Arguements After Command\n""")
    if not date_string:
        raise SystemExit(f"""\nError: Date Invalid or Missing\n""")
    if not start_time:
        raise SystemExit(f"""\nError: Time Invalid or Missing\n""")
    if date_string:
        try:
            test_object = datetime.strptime(date_string, '%Y-%m-%d')
        except:
            raise SystemExit(f"""\nError: Date Invalid.\n\nUse Format yyyy-mm-dd\n""")
    if start_time:
        try:
            test_object = datetime.strptime(start_time, '%H:%M')
        except:
            raise SystemExit(f"""\nError: Time Invalid.\n\nUse 24HR Format: HH:MM\n""")
    
def split_args(args):
    """Splits up Arguments after Command
    :return: Date, Time, Host Name"""

    date_string = ""
    start_time = ""
    host_name = ""

    for arg in args:
        if "-" in arg:
            date_string = arg
        elif ":" in arg:
            start_time = arg
        else:
            host_name = arg
        
    return date_string, start_time, host_name

def run_attend(user_email, user_name, args):
    """Executes Attend Event function after checking validity of input"""

    date_string, start_time, host_name = split_args(args)
    validation_host_attend(date_string, start_time, args)
    attend_event(user_email, user_name, date_string, start_time, host_name)
    write_to_file(return_seven_days())

def run_delete(user_email, user_name, args):
    """Executes Delete Event function after checking validity of input"""

    date_string, start_time, _ = split_args(args)
    validation_host_attend(date_string, start_time, args)
    delete_event(user_email, user_name, date_string, start_time)
    write_to_file(return_seven_days())

def run_host(user_email, user_name, args):
    """Executes Host Event function after checking validity of input"""
    
    date_string, start_time, _ = split_args(args)
    validation_host_attend(date_string, start_time, args)
    host_event(user_email, user_name, date_string, start_time)
    write_to_file(return_seven_days())

def run_help():
    """Displays Available commands that the user can use."""

    print("""\nThe Following Commands Are Available:

LOGIN    - Login to Google Calender.
LOGOUT   - Logout from Google Calender.
HELP     - Prints the Help Commands.
CALENDAR - Displays the Code Clinic Calendar.
HOST     - Create a Code Clinic for you to Host.
ATTEND   - Book a Code Clinic for you to Attend.
DELETE   - Delete an Empty Code Clinic Session.
""")

def run_command(command, args):
    """Calls appropriate function based on the user input."""
    
    user_email, user_name = get_user()

    if command.lower() == "login":
        run_login()
    elif command.lower() == "help":
        run_help()
    elif command.lower() == "calendar":
        run_calendar()
    elif not user_name or not user_email:
        raise SystemExit(f"Please Login to Use Command: {command}")
    elif command.lower() == "logout":
        run_logout()
    elif command.lower() == "host":
        run_host(user_email, user_name, args)
    elif command.lower() == "attend":
        run_attend(user_email, user_name, args)
    elif command.lower() == "delete":
        run_delete(user_email, user_name, args)
    else:
        raise SystemExit(f"""\nError: "{command}" is not a Valid Command\n""")

def run_booking_system():
    """Checks System Arguments and Runs Command Entered."""
    
    if len(sys.argv) < 2:
        raise SystemExit("\nError: No Argument Was Input.\n")
    elif len(sys.argv) > 5:
        raise SystemExit("\nError: Too Many Arguments.\n")
    else:
        run_command(sys.argv[1], sys.argv[2:])


if __name__ == "__main__":
    run_booking_system()
