import sys

direction = 0

def error(robot_name, command):
    """
    error message to be played whenever an invalid command is given
    """
    print(robot_name + ': Sorry, I did not understand \'' + command + '\'' +'.')

def robot_position(robot_name, x, y):
    """
    message that is printed everytime the robot's position must be displayed
    """
    print(" > " + robot_name + " now at position (" + str(x) + "," + str(y) + ").")
        
def safezone(robot_name):
    """
    error message for when the robot tries to go outside the safe zone
    """
    print(robot_name + ": Sorry, I cannot go outside my safe zone.")
    
def sprint(robot_name, steps, x, y):
    """
    Executes the sprint command given the number of steps from which to count down.
    Utilises the forward() function to execute the steps
    """
    if direction == 2 or direction == 3:
        steps = steps*(-1)
    if direction == 1 or direction == 3:
        if x + steps >= 100 or x + steps <= -100:
            safezone(robot_name)
            robot_position(robot_name, x, y)
            user_command(robot_name, x, y)
        else:
            x = x + steps
            print(" > " + robot_name + " moved forward by " + str(abs(steps)) + " steps.")
    if direction == 0 or direction == 2:
        if y + steps >= 200 or y + steps <= -200:
            safezone(robot_name)
            robot_position(robot_name, x, y)
            user_command(robot_name, x, y)
        else:
            y = y + steps
            print(" > " + robot_name + " moved forward by " + str(abs(steps)) + " steps.")
    if abs(steps) > 1:
        return(sprint(robot_name, abs(steps) - 1, x, y))
    robot_position(robot_name, x, y)
    user_command(robot_name, x, y)
    
def forward(robot_name, steps, x, y):
    """
    moves the robot forward given the number of steps by the user
    """
    if direction == 2 or direction == 3:
        steps = steps*(-1)
    if direction == 1 or direction == 3:
        if x + steps >= 100 or x + steps <= -100:
            safezone(robot_name)
            robot_position(robot_name, x, y)
            user_command(robot_name, x, y)
        else:
            x = x + steps
            print(" > " + robot_name + " moved forward by " + str(abs(steps)) + " steps.")
            robot_position(robot_name, x, y)
            user_command(robot_name, x, y)
    if direction == 0 or direction == 2:
        if y + steps >= 200 or y + steps <= -200:
            safezone(robot_name)
            robot_position(robot_name, x, y)
            user_command(robot_name, x, y)
        else:
            y = y + steps
            print(" > " + robot_name + " moved forward by " + str(abs(steps)) + " steps.")
            robot_position(robot_name, x, y)
            user_command(robot_name, x, y)
    user_command(robot_name, x, y)
    
def back(robot_name, steps, x, y):
    """
    moves the robot backwards given the number of steps by the user
    """
    if direction == 0 or direction == 1:
        steps = steps*(-1)
    if direction == 1 or direction == 3:
        if x + steps >= 100 or x + steps <= -100:
            safezone(robot_name)
            robot_position(robot_name, x, y)
            user_command(robot_name, x, y)
        else:
            x = x + steps
            print(" > " + robot_name + " moved back by " + str(abs(steps)) + " steps.")
            robot_position(robot_name, x, y)
            user_command(robot_name, x, y)
    if direction == 0 or direction == 2:
        if y + steps >= 200 or y + steps <= -200:
            safezone(robot_name)
            robot_position(robot_name, x, y)
            user_command(robot_name, x, y)
        else:
            y = y + steps
            print(" > " + robot_name + " moved back by " + str(abs(steps)) + " steps.")
            robot_position(robot_name, x, y)
            user_command(robot_name, x, y)
    user_command(robot_name, x, y)
    
def right(robot_name, x, y):
    """
    rotates the robot 90 degrees to the right and changes the axis on which it is operating
    """
    global direction
    direction += 1
    if direction > 3:
        direction = 0
    print(" > " + robot_name + " turned right.")
    robot_position(robot_name, x, y)
    user_command(robot_name, x, y)
    
def left(robot_name, x, y):
    """
    rotates the robot 90 degrees to the left and changes the axis on which it is operating
    """
    global direction
    direction -= 1
    if direction < 0:
        direction = 3
    print(" > " + robot_name + " turned left.")
    robot_position(robot_name, x, y)
    user_command(robot_name, x, y)
    
def new_coords(steps):
    """
    Gives new 'y' value for user_command() when sprint() is run so that it can continue keeping track of 'y' position.
    """
    z = 0
    count = 0
    while count <= steps:
        z = z + count
        count += 1
    return(z)

def check_int(arg2):
    """
    checks that the argument expected for number of steps is a valid int. 
    Returns True for valid int or False for not valid int
    """
    try:
        int(arg2)
        return True
    except ValueError:
        return False
    
def off(robot_name):
    """
    creates an error with which to end the program when the off command is given
    """
    print("" + robot_name + ": Shutting down..")
    exit = 0/0
    
def help(robot_name, x, y):
    """
    prints out all available commands with a short description
    """
    print("I can understand these commands:\nOFF  - Shut down robot\nHELP - provide information about commands\nFORWARD - moves the robot forward\nBACK - moves the robot back\nRIGHT - turn the robot to the right\nLEFT - turns the robot to the left\nSPRINT - moves the robot forward in reducing steps from the value given until zero")
    user_command(robot_name, x, y)

def user_command(robot_name, x, y):
    """
    handles the user input from the user and then determines which functions to use to execute the command and then executes the command
    """
    command = input(robot_name + ": What must I do next? ")
    move_command = command.split()
    if command.lower() == "off":
        off(robot_name)
    elif command.lower() == "help":
        help(robot_name, x, y)
    if command.lower() == "right":
        right(robot_name, x, y)
    if command.lower() == "left":
        left(robot_name, x, y)
    if len(move_command) == 2:
        if move_command[0].lower() == "forward":
            if check_int(move_command[1]):
                steps = int(move_command[1])
                forward(robot_name, steps, x, y)
            else:
                error(robot_name, command)
                user_command(robot_name, x, y)
        if move_command[0].lower() == "back":
            if check_int(move_command[1]):
                steps = int(move_command[1])
                back(robot_name, steps, x, y)
            else:
                error(robot_name, command)
                user_command(robot_name, x, y)
        if move_command[0].lower() == "sprint":
            if check_int(move_command[1]):
                steps = int(move_command[1])
                sprint(robot_name, steps, x, y)
                user_command(robot_name, x, y)
            else:
                error(robot_name, command)
                user_command(robot_name, x, y)
        else:
            error(robot_name, command)
            user_command(robot_name, x, y)
    else:
        error(robot_name, command)
        user_command(robot_name, x, y)

def robot_start():
    """This is the entry function, do not change"""
    global direction
    x = 0
    y = 0
    robot_name = input("What do you want to name your robot? ")
    print("" + robot_name + ": Hello kiddo!")
    try:
        user_command(robot_name, x, y)
    except:
        direction = 0
        pass

if __name__ == "__main__":
    robot_start()