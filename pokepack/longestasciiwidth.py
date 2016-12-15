mymax = 0
pokedex = open("pokedex.txt")
for line in pokedex.readlines():
    name = (" ").join(line.split()[1:])
    try:
        pokemon = open(name+".txt")
        for line in pokemon.readlines():
            if mymax < len(line):
                mymax = len(line)
    except Exception:
        pass

            
print(mymax)
