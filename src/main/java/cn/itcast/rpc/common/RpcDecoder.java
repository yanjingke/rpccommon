package cn.itcast.rpc.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class RpcDecoder extends ByteToMessageDecoder {
    private Class<?>genericClass;
    public RpcDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }


    public final void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        //System.out.println(in);
        if(in.readableBytes()<4){//
            return;
        }
        in.markReaderIndex();//标记
        int dataLength=in.readInt();
        if(dataLength<0){
            channelHandlerContext.close();
        }
        if (in.readableBytes()<dataLength){
            in.resetReaderIndex();//处理TCP包不全
        }
        byte[]data=new byte[dataLength];
        in.readBytes(data);

        Object obj=SerializationUtil.deserialize(data,genericClass);//反序列化>genericClass数据类型
        //obj.getClass();
        out.add(obj);
    }
}
