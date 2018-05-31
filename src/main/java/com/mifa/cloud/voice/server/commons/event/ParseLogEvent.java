package com.mifa.cloud.voice.server.commons.event;

import com.mifa.cloud.voice.server.commons.dto.ParseLogDto;
import lombok.Data;

/**
 * @author: songxm
 * @date: 2018/5/18 08:05
 * @version: v1.0.0
 */
@Data
public class ParseLogEvent extends BaseBizEvent<ParseLogDto>{

   public ParseLogEvent(Object source,String contractNo,ParseLogDto desc){
       super(source,contractNo,desc);
   }
}
