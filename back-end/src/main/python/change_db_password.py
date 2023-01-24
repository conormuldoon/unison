import shutil
import sys

CURRENT = "../resources/application.properties"
SWITCHED = "../resources/application.tmppy"
DS_PASSWORD = "spring.datasource.password"


# Changes the database password in the application properties file.
def changepassord(password):

    f0 = open(CURRENT, "r")

    f1 = open(SWITCHED, "w+")

    contents = f0.readlines()
    for line in contents:

        if line.startswith(DS_PASSWORD):
            f1.write(DS_PASSWORD + "=" + password + "\n")
            continue

        f1.write(line)

    f0.close()
    f1.close()
    shutil.move(SWITCHED, CURRENT)


# The new password is passed as an argument to main.
if __name__ == "__main__":
    changepassord(sys.argv[1])
