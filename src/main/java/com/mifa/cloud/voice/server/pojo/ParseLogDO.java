package com.mifa.cloud.voice.server.pojo;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author 
 */
@Table(name="t_parse_log")
@Data
public class ParseLogDO extends BaseDataDO {
    /**
     * 主键ID号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 解析的文件ID
     */
    private Long parseFileId;

    /**
     * 解析的文件名 冗余
     */
    private String parseFileName;

    /**
     * 解析状态 1:成功；2:失败；3:运行中
     */
    private String parseStatus;

    /**
     * 解析时间
     */
    @NotEmpty
    private Date parseTime;


}