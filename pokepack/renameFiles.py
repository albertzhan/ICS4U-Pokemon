import os
pokedex = open("pokedex.txt")
while True:
    try:
        [file,name] = pokedex.readline().split()
        os.rename(file+".txt",name+".txt")
        print(file,name)
    except:
        break

for line in pokedex.readlines():
    file = line.split()[0]
    name = (" ").join(line.split()[1:])
    try:
        os.rename(file+".txt",name+".txt")
    except:
        pass
