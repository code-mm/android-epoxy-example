package com.ms.app.utils.encryption;

/**
 * @author maohuawei created in 2018/10/8
 * <p>
 * 字符串加密 解密
 */
public class EncryptionUtils {

    /**
     * 编码
     *
     * @param src
     * @return
     */
    public static String encode(String src) {
        if (src == null || src.trim().length() == 0) {
            return null;
        }
        byte[] src_byts = src.getBytes();
        //自定义加密
        CustomEncrpyUtil.encodeBytes(src_byts);
        //base64加密
        return Base64.encodeFetchString(src_byts);
    }


    /**
     * 解码
     *
     * @param e64Str
     * @return
     */
    public static String decode(String e64Str) {
        if (e64Str == null || e64Str.trim().length() == 0) {
            return null;
        }
        byte[] e64Bytes = Base64.decode(e64Str);
        //自定义解密
        CustomEncrpyUtil.decodeBytes(e64Bytes);
        //转为字符串
        String desrc = new String(e64Bytes);
        return desrc;
    }

    private static class Base64 {

        /**
         * 编码表
         */
        private static final byte[] ENCODINGTABLE = {
                (byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E',
                (byte) 'F', (byte) 'G', (byte) 'H', (byte) 'I', (byte) 'J',
                (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N', (byte) 'O',
                (byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T',
                (byte) 'U', (byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y',
                (byte) 'Z', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd',
                (byte) 'e', (byte) 'f', (byte) 'g', (byte) 'h', (byte) 'i',
                (byte) 'j', (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n',
                (byte) 'o', (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's',
                (byte) 't', (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x',
                (byte) 'y', (byte) 'z', (byte) '0', (byte) '1', (byte) '2',
                (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
                (byte) '8', (byte) '9', (byte) '+', (byte) '/'
        };
        private static final byte[] DECODINGTABLE;

        static {
            DECODINGTABLE = new byte[128];

            for (int i = 0; i < 128; i++) {
                DECODINGTABLE[i] = (byte) -1;
            }

            for (int i = 'A'; i <= 'Z'; i++) {
                DECODINGTABLE[i] = (byte) (i - 'A');
            }

            for (int i = 'a'; i <= 'z'; i++) {
                DECODINGTABLE[i] = (byte) (i - 'a' + 26);
            }

            for (int i = '0'; i <= '9'; i++) {
                DECODINGTABLE[i] = (byte) (i - '0' + 52);
            }

            DECODINGTABLE['+'] = 62;
            DECODINGTABLE['/'] = 63;
        }

        public static String encodeFetchString(byte[] data) {
            return new String(encode(data));
        }

        public static byte[] encode(byte[] data) {
            byte[] bytes;

            int modulus = data.length % 3;

            if (modulus == 0) {
                bytes = new byte[(4 * data.length) / 3];
            } else {
                bytes = new byte[4 * ((data.length / 3) + 1)];
            }

            int dataLength = (data.length - modulus);
            int a1;
            int a2;
            int a3;

            for (int i = 0, j = 0; i < dataLength; i += 3, j += 4) {
                a1 = data[i] & 0xff;
                a2 = data[i + 1] & 0xff;
                a3 = data[i + 2] & 0xff;

                bytes[j] = ENCODINGTABLE[(a1 >>> 2) & 0x3f];
                bytes[j + 1] = ENCODINGTABLE[((a1 << 4) | (a2 >>> 4)) & 0x3f];
                bytes[j + 2] = ENCODINGTABLE[((a2 << 2) | (a3 >>> 6)) & 0x3f];
                bytes[j + 3] = ENCODINGTABLE[a3 & 0x3f];
            }

            int b1;
            int b2;
            int b3;
            int d1;
            int d2;

            switch (modulus) {
                /* nothing left to do */
                case 0:
                    break;

                case 1:
                    d1 = data[data.length - 1] & 0xff;
                    b1 = (d1 >>> 2) & 0x3f;
                    b2 = (d1 << 4) & 0x3f;

                    bytes[bytes.length - 4] = ENCODINGTABLE[b1];
                    bytes[bytes.length - 3] = ENCODINGTABLE[b2];
                    bytes[bytes.length - 2] = (byte) '=';
                    bytes[bytes.length - 1] = (byte) '=';

                    break;

                case 2:
                    d1 = data[data.length - 2] & 0xff;
                    d2 = data[data.length - 1] & 0xff;

                    b1 = (d1 >>> 2) & 0x3f;
                    b2 = ((d1 << 4) | (d2 >>> 4)) & 0x3f;
                    b3 = (d2 << 2) & 0x3f;

                    bytes[bytes.length - 4] = ENCODINGTABLE[b1];
                    bytes[bytes.length - 3] = ENCODINGTABLE[b2];
                    bytes[bytes.length - 2] = ENCODINGTABLE[b3];
                    bytes[bytes.length - 1] = (byte) '=';

                    break;
                default:

                    break;
            }

            return bytes;
        }

        public static byte[] decode(byte[] data) {
            byte[] bytes;
            byte b1;
            byte b2;
            byte b3;
            byte b4;

            data = discardNonBase64Bytes(data);

            if (data[data.length - 2] == '=') {
                bytes = new byte[(((data.length / 4) - 1) * 3) + 1];
            } else if (data[data.length - 1] == '=') {
                bytes = new byte[(((data.length / 4) - 1) * 3) + 2];
            } else {
                bytes = new byte[((data.length / 4) * 3)];
            }

            for (int i = 0, j = 0; i < (data.length - 4); i += 4, j += 3) {
                b1 = DECODINGTABLE[data[i]];
                b2 = DECODINGTABLE[data[i + 1]];
                b3 = DECODINGTABLE[data[i + 2]];
                b4 = DECODINGTABLE[data[i + 3]];

                bytes[j] = (byte) ((b1 << 2) | (b2 >> 4));
                bytes[j + 1] = (byte) ((b2 << 4) | (b3 >> 2));
                bytes[j + 2] = (byte) ((b3 << 6) | b4);
            }

            if (data[data.length - 2] == '=') {
                b1 = DECODINGTABLE[data[data.length - 4]];
                b2 = DECODINGTABLE[data[data.length - 3]];

                bytes[bytes.length - 1] = (byte) ((b1 << 2) | (b2 >> 4));
            } else if (data[data.length - 1] == '=') {
                b1 = DECODINGTABLE[data[data.length - 4]];
                b2 = DECODINGTABLE[data[data.length - 3]];
                b3 = DECODINGTABLE[data[data.length - 2]];

                bytes[bytes.length - 2] = (byte) ((b1 << 2) | (b2 >> 4));
                bytes[bytes.length - 1] = (byte) ((b2 << 4) | (b3 >> 2));
            } else {
                b1 = DECODINGTABLE[data[data.length - 4]];
                b2 = DECODINGTABLE[data[data.length - 3]];
                b3 = DECODINGTABLE[data[data.length - 2]];
                b4 = DECODINGTABLE[data[data.length - 1]];

                bytes[bytes.length - 3] = (byte) ((b1 << 2) | (b2 >> 4));
                bytes[bytes.length - 2] = (byte) ((b2 << 4) | (b3 >> 2));
                bytes[bytes.length - 1] = (byte) ((b3 << 6) | b4);
            }

            return bytes;
        }

        public static byte[] decode(String data) {
            byte[] bytes;
            byte b1;
            byte b2;
            byte b3;
            byte b4;

            data = discardNonBase64Chars(data);

            if (data.charAt(data.length() - 2) == '=') {
                bytes = new byte[(((data.length() / 4) - 1) * 3) + 1];
            } else if (data.charAt(data.length() - 1) == '=') {
                bytes = new byte[(((data.length() / 4) - 1) * 3) + 2];
            } else {
                bytes = new byte[((data.length() / 4) * 3)];
            }

            for (int i = 0, j = 0; i < (data.length() - 4); i += 4, j += 3) {
                b1 = DECODINGTABLE[data.charAt(i)];
                b2 = DECODINGTABLE[data.charAt(i + 1)];
                b3 = DECODINGTABLE[data.charAt(i + 2)];
                b4 = DECODINGTABLE[data.charAt(i + 3)];

                bytes[j] = (byte) ((b1 << 2) | (b2 >> 4));
                bytes[j + 1] = (byte) ((b2 << 4) | (b3 >> 2));
                bytes[j + 2] = (byte) ((b3 << 6) | b4);
            }

            if (data.charAt(data.length() - 2) == '=') {
                b1 = DECODINGTABLE[data.charAt(data.length() - 4)];
                b2 = DECODINGTABLE[data.charAt(data.length() - 3)];

                bytes[bytes.length - 1] = (byte) ((b1 << 2) | (b2 >> 4));
            } else if (data.charAt(data.length() - 1) == '=') {
                b1 = DECODINGTABLE[data.charAt(data.length() - 4)];
                b2 = DECODINGTABLE[data.charAt(data.length() - 3)];
                b3 = DECODINGTABLE[data.charAt(data.length() - 2)];

                bytes[bytes.length - 2] = (byte) ((b1 << 2) | (b2 >> 4));
                bytes[bytes.length - 1] = (byte) ((b2 << 4) | (b3 >> 2));
            } else {
                b1 = DECODINGTABLE[data.charAt(data.length() - 4)];
                b2 = DECODINGTABLE[data.charAt(data.length() - 3)];
                b3 = DECODINGTABLE[data.charAt(data.length() - 2)];
                b4 = DECODINGTABLE[data.charAt(data.length() - 1)];

                bytes[bytes.length - 3] = (byte) ((b1 << 2) | (b2 >> 4));
                bytes[bytes.length - 2] = (byte) ((b2 << 4) | (b3 >> 2));
                bytes[bytes.length - 1] = (byte) ((b3 << 6) | b4);
            }

            return bytes;
        }

        private static byte[] discardNonBase64Bytes(byte[] data) {
            byte[] temp = new byte[data.length];
            int bytesCopied = 0;

            for (int i = 0; i < data.length; i++) {
                if (isValidBase64Byte(data[i])) {
                    temp[bytesCopied++] = data[i];
                }
            }

            byte[] newData = new byte[bytesCopied];

            System.arraycopy(temp, 0, newData, 0, bytesCopied);

            return newData;
        }

        private static String discardNonBase64Chars(String data) {
            StringBuffer sb = new StringBuffer();

            int length = data.length();

            for (int i = 0; i < length; i++) {
                if (isValidBase64Byte((byte) (data.charAt(i)))) {
                    sb.append(data.charAt(i));
                }
            }

            return sb.toString();
        }

        private static boolean isValidBase64Byte(byte b) {
            if (b == '=') {
                return true;
            } else if ((b < 0) || (b >= 128)) {
                return false;
            } else {
                return DECODINGTABLE[b] != -1;
            }

        }
    }

    private static class CustomEncrpyUtil {
        private static final int KEY = 119;
        private static int PIECE_LENGTH = 20;
        private static int SKIP = 4;


        public static void encodeBytes(byte bytes[]) {
            int key = KEY;
            int pieceLen = PIECE_LENGTH;
            int skip = SKIP;
            int bytesLen = bytes.length;
            int i = 0;
            int offset = 1;
            do {
                for (int j = 0; j < pieceLen; ++j) {
                    int tmp29_27 = i;
                    byte[] tmp29_26 = bytes;
                    if (offset > 1) {
                        tmp29_26[tmp29_27] = (byte) (tmp29_26[tmp29_27] ^ key + offset);
                    } else {
                        tmp29_26[tmp29_27] = (byte) (tmp29_26[tmp29_27] ^ key);
                    }

                    ++i;
                    if (i >= bytesLen) {
                        break;
                    }
                }
                i += skip;
            }
            while (i < bytesLen);
        }

        public static byte[] decodeBytes(byte bytes[]) {
            int key = KEY;
            int pieceLen = PIECE_LENGTH;
            int skip = SKIP;
            int bytesLen = bytes.length;
            int i = 0;
            do {
                for (int j = 0; j < pieceLen; ++j) {
                    int tmp29_27 = i;
                    bytes[tmp29_27] = (byte) ((bytes[tmp29_27] ^ key));
                    ++i;
                    if (i >= bytesLen) {
                        break;
                    }
                }
                i += skip;
            }
            while (i < bytesLen);
            return bytes;
        }
    }
}
