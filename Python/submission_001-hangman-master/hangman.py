#TIP: use random.randint to get a random word from the list
import random


def read_file(file_name):
    wordsfile = open(file_name)
    words = wordsfile.readlines()
    wordsfile.close()
    return(words)


def select_random_word(words):
    number = random.randint(1, len(words) -2)
    word = words[number]
    number2 = random.randint(0, len(word) -2)
    letter = word[number2]
    word2 = word.replace(letter, "_", 1)
    print ("Guess the word:", word2)
    return(word)


def get_user_input():
    response = input("Guess the missing letter: ")
    return(response)

def run_game(file_name):
    """
    This is the main game code. You can leave it as is and only implement steps 1 to 3 as indicated above.
    """
    words = read_file(file_name)
    word = select_random_word(words)
    answer = get_user_input()
    print('The word was: '+word)


if __name__ == "__main__":
    run_game('short_words.txt')

