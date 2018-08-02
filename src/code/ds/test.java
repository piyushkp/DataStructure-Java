package code.ds;

import java.util.*;

/**
 * Created by ppatel2 on 11/9/2016.
 */
public class test {
    public static void main(String[] args) {
        //List<String> out = iprange2cidr("192.168.1.8", "192.168.1.9");
        long s = ip2long("1.1.1.111");
        String st = long2ip(s);
        long e = s + 9;

        List<String> out1 = iprange2cidr(s, e);
        System.out.println(Arrays.toString(out1.toArray()));
    }
    //http://stackoverflow.com/questions/5020317/in-java-given-an-ip-address-range-return-the-minimum-list-of-cidr-blocks-that
    public static List<String> iprange2cidr( String ipStart, String ipEnd ) {
        long start = ip2long(ipStart);
        long end = ip2long(ipEnd);

        ArrayList<String> result = new ArrayList<String>();
        while ( end >= start ) {
            byte maxSize = 32;
            while ( maxSize > 0) {
                long mask = iMask( maxSize - 1 );
                long maskBase = start & mask;

                if ( maskBase != start ) {
                    break;
                }

                maxSize--;
            }
            double x = Math.log( end - start + 1) / Math.log( 2 );
            byte maxDiff = (byte)( 32 - Math.floor( x ) );
            if ( maxSize < maxDiff) {
                maxSize = maxDiff;
            }
            String ip = long2ip(start);
            result.add( ip + "/" + maxSize);
            start += Math.pow( 2, (32 - maxSize) );
        }
        return result;
    }

    public static List<String> iprange2cidr( long start, long end ) {
        //long start = ipStart;
        //long end = ipEnd;

        ArrayList<String> result = new ArrayList<>();
        while ( end >= start ) {
            byte max = 32;
            while ( max > 0) {
                long mask = iMask( max - 1 );
                long maskBase = start & mask;

                if ( maskBase != start ) {
                    break;
                }
                max--;
            }
            double x = Math.log( end - start + 1) / Math.log( 2 );
            byte maxDiff = (byte)( 32 - Math.floor( x ) );
            if ( max < maxDiff) {
                max = maxDiff;
            }
            String ip = long2ip(start);
            result.add( ip + "/" + max);
            start += Math.pow( 2, (32 - max) );
        }
        return result;
    }

    private static long iMask(int s) {
        return Math.round(Math.pow(2, 32) - Math.pow(2, (32 - s)));
    }

    private static long ip2long(String ipstring) {
        String[] ipAddressInArray = ipstring.split("\\.");
        long num = 0;
        long ip = 0;
        for (int x = 3; x >= 0; x--) {
            ip = Long.parseLong(ipAddressInArray[3 - x]);
            num |= ip << (x << 3);
        }
        return num;
    }

    private static String long2ip(long longIP) {
        StringBuffer sbIP = new StringBuffer("");
        sbIP.append(String.valueOf(longIP >>> 24));
        sbIP.append(".");
        sbIP.append(String.valueOf((longIP & 0x00FFFFFF) >>> 16));
        sbIP.append(".");
        sbIP.append(String.valueOf((longIP & 0x0000FFFF) >>> 8));
        sbIP.append(".");
        sbIP.append(String.valueOf(longIP & 0x000000FF));

        return sbIP.toString();
    }


}


