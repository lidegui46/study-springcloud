1、NIO

    Buffer:
            是一块连续的内存块。
            是 NIO 数据读或写的中转地。
    Channel:
            数据的源头或者数据的目的地
            用于向 buffer 提供数据或者读取 buffer 数据 ,buffer 对象的唯一接口。
            异步 I/O 支持
          Buffer作为IO流中数据的缓冲区，而Channel则作为socket的IO流与Buffer的传输通道。客户端socket与服务端socket之间的IO传输不直接把数据交给CPU使用，
    而是先经过Channel通道把数据保存到Buffer，然后CPU直接从Buffer区读写数据，一次可以读写更多的内容。
          使用Buffer提高IO效率的原因（这里与IO流里面的BufferedXXStream、BufferedReader、BufferedWriter提高性能的原理一样）：IO的耗时主要花在数据传输的路上，普通的IO是一个字节一个字节地传输，
    而采用了Buffer的话，通过Buffer封装的方法（比如一次读一行，则以行为单位传输而不是一个字节一次进行传输）就可以实现“一大块字节”的传输。比如：IO就是送快递，普通IO是一个快递跑一趟，采用了Buffer的IO就是一车跑一趟。很明显，buffer效率更高，花在传输路上
    的时间大大缩短。