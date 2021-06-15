#!/bin/bash
##定义路径
testPath=/data/code/zpdq/target
echo "----------------update code start----------------------"
cd /data/code/zpdq
git fetch --all  2>1 >/dev/null
git pull origin master 2>1 >/dev/null
if [[ ! -d "$testPath" ]]; then
 echo "target is not exist!"
else
 echo "target is exist, delete this !"
 rm -rf target
fi
echo "----------------update code end----------------------"

echo "----------------make jar start----------------------"
mvn clean install -Dmaven.test.skip=true
cp -r /data/code/zpdq/target/cpdq-0.0.1-SNAPSHOT.jar /data/java-jar/
echo "----------------make jar end----------------------"

echo "----------------start project----------------------"
ps -ef | grep cpdq-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{print $2}' | xargs -r kill -9
BUILD_ID=DONTKILLME

nohup java -jar -Xms256M -Xmx512M -XX:PermSize=256M -XX:MaxPermSize=512M  -Dspring.profiles.active=pro /data/java-jar/cpdq-0.0.1-SNAPSHOT.jar >/data/logs/cpdq_start.log 2>&1 &