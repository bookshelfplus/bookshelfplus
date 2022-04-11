java -Dloader.path=D:\temp\lib -Ddruid.mysql.usePingMethod=false -jar target/bookshelfplus-1.0-SNAPSHOT.jar


:: 2022-04-11 11:27:02.240  WARN 15012 --- [nio-8090-exec-3] c.a.druid.pool.DruidAbstractDataSource   : discard long time none received connection. , jdbcUrl : jdbc:mysql://127.0.0.1:3306/bookshelfplus?useSSL=false&serverTimezone=Asia/Shanghai, version : 1.2.8, lastPacketReceivedIdleMillis : 70565
:: 解决方法：-Ddruid.mysql.usePingMethod=false