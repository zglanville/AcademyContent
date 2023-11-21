

def create_outline():
    course_topics = {"Introduction to Python":"", "Tools of the Trade":"", "How to make decisions":"", "How to repeat code":"", "How to structure data":"", "Functions":"", "Modules":""}
    sorted_topics = sorted(course_topics)
    problem_list = ["Problem 1, ", "Problem 2, ", "Problem 3"]
    student_progress = [("Tom - ", "Introduction to Python - ", "Problem 3 ", "[GRADED]"),("Nyari - ", "Tools of the Trade - ", "Problem 2 ", "[STARTED]"),("Adam - ", "How to make decisions - ", "Problem 1 ", "[COMPLETED]")]
    student_progress.sort(key=lambda tup: tup[3], reverse=True)
    print("Course Topics:")
    for i in sorted_topics:
        print("*", i)
        course_topics[i] = "".join(problem_list)
    print("Problems:")
    for i in course_topics:
        print("*", i, ":", course_topics[i])
    print("Student Progress:")
    j = 1
    for i in student_progress:
        print(str(j) + ".", "".join(i))
        j += 1
    pass


if __name__ == "__main__":
    create_outline()
