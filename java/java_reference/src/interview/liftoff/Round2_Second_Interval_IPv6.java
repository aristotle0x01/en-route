package interview.liftoff;

public class Round2_Second_Interval_IPv6 {
    public byte[] parse(String ipv6) {
        // todo: check input later: InvalidIPv6Address

        byte[] bytes = new byte[16];
        String[] array = ipv6.split(":");
        if (array.length == 8) {
            // should be case 1
            // case 1:  1203:405:0:0:a0b:0:0:fffe
            byte[] twoBytes = new byte[2];
            for (int i=0; i<8; i++) {
                parsePart(array[i], twoBytes);
                bytes[2*i+0] = twoBytes[0];
                bytes[2*i+1] = twoBytes[1];
            }
        } else {
            int dot = ipv6.indexOf('.');
            if (dot == -1) {
                // case 2
                // case 2: 1203:405::a0b:0:0:fffe
                //         1203:405:0:0:a0b::fffe

                // plus one include self
                int shortenedZeroCount = 8 - array.length+1;
                byte[] twoBytes = new byte[2];
                int i = 0;
                int index = 0;
                while (i < array.length) {
                    if (array[i].isEmpty()) {
                        for (int j=0; j<shortenedZeroCount; j++) {
                            bytes[2*index+0] = 0;
                            bytes[2*index+1] = 0;
                            index++;
                        }
                        i++;
                        continue;
                    }
                    parsePart(array[i], twoBytes);
                    bytes[2*index+0] = twoBytes[0];
                    bytes[2*index+1] = twoBytes[1];
                    i++;
                    index++;
                }
            } else {
                // case 3: 1203:405::a0b:0:0.0.255.254

                String ipv4 = array[array.length-1];
                String[] v4Array = ipv4.split("\\.");
                // todo: check v4Array length
                for (int i=0; i<4; i++) {
                    bytes[12+i] = (byte) Integer.parseInt(v4Array[i]);
                }

                // like case 2
                // plus one include self
                int ipv6PartsLen = array.length - 1;
                int shortenedZeroCount = 6 - ipv6PartsLen + 1;
                byte[] twoBytes = new byte[2];
                int i = 0;
                int index = 0;
                while (i < ipv6PartsLen) {
                    if (array[i].isEmpty()) {
                        for (int j=0; j<shortenedZeroCount; j++) {
                            bytes[2*index+0] = 0;
                            bytes[2*index+1] = 0;
                            index++;
                        }
                        i++;
                        continue;
                    }
                    parsePart(array[i], twoBytes);
                    bytes[2*index+0] = twoBytes[0];
                    bytes[2*index+1] = twoBytes[1];
                    i++;
                    index++;
                }
            }
        }

        return bytes;
    }

    private void parsePart(String s, byte[] twoBytes) {
        // todo: check s later
        int n = s.length();
        if (n == 4) {
            twoBytes[0] = (byte) Integer.parseInt(s.substring(0, 2), 16);;
            twoBytes[1] = (byte) Integer.parseInt(s.substring(2), 16);
        } else if (n == 3) {
            twoBytes[0] = (byte) Integer.parseInt(s.substring(0, 1), 16);;
            twoBytes[1] = (byte) Integer.parseInt(s.substring(1), 16);
        } else if (n == 2) {
            twoBytes[0] = 0;
            twoBytes[1] = (byte) Integer.parseInt(s, 16);
        } else if (n == 1) {
            twoBytes[0] = 0;
            twoBytes[1] = (byte) Integer.parseInt(s, 16);
        } else {
            // todo: error case
        }
    }

    public static void main(String[] args) {
        Round2_Second_Interval_IPv6 test2 = new Round2_Second_Interval_IPv6();
        byte[] bytes1 = test2.parse("1203:405:0:0:a0b:0:0:fffe");
        printBytes(bytes1);
        byte[] bytes2 = test2.parse("1203:405::a0b:0:0:fffe");
        printBytes(bytes2);
        byte[] bytes3 = test2.parse("1203:405::a0b:0:0.0.255.254");
        printBytes(bytes3);
    }

    static void printBytes(byte[] bytes3) {
        for(int i=0; i<16; i++) {
            int unsignedByte = bytes3[i] & 0xFF;
            System.out.print(unsignedByte + " ");
        }
        System.out.println();
    }
}
