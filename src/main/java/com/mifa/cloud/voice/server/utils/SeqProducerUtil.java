package com.mifa.cloud.voice.server.utils;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 序列号生成工具类
 * @author 宋烜明
 */
@Slf4j
public final class SeqProducerUtil {

    /** 服务器IP后三位(不足左补0) */
    private static String ipNo;

    /** 凭证起始位 */
    private static Integer currentSequence = 0;

    /** 上一次生成凭证号的日期 */
    private static String date;

    private static final BlockingQueue<String> SEQUENCES_QUEUE = new LinkedBlockingQueue<String>(999999);

    private static IdWorker idWorker = new IdWorker();

    public static String getID(){
        if(StringUtils.isBlank(ipNo)){
            String ip = IPUtil.getLocalIP();
            ipNo = ip.substring(ip.lastIndexOf(".")+1,ip.length());
            ipNo = StringUtils.leftPad(ipNo,3,"0");
        }
        return ipNo + idWorker.nextId();
    }

    private static String getSequenceNo(){

        synchronized (SeqProducerUtil.SEQUENCES_QUEUE){
            try{

                String currentDate = BaseDateUtils.getCurrent(BaseDateUtils.YMD);

                if(StringUtils.isBlank(date)){
                    date = currentDate;
                }

                if(!StringUtils.isBlank(date) && !date.equals(currentDate)){

                    currentSequence = 1;
                    date = currentDate;
                    SEQUENCES_QUEUE.clear();
                }

                return generateSequenceNo();


            }catch (Exception e){
                log.error("获取序列号异常:"+e.getMessage(),e);
            }

        }



        return null;

    }

    private static String generateSequenceNo() throws InterruptedException {

        if(SEQUENCES_QUEUE != null && !SEQUENCES_QUEUE.isEmpty()){
            return SEQUENCES_QUEUE.poll(2, TimeUnit.SECONDS);
        }

        String time = BaseDateUtils.getCurrent("HH");
        for(int i =0; i < 1000 ;i++){

            StringBuilder sequence = new StringBuilder();

            sequence.append(date).append(time);

            if(StringUtils.isBlank(ipNo)){
                String ip = IPUtil.getLocalIP();
                ipNo = ip.substring(ip.lastIndexOf(".")+1,ip.length());
                ipNo = StringUtils.leftPad(ipNo,3,"0");
            }

            sequence.append(ipNo);

            currentSequence += 1;

            String seqNo = StringUtils.leftPad(String.valueOf(currentSequence),6,"0");

            sequence.append(seqNo);

            SEQUENCES_QUEUE.put(sequence.toString());
        }


        return SEQUENCES_QUEUE.poll(2, TimeUnit.SECONDS);

    }

    private static String getLastStr(String str, int length){
        if(str.length() > length){
            return str.substring(str.length() - length , str.length());
        }
        return str;
    }
    public static String getContractNo() {
        String seqNo = SeqProducerUtil.getID();
        StringBuilder bf = new StringBuilder();
        String seq = Strings.padStart(seqNo, 15, '0');
        seq = getLastStr(seq, 15);
        return bf.append(Constant.LUCKY_NO).append(seq).toString();
    }

    public static String getAccountNo() {
        String seqNo = SeqProducerUtil.getID();
        StringBuilder bf = new StringBuilder();
        String seq = Strings.padStart(seqNo, 15, '0');
        seq = getLastStr(seq, 15);
        return bf.append(Constant.LUCKY_ACCOUNT_NO).append(seq).toString();
    }
    public static void main(String[] args){
        System.out.println(getContractNo());
        System.out.println(getContractNo());
        System.out.println(getContractNo());

        System.out.println("-------------------------------------------------------");
        System.out.println(getAccountNo());
        System.out.println(getAccountNo());
        System.out.println(getAccountNo());
    }

}
