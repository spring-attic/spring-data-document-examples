Start your MongoDB instance. For a MongoDB guide see http://www.mongodb.org/display/DOCS/Quickstart
Your MongoDB instance should be listening on the default port.

From the commandline run:
	mvn exec:java
	
You should see some output resembling this:

Results: { "_id" : { "$oid" : "4d54502b60ad8987435bbdd4"} , "accounts" : [ { "accountType" : "SAVINGS" , "accountNumber" : "1234-59873-893-1" , "balance" : 123.45}] , "name" : "John" , "age" : 39}
DONE!
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESSFUL
[INFO] ------------------------------------------------------------------------

