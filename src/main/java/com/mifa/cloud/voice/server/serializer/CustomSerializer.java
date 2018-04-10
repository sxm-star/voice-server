package com.mifa.cloud.voice.server.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mifa.cloud.voice.server.annotation.JsonMosaic;
import com.mifa.cloud.voice.server.utils.BaseJsonUtils;
import com.mifa.cloud.voice.server.utils.BaseStringUtils;

import java.io.IOException;

/**
 * @author 宋烜明
 */
@SuppressWarnings("unchecked")
public class CustomSerializer extends StdSerializer {
    private JsonMosaic jsonMosaic;

    public CustomSerializer(JsonMosaic jsonMosaic) {
        super((Class)null);
        this.jsonMosaic = jsonMosaic;
    }

    @Override
    public void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(obj != null) {
            String text = BaseJsonUtils.writeValue(obj);
            text = BaseStringUtils.mosaic(text, this.jsonMosaic, '*');
            jsonGenerator.writeString(text);
        }
    }
}