package com.zgz.cpdq.util;

import cn.hutool.core.util.RandomUtil;

public class RandomImage {

    private final static String domain = "http://qmlf0cplx.hn-bkt.clouddn.com/images/heads/";

    private final static String images[] = {"ia_100000024.png",
            "ia_100000018.png",
            "ia_100000019.png",
            "ia_100000025.png",
            "ia_100000027.png",
            "ia_100000026.png",
            "ia_100000022.png",
            "ia_100000023.png",
            "ia_100000009.png",
            "ia_100000021.png",
            "ia_100000020.png",
            "ia_100000008.png",
            "ia_100000011.png",
            "ia_100000005.png",
            "ia_100000004.png",
            "ia_100000010.png",
            "ia_100000006.png",
            "ia_100000012.png",
            "ia_100000013.png",
            "ia_100000007.png",
            "ia_100000003.png",
            "ia_100000017.png",
            "ia_100000016.png",
            "ia_100000002.png",
            "ia_100000028.png",
            "ia_100000014.png",
            "ia_100000000.png",
            "ia_100000001.png",
            "ia_100000015.png",
            "ia_100000029.png",
            "ia_600000028.png",
            "ia_600000000.png",
            "ia_600000014.png",
            "ia_600000015.png",
            "ia_600000001.png",
            "ia_600000029.png",
            "ia_600000017.png",
            "ia_600000003.png",
            "ia_600000002.png",
            "ia_600000016.png",
            "ia_600000012.png",
            "ia_600000006.png",
            "ia_600000007.png",
            "ia_600000013.png",
            "ia_600000005.png",
            "ia_600000011.png",
            "ia_600000010.png",
            "ia_600000004.png",
            "ia_600000009.png",
            "ia_600000021.png",
            "ia_600000020.png",
            "ia_600000008.png",
            "ia_600000022.png",
            "ia_600000023.png",
            "ia_600000027.png",
            "ia_600000026.png",
            "ia_600000024.png",
            "ia_600000018.png",
            "ia_600000019.png",
            "ia_600000025.png"};


    public static String randomImage() {
        int index = RandomUtil.randomInt(0, images.length);
        return domain.concat(images[index]);
    }
}
