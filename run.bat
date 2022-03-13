@echo off
:: 启动后端SpringBoot
cd ./bookshelfplus/
:: refer: https://blog.csdn.net/abc86319253/article/details/44019881
:: mvn clean install
start mvn install -Djar.forceCreation spring-boot:run
echo 启动 SpringBoot
cd ../

:: 启动前端nodejs
cd ./bookshelfplus-frontend/
start cmd /C npm run dev
echo 启动 nodejs
cd ../

:: 启动nginx
(taskkill /f /t /im nginx.exe) > nul
cd ./server/nginx/
start /b nginx.exe
:: /b 不弹出新窗口
echo 启动 nginx
cd ../../

:: 启动浏览器
start explorer quickReach.html
echo 启动 浏览器
:: pause

:: 运行完毕不会退出
:: start cmd /k echo Hello, World!
:: 运行完毕会退出
:: start cmd /C echo Hello, World!