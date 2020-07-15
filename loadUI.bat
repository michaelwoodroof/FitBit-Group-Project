@ECHO OFF
javac -cp ".;./json.jar" *.java
java -cp ".;./json.jar" DrawUI
pause