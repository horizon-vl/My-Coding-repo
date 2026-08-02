a=input("Enter a password:")
is_upper = False
is_lower = False
is_digit = False
is_len = False

if len(a)>8:
    islen=True

for ch in a:
    if ch.isupper():
        is_upper=True
    if ch.islower():
        is_lower=True
    if ch.isdigit():
        is_digit=True

if is_upper and is_lower and is_digit and is_len:
    print("Good strength")
else:
    print("Bad strength")
