a=int(input("Enter a number:"))
b=0
temp=a
while a>0:
   b=b*10+(a%10)
   a//=10

if temp==b:
    print("Palindrome")
else:
    print("Not Palindrome")
