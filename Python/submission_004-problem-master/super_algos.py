
def find_min(element):
    i = len(element)
    if i == 0:
        return -1
    if not isinstance(element[0], int):
        return -1
    if i == 1:
        return(element[0])
    if int(element[0]) > element[1]:
        return(find_min(element[1:]))
    else:
        element.pop(1)
        return(find_min(element))
    pass


def sum_all(element):
    i = len(element)
    if i == 1:
        return(element[0])
    if i == 0:
        return -1
    if not isinstance(element[0], int):
        return -1
    element[0] = element[0] + element[1]
    element.pop(1)
    return(sum_all(element))
    pass


def find_possible_strings(character_set, n):
    """TODO: complete for Step 3"""
    if character_set:
        for x in range(len(character_set)):
            if not isinstance(character_set[x],str) or len(character_set[x]) != 1 or not n > 0:
                return []
    else:
        return []
    return find_all_possible(character_set,n,'',[])
    pass

def find_all_possible(character_set,n,word,list_of_combos):
    newword = ''
    count = 0
    #base case
    if n == 0:
        list_of_combos.append(word)
        return
    #recursion
    while count < len(character_set):
        newword = word + character_set[count]
        find_all_possible(character_set,n-1,newword,list_of_combos)
        count = count + 1
    return list_of_combos
    pass