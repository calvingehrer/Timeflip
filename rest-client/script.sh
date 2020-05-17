#!/bin/sh

cd ~

sudo apt-get update

sudo apt-get upgrade

sudo apt-get install git

sudo apt-get install cmake

sudo apt-get purge openjdk*

sudo apt-get install openjdk-8-jdk

echo "\nexport JAVA_HOME=/usr/lib/jvm/java-8-openjdk-armhf/" >> ~/.bashrc

bash

sudo apt-get install maven

sudo apt-get install libglib2.0-dev libdbus-1-dev libudev-dev libical-dev libreadline6 libreadline6-dev

cd ~ 

wget http://www.kernel.org/pub/linux/bluetooth/bluez-5.47.tar.xz

tar -xf bluez-5.47.tar.xz && cd bluez-5.47

./configure --prefix=/usr --mandir=/usr/share/man --sysconfdir=/etc -- localstatedir=/var

make

sudo make install

sudo adduser --system --no-create-home --group --disabled-login openhab

sudo usermod -a -G bluetooth openhab

sudo systemctl daemon-reload

sudo systemctl restart bluetooth

sudo apt-get install graphviz

sudo apt-get install doxygen 

sudo git clone https://github.com/intel-iot-devkit/tinyb.git && cd tinyb

mkdir build

cd build

sudo -E cmake -DBUILDJAVA=ON -DCMAKE_INSTALL_PREFIX=/usr ..

sudo make

sudo make install
