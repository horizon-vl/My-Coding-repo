a=int(input("Enter a number:"))
b=0
while a>0:
   b=b*10+(a%10)
   a//=10

print(b)
