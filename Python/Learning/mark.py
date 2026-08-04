marks={}

a=int(input("Enter number of students:"))
for i in range(a):
    name = input("Enter name: ")
    mark = int(input("Enter marks: "))
    if mark>=90:
        marks[name]="A"
    if mark<=89 and mark>=80:
         marks[name]="B"
    if mark<=79 and mark>=70:
        marks[name]="C"
    if mark<=69 and mark>=60:
        marks[name]="D"
    if mark<=59:
        marks[name]="E"

for name,grade in marks.items():
    print(name,":",grade)
