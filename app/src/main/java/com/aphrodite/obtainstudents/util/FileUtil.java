package com.aphrodite.obtainstudents.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Environment;
import android.util.Log;

public class FileUtil {

    public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值
    final private static String TAG = "FileUtil";

    private FileUtil() {}

    public static byte[] getBytesFromFile(File f) {
        if (f == null || !f.exists()) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[(int) f.length()];
            int n;
            while ((n = stream.read(b)) != -1) {
                out.write(b, 0, n);
            }
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }

    public static boolean forceDeleteFile(File file) {
        boolean result = false;
        int tryCount = 0;
        while (!result && tryCount++ < 10) {
            result = file.delete();
            if (!result) {
                try {
                    synchronized (file) {
                        file.wait(200);
                    }
                } catch (InterruptedException e) {
                    Log.e(TAG, e.getMessage() == null ? e.getClass().getName() : e.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * 通过提供的文件名在默认路径下生成文件
     * 
     * @param fileName 文件的名称
     * @return 生成的文件
     * @throws IOException
     */
    public static File createFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 通过提供的文件名在默认路径下生成文件
     * 
     * @param fileName 文件的名称
     * @return 生成的文件
     * @throws IOException
     */
    public static void createFolder(String filePath) {
        filePath = filePath.replace("\\", "/");
        String folderPath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    /**
     * 判断文件是否存在
     * 
     * @param fileName 文件的名称
     * @return 生成的文件
     * @throws IOException
     */
    public static boolean isExistFolder(String filePath) {
        filePath = filePath.replace("\\", "/");
        String folderPath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        File folder = new File(folderPath);
        if (folder.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 删除路径指向的文件
     * 
     * @param fileName 文件的名称
     * @return true删除成功，false删除失败
     */
    public static boolean deleteFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }

    /**
     * 将从下载管理那里获取来的数据流写入文件中
     * 
     * @param ops 从下载管理那里获取来的io流
     * @param fileName 需要存储的文件的路径和名称
     * @return 总共存储成功的字节数
     * @throws SDNotEnouchSpaceException
     * @throws SDUnavailableException
     * @throws IOException
     */
    public static void writeFile(byte[] bytes, String filePath) throws IOException {
        if (bytes != null) {
            RandomAccessFile file = null;
            try {
                file = new RandomAccessFile(createFile(filePath), "rw");
                file.seek(file.length());
                file.write(bytes);
            } catch (IOException e) {
                Log.w(TAG, e.getMessage(), e);
                throw e;
            } finally {
                try {
                    if (file != null) {
                        file.close();
                    }
                } catch (IOException e) {
                    Log.w(TAG, e.getMessage(), e);
                }
            }
        }
    }

    public static void copyFile(String ownerFilePath, String targetFilePath) throws Exception {
        File ownerFile = new File(ownerFilePath);
        File targetFile = new File(targetFilePath);
        if (ownerFile.isFile() && targetFile.createNewFile()) {
            FileInputStream input = new FileInputStream(ownerFile);
            FileOutputStream output = new FileOutputStream(targetFile);
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = input.read(b)) != -1) {
                output.write(b, 0, len);
            }
            output.flush();
            output.close();
            input.close();
        }
    }

    /**
     * 通过提供的文件名在默认路径下生成文件
     * 
     * @param fileName 文件的名称
     * @return 生成的文件
     * @throws IOException
     */
    public static File createNewFile(String filePath) throws IOException {
        String folderPath = filePath.substring(0, filePath.lastIndexOf("/"));
        File folder = getFileByPath(folderPath);
        folder.mkdirs();
        File file = getFileByPath(filePath);
        if (!file.exists()) {
            file.createNewFile();
        } else {
            return createFile(getNextPath(filePath));
        }
        return file;
    }

    private static String getNextPath(String path) {
        Pattern pattern = Pattern.compile("\\(\\d{1,}\\)\\.");
        Matcher matcher = pattern.matcher(path); // 除中文不用外，其他的都要
        String str = null;
        while (matcher.find()) {
            str = matcher.group(matcher.groupCount());
        }
        if (str == null) {
            int index = path.lastIndexOf(".");
            path = path.substring(0, index) + "(1)" + path.substring(index);
        } else {
            int index = Integer.parseInt(str.replaceAll("[^\\d]*(\\d)[^\\d]*", "$1")) + 1;
            path = path.replace(str, "(" + index + ").");
        }
        return path;
    }

    public static File getFileByPath(String filePath) {
        filePath = filePath.replaceAll("\\\\", "/");
        boolean isSdcard = false;
        int subIndex = 0;
        if (filePath.indexOf("/sdcard") == 0) {
            isSdcard = true;
            subIndex = 7;
        } else if (filePath.indexOf("/mnt/sdcard") == 0) {
            isSdcard = true;
            subIndex = 11;
        }

        if (isSdcard) {
            if (isExistSdcard()) {
                File sdCardDir = Environment.getExternalStorageDirectory();// 获取SDCard目录,2.2的时候为:/mnt/sdcard
                                                                           // 2.1的时候为：/sdcard，所以使用静态方法得到路径会好一点。
                String fileName = filePath.substring(subIndex);
                return new File(sdCardDir, fileName);
            } else if (isEmulator()) {
                File sdCardDir = Environment.getExternalStorageDirectory();
                String fileName = filePath.substring(subIndex);
                return new File(sdCardDir, fileName);
            }
            return null;
        } else {
            return new File(filePath);
        }
    }

    public static boolean isExistSdcard() {
        if (!isEmulator()) {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        }
        return true;
    }

    /**
     * 获取文件指定文件的指定单位的大小
     * 
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 转换文件大小,指定转换的类型
     * 
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    /**
     * 获取指定文件夹
     * 
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 获取指定文件大小
     * 
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        FileInputStream fis = null;
        try {
            if (file.exists()) {
                fis = new FileInputStream(file);
                size = fis.available();
            } else {
                file.createNewFile();
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            //
            if (fis != null) {
                //
                fis.close();
            }
        }

        return size;
    }

    private static boolean isEmulator() {
        return android.os.Build.MODEL.equals("sdk");
    }
}
