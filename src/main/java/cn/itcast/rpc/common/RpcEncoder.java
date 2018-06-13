package cn.itcast.rpc.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RpcEncoder extends MessageToByteEncoder {
    private Class<?>genericClass;
    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }


    public void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if(genericClass.isInstance(o)){

            byte[]data=SerializationUtil.serialize(o);
         //   System.out.println(data.length);
            byteBuf.writeInt(data.length);
            byteBuf.writeBytes(data);
        }
    }
}
