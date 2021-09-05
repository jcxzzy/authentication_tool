# authentication_tool
实现了接口鉴权逻辑的代码片段，如果你有这个需求可以复制到你的代码中去

#### 接口调用鉴权
只有经过认证之后的系统才能调用我们的接口，没有认证过的系统调我们的接口会被拒绝
#### 需求：
1.  调用方进行接口请求时，从请求中拆解护token，AppID、时间戳拼接在一起，通过加密算法生成token,并且将token、AppID、时间戳拼接在URL中，一并发送到微服务端。
2.  微服务端在接受到调用方的接口请求之后，从请求中拆解出token、AopID、时间戳。
3.  微服务端首先检查传递过来的时间戳跟当前时间，是否在token失效时间窗口内。如果超出失效时间。那就算接口调用鉴权失败，拒绝接口调用请求。
4.  如果token验证没有过期失效，微服务端再从自己端存储中，取出AppID对应的密码，通过同样的tokens生成算法，生成另外一个token,与调用方传递过来的token进行匹配；如果一致，则鉴权成功，允许接口调用，否则就拒绝接口调用。
#### 功能清单：
1.  把URL、AppID、密码、时间戳拼接为一个字符串;
2.  对字符串通过加密算法生成token;
3.  将token、AppID、时间戳拼接到URL中，形成新的URL;
4.  解析URL,得到token、AppID、时间戳等信息;
5.  从存储中取出AppID和对应的密码;
6.  根据时间戳判断token是否过期失效;
7.  验证两个token是否匹配;
8.  考虑扩展性，获取证书信息可从多种存储介质中获取（如：本地文件、mysql、zookeeper等，实现例子为从mysql获取），加密算法及接口鉴权业务处理也进行抽象了接口，方便后续扩展
#### 工程实现目录结构及各个类功能介绍

    dao：和存储介质进行交互，获取证书信息
        CredentialStorage：从存储获取证书信息接口类
        impl：接口类的具体实现
            MysqlCredentialStorage：通过mysql获取证书信息
            UserMapper:查询用户密钥信息
    encryption: 密钥加密算法 
        EncryptionAlgBase: 对密钥进行加密的算法接口类
        impl: 接口类的具体实现
            AesEncryptionAlg: Ase对称加密
    entity: 实体信息
        ApiRequest: 鉴权请求请求信息父类
    service: 接口鉴权业务逻辑
        ApiAuthenticator: 接口鉴权抽象接口类 
        impl: 接口类的具体实现
            DefaultApiAuthenticatorImpl: 默认鉴权接口实现类（基于mysql存储实现）
    AuthToken: 授权操作类，对外提供鉴权功能
    
# authentication_tool
实现了接口鉴权逻辑的代码片段，如果你有这个需求可以复制到你的代码中去

####接口调用鉴权
只有经过认证之后的系统才能调用我们的接口，没有认证过的系统调我们的接口会被拒绝
####需求：
1.  调用方进行接口请求时，从请求中拆解护token，AppID、时间戳拼接在一起，通过加密算法生成token,并且将token、AppID、时间戳拼接在URL中，一并发送到微服务端。
2.  微服务端在接受到调用方的接口请求之后，从请求中拆解出token、AopID、时间戳。
3.  微服务端首先检查传递过来的时间戳跟当前时间，是否在token失效时间窗口内。如果超出失效时间。那就算接口调用鉴权失败，拒绝接口调用请求。
4.  如果token验证没有过期失效，微服务端再从自己端存储中，取出AppID对应的密码，通过同样的tokens生成算法，生成另外一个token,与调用方传递过来的token进行匹配；如果一致，则鉴权成功，允许接口调用，否则就拒绝接口调用。
####功能清单：
1.  把URL、AppID、密码、时间戳拼接为一个字符串;
2.  对字符串通过加密算法生成token;
3.  将token、AppID、时间戳拼接到URL中，形成新的URL;
4.  解析URL,得到token、AppID、时间戳等信息;
5.  从存储中取出AppID和对应的密码;
6.  根据时间戳判断token是否过期失效;
7.  验证两个token是否匹配;
8.  考虑扩展性，获取证书信息可从多种存储介质中获取（如：本地文件、mysql、zookeeper等，实现例子为从mysql获取），加密算法及接口鉴权业务处理也进行抽象了接口，方便后续扩展
####工程实现目录结构及各个类功能介绍

    dao：和存储介质进行交互，获取证书信息
        CredentialStorage：从存储获取证书信息接口类
        impl：接口类的具体实现
            MysqlCredentialStorage：通过mysql获取证书信息
            UserMapper:查询用户密钥信息
    encryption: 密钥加密算法 
        EncryptionAlgBase: 对密钥进行加密的算法接口类
        impl: 接口类的具体实现
            AesEncryptionAlg: Ase对称加密
    entity: 实体信息
        ApiRequest: 鉴权请求请求信息父类
    service: 接口鉴权业务逻辑
        ApiAuthenticator: 接口鉴权抽象接口类 
        impl: 接口类的具体实现
            DefaultApiAuthenticatorImpl: 默认鉴权接口实现类（基于mysql存储实现）
    AuthToken: 授权操作类，对外提供鉴权功能
    
项目从存储获取证书信息部分代码使用了mysql数据库（引入了springMVC、Mybatis和druid数据库连接池，增加了耦合），可根据实际需求进行修改
