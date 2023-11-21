import random


def run_game():
    """
    TODO: implement Mastermind code here
    """
    code = [0, 0, 0, 0]
    print("4-digit Code has been set. Digits in range 1 to 8. You have 12 turns to break it.")
    for i in range(len(code)):
        x = random.randint(1, 8)
        while x in code:
            x = random.randint(1, 8)
        code[i] = x
    for attempts in range (0, 12):
        correctdigit = 0
        correctplace = 0
        userInput = input("Input 4 digit code: ")
        x = 0
        while x == 0:
            userCode = list(userInput)
            if userInput.isnumeric() and len(userInput) == 4:
                for i in range(len(userCode)):
                    if int(userCode[i]) > 0 and int(userCode[i]) < 9:
                        userCode[i] = int(userCode[i])
                        x = 1
                    else:
                        x = 0
                        print("Please enter exactly 4 digits.")
                        userInput = input("Input 4 digit code: ")
                        break
            else:
                print("Please enter exactly 4 digits.")
                userInput = input("Input 4 digit code: ")
        for i in range(len(code)):
            if code[i] == userCode[i]:
                correctplace += 1
            elif code[i] == userCode[0]:
                correctdigit += 1
            elif code[i] == userCode[1]:
                correctdigit += 1
            elif code[i] == userCode[2]:
                correctdigit += 1
            elif code[i] == userCode[3]:
                correctdigit += 1
        if correctplace == 4:
            print("Number of correct digits in correct place:     " + str(correctplace))
            print("Number of correct digits not in correct place: 0")
            print("Congratulations! You are a codebreaker!")
            print("The code was: " + ''.join(map(str, code)))
            break
        else:
            print("Number of correct digits in correct place:     " + str(correctplace))
            print("Number of correct digits not in correct place: " + str(correctdigit))
            if 11 - attempts > 0:
                print("Turns left: " + str(11 - attempts))
            else:
                break

if __name__ == "__main__":
    run_game()
