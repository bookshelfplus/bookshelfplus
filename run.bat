@echo off
:: �������SpringBoot
cd ./bookshelfplus/
:: refer: https://blog.csdn.net/abc86319253/article/details/44019881
:: mvn clean install
start mvn install -Djar.forceCreation spring-boot:run
echo ���� SpringBoot
cd ../

:: ����ǰ��nodejs
cd ./bookshelfplus-frontend/
start cmd /C npm run dev
echo ���� nodejs
cd ../

:: ����nginx
(taskkill /f /t /im nginx.exe) > nul
cd ./server/nginx/
start /b nginx.exe
:: /b �������´���
echo ���� nginx
cd ../../

:: ���������
start explorer quickReach.html
echo ���� �����
:: pause

:: ������ϲ����˳�
:: start cmd /k echo Hello, World!
:: ������ϻ��˳�
:: start cmd /C echo Hello, World!