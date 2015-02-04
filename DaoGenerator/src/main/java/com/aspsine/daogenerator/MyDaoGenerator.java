package com.aspsine.daogenerator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by Aspsine on 2015/2/4.
 * <p/>
 * Daos will be generated in {@link ../app/src/main/java-gen/com/aspsine/bluechat.greendao} folder
 * automatically after Run module DaoGenerator's run task.
 * <p/>
 */
public class MyDaoGenerator {
    private static final int DB_VERSION = 1;

    private static final String OUT_DIR = "../app/src/main/java-gen";

    private static final String PACKAGE_DAO = "com.aspsine.bluechat.greendao";

    private static final String PACKAGE_MODEL = "com.aspsine.bluechat.model";

    /**
     * @param args build.gradle outputDir
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(DB_VERSION, PACKAGE_MODEL);
        schema.setDefaultJavaPackageDao(PACKAGE_DAO);
        schema.enableKeepSectionsByDefault();
        addNotice(schema);

        new DaoGenerator().generateAll(schema, OUT_DIR);
    }

    private static void addNotice(Schema schema){
        Entity notice = schema.addEntity("Notice");
        notice.addIdProperty();
        notice.addDateProperty("time");
        notice.addIntProperty("type");
        notice.addStringProperty("message");
    }


}
