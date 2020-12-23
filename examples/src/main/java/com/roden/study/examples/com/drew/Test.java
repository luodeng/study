package com.roden.study.examples.com.drew;

/**
 * Created by Roden on 2016/7/6.
 */
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;

/**
 * @author wyyl1
 */
public class Test {

    /**
     * 图片数据元提取
     */
    public static void metadataExtractor(){
        //File jpegFile = new File("G:\\BaiduYunDownload\\2016-07-03 143102.jpg");
        File jpegFile = new File("E:\\DCIM\\2016\\IMG_0601.JPG");
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(jpegFile);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    System.out.println(tag);
                }
            }
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        metadataExtractor();
    }
}
