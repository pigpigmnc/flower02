package com.zsc.flower.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016102300747043";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCGNj6l/kgWdhJO5W8QcS3wc11SBST8uuNZnUnmLAylxtmKJWMqAiQjhBFzrVZN+Ihfi+Mm8NatVoxnbo6C6gJHrMZCIcOaNU9wN613c8WQCKEHYITSCkz78bKApzZQM4VyyJtIHdXDusQ2nFOqYeTVHpYMvS/pz1yTUciL+UHkprEMiZzUKGiBJyCl6OZmKDtg5BqcIVU0OpSfoOlPoXL/Z8idmDwwfIlhT9ins/6i6wiZPmUlBbFO2MRTUeCpAVsu7ylwmZ7nbFlvKDbzYTGeA8cH456kbHE6GE0JrqdFSe8b+jFxwUM+K1RNHkcMXmacFAnfEZcsLSVMwB25ZYehAgMBAAECggEAW/ZLUx47cviQbvMS+Sc0HTBPX2YbA/li0wLNoiPIvsbURK59lklFTfiupJ6/JkoH18BYc1RPJRLKvgypJdcGnOosJyBPkRi6R+A+wjzXwGvJOu7N08YvHettLm5k7ut+ozoF/JJE6QrOa8UOJw9Q5x9AHZZ8gC8P9fnx+sdeeTcoqj64RRSHotqkf2X8xOgpzy4rw4jirvq1blGCHr3KxsfA/XQyhk6C4voNE6zfvMJC0Bs862W+Fq/XAA7qOu2PEn8r+J2bIdmG0SBG5yhKOk0ByJnsMPT80h7oUvqrDWGU9Sgn6gm9nXMNRiNzO+uhcwkq9W64HAhJ8319QYw3gQKBgQC/z37fw/lgbZ72WilY2AlX8cpkJRgF4lvDPTUvWZR+9J69mYhqhOV6dqVyiFiG4wAzJrxh5BDP/mZT1gOa36+m7rueBXhELMe7u5zo9HlJrFdNXFwFsQqRD3/Qll2uxzkVyu9gBtsvSHjRtNN5S2JSbNzYeKq0o1DkULEvhrIBqQKBgQCzID66E79zkvRb0BKIZYOm0MFVZ6bj2WG9dcOSvPxyT8AjwwgfgY7Ry66g7AznGYirIkZJCg3aQRP9igfJSxT3wmIj55TDRf1T+ZPyaPH//JP5BzBTPIFkE5a6TgQZ14H5PKCGuYDYXkTYcS48IuKfZ89BjXocUEiL2UZ2n6WBOQKBgQC97/oCGW0biNHNv3CMMrnl5V/2lSjZwtH9XuIItlsu8Is00xFb8afQAidJaCzJvYXYITaQdSgU3yVzRU0ikvi8hFEzZNU4JWPPyjPSFyz3wHpe9+OEG1GMXd3CrvOr/EnSA7fQzllD1C8X8kkrJnQBjR2QGsVnxQV4YVNj7azdqQKBgAZZZz/LQWcEV3sFphKFnqQEFEZFtUmwQ+GmUfdakYwvvXzHBfuv6PMQyDDWDSMH6as/PMSMAa+xzNaY1OvSqHybvETPiZteWLtoRlE6XRGYz1ntYk4g/+kfNKGH8GdGF0pRVtNPsfx5YvFVGbFx/xxtXpBRRnnIcrnaSsAbqo8pAoGAONi199eVtnozFcgDn0KOoshL3PLbBGx+XcyQLBHuN0tKn3B1s6wpbXoRXxkH3wMU0Gsq8oRGu/OxjPgfexlG0+ON0SZrIciu7qJfKBqEHz9oZ7HNuME60B/FUHYVT0RGoRWgjxxvB/TmAR1KJfOfk15RxMehId4ITD9UVx4G4ps=";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiOlhIblUPWgSSaCTKKsHah1YvEfGxyDJO76Gtn/YN84MTQUbDILcGfbtOCjtC3OE/RagYai69+34oLVcdyoQVOIoD4RtwIOr2PRA+Te0dBEg0TiNPIK50u7BviQ8fe/CZIStjyDhXf+k5Z1iqpK4s1ufOQciUw1vGZ9sW7I17GUcKtik62giGtAoDRki1GWfQy62nQI7+3hty8CfAAO/EYGG6JfrghNEbheVERUTEx/dipsXXsUatqdBFsnP+yvZReVbKXYDUeIE6kOiiLX41HXY3D3mGacK503ABZsbNoFAJTSJH9syJftPg7CD7RYIKGMU1wKinmcOZf6KXltZMQIDAQAB";
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//	public static String notify_url = "http://airnet-data.natapp1.cc/order/alipayNotifyNotice";
	public static String notify_url = "http://49.232.140.139:8088/order/alipayNotifyNotice";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//	public static String return_url = "http://airnet-data.natapp1.cc/mall/my_account.html";
	public static String return_url = "http://49.232.140.139:8081/mall/my_account.html";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
//    写成"utf-8"时，支付宝支付调试出现错误代码 invalid-signature 错误原因: 验签出错，建议检查签名字符串或签名私钥与应用公钥是否匹配
	public static String charset = "gbk";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
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

