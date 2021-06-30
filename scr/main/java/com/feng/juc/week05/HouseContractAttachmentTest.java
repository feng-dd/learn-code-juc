package com.feng.juc.week05;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:购房合同附件测试类
 **/
public class HouseContractAttachmentTest {

    public static void main(String[] args) {
        HouseContractAttachmentProcessor attachmentProcessor = new HouseContractAttachmentProcessor();

        // 初始化
        // 1.创建了上传文件的管道
        // 2.启动构建索引的线程
        attachmentProcessor.init();

        // 上传第一份合同
        HouseContractFile firstFile = new HouseContractFile();
        firstFile.setBuyerName("zhangsan");
        firstFile.setSellName("lisi");
        firstFile.setFile("购房合同01文件".getBytes());
        firstFile.setFileName("购房合同01");
        attachmentProcessor.uploadHouseContractAttachment(firstFile);

        // 上传第二份合同
        HouseContractFile secondFile = new HouseContractFile();
        secondFile.setBuyerName("zhangsan");
        secondFile.setSellName("lisi");
        secondFile.setFile("购房合同02文件".getBytes());
        secondFile.setFileName("购房合同02");
        attachmentProcessor.uploadHouseContractAttachment(secondFile);

        // 终止
        attachmentProcessor.shutdown();
    }
}