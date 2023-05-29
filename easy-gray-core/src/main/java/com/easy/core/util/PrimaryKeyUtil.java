package com.easy.core.util;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicLong;


/**
 * ID工具
 *
 * @author liupenghao
 */
public class PrimaryKeyUtil {
    /**
     * 获取一个{@link Long} ID
     *
     * @return long
     */
    public static long nextId() {
        return IdGenerator.createGenerator().getNextId();
    }

    /**
     * ID生成器
     */
    static class IdGenerator {

        private final AtomicLong baseId;

        private static IdGenerator idGenerator = null;

        public static IdGenerator createGenerator() {
            if (null == idGenerator) {
                synchronized (IdGenerator.class) {
                    if (null == idGenerator) {
                        idGenerator = new IdGenerator();
                    }
                }
            }
            return idGenerator;
        }

        /**
         * ID生成器
         */
        private IdGenerator() {
            long t = System.currentTimeMillis();
            //53~45
            long initBaseId = t;
            initBaseId &= 0x1FF0000000L;
            initBaseId <<= 16;
            //30~17
            t &= 0xFFFC000L;
            t <<= 2;
            initBaseId |= t;
            //44~31
            SecureRandom ng = new SecureRandom();
            t = ng.nextLong();
            t &= 0x3FFF0000000L;
            t <<= 2;
            initBaseId |= t;
            //16~1
            initBaseId /= 50000;
            initBaseId *= 50000;
            initBaseId &= 0x1FFFFFFFFFFFFFL;
            baseId = new AtomicLong(initBaseId);
        }

        /**
         * 获取ID
         *
         * @return long
         */
        public long getNextId() {
            return baseId.getAndIncrement();
        }
    }

}
