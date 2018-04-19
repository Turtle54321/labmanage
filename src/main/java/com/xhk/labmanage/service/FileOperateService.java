package com.xhk.labmanage.service;

import com.xhk.labmanage.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

/**
 * create by xhk on 2018/4/10
 */
@Service
public class FileOperateService {
    private static Logger logger = LoggerFactory.getLogger(FileOperateService.class);

    /**
     *
     * @param multipartFile
     * @param path
     * @return 访问路径
     * @throws Exception
     */
    public String saveFile(MultipartFile multipartFile, String path) throws Exception{
        //获得原文件名
        String originalFilename = multipartFile.getOriginalFilename();

        System.out.println("originalFilename: "+originalFilename);
        String filename = DateUtil.getCurrentTimeMillis()+originalFilename.substring(originalFilename.indexOf("."));

        //检查该路径对应的目录是否存在. 如果不存在则创建目录
        File dir=new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filePath = path + filename;
        System.out.println("filePath: "+filePath);

        //保存文件
        File dest = new File(filePath);
        if (!(dest.exists())) {
            /*
             * MultipartFile提供了void transferTo(File dest)方法,
             * 将获取到的文件以File形式传输至指定路径.
             */
            multipartFile.transferTo(dest);
        }

        //MultipartFile也提供了其他一些方法, 用来获取文件的部分属性

        //获取文件contentType
        String contentType=multipartFile.getContentType();
        System.out.println("contentType: "+contentType);

        /*
         * 获取name
         * 其实这个name跟上面提到的getFileName值是一样的,
         * 就是Map中Key的值. 即前台页面<input>中name=""
         * 属性. 但是上面的getFileName只是得到这个Map的Key,
         * 而Spring在处理上传文件的时候会把这个值以name属性
         * 记录到对应的每一个文件. 如果需要从文件层面获取这个
         * 值, 则可以使用该方法
         */
        String name=multipartFile.getName();
        System.out.println("name: "+name);

        //获取文件大小, 单位为字节
        long size=multipartFile.getSize();
        System.out.println("size: "+size);
        return filename;
    }
}
