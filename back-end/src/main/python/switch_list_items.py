import shutil

CURRENT = "../resources/application.properties"
SWITCHED = "../resources/application.tmppy"

# Toggles (comments/uncomments) the properties specified in the application properties file.
def switchitems(matchlist):

    f0 = open(CURRENT, "r")

    f1 = open(SWITCHED , "w+")

    contents = f0.readlines()
    for line in contents:
        addline = True
        for matchitem in matchlist:
            if matchitem in line:
                addline = False
                idx = line.find("#")+1

                if idx > 0:
                    f1.write(line[idx:])
                else:
                    f1.write("#"+line)
                break
        if addline:
            f1.write(line)

    f0.close()
    f1.close()
    shutil.move(SWITCHED, CURRENT)
