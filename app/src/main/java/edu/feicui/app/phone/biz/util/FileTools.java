package edu.feicui.app.phone.biz.util;

import java.io.File;

/**
 * Created by Administrator on 2017/2/15.
 */
public class FileTools {
    private static String[] txtFileTypeSuffix;
    private static String[] videoFileTypeSuffix;
    private static String[] mp3FileTypeSuffix;
    private static String[] imgFileTypeSuffix;
    private static String[] zipFileTypeSuffix;
    private static String apkFileTypeSuffix;

    public static boolean isTxtFile(File file) {
        String fileName = file.getName();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        txtFileTypeSuffix = new String[]{"asc", "c", "css", "dot", "htm", "doc", "dhtml"
                , "cpp", "h", "html", "hts", "java", "jnlp", "js", "pdf", "pps", "pot", "ppt", "rtf", "shtml"
                , "txt", "xht", "xhtm", "xhtml", "xla", "xlm", "xlc", "xls", "xlt", "xlw", "xml", "xsit", "xsl"};
        for (int i = 0; i < txtFileTypeSuffix.length; i++) {
            if (txtFileTypeSuffix[i].equals(fileType)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isVideoFile(File file) {
        String fileName = file.getName();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        videoFileTypeSuffix = new String[]{"rmvb", "3gp", "asf", "asx", "avi", "fvi", "lsf"
                , "lsx", "mng", "mov", "movie", "mp4", "mpe", "mpeg", "mpg", "mpg4", "pvx", "qt", "rv", "swf"
                , "swfl", "vdo", "viv", "vivo", "wav", "wm", "wmv", "wmx", "wv", "wvx"};
        for (int i = 0; i < videoFileTypeSuffix.length; i++) {
            if (videoFileTypeSuffix[i].equals(fileType)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMp3File(File file) {
        String fileName = file.getName();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        mp3FileTypeSuffix = new String[]{"aif", "aifc", "aiff", "als", "au", "awb", "es", "esl"
                , "imy", "it", "itz", "m15", "m3u", "m3url", "ma1", "ma2", "ma3", "ma5", "mdz", "mid", "midi", "mio", "mod"
                , "mp2", "mp3", "mpga", "nsnd", "pac", "pae", "qcp", "ra", "ram", "rm", "rmf", "rmm", "rpm", "s3m", "s3z"
                , "smd", "smz", "snd", "stm", "tsi", "ult", "vib", "vox", "vqe", "vqf", "vql", "wax", "wma", "xm", "xmz"};
        for (int i = 0; i < mp3FileTypeSuffix.length; i++) {
            if (mp3FileTypeSuffix[i].equals(fileType)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isImgFile(File file) {
        String fileName = file.getName();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        imgFileTypeSuffix = new String[]{"xwd", "xpm", "xbm", "wpng", "wi", "wbmp", "toy", "tiff"
                , "tif", "svh", "svg", "svf", "si9", "si7", "si6", "rp", "rgb", "rf", "ras", "qtif", "qti", "ppm", "pnz"
                , "pnm", "png", "pict", "pgm", "pda", "pcx", "pbm", "nokia-op-logo", "nbmp", "mil", "jpz", "jpg", "jpeg"
                , "jpe", "j2k", "ifs", "ifm", "ief", "ico", "gif", "bmp"};
        for (int i = 0; i < imgFileTypeSuffix.length; i++) {
            if (imgFileTypeSuffix[i].equals(fileType)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isZipFile(File file) {
        String fileName = file.getName();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        zipFileTypeSuffix = new String[]{"rar", "nar", "jar", "bz2", "gz", "tar", "taz", "tgz", "zip"};
        for (int i = 0; i < zipFileTypeSuffix.length; i++) {
            if (zipFileTypeSuffix[i].equals(fileType)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isApkFile(File file) {
        String fileName = file.getName();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        apkFileTypeSuffix = "apk";
        if (apkFileTypeSuffix.equals(fileType)) {
            return true;
        }
        return false;
    }
}
