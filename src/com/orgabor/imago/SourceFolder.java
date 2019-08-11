package com.orgabor.imago;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SourceFolder extends File {

    public SourceFolder(String pathname) {
        super(pathname);
    }

    //copies all files from source to destination folder
    public boolean copyImgs(String destFolderPathname, String newFileName) {
        int serialNumber = 1;
        for (File fileEntry : this.listFiles()) {
            //checks if entry is image file and skips if not
            if (fileEntry.isFile()) {
                if (readImg(fileEntry) == null) {
                    continue;
                }
            } else {
                continue;
            }

            BufferedImage bImg = readImg(fileEntry);
            File copy = new File(destFolderPathname,
                    nameFile(fileEntry, newFileName, serialNumber));
            try {
                ImageIO.write(bImg, getExtension(fileEntry), copy);
            } catch (IOException e) {
                e.printStackTrace();
            }
            serialNumber++;
        }
        System.out.println((serialNumber - 1) + " kép másolása befejeződött");
        return true;
    }

    //reads image file and returns BufferedImage
    private BufferedImage readImg(File file) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }

    //names the new file with numbering and extension
    private String nameFile(File original, String fileName, int serialNumber) {
        String number = "_" + String.format("%05d", serialNumber);
        String name = fileName + number + "." + getExtension(original);
        return name;
    }

    //gets file extension
    private String getExtension(File file) {
        String fileName = file.getName();
        String extension = fileName.substring((fileName.length() - 3), fileName.length());
        return extension;
    }

}