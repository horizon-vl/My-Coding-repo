freq={}
word=input("Enter a word:")
for ch in word:
    if ch in freq:
        freq[ch]+=1
    else:
        freq[ch]=1

for key in freq:
    print(key, ":", freq[key])
