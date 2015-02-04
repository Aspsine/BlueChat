package com.aspsine.daogenerator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by Aspsine on 2015/2/4.
 */
public class MyDaoGenerator {
    private static final int DB_VERSION = 1;
    private static final String PACKAGE_DAO = "com.aspsine.bluechat.dao";
    private static final String OUT_DIR = "../app/src/main/java-gen";


    public static void main(String[] args) throws Exception{
        Schema schema = new Schema(DB_VERSION, PACKAGE_DAO);

        addDaos(schema);

        new DaoGenerator().generateAll(schema, OUT_DIR);
    }

    private static void addDaos(Schema schema){

    }

}
