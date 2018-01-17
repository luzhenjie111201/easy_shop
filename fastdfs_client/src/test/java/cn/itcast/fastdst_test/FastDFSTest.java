package cn.itcast.fastdst_test;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

public class FastDFSTest {
	
	/**
	 * 以下代码目的：将文件上传到FasDFS,获取URL
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		//读取追踪服务器配置文件
		String trackerConfig = ClassLoader.getSystemResource("tracker.conf").getPath();
		//设置追踪服务器地址
		ClientGlobal.init(trackerConfig);
		
		//创建追踪客户端
		TrackerClient trackerClient = new TrackerClient();
		//获取追踪服务器
		TrackerServer trackerServer = trackerClient.getConnection();
		
		//创建存储服务器
		StorageServer storageServer = null;	
		//创建存储客户端
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		
		//上传文件
		/**
		 * 参数一:文件路径
		 * 参数二:文件后缀
		 * 参数三:文件信息
		 */
		String[] fileInfos = storageClient.upload_file("D:\\Users\\Julie\\Desktop\\系统管理模块表设计.jpg", "jpg", null);
		
		//获取上传文件的文件信息
		if(fileInfos != null && fileInfos.length > 0) {
			for (String string : fileInfos) {
				System.out.println(string);
				//第一个值是存储服务器组名
				//第二个值是文件存储相对路径
			}
		}
		String groupName = fileInfos[0];
		String pathName = fileInfos[1];
		
		//获取存储服务器信息（IP和端口）
		ServerInfo[] serverInfos = trackerClient.getFetchStorages(trackerServer,groupName,pathName);
		
		for (ServerInfo serverInfo : serverInfos) {
			System.out.println(serverInfo.getIpAddr()+":"+serverInfo.getPort());
		}
		
		//拼接最终URL
		String url = "http://"+serverInfos[0].getIpAddr()+"/"+groupName+"/"+pathName;
		System.out.println(url);
	}

}
