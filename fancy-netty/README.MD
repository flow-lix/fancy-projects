1.daemon thread
 --    private final EventLoopGroup bossEventLoopGroup = EventLoopGroupUtil.newEventLoopGroup(1,
                new DefaultThreadFactory("Netty-Remoting-Server-Boss", false)); 
                
2. ChannelDuplexHandler                