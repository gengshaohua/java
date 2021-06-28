package netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

public class PooledByteBuf {

    public static void main(String[] args) {
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(128);
        byteBuf.release();
    }

}
