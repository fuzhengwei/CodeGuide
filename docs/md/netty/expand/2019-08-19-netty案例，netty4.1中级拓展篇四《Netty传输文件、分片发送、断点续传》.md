---
layout: post
category: itstack-demo-netty-2
title: netty案例，netty4.1中级拓展篇四《Netty传输文件、分片发送、断点续传》
tagline: by 付政委
tag: [netty,itstack-demo-netty-2]
lock: need
---

# netty案例，netty4.1中级拓展篇四《Netty传输文件、分片发送、断点续传》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
1. 在实际应用中我们经常使用到网盘服务，他们可以高效的上传下载较大文件。那么这些高性能文件传输服务，都需要实现的分片发送、断点续传功能。
2. 在Java文件操作中有RandomAccessFile类，他可以支持文件的定位读取和写入，这样就满足了我们对文件分片的最基础需求。
3. Netty服务端启动后，可以向客户端发送文件传输指令；允许接收文件、控制读取位点、记录传输标记、文件接收完成。
4. 为了保证传输性能我们采用protostuff二进制流进行传输。
5. 读取文件的时候需要注意，我们设定byte[1024]为默认读取范围，但当读取到最后的时候可能不足1024个字节，就会出现空字节。这个时候需要去掉空字节，否则我们的文件写入会多额外信息，导致文件不能打开｛zip、war、exe、jar等｝。

## 开发环境
1、jdk1.8【jdk1.7以下只能部分支持netty】
2、Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】

## 代码示例
```java
itstack-demo-netty-2-04
└── src
    ├── main
    │   └── java
    │       └── org.itstack.demo.netty
    │           ├── client
    │           │	├── MyChannelInitializer.java
    │           │	├── MyClientHandler.java
    │           │	└── NettyClient.java
    │           ├── codec
    │           │	├── ObjDecoder.java
    │           │	└── ObjEncoder.java
    │           ├── domain
    │           │	├── Constants.java
    │           │	├── FileBurstData.java
    │           │	├── FileBurstInstruct.java
    │           │	├── FileDescInfo.java
    │           │	└── FileTransferProtocol.java
    │           ├── server
    │           │	├── MyChannelInitializer.java
    │           │	├── MyServerHandler.java
    │           │	└── NettyServer.java
    │           └── util
    │           	├── CacheUtil.java
    │           	├── FileUtil.java
    │           	├── MsgUtil.java
    │           	└── SerializationUtil.java
    │
    └── test
         └── java
             └── org.itstack.demo.test
				 ├── ApiTest.java
				 ├── NettyClientTest.java 
                 └── NettyServerTest.java
```

**演示部分重点代码块，完整代码下载关注公众号；bugstack虫洞栈**

>client/MyClientHandler.java *文件客户端；channelRead处理文件协议，其中模拟传输过程中断，场景测试可以注释掉

```java
@Override
public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	//数据格式验证
	if (!(msg instanceof FileTransferProtocol)) return;

	FileTransferProtocol fileTransferProtocol = (FileTransferProtocol) msg;
	//0传输文件'请求'、1文件传输'指令'、2文件传输'数据'
	switch (fileTransferProtocol.getTransferType()) {
		case 1:
			FileBurstInstruct fileBurstInstruct = (FileBurstInstruct) fileTransferProtocol.getTransferObj();
			//Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
			if (Constants.FileStatus.COMPLETE == fileBurstInstruct.getStatus()) {
				ctx.flush();
				ctx.close();
				System.exit(-1);
				return;
			}
			FileBurstData fileBurstData = FileUtil.readFile(fileBurstInstruct.getClientFileUrl(), fileBurstInstruct.getReadPosition());
			ctx.writeAndFlush(MsgUtil.buildTransferData(fileBurstData));
			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " bugstack虫洞栈客户端传输文件信息。 FILE：" + fileBurstData.getFileName() + " SIZE(byte)：" + (fileBurstData.getEndPos() - fileBurstData.getBeginPos()));
			break;
		default:
			break;
	}

	/**模拟传输过程中断，场景测试可以注释掉
	 *
	 */
	System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " bugstack虫洞栈客户端传输文件信息[主动断开链接，模拟断点续传]");
	ctx.flush();
	ctx.close();
	System.exit(-1);

}
```

>domain/FileBurstData.java *文件分片数据块

```java
/**
 * 文件分片数据
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class FileBurstData {

    private String fileUrl;     //客户端文件地址
    private String fileName;    //文件名称
    private Integer beginPos;   //开始位置
    private Integer endPos;     //结束位置
    private byte[] bytes;       //文件字节；再实际应用中可以使用非对称加密，以保证传输信息安全
    private Integer status;     //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝

    ... get/set
}
```

>domain/FileBurstInstruct.java *文件分片指令

```java
/**
 * 文件分片指令
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on @2019
 */
public class FileBurstInstruct {

    private Integer status;       //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
    private String clientFileUrl; //客户端文件URL
    private Integer readPosition; //读取位置

    ... get/set
}
```

>domain/FileDescInfo.java *文件传输信息

```java
/**
 * 文件描述信息
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on @2019
 */
public class FileDescInfo {

    private String fileUrl;
    private String fileName;
    private Long fileSize;

	... get/set
}
```

>domain/FileTransferProtocol.java *文件传输协议

```java
/**
 * 文件传输协议
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：5360692
 * Create by fuzhengwei on @2019
 */
public class FileTransferProtocol {

    private Integer transferType; //0请求传输文件、1文件传输指令、2文件传输数据
    private Object transferObj;   //数据对象；(0)FileDescInfo、(1)FileBurstInstruct、(2)FileBurstData

	... get/set
}

```

>serverMyServerHandler.java *文件服务端；channelRead处理文件协议，并包含了保存续传信息，用于断点续传

```java
@Override
public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	//数据格式验证
	if (!(msg instanceof FileTransferProtocol)) return;

	FileTransferProtocol fileTransferProtocol = (FileTransferProtocol) msg;
	//0传输文件'请求'、1文件传输'指令'、2文件传输'数据'
	switch (fileTransferProtocol.getTransferType()) {
		case 0:
			FileDescInfo fileDescInfo = (FileDescInfo) fileTransferProtocol.getTransferObj();

			//断点续传信息，实际应用中需要将断点续传信息保存到数据库中
			FileBurstInstruct fileBurstInstructOld = CacheUtil.burstDataMap.get(fileDescInfo.getFileName());
			if (null != fileBurstInstructOld) {
				if (fileBurstInstructOld.getStatus() == Constants.FileStatus.COMPLETE) {
					CacheUtil.burstDataMap.remove(fileDescInfo.getFileName());
				}
				//传输完成删除断点信息
				System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " bugstack虫洞栈服务端，接收客户端传输文件请求[断点续传]。" + JSON.toJSONString(fileBurstInstructOld));
				ctx.writeAndFlush(MsgUtil.buildTransferInstruct(fileBurstInstructOld));
				return;
			}

			//发送信息
			FileTransferProtocol sendFileTransferProtocol = MsgUtil.buildTransferInstruct(Constants.FileStatus.BEGIN, fileDescInfo.getFileUrl(), 0);
			ctx.writeAndFlush(sendFileTransferProtocol);
			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " bugstack虫洞栈服务端，接收客户端传输文件请求。" + JSON.toJSONString(fileDescInfo));
			break;
		case 2:
			FileBurstData fileBurstData = (FileBurstData) fileTransferProtocol.getTransferObj();
			FileBurstInstruct fileBurstInstruct = FileUtil.writeFile("E://", fileBurstData);

			//保存断点续传信息
			CacheUtil.burstDataMap.put(fileBurstData.getFileName(), fileBurstInstruct);

			ctx.writeAndFlush(MsgUtil.buildTransferInstruct(fileBurstInstruct));
			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " bugstack虫洞栈服务端，接收客户端传输文件数据。" + JSON.toJSONString(fileBurstData));

			//传输完成删除断点信息
			if (fileBurstInstruct.getStatus() == Constants.FileStatus.COMPLETE) {
				CacheUtil.burstDataMap.remove(fileBurstData.getFileName());
			}
			break;
		default:
			break;
	}

}
```

>util/FileUtil.java *文件读写工具，分片读取写入处理类

```java
/**
 * 文件读写工具
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：5360692
 * Create by fuzhengwei on @2019
 */
public class FileUtil {

    public static FileBurstData readFile(String fileUrl, Integer readPosition) throws IOException {
        File file = new File(fileUrl);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");//r: 只读模式 rw:读写模式
        randomAccessFile.seek(readPosition);
        byte[] bytes = new byte[1024];
        int readSize = randomAccessFile.read(bytes);
        if (readSize <= 0) {
            randomAccessFile.close();
            return new FileBurstData(Constants.FileStatus.COMPLETE);//Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
        }
        FileBurstData fileInfo = new FileBurstData();
        fileInfo.setFileUrl(fileUrl);
        fileInfo.setFileName(file.getName());
        fileInfo.setBeginPos(readPosition);
        fileInfo.setEndPos(readPosition + readSize);
        //不足1024需要拷贝去掉空字节
        if (readSize < 1024) {
            byte[] copy = new byte[readSize];
            System.arraycopy(bytes, 0, copy, 0, readSize);
            fileInfo.setBytes(copy);
            fileInfo.setStatus(Constants.FileStatus.END);
        } else {
            fileInfo.setBytes(bytes);
            fileInfo.setStatus(Constants.FileStatus.CENTER);
        }
        randomAccessFile.close();
        return fileInfo;
    }

    public static FileBurstInstruct writeFile(String baseUrl, FileBurstData fileBurstData) throws IOException {

        if (Constants.FileStatus.COMPLETE == fileBurstData.getStatus()) {
            return new FileBurstInstruct(Constants.FileStatus.COMPLETE); //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
        }

        File file = new File(baseUrl + "/" + fileBurstData.getFileName());
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");//r: 只读模式 rw:读写模式
        randomAccessFile.seek(fileBurstData.getBeginPos());      //移动文件记录指针的位置,
        randomAccessFile.write(fileBurstData.getBytes());        //调用了seek（start）方法，是指把文件的记录指针定位到start字节的位置。也就是说程序将从start字节开始写数据
        randomAccessFile.close();

        if (Constants.FileStatus.END == fileBurstData.getStatus()) {
            return new FileBurstInstruct(Constants.FileStatus.COMPLETE); //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
        }

        //文件分片传输指令
        FileBurstInstruct fileBurstInstruct = new FileBurstInstruct();
        fileBurstInstruct.setStatus(Constants.FileStatus.CENTER);            //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
        fileBurstInstruct.setClientFileUrl(fileBurstData.getFileUrl());      //客户端文件URL
        fileBurstInstruct.setReadPosition(fileBurstData.getEndPos() + 1);    //读取位置

        return fileBurstInstruct;
    }

}
```

>util/MsgUtil.java *传输消息体构建工具类

```java
/**
 * 消息构建工具
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：5360692
 * Create by fuzhengwei on @2019
 */
public class MsgUtil {

    /**
     * 构建对象；请求传输文件(客户端)
     *
     * @param fileUrl  客户端文件地址
     * @param fileName 文件名称
     * @param fileSize 文件大小
     * @return 传输协议
     */
    public static FileTransferProtocol buildRequestTransferFile(String fileUrl, String fileName, Long fileSize) {

        FileDescInfo fileDescInfo = new FileDescInfo();
        fileDescInfo.setFileUrl(fileUrl);
        fileDescInfo.setFileName(fileName);
        fileDescInfo.setFileSize(fileSize);

        FileTransferProtocol fileTransferProtocol = new FileTransferProtocol();
        fileTransferProtocol.setTransferType(0);//0请求传输文件、1文件传输指令、2文件传输数据
        fileTransferProtocol.setTransferObj(fileDescInfo);

        return fileTransferProtocol;

    }

    /**
     * 构建对象；文件传输指令(服务端)
     * @param status          0请求传输文件、1文件传输指令、2文件传输数据
     * @param clientFileUrl   客户端文件地址
     * @param readPosition    读取位置
     * @return                传输协议
     */
    public static FileTransferProtocol buildTransferInstruct(Integer status, String clientFileUrl, Integer readPosition) {

        FileBurstInstruct fileBurstInstruct = new FileBurstInstruct();
        fileBurstInstruct.setStatus(status);
        fileBurstInstruct.setClientFileUrl(clientFileUrl);
        fileBurstInstruct.setReadPosition(readPosition);

        FileTransferProtocol fileTransferProtocol = new FileTransferProtocol();
        fileTransferProtocol.setTransferType(Constants.TransferType.INSTRUCT); //0传输文件'请求'、1文件传输'指令'、2文件传输'数据'
        fileTransferProtocol.setTransferObj(fileBurstInstruct);

        return fileTransferProtocol;
    }

    /**
     * 构建对象；文件传输指令(服务端)
     *
     * @return 传输协议
     */
    public static FileTransferProtocol buildTransferInstruct(FileBurstInstruct fileBurstInstruct) {
        FileTransferProtocol fileTransferProtocol = new FileTransferProtocol();
        fileTransferProtocol.setTransferType(Constants.TransferType.INSTRUCT);  //0传输文件'请求'、1文件传输'指令'、2文件传输'数据'
        fileTransferProtocol.setTransferObj(fileBurstInstruct);
        return fileTransferProtocol;
    }

    /**
     * 构建对象；文件传输数据(客户端)
     *
     * @return 传输协议
     */
    public static FileTransferProtocol buildTransferData(FileBurstData fileBurstData) {
        FileTransferProtocol fileTransferProtocol = new FileTransferProtocol();
        fileTransferProtocol.setTransferType(Constants.TransferType.DATA); //0传输文件'请求'、1文件传输'指令'、2文件传输'数据'
        fileTransferProtocol.setTransferObj(fileBurstData);
        return fileTransferProtocol;
    }

}
```

>test/NettyServerTest.java *服务端启动器

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class NettyServerTest {

    public static void main(String[] args) {
        //启动服务
        new NettyServer().bing(7397);
    }

}
```

>test/NettyClientTest.java *客户端启动器

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class NettyClientTest {

    public static void main(String[] args) {

        //启动客户端
        ChannelFuture channelFuture = new NettyClient().connect("127.0.0.1", 7397);

        //文件信息{文件大于1024kb方便测试断点续传}
        File file = new File("C:\\Users\\fuzhengwei\\Desktop\\测试传输文件.rar");
        FileTransferProtocol fileTransferProtocol = MsgUtil.buildRequestTransferFile(file.getAbsolutePath(), file.getName(), file.length());

        //发送信息；请求传输文件
        channelFuture.channel().writeAndFlush(fileTransferProtocol);

    }

}
```
## 测试结果

>启动NettyServerTest *默认接收地址为E盘根目录

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-2-04-1.png)

>启动NettyClientTest *设置传输文件

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-2-04-2.png)

>文件传输结果

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-2-04-3.png)

>服务端执行结果

```java
itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}
链接报告开始
链接报告信息：有一客户端链接到本服务端。channelId：3a1df8c1
链接报告IP:127.0.0.1
链接报告Port:7397
链接报告完毕
2019-08-04 19:46:46 bugstack虫洞栈服务端，接收客户端传输文件请求。{"fileName":"测试传输文件.rar","fileSize":1400,"fileUrl":"C:\\Users\\fuzhengwei1\\Desktop\\测试传输文件.rar"}
2019-08-04 19:46:46 bugstack虫洞栈服务端，接收客户端传输文件数据。{"beginPos":0,"bytes":"UmFyIRoHAM+QcwAADQAAAAAAAAC4C3SgkkkAFAUAAIjDEQACJRsHe0WECE8dMyQAIAAAALLiytS0q8rkzsS8/i50eHQAbWpL1YsgT5OPoIdl9k4udAB4dACwS2heCZgVEQzPzUEXfAnhs2R75rhNbCQhNE3uMY4EBkBqQJ45izS4lFGujEk08xLGuhp4sSUSbzEscRICakGyOdARhIE6GEPucySJpY5kQ/Cq28ur4XdfH/j1V8UVoo5X5V+B3dl2f8qvvoxd3t6GPv8HZ7dXs+98XT6uJ0Oj8GZ4c6tvV6vzV865ka375utod+9i+pX/O1Uu1tT76tT38TE+Hq+tzud6Of9Xo9T0/S/xytLm12v4NWztfhnda3lbevs7dnXsWL1vT3Kte91triqYuHW/9bf3WPnjq5r5savbHd67V8Nu6r5+lmZtrP0eO63Ba+upVuWtf7bvByg2/0w+5hz8ru14ND5ex/Odw4F7uRWrYedwU2tXmw5m1u+S7lWdjvTq5e/Kv+1apZxrdjT2dHizdHDrlrH1cvkbrWe5k97u6WRnXdviw6zkvc3cD9TOt7+7W9z2/Ys+Sx9VTPwGeYLmrz+h8fQt5u5v8/3fZ5sXKnc4MOT1+n0upicmOVDT86GfY4bPf5vN7XSxMT5Mnsdry8e84///+quHp9l19fRz8vkds+O7qb+9pWe1WvXdb7NWza3vNO3V3cZZ2rPDr1svHwO3Nq14sBuZu3P1zOvuWP++s8ex9O95e5U/vW9/F7Jtb+p+PGeRtzlg8VfG5t5TyAAAAAAAAAAAAAAAARu1PHU9QX3wAAAAAAAAAAAAAAAiiXyXwAAAAAAAAAAAAAAAiiXyXwAAAAAAAAAAAAAAAiiXyXwAAAAAAAAAAAAAAAiiXyXwAEbDTyAAAAAAAAAAAAAEctTx1PUF98AAAAAAAAAAAAAAAIol8l8AAAAAAAAAAAAAAAIol8l8AAAAAAAAAAAAAAAIol8l8AAAAAAAAAAAAAAAIol8l8AAAABHx08gAAAAAAAAAAj108dT1BffAAAAAAAAAAAAAAACKJfJfAAAAAAAAAAAAAAACKJfJfAAAAAAAAAAAAAAACKJfJfAAAAAAAAAAAAAAACKJfJfAAAAAAAAgankAAAAAAAEZGnjqeoL74AAAAAAAAAAAAAAARRL5L4AAAAAAAAAAAAAAARRL5L4AAAAAAAAAAAAAAARRL5L4AAAAAAAAAAAAAAARRL5L4AAAAAAAAAAEYinkAAAAEH08dT1BffAAAAAAAAAAAAAAACKJfJfAAAAAAAAAAAAAAACKJfJfAAAAAAAAAAAAAAACKJfJfAAAAAAAAAAAAAAACKJfJfAAAAAAAAAAA==","endPos":1024,"fileName":"测试传输文件.rar","fileUrl":"C:\\Users\\fuzhengwei1\\Desktop\\测试传输文件.rar","status":1}
2019-08-04 19:46:46 bugstack虫洞栈服务端，接收客户端传输文件数据。{"beginPos":1025,"bytes":"AAI8VPIAEfZTx1PUF98AAAAAAAAAAAAAAAIol8l8AAAAAAAAAAAAAAAIol8l8AAAAAAAAAAAAAAAIol8l8AAAAAAAAAAAAAAAIol8l8AAAAAAAAAAAAAAAIol8l8R+dPIAAAAAAAAAAAAAAAjt08dT1BffAAAAAAAAAAAAAAACKJfJfAAAAAAAAAAAAAAACKJfJfAAAAAAAAAAAAAAACKJfJfAAAAAAAAAAAAAAACKJfJfAAAIWp5AAAAAAAAAAAAEYGnjqeoL74AAAAAAAAAAAAAAARRL5L4AAAAAAAAAAAAAAARRL5L4AAAAAAAAAAAAAAARRL5L4AAAAAAAAAAAAAAARRL5L4AAAAABGYp5AAAAAAAAAEc/Tx1PUF98AAAAAAAAIyFPIAAAAAACB6eOp6gvvgAAAAAAAAAARiKeQAAAAQfTx1PUF98AAAAAAAAAAAAAjAU8gACF6eOp6gvvgAAAAAAAAAAAAAABFH9IDEPXsAQAcA","endPos":1400,"fileName":"测试传输文件.rar","fileUrl":"C:\\Users\\fuzhengwei1\\Desktop\\测试传输文件.rar","status":2}
客户端断开链接/127.0.0.1:7397

Process finished with exit code -1
```

>客户端执行结果

```java
itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}
链接报告开始
链接报告信息：本客户端链接到服务端。channelId：71399d8c
链接报告IP:127.0.0.1
链接报告Port:54974
链接报告完毕
2019-08-04 19:46:46 bugstack虫洞栈客户端传输文件信息。 FILE：测试传输文件.rar SIZE(byte)：1024
2019-08-04 19:46:46 bugstack虫洞栈客户端传输文件信息。 FILE：测试传输文件.rar SIZE(byte)：375

Process finished with exit code -1
```

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！
