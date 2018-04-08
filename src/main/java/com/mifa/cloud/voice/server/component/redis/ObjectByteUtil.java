package com.mifa.cloud.voice.server.component.redis;


import org.apache.log4j.Logger;

import java.io.*;

public class ObjectByteUtil implements Serializer<Object> {
    
    private static final Logger log = Logger.getLogger(ObjectByteUtil.class);
	
	public static byte[] objectToBytes(Object s){
		if(s==null){
			return null;
		}
		if(!(s instanceof Externalizable)){
			log.debug("序列化对象未实现Externalizable接口，对象类型为:"+s.getClass().getName());
		}
        // 序列化后数据流给ByteArrayOutputStream 来保存。
        // ByteArrayOutputStream 可转成字符串或字节数组
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        byte[] b = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(s);
			b = baos.toByteArray();
			oos.close();
			baos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(oos!=null){
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(baos!=null){
				try {
					baos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        return b;
	}
	
	public static Object bytesToObject(byte[] b){
		if(b==null){
			return null;
		}
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
        Object obj = null;
		try {
			bais = new ByteArrayInputStream(b);
			ois = new ObjectInputStream(bais);
			obj = ois.readObject();
			ois.close();
			bais.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(ois!=null){
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(bais!=null){
				try {
					bais.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        return obj;
	}

    @Override
    public byte[] toBytes(Object object) {
        return objectToBytes(object);
    }

    @Override
    public Object toObject(byte[] bytes) {
        return bytesToObject(bytes);
    }
	

}
