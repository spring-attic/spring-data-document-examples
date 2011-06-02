Start your MongoDB instance. For a MongoDB guide see http://www.mongodb.org/display/DOCS/Quickstart
Your MongoDB instance should be listening on the default port.

From the commandline run:
	mvn exec:java
	
You should see some output resembling this:

Results: [Person [id=4de7c19bab45985f76ebe650, name=John, age=39, accounts=[Account [id=null, accountNumber=1234-59873-893-1, accountType=SAVINGS, balance=123.45]]]]
DONE!
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESSFUL
[INFO] ------------------------------------------------------------------------

