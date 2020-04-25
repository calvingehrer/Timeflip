## Prerequisites

* In the `lib` directory of the `ble-start` project there should be the built file `tinyb.jar`

#### In the command line
Build the project

        mvn clean install
        
Execute 

        sudo java -cp target/rest-client-0.0.1-SNAPSHOT.jar:./lib/tinyb.jar:./target/lib/* com.example.setup.ClientApplication username password

## Sources
* [Raspberry Pi Bluetooth Manager TinyB - Building bluez 5.47 from sources](https://github.com/sputnikdev/bluetooth-manager-tinyb)
* [TinyB Bluetooth LE Library](https://github.com/intel-iot-devkit/tinyb)
* [Raspberry Pi Installation of TinyB (Note: do not install bluez)](http://www.martinnaughton.com/2017/07/install-intel-tinyb-java-bluetooth.html)
* [Java for Bluetooth LE applications](https://www.codeproject.com/Articles/1086361/Java-for-Bluetooth-LE-applications)
* [TinyB Java examples (HelloTinyB.java, etc.)](https://github.com/intel-iot-devkit/tinyb/tree/master/examples/java)