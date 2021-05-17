package wx.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号,开发时使用沙箱提供的APPID，生产环境改成自己的APPID
    public static String APP_ID = "2021000117658481";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCvV1AS7FLWV00AZwAf3Rrpjx211AB/ik8Pck5oOS70x0htWU94YCOq0nV9uRjBugOes9bTrMbXo6BtLBQrbrNHv7n3DujQMoyVSchv8J/iWwfG0alJFxp/rbc7IV/EL9MvVfX6GjkX+bNs0ZfQyZcvlLK/ZiXlCBhD91IRnWGxqZ6PcpvoutEQ+dA6OcyIjixA4tI2iQ0dQBXU5Ej9my0umZ8v9qegcId95AxKpMLhL3fpshKBiPxd7UIi4tdK47NQiNg6/15w5+zLivhu1GO3Ghu50Cneig2cLNTicbomrAFoJpzDXVx7j9EcDaYiiCOnpHs1lcKQ6r/If225aHqxAgMBAAECggEANWbqRFklgZJlKVI1f37fy3KDIfXHkRCWs6Wkq6l+2542ONeSlJ7zPfuLPRc6qwX0REmRjDZ6+QIkwhtCqVe10oxaHxyzW3WJy44FNdKbX11KKQuB+3jF4U0PGTs454+JSklr+Rgze1HZi7LqAfTjzuIC7rg1omOswEZ7AFJmhq0ZCFtWHhrpDtl6s7AnCJxhFeIAlKtlOvi/bePJ/aNMnXTgGSQcUg4PnzAoS0dwpRe4ZFTMZADE8cQbk/HQ8iVdS0hhej2D41rPNCAoOdNwE4f1qfC03Mo7DjV+J0EAn0TDoVFnizqxZRh0plXrbsqSgYhXkHdyhqOLlUm0jLD0gQKBgQDhZ6LoBMhz5c9wcOdqpg3SKYPGzfbV5TflBZ70g+9lwit/oyD6pduNymPmLKSYNL2wpI9t9r7UmnwR6gouEPFHRnnNQulcZizzqwdixbQAjwPqY420saqaq6iq4jkM1XHPabIDdVwbofnnI2hl/lmuORPcmSGI/uwvUs/zKhzfVQKBgQDHJA+/d2CSmnWqnzeRR1GKzLzYbkQb2B8+/JS/eWXgPovo/Vau2Wjvrl+G7rP7uZK/NwMH9NE5Ejq9M3ER1TXXQOE4SXb+cIgMU+1q2U3RKaAULDZdm7K3EFEdAwCn+nTzbew5uEZz2PI8ezqe/aCkRRNtWauyJ+zjQF2Tg2zV7QKBgHKk2Imz7RRtnX1uH3Xx8XOkI98Y4OPXyv8URc3N0RRrlt47ZUm21B2hBACNUkYMBTLz7/OZHWRbEqG17OmTOnTrFoZc8v5JBbIFmiMVtUDXfMFZUzjFaKV0i7wmhafOZC1/b8gsh+WSE94I909w98ISS9TWmeTo684CC7x8/eRlAoGAE0uNMxIQC2Rn7kZLDgqI/hfkXFwiwUfpjyLODtdmZZnp2INjeMGcB00v+FT8n3FVpK1h38160DrR8i8fz4iHFPo2WEvV+cVRiVBAes/fWFreJdY61ksVv+MW2ObHk38TAdsuvlD6kpL8tZuDDJCqlgATZ87+cd6od0RvTLG5Q+kCgYAM4sx3vFtC8IhPQHdtO0pXFZST4NpaaFXD1jrBXMYD/SpxFwfNgOkistS8npxUq9/vp4bVqY1RqgLvzb93rPhCz0rW7EIGogcqB9IA1dgWy7CHRMh4Z1zmIzD1EvFybnPKsXmyZUa4h+6zqPZ4OPUIlVkHaFxGQKACLEtrFUUlTQ==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtYTWHol58A1DCM2GB+gThvTuBp8ZbNW4UjNW6waV9Bu8Y50PHfL+digo7RPA1TTrtNKSjGWUMPZWDlL/PY2elLWNUfdwY8aKBl3+N9g4/VBvajqbEsh0SzjdKote/Ww2Ksqp/0R2eznKUFCzTiPPsujk2/9go7Hd9gS2huQ4C5z49+6N5KcMyAid3KTju+PsMIxMYZ9LJN589C1UzJI+0trOfIcdtvjNXcB/mYOxLUV1uwYtQ4qAUQouYZECeSqm+yWhdMHViexIOZ9eWT2Zykv6jALimDamwynQ0MoJzlVjgfZOlrGRPuTzsFYUnt3/T55AE/Iq0jNYmnJjUOxnPwIDAQAB";

    // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/pay/alipayNotifyNotice";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问(其实就是支付成功后返回的页面)
    public static String return_url = "http://localhost:8080/pay/alipayReturnNotice";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String CHARSET = "utf-8";

    // 支付宝网关，这是沙箱的网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}