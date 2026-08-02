word=input("Enter a word:")
count=0
for ch in word:
    if ch in "AEIOUaeiou":
        count=count+1

print(count)
