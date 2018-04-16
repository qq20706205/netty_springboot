package com.netty.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @ClassName ProtocoDecoderUtil
 * @Author zhiwei.jiang
 * @Date 2018/4/16 15:58
 * @Version 1.0
 */
public class ProtocoDecoderUtil  extends ByteToMessageDecoder {
    /**
     * 16进制转换
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readCount = in.readableBytes();
        byte[] req = new byte[readCount];
        in.readBytes(req);
        String hexString = trace(req);
        out.add(hexString);
    }
    /**
     * 2进制转16进制
     * @param b
     * @return
     */
    public String trace(byte[] b) {
        StringBuffer sixTeenHexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hexString = Integer.toHexString(b[i] & 0XFF).toUpperCase();
            if (hexString.length() == 1) {
                sixTeenHexString.append("0");
                sixTeenHexString.append(hexString);
            } else {
                sixTeenHexString.append(hexString);
            }
            sixTeenHexString.append(" ");
        }
        return sixTeenHexString.toString();
    }
}
