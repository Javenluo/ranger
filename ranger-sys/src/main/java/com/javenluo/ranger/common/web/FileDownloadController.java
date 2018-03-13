package com.javenluo.ranger.common.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * 
 * @author gulong
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("${adminPath}/common/filedownload")
public class FileDownloadController extends BaseController {

	/**
	 * 从临时文件目录中下载文件
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws IOException
	 */
	@RequiresPermissions("user")
	@RequestMapping("downloadExcel4Tmpdir")
	public void downloadExcel4Tmpdir(HttpServletRequest req ,HttpServletResponse res) throws IOException{
		res.setContentType("application/vnd.ms-excel");
		res.setHeader("Cache-Control", "max-age=0");
		
		String fileName = req.getParameter("table_exportFileName");
		fileName = new String(fileName.getBytes("GBK"), "ISO8859_1");
		
		res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xlsx\"");
		
		String fileId = req.getParameter("fileId");
		
		ServletOutputStream out = null;   
        File file = new File(this.tmpDir+File.separator+fileId);   
  
        try {   
            FileInputStream inputStream = new FileInputStream(file);   
  
            //3.通过response获取ServletOutputStream对象(out)   
            out = res.getOutputStream();   
  
            int b = 0;   
            byte[] buffer = new byte[512];   
            while (b != -1){   
                b = inputStream.read(buffer);   
                //4.写到输出流(out)中   
                out.write(buffer,0,b);   
            }   
            
            inputStream.close();   
        } catch (IOException e) {   
            logger.error("", e);
        }  finally{
        	out.close();   
            out.flush();  
        }
		
	}
	
	
}
