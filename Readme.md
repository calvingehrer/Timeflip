
# Setup

You can either start the program with Maven and Mysql or with a docker.

## Docker

Make sure you have docker and docker-compose installed. You can check this with 
>docker --version\
>docker-compose --version

A docker-compose file is provided in the project. Change in project directory and 
run 'sudo docker-compose up'. Then the Mysql client and application server are started.

## Maven and Mysql

First of all make sure you have installed Maven and Mysql

You need to set up the database. Log in into Mysql with your root user
and type in following 3 commands:

>CREATE DATABASE timeflip;\
>CREATE USER 'default'@'%' identified by 'Timeflippers.123';\
>GRANT ALL ON timeflip.* to 'default'@'%';\

After these commands your database is setup correctly. You could also 
take another name of database, username and password for user but then you have
to change the applications.property file. Then change the following:

>spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/<nameOfDatabase>?serverTimezone=UTC\
>spring.datasource.username=<yourUsername>\
>spring.datasource.password=<yourPassword>\

You can now start the program in your IDE or in the terminal with 'mvn spring-boot:run' in 
project directory. After a few seconds you should see the message:

[main] at.qe.sepm.skeleton.Main  : Started Main in 12.152 seconds (JVM running for 12.585)

now localhost:8080 is available


# Hardware integration

## Start client program

Head to Client folder and run:

>sudo java -cp target/re-client-0.0.1-SNAPSHOT.jar:./lib/tinyb.jar:./target/lib/* at.ac.uibk.rest.ClientApplication

This will start the client and send data to http://localhost:8080/history. You can also add a URI as argument to send
the data to the given URI, e.g. when running on the raspberry then localhost does not work. 

>sudo java -cp target/re-client-0.0.1-SNAPSHOT.jar:./lib/tinyb.jar:./target/lib/* at.ac.uibk.rest.ClientApplication URI

In order to use the webapplication with your TimeFlip device, you have to create a TmeFlip device in the webapp and 
associate it with a user. Also check, if the devicename of your TimeFlip contains the substring "timeflip" (caseinsensitive).

The client is meant to run permanently and send data every half an hour (just for now to get a better testing experience).
If you wish to have a smaller or longer interval, you can change that in the last line of the main-method in ClientApplication.java. 

It may happen that the client exits at some point with following exception: 

>Exception in thread "Timer-0" tinyb.BluetoothException: GDBus.Error:org.bluez.Error.Failed: Software caused connection abort\
>	at tinyb.BluetoothDevice.connect(Native Method)\
>	at at.ac.uibk.rest.TimeFlipService.getHistoryObjects(TimeFlipService.java:112)\
>	at at.ac.uibk.rest.ClientApplication$1.run(ClientApplication.java:52)\
>	at java.util.TimerThread.mainLoop(Timer.java:555)\
>	at java.util.TimerThread.run(Timer.java:505)\

In this case, please restart the client (we still have to work on that issue)

## Raspberry Installation-Script

Execute the file "installation_script.sh" in the rest-client project with "./installation_script.sh". It could be that you have to set 
the permissions before executing. Do that with:

>chmod +x installation_script.sh

If you run the client from your Raspberry Pi, then (as mentioned above) the default URI will not work. In this case change the 
URI so that the raspberry can connect to your computer, e.g http://192.168.0.31:8080/history, depending on the IP address
of your local machine.














