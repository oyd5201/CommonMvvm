# CommonMvvm
登录测试账号：testyq0711 密码000000  
一个最通熟易懂的mvvm框架。  
通过一个登录页面和刷新列表页面快速上手，并且写了个下载页面  
mvvm的核心就是谁观察就通知谁，只要数据变化了，lifecle就能监听到 自己页面就能通知ui更新  
快速使用方法：  
直接复制复制CommonMvvm这个文件夹到你自己的项目，然后成为你项目的module  
sync应该会报错，因为还没有把gradle下的版本复制到你自己的项目  
复制下面的到你的项目就行  
 ext {  
        appTargetSdk = 31  
        appMinSdk = 23  
        appVersionCode = 30204  
        appVersionName = "3.2.4"  
        lifecycleVersion = "2.4.0"  
        navigationVersion = "2.3.5"  
        retrofitVersion = "2.9.0"   
        okHttpVersion = "4.9.2"  
    }
