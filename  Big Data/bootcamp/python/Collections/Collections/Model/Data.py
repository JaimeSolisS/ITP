import datetime


class Data:

    def __init__(self, id, name):
        self.id = id
        self.name = name
        self.key = str(id) + name[:3]
        self.status = datetime.datetime.now().strftime("%H:%M:%S")

    def __str__(self):
        return "| id: %i | Username: %s | key: %s | status: %s| " % (self.id, self.name, self.key, self.status)
