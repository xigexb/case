package test;

import com.itgo.util.redis.RedisUtil;

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

    public static void main(String[] args){
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
        RedisUtil.init("db.properties");
        RedisUtil redisInstance = RedisUtil.getRedisInstance();
        RedisUtil.Strings strings   = redisInstance.new Strings();
        strings.set("xgxb","何明喜");
        System.out.println(strings.get("xgxb"));
    }
}
