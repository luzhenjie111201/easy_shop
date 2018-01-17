package com.taotao.manage.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.StringUtil;
import com.taotao.manage.vo.PicUploadResult;

@RequestMapping("/pic")
@Controller
public class PicUploadController {
	private static final String[] IMAGE_TYPE = {"jpg","png","gif","bmp","jpeg"};
	
	//将Json转成存文本文件的工具类,目的是兼容其他浏览器
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	
	@RequestMapping(value = "/upload",method = RequestMethod.POST , produces=MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String upload(@RequestParam("uploadFile") MultipartFile multipartFile) throws JsonProcessingException {
		//创建文件上传对象
		PicUploadResult picUploadResult = new PicUploadResult();
		picUploadResult.setError(1);
		
		//判断文件是否合法
		boolean isLegal = false;
		
		for(String type : IMAGE_TYPE) {
			//获取原文件名称
			if(multipartFile.getOriginalFilename().lastIndexOf(type) > 0) {
				isLegal =true;
				break;
			}
		}
		
		//如果合法
		if(isLegal) {
			//判断是否为图片，使用图片IO流
			try {
				BufferedImage image = ImageIO.read(multipartFile.getInputStream());
				//设置图片规格
				picUploadResult.setWidth(image.getWidth()+"");
				picUploadResult.setHeight(image.getHeight()+"");
				
				//在这里上传
				//读取追踪服务器配置文件
				String trackerConfig = this.getClass().getClassLoader().getResource("tracker.conf").getPath();
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
				String file_ext_name = StringUtils.substringAfterLast(multipartFile.getOriginalFilename(), ".");
				String[] fileInfos = storageClient.upload_file(multipartFile.getBytes(), file_ext_name, null);
				//获取上传文件的文件信息
				//第一个值是存储服务器组名
				//第二个值是文件存储相对路径
				String groupName = fileInfos[0];
				String pathName = fileInfos[1];
				
				//获取存储服务器信息（IP和端口）
				ServerInfo[] serverInfos = trackerClient.getFetchStorages(trackerServer,groupName,pathName);
				
				//拼接最终URL
				String url = "http://"+serverInfos[0].getIpAddr()+"/"+groupName+"/"+pathName;
				System.out.println(url);
				
				//设置返回结果
				picUploadResult.setError(0);
				picUploadResult.setUrl(url);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return MAPPER.writeValueAsString(picUploadResult);
		
	}
}
