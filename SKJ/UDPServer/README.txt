HOW TO START: Server.java launces the server threads. You need to launch this by specifying 
the port numbers, that writes in arguments.

After that you need to run Client.java. To run you need to specify an IP and port (one form the 
sequence which was given to the Server.java)

Client will connect to the server DatagramSocket and send to it a datagram with it’s IP and 
PORT. If this data is correct (in other case will be TIMEOUT ERROR) then Server will open a new 
Socket with random port. 
This port server will send to client and after that on this socket server will begin transmission. 
First will be the name of the file and after that bytes of file. Client will read these bytes and will 
create a txt file with name (new + name what was sent) and will write data in it. After that server 
begins to wait while client will be closed. I used DatagramSocket, DatagramPacket to implement UDP 
protocol, InetAdress class to get IP addresses, File, OutputStream to work with files