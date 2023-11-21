import sys
import world.text.world as world
for x in sys.argv:
    if x == 'turtle':
        import world.turtle.world as world
from collections import defaultdict

edges_list = []
graph = defaultdict(list)

def is_position_blocked(x, y, obstacles):
    """
    determines whether there is an obstacle blocking the robot at its current position
    """
    for i in obstacles:
        if i[0] <= x and x <= i[0] + 4:
            if i[1] <= y and y <= i[1] + 4:
                return True
    return False

def check_edges(obstacles):
    global edges_list
    count_y = -200
    while count_y <= 200:
        count_x = -100
        while count_x <= 100:
            if not is_position_blocked(count_x, count_y, obstacles):
                if not is_position_blocked(count_x, count_y + 1, obstacles) and count_y + 1 <= 200:
                    edges_list.append(((count_x, count_y), (count_x, count_y + 1)))
                if not is_position_blocked(count_x + 1, count_y, obstacles) and count_x + 1 <= 100:
                    edges_list.append(((count_x, count_y), (count_x + 1, count_y)))
            count_x = count_x + 1
        count_y = count_y + 1

def create_graph():
    global graph
    for x in edges_list: 
        a, b = x[0], x[1]  
        graph[a].append(b) 
        graph[b].append(a)

def simple_solver(robot_name, goal, i, turtle, edge):
    """
    function that runs the maze if there are a low number of obstacles in the interest of time
    """
    path = []
    count_x = 0
    count_y = 0
    position = (count_x, count_y)
    if edge == 'top':
        while position[i] != goal[i]:
            path.append(position)
            count_y = count_y + 1
            position = (count_x, count_y)
        path.append(position)
        if turtle == False:
            print("Shortest path =", *path)
            world.show_position(robot_name, path[-1][0], path[-1][1])
            print(' > '+robot_name+': I am at the top edge.')
            return
        if turtle == True:
            world.show_position(robot_name, path)
            count_x = 0
            count_y = 0
            path = []
            return
    if edge == 'bottom':
        while position[i] != goal[i]:
            path.append(position)
            count_y = count_y - 1
            position = (count_x, count_y)
        path.append(position)
        if turtle == False:
            print("Shortest path =", *path)
            world.show_position(robot_name, path[-1][0], path[-1][1])
            print(' > '+robot_name+': I am at the '+edge+' edge.')
            return
        if turtle == True:
            world.show_position(robot_name, path)
            return
    if edge == 'left':
        while position[i] != goal[i]:
            path.append(position)
            count_x = count_x - 1
            position = (count_x, count_y)
        path.append(position)
        if turtle == False:
            print("Shortest path =", *path)
            world.show_position(robot_name, path[-1][0], path[-1][1])
            print(' > '+robot_name+': I am at the '+edge+' edge.')
            return
        if turtle == True:
            world.show_position(robot_name, path)
            return
    if edge == 'right':
        while position[i] != goal[i]:
            path.append(position)
            count_x = count_x + 1
            position = (count_x, count_y)
        path.append(position)
        if turtle == False:
            print("Shortest path =", *path)
            world.show_position(robot_name, path[-1][0], path[-1][1])
            print(' > '+robot_name+': I am at the '+edge+' edge.')
            return
        if turtle == True:
            world.show_position(robot_name, path)
            return
    return
    
def BFS_SP(robot_name, goal, i, turtle, edge, obstacles):
    """
    pathfinding algorithm called Breadth First Search that solves the maze and prints out the path or an error if no path was found
    """
    check_edges(obstacles)
    create_graph()
    explored = [] 
    queue = [[(0,0)]] 
    if (0,0) == goal: 
        print("Same Node") 
        return
    while queue: 
        path = queue.pop(0) 
        node = path[-1] 
        if node not in explored: 
            neighbours = graph[node] 
            for neighbour in neighbours: 
                new_path = list(path) 
                new_path.append(neighbour) 
                queue.append(new_path)  
                if neighbour == goal or neighbour[i] == goal[i]:
                    if turtle == False:
                        print("Shortest path =", *new_path)
                        world.show_position(robot_name, new_path[-1][0], new_path[-1][1])
                        print(' > '+robot_name+': I am at the '+edge+' edge.')
                    if turtle == True:
                        world.show_position(robot_name, new_path)
                    return
            explored.append(node) 
    print("So sorry, but a connecting path doesn't exist :(") 
    return

# print(time.process_time())