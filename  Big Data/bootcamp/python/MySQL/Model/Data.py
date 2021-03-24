import datetime


class Data:

    def __init__(self, id, name):
        self.id = id
        self.name = name
        self.key = str(id) + name[:3]
        self.status = "NEW-" + datetime.datetime.now().strftime("%H:%M:%S")

    def __str__(self):
        return "%i, %s, %s, %s" % (self.id, self.name, self.key, self.status)

    def __repr__(self):
        return "%i, %s, %s, %s" % (self.id, self.name, self.key, self.status)
