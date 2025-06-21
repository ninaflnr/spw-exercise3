SPW4 - Exercise 3
=================

Name: Nina Fischerlehner

Effort in hours: 5.5

## 1. Connect Four Web Application and CI/CD Pipeline

### Task 1.a

Da ich bei meiner ConnectFour Implementation 1-based indexing verwendet habe, musste ich einiges von der Implementierung und den Tests umschreiben, da die webapp 0-based erwartet hat. Danach gingen die Tests aber problemlos durch:

mvn compile:

<img src="doc/image.png" alt="mvn-compile" width="600">

mvn test:

<img src="doc/image-4.png" alt="mvn-test" width="600">

mvn package:

<img src="doc/image-2.png" alt="mvn-package" width="600">

Server:

<img src="doc/image-3.png" alt="tomcat" width="600">
<img src="doc/image-5.png" alt="connectfour-server" width="600">

### Task 1.b

Die Pipeline hat zuerst nicht funktioniert, weil node.js nicht beim docker-image verfügbar war. Ich hab länger versucht, dass es funktioniert, bin dann aber auf die Lösung gestoßen, die CSS-Files bei Sonar zu excluden, was dann funktioniert hat.

<img src="doc/image-6.png" alt="gitlab-pipeline" width="600">

### Task 1.c

Für den Github runner wurden wie bei gitlab die verschiedenen Jobs erstellt (build, test, analyze, package, deploy_test und stop_test).

Jeder Job startet mit einer clean copy vom source code (uses actions/checkout@v4). Der build job lädt das target directory hoch, der test job lädt dies herunter und lässt die Tests laufen, analyze lädt target wieder herunter und lädt die reports dann hoch, der package job erstellt das war artifact und der deploy job lädt dieses dann herunter. Kein Job verwendet Dateien vom vorherigen (sondern lädt sie wenn nötig erneut herunter).

Sie laufen wie erwartet:

<img src="doc/img-7.png" alt="runner-logs" width="600">
<img src="doc/img_8.png" alt="github-pipeline" width="600">
<img src="doc/img_9.png" alt="pipeline-steps" width="600">
