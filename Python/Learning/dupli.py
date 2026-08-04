sent=input("Enter a sentence:")
words=sent.split()

un=[]

for word in words:
    if word not in un:
        un.append(word)

for a in un:
    print(a,end=" ")
