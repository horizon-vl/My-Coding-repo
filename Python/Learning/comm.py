list1=[1,2,3,4,5]
list2=[2,4,6,8,0]
list3=[]
for a in list1:
    if a in list2:
        list3.append(a)

print(list3)
