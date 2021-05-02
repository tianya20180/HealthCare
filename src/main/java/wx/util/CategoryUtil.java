package wx.util;

public class CategoryUtil {
    public static String convertCategory(Integer category){
        switch (category){
            case 1:
                return "皮肤科";
            case 2:
                return "耳喉鼻科";

            case 3:
                return "肝病科";

            case 4:
                return "儿科";

            case 5:
                return "内科";

            case 6:
                return "消化科";
            case 7:
                return "妇科";
            case 8:
                return "心脑科";




        }
        return "";
    }
}
