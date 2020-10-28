# 学习笔记



### Java字节码

1. Java字节码单字节组成

   * 包含四大类型字节码：栈操作指令、程序流程控制指令、对象操作执行、算数运算以及类型转换指令

2. load加载 与 store保存

   * load ： 本地变量表  加载到  栈顶
   * store：栈顶元素 保存到  本地变量表

3. 如何查看Java字节码信息

   `javap -c -verbose XXX(class文件)`

4. 字节码运行结构

   * 每个线程都有一个隔离的线程栈 JVM Stack，存储栈帧 Frame
   * 栈帧由操作数(Operand Stack)、局部变量数组(Local Variable)以及一个class引用组成；该Class引用指向当前方法在运行时常量池中对应的Class

5. 方法调用指令：invokestatic、invokespecial、invokevirtual、invokeinterface、invodedynamic



## 类加载

1. 类的生命周期(7个阶段)：加载、验证、准备、解析、初始化、使用、卸载

2. 类加载时机：
    * Main方法所在的类
    * new出来的对象
    * 静态方法/字段所在的类
    * 子类的初始化触发父类的初始化、
    * 定义default方法接口的直接或间接的类、
    * 反射API调用的类
    * MethodHandler指向的类

3. 不会加载情况：

   * 子类饮用父类的静态字段、只触发父类的初始化
   * 定义对象数组，不会触发类的初始化
   * 常量
   * 通过类名获取Class对象
   * Class.forName：如果initialize设置为fals
   * 通过ClassLoader默认的loadClass方法
   
4. 三类加载器及特点
   * 启动类加载器、扩展类加载器、应用类加载器
   * 双亲委托、负责依赖、缓存加载
   
5. 添加引用类
   * Jdk的lib/ext 或者 -Djava.ext.dirs
   * -cp/classpath 或者 class文件 放到当前路径
   * 自定义CLasLoader加载
   * 拿到当前执行类的ClassLoader、反射调用addUrl方法添加Jar或路径
   
## Java内存结构
   * 线程栈： 包括Java方法栈、NativeStack本地方法栈
   * 共享堆： 包括年轻代(Eden区+存活区)、老年代
   * 非堆Non-heap：元数据区Metaspace、CCS、CodeCache
   * JMM 规范的是线程间的交互操作，而不管线程内部对局部变量进行的操作

## JVM启动参数
* 系统属性参数
* 运行模式参数
* 堆内存设置参数 
* GC设置参数
* 分析诊断参数
* JavaAgent



## JDK命令行工具
* `java` `javac`  `javap`
* `jps/jinfo` ：查看java进程
* `jstat`：查看jvm内部gc信息
* `jmap`：查看heap或者类占用统计
* `jstack`：查看线程信息
* `jcmd`：综合命令
* `jrunscript/jjs`：执行js命令



## JVM图形化工具

* `jconsole`
* `jvisualvm`
* `visualgc`
* `jmc`



## GC背景

* 引用计数法：存在循环引用的问题
* 标记-清除算法
* 标记-清除-压缩算法
* 标记-复制算法
* 基于分代假设：新生代(Eden、S0、S1) 老年代
* GCroots：正在执行方法里的局部变量和输入参数、活动线程、类的静态字段、JNI引用



## 串行SerialGC

* 串行GC启用 `-XX:+UseSerialGC`
* 串行GC算法：新生代 标记-复制  老年代  标记-清除-整理
* 会触发STW



## 并行Parallel GC 

* `-XX:+UseParallelGC `
* `-XX:+UseParallelOldGC`
* `-XX:+UseParallelGC. -XX:+UseParallelOldGC`
* 并行GC算法：新生代 标记-复制  老年代  标记-清除-整理
* 使用 `-XX:ParallelGCThreads=N`指定GC线程数
* 增加吞吐量



## CMS

* `+XX:+UseConcMarkSweep`
* CMS GC算法：新生代 标记-复制  老年代  标记-清除
* CPU使用的默认并行线程数：CPU核心数的1/4
* 主要目的：降低STW导致的系统延迟
* 6个阶段：初始标记STW、并发标记、并发预清理、最终标记STW、并发清除、并发重置



## G1

1. `-XX:+UseG1GC -XX:MaxGCPauseMillis=50`
2. 主要目标：将STW停顿的时间和分布，变成可预期且可配置的。
3. 堆不再区分年轻代和老年代
4. 处理步骤：年轻代模式转移、并发标记、转移暂停混合模式
5. 标记清除6个阶段：初始标记STW、Root区扫描、并发标记、再次标记STW、清理
6. 注意事项：并发模式失败(增大堆大小或调整周期)、晋升失败(增加预留内存量或增加并发标记线程数或减少提前启动标记周期)、巨型对象分配失败(增加内存或者增大`-XX:G1HeapRegionSize`)



## GC总结

* 新生代：Serial(串行)、ParNew(并行)、ParallelScvenge(并行)、G1(并发)
* 老年代：SerialOld(串行)、ParallelOld(并行)、CMS(并发)、G1(并发)
* 常用组合：单线程低延迟 Serial+SerialOld、多线程低延迟 ParNew+CMS、多线程高吞吐量Parallel Scavenge+ParallelOld
* 选择：
  * 考虑吞吐优先：用ParallelGC
  * 考虑低延迟：用CMS GC
  * 系统内存堆较大，用G1 GC
* JDK8 默认 新生代 ParallelScavenge并行标记清除  老年代标记Parallel Old  并行 标记-清除-整理算法 
* 串行到并行、并行到并发、CMS到G1、G1到ZGC

