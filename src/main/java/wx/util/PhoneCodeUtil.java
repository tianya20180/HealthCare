package wx.util;

import java.util.Random;

public class PhoneCodeUtil {

    public static String getCode(){
        StringBuffer buffer=new StringBuffer();
        Random random=new Random();
        for(int i=0;i<4;i++){
            buffer.append(random.nextInt(10));
        }
        return buffer.toString();
    }

}
