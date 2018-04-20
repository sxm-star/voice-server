package com.mifa.cloud.voice.server.commons.enums;

/**
 * Created by Administrator on 2018/4/20.
 * 系统字典枚举
 */
public enum DictionaryEnums {

    WEBSITE("WEBSITE", "网站", "PROFESSION"),
    APP("APP", "移动app", "PROFESSION"),
    IT("IT", "IT与软件开发", "PROFESSION"),
    NEWS_MEDIA("NEWS_MEDIA", "新闻媒体", "PROFESSION"),
    SOCIAL_COMMUNICATION("SOCIAL_COMMUNICATION", "通讯社交", "PROFESSION"),
    E_BUSINESS("E_BUSINESS", "电子商务", "PROFESSION"),
    GAME("GAME", "游戏", ""),
    AUDIO_VIDEO("AUDIO_VIDEO", "音视频", "PROFESSION"),
    FINANCE("FINANCE", "金融", "PROFESSION"),
    EDUCATION("EDUCATION", "教育", "PROFESSION"),
    MEDICAL("MEDICAL", "医疗健康", "PROFESSION"),
    TRAVEL("TRAVEL", "旅游", "PROFESSION"),
    IOT("IOT", "物联网", "PROFESSION"),
    IOV("IOV", "汽车/车联网", "PROFESSION"),
    O2O("O2O", "O2O", "PROFESSION"),
    NEW_ENERGY("NEW_ENERGY", "电力/新能源", "PROFESSION"),
    TRANSPORTATION("TRANSPORTATION", "交通运输", "PROFESSION"),
    MANUFACTURING("MANUFACTURING", "生产制造", "PROFESSION"),
    CONSTRUCTION("CONSTRUCTION", "建筑/地产", "PROFESSION"),
    INSTITUTION("INSTITUTION", "政府/事业单位", "PROFESSION"),
    GENE("GENE", "基因", "PROFESSION"),
    TOBACCO("TOBACCO", "新零售/烟草业", "PROFESSION"),
    LOGISTICS("LOGISTICS", "物流邮政", "PROFESSION"),
    MNO("MNO", "运营商", "PROFESSION"),
    ENERGY_HEAVY_INDUSTRY("ENERGY_HEAVY_INDUSTRY", "能源重工", "PROFESSION"),
    CITY_SERVICE("CITY_SERVICE", "公共事业/城市服务", "PROFESSION"),
    ELSE("ELSE", "其他", "PROFESSION"),
    WITHIN_THREE_YEARS("WITHIN_THREE_YEARS", "3年以内", "BUSINESSLIFE"),
    WITHIN_FIVE_YEARS("WITHIN_FIVE_YEARS", "5年以内", "BUSINESSLIFE"),
    WITHIN_TEN_YEARS("WITHIN_TEN_YEARS", "10年以内", "BUSINESSLIFE"),
    ABOVE_TEN_YEARS("ABOVE_TEN_YEARS", "10年以上", "BUSINESSLIFE"),
    WITHIN_HUNDRED_PEOPLE("WITHIN_HUNDRED_PEOPLE", "100人以下", "SCALE"),
    HUNDRED_THREEHUNDRED_PEOPLE("HUNDRED_THREEHUNDRED_PEOPLE", "100人-300人", "SCALE"),
    THREEHUNDRED_FIVEHUNDRED_PEOPLE("THREEHUNDRED_FIVEHUNDRED_PEOPLE", "300人-500人", "SCALE"),
    WITHIN_FIVEHUNDRED_PEOPLE("WITHIN_FIVEHUNDRED_PEOPLE", "500人以上", "SCALE");

    private String key;

    private String value;

    /**
     * 业务类型
     */
    private String biz_type;


    DictionaryEnums(String key, String value, String biz_type) {
        this.key = key;
        this.value = value;
        this.biz_type = biz_type;
    }
}
