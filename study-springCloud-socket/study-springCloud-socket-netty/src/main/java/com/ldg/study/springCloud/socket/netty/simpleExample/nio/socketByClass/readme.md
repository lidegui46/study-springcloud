ChannelPipeline和handlerl累死Servlet和Filter，是职责链模式的变种。

handler包含两种，分别是Inbound和Outbound。

## 触发Inbound事件有：

- fireChannelRegister Channel注册
- fireChannelActive TCP链路成功，
- fireChannelRead 读事件
- fireChannelReadComplete 读操作完成
- fireExceptionCaught 异常通知
- fireUserEventTrigger 用户自定义事件
- fireChannelWritable 可写
- fireChannelInactive TCP关闭

## 触发OutBound事件有：

- bind 绑定本地事件
- connect 连接服务端
- write 发送
- flush 刷新
- read 读
- disconnect 断开连接
- close 关闭

# ChannelPipeline

Iterable -- ChannelPipeline -- DefaultChannelPipeline

# ChannelHandler注解

- Sharable 多个pipeline可以共用一个
- Skip 直接忽略

# 常用的Handler

- ByteToMessageDecoder 把字节转换成POJO类
- MessageToMessageDecoder 把一个对象转换成另一个对象
- MessageToByteEncoder
- MessageToMessageEncoder



