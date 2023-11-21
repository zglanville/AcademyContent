import random
obstacles = []
    
def generate_obstacles():
    """
    generates an obstacle list for the robot making sure that no obstacles spawn within 10 units of the robot
    """
    global obstacles
    for i in range(random.randint(1000, 2000)):
        xy = ((random.randint(-100, 100), (random.randint(-200, 200))))
        if xy[0] not in range(-10,10) or xy[1] not in range(-10,10):
            obstacles.append(xy)
        
def is_position_blocked(x, y):
    """
    determines whether there is an obstacle blocking the robot at its current position
    """
    for i in obstacles:
        if i[0] <= x and x <= i[0] + 4:
            if i[1] <= y and y <= i[1] + 4:
                return True
    return False

def smaller_bigger(first, second):
    """
    makes sure that the first x/y value is smaller than the second x/y value for the purposes of the 'range' functionality
    """
    if first < second:
        return first, second
    elif second < first:
        return second, first
    return first, second

def is_path_blocked(x1, y1, x2, y2):
    """
    checks whether the robot is going to be blocked by an obstacle on it's trajectory from its current position to the projected position
    """
    if y1 == y2:
        x_val = smaller_bigger(x1, x2)
        for i in range(x_val[0], x_val[1], 4):
            if is_position_blocked(i, y1):
                return True
    elif x1 == x2:
        y_val = smaller_bigger(y1, y2)
        for i in range(y_val[0], y_val[1], 4):
            if is_position_blocked(x1, i):
                return True
    return False

def get_obstacles():
    """
    gets and returns the list of obstacles
    """
    generate_obstacles()
    return obstacles

def return_obstacles():
    """
    simply returns the global variable 'obstacles'
    """
    return obstacles

def del_obstacles():
    """
    deletes the list of obstacles. For us at the end of the program when resetting variables for next run
    """
    global obstacles
    obstacles = []