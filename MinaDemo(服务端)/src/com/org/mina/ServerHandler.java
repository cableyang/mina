package com.org.mina;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.org.dao.FileUpdataDao;
import com.org.dao.impl.FileUpdataDaoImpl;
import com.org.entity.FileData;


public class ServerHandler extends IoFilterAdapter implements IoHandler {

	
	private FileUpdataDao impl = new FileUpdataDaoImpl();
	
private static ServerHandler samplMinaServerHandler = null;     
    
    public static ServerHandler getInstances() {     
        if (null == samplMinaServerHandler) {     
            samplMinaServerHandler = new ServerHandler();     
        }     
        return samplMinaServerHandler;     
    }     
    
    private ServerHandler() {     
    
    }

	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
		System.out.println("exceptionCaught");
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println("messageReceived");
		
		System.out.println("message->"+(String)message);
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		System.out.println("messageSent");
	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {
		System.out.println("sessionClosed");
	}

	@Override
	public void sessionCreated(IoSession arg0) throws Exception {
		System.out.println("sessionCreated");
	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
		System.out.println("sessionIdle");
	}

	@Override
	public void sessionOpened(final IoSession session) throws Exception {
		System.out.println("sessionOpened");
		//不停访问数据库某个表
//	     Thread thread = new Thread(new myThread(session));   
//	     thread.start();
		
		session.write("服务端向客户端发送来消息：hello!");
	}     
    
	
	
	
    public class myThread implements Runnable{
    	private IoSession session;
    	public myThread(IoSession session){
    		this.session = session;
    	}
    	
		@Override
		public void run() {
			
			while(true){
			try {
				String hql = "from FileData";
		        List<FileData> datalist = impl.getList(hql);
				
				JSONObject Jsonobject = new JSONObject();
				JSONArray JsonArray = new JSONArray();
				for(int i = 0;i<datalist.size();i++)
				{
					FileData fileData = (FileData)datalist.get(i);
					JSONObject object = new JSONObject();
					object.put("info_no", fileData.getInfoNo());
					object.put("File_Type_name", fileData.getFileTypeName());
					object.put("Insert_Operator", fileData.getInsertOperator());
					JsonArray.put(object);
				}
				
				Jsonobject.put("FileData", JsonArray);
				
				session.write(Jsonobject.toString());
				
				System.out.println(Jsonobject.toString());
				
				Thread.sleep(3000);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			}
		}
    	
    }
	
}
