

# TODO: Step 1 - get shape (it can't be blank and must be a valid shape!)
def get_shape():
    shape = input("Shape?: ")
    shape = shape.lower()
    shape_selection = ["pyramid", "square", "triangle", "rhombus", "diamond", "rectangle"]
    while shape not in shape_selection:
        shape = input("Shape?: ")
    return shape


# TODO: Step 1 - get height (it must be int!)
def get_height():
    height = input("Height?: ")
    if height.isnumeric() == True:
        if (int(height) > 0 and int(height) <= 80):
            return int(height)
        else:
            return get_height()
    else:
        return get_height()

# TODO: Step 2
def draw_pyramid(height, outline):
    if outline == False:
        for i in range(1, height + 1):
            print(" " * (height - i) + "*" * (2 * i - 1))
    else:
        row = 1
        linespace = height - 1
        inspace = row - 1
        print (" " * linespace + "*")
        linespace -= 1
        while linespace != 0:
            row += 1
            inspace = (((row - 1) * 2) - 1)
            print (" " * linespace + "*" + " " * inspace + "*")
            linespace -= 1
        print ("*" * (2 * height - 1))



# TODO: Step 3
def draw_square(height, outline):
    if outline == False:
        for row in range(1, height + 1):
            print("*" * height)
    else:
        for row in range(1, height + 1):
            for column in range(1, height + 1):
                if row == 1 or row == height or column == 1 or column == height:
                    print("*", end = "")
                else:
                    print(end = " ")
            print()


# TODO: Step 4
def draw_triangle(height, outline):
    if outline == False:
        for i in range(1, height + 1):
            print("*" * i)
    else:
        for i in range(1, height + 1):
            if i - 2 < 1 or i == height:
                print("*" * i)
            else:
                print("*" + (" " * (i - 2)) + "*")

def draw_rhombus(height, outline):
    if outline == False:
        for i in range(1, height + 1):
            print(" " * (height - i) + "*" * height)
    else:
        for i in range(1, height + 1):
            if i == 1:
                print(" " * (height - i) + "*" * height)
            elif i > 1 and i < height:
                print(" " * (height - i) + "*" + " " * (height - 2) + "*")
            else:
                print("*" * height)

def draw_diamond(height, outline):
    offheight = height/2 + 0.5
    newheight = int(offheight)
    if outline == False:
        for i in range(1, newheight + 1):
            print(" " * (newheight - i) + "*" * (i * 2 - 1))
        for i in range(1, newheight):
            print(" " * (newheight - (newheight - i)) + "*" * ((newheight - i) * 2 - 1))
    else:
        row = 1
        linespace = newheight - 1
        inspace = row - 1
        print (" " * linespace + "*")
        linespace -= 1
        while linespace >= 0:
            row += 1
            inspace = (((row - 1) * 2) - 1)
            print (" " * linespace + "*" + " " * inspace + "*")
            linespace -= 1
        linespace = 1
        while linespace < newheight - 1:
            row -= 1
            inspace = (((row - 1) * 2) - 1)
            print (" " * linespace + "*" + " " * inspace + "*")
            linespace += 1
        print (" " * linespace + "*")

def draw_rectangle(height, outline):
    if outline == False:
        for row in range(height):
            print("*" * 2 * height)
    else:
        for row in range(0, height):
            for column in range(0, height * 2):
                if column == 0 or row == 0 or column == height * 2 - 1 or row == height - 1:
                    print("*", end="")
                else:
                    print(end=" ")
            print()

# TODO: Steps 2 to 4, 6 - add support for other shapes
def draw(shape, height, outline):
    
    if shape == 'pyramid':
        draw_pyramid(height, outline)
    elif shape == 'square':
        draw_square(height, outline)
    elif shape == 'triangle':
        draw_triangle(height, outline)
    elif shape == 'rhombus':
        draw_rhombus(height, outline)
    elif shape == 'diamond':
        draw_diamond(height, outline)
    elif shape == 'rectangle':
        draw_rectangle(height, outline)


# TODO: Step 5 - get input from user to draw outline or solid
def get_outline():
    outline = input("Outline only? (y/N): ")
    if outline == 'y':
        return True
    else:
        return False


if __name__ == "__main__":
    shape_param = get_shape()
    height_param = get_height()
    outline_param = get_outline()
    draw(shape_param, height_param, outline_param)

