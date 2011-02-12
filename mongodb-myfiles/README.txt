Start your MongoDB instance. For a MongoDB guide see http://www.mongodb.org/display/DOCS/Quickstart
Your MongoDB instance should be listening on the default port.

From the commandline run:
	mvn exec:java -Dexec.mainClass=com.springone.examples.mongo.myfiles.MongoFilesLoadApp
	mvn exec:java -Dexec.mainClass=com.springone.examples.mongo.myfiles.MongoFilesQueryApp	

You should see some output resembling this:

INITIALIZING!
CLOSING!
COLLECTIONS: [HelloMongo, myFiles, system.indexes, system.users]
LOADED: 66

...

ALL [66]
QRY [0]



