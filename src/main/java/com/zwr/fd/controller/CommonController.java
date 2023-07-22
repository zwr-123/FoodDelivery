package com.zwr.fd.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zwr.fd.common.R;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/common")
public class CommonController {
	@Value("${uploadFile.path}")
	String basePath;
	
	@PostMapping("/upload")
	public R<String> upload(@RequestPart("file") MultipartFile file){
		String originalFilename = file.getOriginalFilename();
		String suffix=originalFilename.substring(originalFilename.lastIndexOf("."));
		String realName=UUID.randomUUID().toString()+suffix;
		File file2 = new File(basePath);
		if(!file2.exists()) {
			file2.mkdirs();
		}
		try {
			file.transferTo(new File(basePath, realName));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return R.success(realName);
	}
	
	/**
	 * 上传图片成功后，回显（下载）图片
	 * C:\\Users\\ZW\\Desktop\\rjwmDLafc5c83e-c1e0-45df-ba91-776ec5822f62.png
	 * @param name
	 * @param res
	 */
	@GetMapping("/download")
	public void download(String name,HttpServletResponse res) {
		res.setContentType("image/jpeg");
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));
			ServletOutputStream outputStream = res.getOutputStream();
			int len=0; 
			byte sz[]=new byte[1024];
			while((len=fileInputStream.read(sz))!=-1) {
				outputStream.write(sz, 0, len);
				outputStream.flush();
			}
			fileInputStream.close();
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
