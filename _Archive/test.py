import math


r = 1.04

a = 450/r + 487/r**2 + 503/r**3 + 607/r**4

print(a)

mean = 23/7

lst = [4,5,3,2,6,2,1]
tot = 0
for ele in lst:
    tot += (ele-mean)**2

std = math.sqrt((tot)/6)

print(std)