package test;


import com.itgo.util.yml.YmlUtils;
import sun.misc.BASE64Encoder;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Set;

/**
 * Create by xb
 * The file is [ Test] on [ case ] project
 * The file path is test.Test
 *
 * @versio 1.0.o
 * @Author he ming xi
 * @date 2019/3/23 14:59
 * @description
 */
public class Test {

//    private static  final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws Exception {
//        DBConfig config = new DBConfig();
//        config.setUri("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&useSSL=true");
//        config.setDriver("com.mysql.jdbc.Driver");
//        config.setUserName("test");
//        config.setPassword("123456");
//
//        DatabaseManager.init(config);
//        if(DatabaseManager.getConnection() != null){
//            System.out.println("获取数据库连接成功！");
//            DatabaseManager.closeConnection();
//        }

//        String jsonData = "{\n" +
//                "  \"id\": \"1\",\n" +
//                "  \"name\": \"何明喜\",\n" +
//                "  \"address\": \"北京市\",\n" +
//                "  \"email\": \"xigexb@outlook.com\",\n" +
//                "  \"age\": 11,\n" +
//                "  \"gender\": \"男\"\n" +
//                "}\n";
        String jsonData2 = "{id='1', name='何明喜', address='北京市', email='xigexb@outlook.com', age=11, gender=男}";
//        Student student = new Student();
//        student.setAddress("北京市");
//        student.setAge(11);
//        student.setEmail("xigexb@outlook.com");
//        student.setGender('男');
//        student.setId("1");
//        student.setName("何明喜");
//        String json = GsonUtil.toJson(student);
//        System.out.println(json);
//        System.out.println(student.toString());
//        Student student1 = GsonUtil.gsonToBean(jsonData, Student.class);
//        System.out.println(student1);
//        Student student2= GsonUtil.gsonToBean(jsonData2, Student.class);
//        System.out.println(student2);
        // ECFD1ED37695B66A55C84867F4DF2E6D9142AB4A8A5003C0CE9C01D04D8961AF7CC774100563F0BF1601EBE95729A4124ED5064939D3BEE88C3E1E48CA56FEF082B3A2E6BB34C75DC3947D19B2B5EF8F21DAB136316E359D986ECFA547180D8ED73566646E6A3CD3B38E0CE2A70CBB03077AC6CB286CE3E15F5A18EFB8400A89AB4E181013E1A2FC6DC6C1E8180F60E5DF7FDFC478B85903E7D26C268BBE04CDC8C831C7449246626C6A4F7BB97B3E8E0F5448DA73F645F9E1726B0763B87AB98B21415680C82F78BD2520C8B1B769C2
//        String key = "81196xigex";
//        System.out.println("加密前:"+"81196xigex");
//        String decrypt = EncryptAESUtil.encrypt("81196xigex", key);
//        System.out.println("加密后："+decrypt);
//        String decrypt1 = EncryptAESUtil.decrypt(decrypt, "81196xige");
//        System.out.println("解密后："+decrypt1);
        //String ss = "ECFD1ED37695B66A55C84867F4DF2E6D9142AB4A8A5003C0CE9C01D04D8961AF7CC774100563F0BF1601EBE95729A4124ED5064939D3BEE88C3E1E48CA56FEF082B3A2E6BB34C75DC3947D19B2B5EF8F21DAB136316E359D986ECFA547180D8ED73566646E6A3CD3B38E0CE2A70CBB03077AC6CB286CE3E15F5A18EFB8400A89AB4E181013E1A2FC6DC6C1E8180F60E5DF7FDFC478B85903E7D26C268BBE04CDC8C831C7449246626C6A4F7BB97B3E8E0F5448DA73F645F9E1726B0763B87AB98B21415680C82F78BD2520C8B1B769C2";
//        String encryptData = CastUtil.str2HexStr(ss); 74ee48fcb47414f9f5e3eca1ecf7973223a37f6cafe0dfda2ad7a9aa3f41d1b6808ecdbadb97a8ae8abcebdafce6e58f9170f379cf0f1493ee01f5abc01cad8e7d71a38af4e760b5c5f6d518fb68d8b5c63886596458773102d93d688197755a41e8fff2ae776c32266de755e0f76b6e2b5eadcd061ae269d367e7b3054da6ede1682bf5caf19cd2ccdb26d37aeec878a50c1ad8c98e4e9ec81c60b942db15598aa32074b301b684ebdfc92797a90bf6caed87899a0929d0a507825e900d3086e4acc8e129b8b0ea5a01e40d7b6d0849e9bb736f28230ad8d2620641323d492180224dd8c228635c4637c5a58c0a3841e1e92e93bde4b2c0d140de82cbc5bf19e721a6bc04c536419ae84c3b8ed843104a6962fb0d6dc58ca70143186649d4d65bdde5a3351799724cda478c210b92d97b7dd18c4a5c7b4f98bfc7c3df3b88fd49196f663d6202cccc58e88b805c3444d30e0d4a8a7ad2fa3c03cef20f9b484516774e607fe5399be681e61e012aad5ee2733116d5591f363e2583031e62ece2ef6142387b9b0a3298fd1dc01e7f5e3e91fd62bf2b7e54ae231b829b01ce7c3f1a91cca671248ed81a242b0b671c3b4ec5e829e8843fa015c572fb1db9854ff2550708a752d73613a1a153a0fecb14bdfd5ed4b64c94a1482726f9d047da44579285d1492394b54ee0ba1dad6f5be20ad82ad7d3e5f7a1e01cab532f2ddf4941e8e8447b4e52bd7f6550360b740da139dbec57e86a2fae295bb61b9fb05c0eacc4826f9aa2c3c9fb04c37d0c297182fc99b68f7b4349d38331ccce169c9b3f8b1da0fbb5a67162af8c272eb3e64115bad06608ae7beccefb0cfadd0c9cd75edc30e97e87fb7bbfa8fb96a45a06b39253ad3c0faf8b61e817189c77283d456efd8e4f51dbac0050e761c5c34a3976010ed463d16c572b2a80a33c2255eb0444d03e5104100b5ece7f5546df64ac292a122038a4b3aec2124a71faa3fe5781792ba84d7f2be2f306c21b77805b25c
//        BigIntegerStringConverter bigIntegerStringConverter = new BigIntegerStringConverter();
//        BigInteger bigInteger = bigIntegerStringConverter.fromString(encryptData);
//        String bigstr = bigInteger.toString(16);
//        BigInteger bigInteger1 = new BigInteger(bigstr, 16);
//        System.out.println(bigstr);
//        System.out.println("转换字符串"+bigInteger1.toString());
//        String sss =  CastUtil.hexStr2Str(encryptData);
//        System.out.println("原字符串"+ss);
//        System.out.println("转换字符串"+encryptData);
//        System.out.println("转回字符串"+sss);
//        System.out.println("结果："+ss.equals(sss));
        //OmFTSx4kinv7EOHgyk8KqS5OnZexUwdZyJH9wJJs1CFnbV5tHcDyI3ZFty+5Ozgh7p6IaE/1+meFlyKxz94DeTdnT/pd0+fVXDbzhLrkL/5pxx4PAUXVMf0L44Asw7VfiP6erBfGAWWIz3htm2hd26bYXPyciaSJTScohOOSE7Q=
        //Ei6dClJ3KtZekTWf0NyhMNGv8FE2QtJmg5Vc+oUTndbflAfKNAuzWFypC9Ijme9Lba6GmX5IveVU2vdMvvDX4k6Tyvfv0ZQaD+3EqZBIzxBniUHo1rkbtAl6ZiKwCieVJiNhlzKE8Nszrgcvr3JJEeVtXpPTfm7xClIduyz2sBE=
        // flA+5glGk1OW4KbnpSYzMMUkRtTJok8JefOG0qEnLnlpZOlTnSnCgomsIf9JMvpq3y6dapcBjsWAVowPGUbyhftSzhpcKGNgBV5thq72EAdN1KnYbuyJXvnhmYy0mpgqrFNE17b9Rs+D/4Aj53hHy6kSKrtm4pi0jq08SDvSlR4=
        // MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXdrXNn1frPZXZGhdZ8hY0SKJ2CP/u9j7xYzBuKW+tLUyYSi8I2q2ihR/YCJJN29ehAiR/6y87nFxZiiJzWoICoUWJnokFDO8nQ6b67YcL6J/2HbYbU/Az/+DKKsQXSD8B8BA7slRQ4NHnhOqlhSv8AOMKsBp9RBMEI63iB9s/0wIDAQAB


//        String key = "81196xigexb";
//        String encrypt = EncryptDESUtil.encrypt(jsonData2, key);
//        System.out.println(jsonData2);
//        System.out.println(encrypt);
//        System.out.println(EncryptDESUtil.encrypt(encrypt,key));
//        RedisUtil.init(null);
//        RedisUtil redisInstance = RedisUtil.getRedisInstance();
//        RedisUtil.Strings strings   = redisInstance.new Strings();
//        strings.set("xgxb","何明喜");
//        System.out.println(strings.get("xgxb"));

////        logger.info("ssssssssssssssssssssssssss");
//        Student student = new Student("Tom",19);

//        student.setSex(12);
//        Student student2 = new Student();
//
//
//        ObjectUtil.copy(student, student2);
//        System.out.println(student);
//        System.out.println(student2);

//        List<Student> students = Arrays.asList(new Student("Tom", 19,1), new Student("周三", 19,1), new Student("王八", 19,1), new Student("赵柳", 19,1), new Student("无极", 19,1));
////        List<Student2> student2s = ObjectUtil.copyList(students, Student2.class);
////        System.out.println(students);
//        for (Student student : students) {
//            Map<String, Object> map = ObjectUtil.toMap(student);
//            System.out.println(map);
//        }

//        Map<String,Object> map = new HashMap<>();
//        map.put("naMe","Tom");
//        map.put("aGe",18);
//        map.put("SEX",1);
//
//        Student student = ObjectUtil.toObject(map, Student.class,true);
//        System.out.println(student);
//        Integer[][] is = new Integer[12][12];
//        is[0][1] = 1;
//        is[6][1] = 0;
//        is[2][6] = 1;
//        Object[][] objects = ArrayUtil.toSparseArray(null);
//        Object[][] newObjects = ArrayUtil.toTwoDimensionalArray(objects);
//
//        for (Integer[] i : is) {
//            for (Integer j : i) {
//                System.out.print(j+"\t");
//            }
//            System.out.println();
//        }
//
//        System.out.println("===================================================================");
//
//        for (int i = 0; i < objects.length; i++) {
//            System.out.printf("%d\t%d\t%d\n",objects[i][0],objects[i][1],objects[i][2]);
//        }
//        System.out.println("===================================================================");
//
//        for (Object[] i : newObjects) {
//            for (Object j : i) {
//                System.out.print(j+"\t");
//            }
//            System.out.println();
//        }
//        MailUtil.init("mail.properties");
//        String code = "125701";
//        MainBean mainBean = new MainBean();
//        mainBean.setToMail("365826650@qq.com");
//        mainBean.setSubject("注册通知");
//        mainBean.setUserName("白先生");
//        mainBean.setTitle("ITGo-研发部");
//        mainBean.setContent("<div style=\"width: 500px\">\n" +
//                "        <h4>尊敬的"+mainBean.getUserName()+ ",您好：</h4>\n" +
//                "        <p style=\"padding-left: 20px;\">你的验证码是：<span style=\"font-size: 25px; color: blue;\">"+code+"</span>，五分钟内有效。</p >\n" +
//                "</div>");
//        String s = MailUtil.sendSimpleMail(mainBean);
//        System.out.println(s);


//        System.out.println(firstLetterToUpper("name"));
//        char n = 'n';
//        char s = 's';
//        System.out.println((char)83);
//        n = (char)(n-32);
//        System.out.println(n);

//        CodeBean codeBean =  new CodeBean();
//
//        DBBean db = new DBBean();
//        db.setDriver("com.mysql.cj.jdbc.Driver");
//        db.setUrl("jdbc:mysql://localhost:3306/shop?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC");
//        db.setUsername("dev");
//        db.setPassword("81196");
//        codeBean.setDbBean(db);
//
//        BeanMeta bean = new BeanMeta();
//        bean.setPackagePath("com.itgo");
//        bean.setAuthor("xigexb");
//        bean.setVersion("1.0.0");
//        bean.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:sss").format(new Date()));
//        bean.setClassDesc("用户");
//        bean.setImportList(Arrays.asList("com.itgo.annotation.BeanField","com.itgo.bean.BaseBean"));
//        bean.setGenPath("E:\\project\\case\\src\\main\\java\\test\\");
//        codeBean.setBeanMeta(bean);
//        codeBean.start("shop_user");


//        String url = "http://localhost:8088/test/t";
//        Map<String,Object> params = new HashMap<>();
//        params.put("userName","白艳龙");
//        params.put("token","he");
//        String ssl = HttpUtil.get(url, params, 1000, 1000);
//        System.out.println(ssl);

//        PrintWriter out = new PrintWriter("e:/test.txt","UTF-8");
//        out.println("测试文本2\r\n");
//        out.flush();
//        InputStreamReader inputStreamReader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
//        int read = inputStreamReader.read();
//        System.out.println((char)read);
//
//        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream("e:/zip.zip"));
//        ZipEntry entry = null;
//        while ((entry = zipInputStream.getNextEntry()) != null){
//            InputStream inputStream =  zipInputStream.getInputStream(entry);
//        }

//        ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream("e:/aa.zip"));
//        outputStream.putNextEntry(new ZipEntry("e:/ceshi.txt"));
//        outputStream.closeEntry();
//        outputStream.close();

        YmlUtils ymlUtils = YmlUtils.getInstance();
        System.out.println(ymlUtils.getProperty("bean.name"));
        System.out.println(ymlUtils.getProperty("bean.name2"));
    }


    /**
     * 获取权健
     *
     * @param params
     * @param appKey
     * @return
     */
    public String getReqSign(Map<String, String> params, String appKey) {
        StringBuffer url = new StringBuffer("");
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            String value = params.get(key);
            if (value != null && !"".equals(value)) {
                url.append(key);
                url.append("=");
                url.append(URLEncoder.encode(value));
                url.append("&");
            }
        }
        url.append("app_key=").append(appKey);
        String newUrl = url.toString();
        newUrl = md5AndUpperCase(newUrl);
        return newUrl;
    }

    /**
     * MD5和转大写
     *
     * @param source
     * @return
     */
    public String md5AndUpperCase(String source) {
        //确定计算方法
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            String newstr = base64en.encode(md5.digest(source.getBytes("utf-8")));
            newstr = newstr.toUpperCase();
            return newstr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
