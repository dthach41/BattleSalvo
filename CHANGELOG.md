## Changes:
- Implemented a way for program to detect if we are playing against server or our AI 
(if there are command line arguments or not)
- Updated the name() method for our AI player to have our GitHub username
- Implemented records to represent JSONs of messages that have arguments
so that we can take the information from over the server and work with it
- Added a JsonsUtils class that has a method for serializing records
- Implemented a record for the response message Jsons for each of the 6 types of message
that we will be receiving from the server
- Implemented a ProxyController in order to handle games over a server
- In ProxyController, it handles receiving and sending messages from/to the server by taking in a socket as field
- Implemented a delegatesMessage method that calls a handler method based on
what the method-name of the message from the server was
