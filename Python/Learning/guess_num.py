import random
a=int(random.randint(0,100))
cor=True
while cor:
    b=int(input("Enter a number to guess:"))
    if a==b:
        print("U guessed it!")
        cor=False
    elif a>b:
        print("Too low")
    else:
        print("Too High")
