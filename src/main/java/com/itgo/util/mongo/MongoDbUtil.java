package com.itgo.util.mongo;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFS;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class MongoDbUtil {

    private MongoDbUtil() {
    }

    private final static Properties properties = new Properties();
    private static Logger log = LoggerFactory.getLogger(MongoDbUtil.class);

    static {
        try {
            InputStream in = MongoDbUtil.class.getClassLoader().getResourceAsStream("mongodb.properties");
            if (in != null) {
                log.info("Found  mongodb.properties file in local classpath");
                try {
                    properties.load(in);
                } finally {
                    in.close();
                }
            } else {
                log.info("Not Found mongodb.properties file in local classpath");
            }
        } catch (Exception e) {
            log.info("Could not load mongodb.properties file from local classpath: " + e);
        }
    }

    /**
     * config file info
     */
    private static class Config {
        public static String ip;
        public static Integer port;
        public static String userName;
        public static String password;
        public static String databaseName;
        public static Integer connectionsPerHost;
        public static Integer maxWaitTime;
        public static Integer connectTimeout;
        public static MongoClientOptions options;
        public static DB db;
        public static GridFS gridFS;
        public static MongoDatabase database;
        /**
         *
         */
        public static List<MongoCredential> credential = new ArrayList<>();

        //init mongodb
        static {
            if (ip == null) {
                ip = properties.getProperty("mongodb.ip") != null ? properties.getProperty("mongodb.ip") : "127.0.0.1";
            }
            if (port == null) {
                port = Integer.valueOf(properties.getProperty("mongodb.port")) != null ? Integer.valueOf(properties.getProperty("mongodb.port")) : 27017;
            }
            if (userName == null) {
                userName = properties.getProperty("mongodb.userName") != null ? properties.getProperty("mongodb.userName") : userName;

            }
            if (password == null) {
                password = properties.getProperty("mongodb.password") != null ? properties.getProperty("mongodb.password") : password;
            }
            if (databaseName == null) {
                databaseName = properties.getProperty("mongodb.dbName") != null ? properties.getProperty("mongodb.dbName") : databaseName;
            }
            if (connectionsPerHost == null) {
                connectionsPerHost = Integer.valueOf(properties.getProperty("mongodb.connections.per.Host")) != null ? Integer.valueOf(properties.getProperty("mongodb.connections.per.Host")) : 10;
            }
            if (maxWaitTime == null) {
                maxWaitTime = Integer.valueOf(properties.getProperty("mongodb.maxWaitTime")) != null ? Integer.valueOf(properties.getProperty("mongodb.maxWaitTime")) : 6000;
            }
            if (connectTimeout == null) {
                connectTimeout = Integer.valueOf(properties.getProperty("mongodb.connectTimeout")) != null ? Integer.valueOf(properties.getProperty("mongodb.connectTimeout")) : 0;
            }
            options = MongoClientOptions.builder().connectTimeout(0)
                    .maxWaitTime(6000).connectionsPerHost(10).build();
            MongoCredential credential1 = MongoCredential.createCredential(userName,
                    databaseName, password.toCharArray());
            credential.add(credential1);
        }

    }


    private static final class MongoInstance {
        public final static MongoClient client;

        static {
            client = new MongoClient(new ServerAddress(Config.ip, Config.port), Config.credential, Config.options);
            Config.database = client.getDatabase(Config.databaseName);
        }
    }


    /**
     * destroy pool
     */
    public static final void destroy() {
        MongoInstance.client.close();
    }

    /**
     * get a MongoDatabase
     *
     * @return MongoInstance
     */
    public static MongoDatabase getDatabase() {
        return MongoInstance.client.getDatabase(Config.databaseName);
    }


    /**
     * get a MongoDatabase by Name
     *
     * @param databaseName
     * @return
     */
    public static MongoDatabase getDatabase(String databaseName) {
        return MongoInstance.client.getDatabase(databaseName);
    }


    /**
     * get current collection's names
     * @return
     */
    public static List<String> getCollectionNames(){
        List<String> names = new ArrayList<>();
        for (String name : getDatabase().listCollectionNames()){
            names.add(name);
        }
        if(names != null && names.size()>0){
            return  names;
        }else {
            return null;
        }
    }

    /**
     * get current collection's names by database name
     * @return
     */
    public static List<String> getCollectionNames(String dbName){
        List<String> names = new ArrayList<>();
        for (String name : getDatabase(dbName).listCollectionNames()){
            names.add(name);
        }
        if(names != null && names.size()>0){
            return  names;
        }else {
            return new ArrayList<>();
        }
    }


    /**
     * insert one Document
     * @param databaseName
     * @param collectionName
     * @param document
     */
    public static  void insertOne(String databaseName,String collectionName,Document document){
        MongoCollection<Document> collection = getDatabase(databaseName).getCollection(collectionName);
        collection.insertOne(document);
    }

    /**
     * insert one Document
     * @param collectionName
     * @param document
     */
    public static  void insertOne(String collectionName,Document document){
        MongoCollection<Document> collection = getDatabase().getCollection(collectionName);
        collection.insertOne(document);
    }



    /**
     * upload file to mongo
     *
     * @param filename
     * @param in
     * @return
     */
    public static String uploadFileToGridFS(String filename, InputStream in) {
        //default bucket name is fs
        GridFSBucket bucket = GridFSBuckets.create(getDatabase());
        ObjectId fileId = bucket.uploadFromStream(filename, in);
        return fileId.toHexString();
    }

    /**
     * upload file to mongo, if close is true then close the inputStream
     *
     * @param filename
     * @param in
     * @param close
     * @return
     */
    public static String uploadFileToGridFS(String filename, InputStream in, boolean close) {
        String returnId = null;
        try {
            returnId = uploadFileToGridFS(filename, in);
        } finally {
            if (close) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.info("close inputstream fail:" + e);
                }
            }
        }
        return returnId;
    }

    /**
     * upload file to mongo
     *
     * @param fileName
     * @param file
     * @return
     */
    public static String uploadFileToGridFs(String fileName, File file) {
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            String returnId = uploadFileToGridFS(fileName, in, true);
            return returnId;
        } catch (IOException e) {
            log.info("upload fail:" + e);
        }
        return null;
    }

    /**
     * set filename = file name
     *
     * @param file
     * @return
     */
    public static String uploadFileToGridFs(File file) {
        return uploadFileToGridFs(file.getName(), file);
    }

    /**
     * set filename = uuid
     *
     * @param file
     * @return
     */
    public static String uploadFileToGridFSByUUID(File file) {
        return uploadFileToGridFs(UUID.randomUUID().toString(), file);
    }

    /**
     * download file for gridfs by objectid
     *
     * @param objectId
     * @param out
     */
    public static void downloadFile(String objectId, OutputStream out) {
        GridFSBucket bucket = GridFSBuckets.create(getDatabase());
        bucket.downloadToStream(new ObjectId(objectId), out);
    }

    /**
     * download file for gridfs by objectid
     *
     * @param objectId
     * @param file
     */
    public static void downloadFile(String objectId, File file) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            downloadFile(objectId, os);
        } catch (IOException e) {
            log.info("download fail:" + e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    log.info("close outputstream fail:" + e);
                }
            }
        }
    }

    /**
     * download file for gridfs by objectid
     *
     * @param objectId
     * @param filename
     */
    public static void downloadFile(String objectId, String filename) {
        File file = new File(filename);
        downloadFile(objectId, file);
    }

    /**
     * download file for gridfs by filename
     *
     * @param filename
     * @param out
     */
    public static void downloadFileByName(String filename, OutputStream out) {
        GridFSBucket bucket = GridFSBuckets.create(getDatabase());
        bucket.downloadToStreamByName(filename, out);
    }

    /**
     * download file for gridfs use stream
     * 如果一次性读取所有字节，大于chunk size的可能会出现乱序，导致文件损坏
     *
     * @param objectId
     * @param out
     */
    public static void downloadFileUseStream(String objectId, OutputStream out) {
        GridFSBucket bucket = GridFSBuckets.create(getDatabase());
        GridFSDownloadStream stream = null;
        try {
            stream = bucket.openDownloadStream(new ObjectId(objectId));
            /** gridfs file */
            GridFSFile file = stream.getGridFSFile();
            /** chunk size */
            int size = file.getChunkSize();
            int len = (int) file.getLength();
            /** loop time */
            int cnt = len / size + (len % size == 0 ? 0 : 1);
            byte[] bts = new byte[Math.min(len, size)];
            try {
                while (cnt-- > 0) {
                    int tmp = stream.read(bts);
                    out.write(bts, 0, tmp);
                }
                out.flush();
            } catch (IOException e) {
                log.info("download fail:");
            }
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }


    /**
     * download file for gridfs use stream
     *
     * @param objectId
     * @param fileName
     */
    public static void downloadFileUseStream(String objectId, String fileName) {
        File file = new File(fileName);
        downloadFileUseStream(objectId, file);
    }

    /**
     * download file for gridfs use stream
     *
     * @param objectId
     * @param file
     */
    public static void downloadFileUseStream(String objectId, File file) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            downloadFileUseStream(objectId, os);
        } catch (IOException e) {
            log.info("download fail:" + e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    // skip
                }
            }
        }
    }

    /**
     * 将mongo gridfs的文件下载到内存
     *
     * @param objectId
     * @return
     */
    public static byte[] downloadFileUseStream(String objectId) {
        GridFSBucket bucket = GridFSBuckets.create(getDatabase());
        GridFSDownloadStream stream = null;
        try {
            stream = bucket.openDownloadStream(new ObjectId(objectId));
            /** gridfs file */
            GridFSFile file = stream.getGridFSFile();
            /** chunk size */
            int size = file.getChunkSize();
            int len = (int) file.getLength();
            int readSize = Math.min(len, size);
            byte[] returnBts = new byte[len];
            /** offset num */
            int offset = 0;
            while (len > 0) {
                int tmp;
                if (len > readSize) {
                    tmp = stream.read(returnBts, offset, readSize);
                    offset += tmp;
                } else {
                    tmp = stream.read(returnBts, offset, len);
                }
                len -= tmp;
            }
            return returnBts;
        } finally {
            if (stream != null) stream.close();
        }
    }

    /**
     * delete file from gridfs by objectId
     *
     * @param objectId
     */
    public static void deleteByObjectId(String objectId) {
        GridFSBucket bucket = GridFSBuckets.create(getDatabase());
        bucket.delete(new ObjectId(objectId));
    }

}
