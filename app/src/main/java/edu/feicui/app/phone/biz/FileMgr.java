package edu.feicui.app.phone.biz;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import edu.feicui.app.phone.biz.util.FileTools;

/**
 * Created by Administrator on 2017/2/14.
 */
public class FileMgr {
    public static File mInternalDirectory;
    public static File mSDcardDirectory;

    static {
        if (StorageMent.getExternalIsMounted()) {
            mSDcardDirectory = Environment.getExternalStorageDirectory().getAbsoluteFile();
            Log.i("################","mSDcardDirectory========"+mSDcardDirectory);
        } else {
            mSDcardDirectory = null;
        }
        mInternalDirectory = Environment.getDataDirectory().getAbsoluteFile();
        Log.i("################","mInternalDirectory========"+mInternalDirectory);
    }

    private static FileMgr instance = null;

    private FileMgr() {
    }

    public static FileMgr getInstance() {
        if (instance == null) {
            instance = new FileMgr();
        }
        return instance;
    }

   public class FileInfo {
        public File mFile;
        public String mFileType;
        private boolean mSelected;

        public FileInfo(File file, String fileType) {
            this.mFile = file;
            this.mFileType = fileType;
        }

        public void setSelected(boolean selected) {
            this.mSelected = selected;
        }

        public boolean getSelected() {
            return mSelected;
        }
    }

    public interface SearchFileListener {
        void searching(String fileType);

        void end(boolean isException);
    }

    private SearchFileListener listener;

    public void setSearchFileListener(SearchFileListener listener) {
        this.listener = listener;
    }

    private void callIsSearching(String fileType) {
        if (listener != null) {
            listener.searching(fileType);
        }
    }

    private void callEnd(boolean isException) {
        if (listener != null) {
            listener.end(isException);
        }
    }

    private ArrayList<FileInfo> mAllFileList = new ArrayList<FileInfo>();
    private long mAllFileListSize;
    private ArrayList<FileInfo> mTxtFileList = new ArrayList<FileInfo>();
    private long mTxtFileListSize;
    private ArrayList<FileInfo> mVideoFileList = new ArrayList<FileInfo>();
    private long mVideoFileListSize;
    private ArrayList<FileInfo> mMp3FileList = new ArrayList<FileInfo>();
    private long mMp3FileListSize;
    private ArrayList<FileInfo> mImgFileList = new ArrayList<FileInfo>();
    private long mImgFileListSize;
    private ArrayList<FileInfo> mZipFileList = new ArrayList<FileInfo>();
    private long mZipFileListSize;
    private ArrayList<FileInfo> mApkFileList = new ArrayList<FileInfo>();
    private long mApkFileListSize;

    private boolean isStopSearch;

    public void setIsStopSearch() {
        this.isStopSearch = true;
    }

    public ArrayList<FileInfo> getAllFileList() {
        return mAllFileList;
    }

    public ArrayList<FileInfo> getTxtFileList() {
        return mTxtFileList;
    }

    public ArrayList<FileInfo> getVideoFileList() {
        return mVideoFileList;
    }

    public ArrayList<FileInfo> getMp3FileList() {
        return mMp3FileList;
    }

    public ArrayList<FileInfo> getImgFileList() {
        return mImgFileList;
    }

    public ArrayList<FileInfo> getZipFileList() {
        return mZipFileList;
    }

    public ArrayList<FileInfo> getApkFileList() {
        return mApkFileList;
    }

    public long getAllCanSeeFileListSize() {
        return mTxtFileListSize+mVideoFileListSize+mMp3FileListSize+mImgFileListSize+mZipFileListSize+mApkFileListSize;
    }

    public long getAllFileListSize() {
        return mAllFileListSize;
    }

    public long getTxtFileListSize() {
        return mTxtFileListSize;
    }

    public long getVideoFileListSize() {
        return mVideoFileListSize;
    }

    public long getMp3FileListSize() {
        return mMp3FileListSize;
    }

    public long getImgFileListSize() {
        return mImgFileListSize;
    }

    public long getZipFileListSize() {
        return mZipFileListSize;
    }

    public long getApkFileListSize() {
        return mApkFileListSize;
    }

    public void initData() {
        isStopSearch = false;
        mAllFileListSize = 0;
        mTxtFileListSize = 0;
        mVideoFileListSize = 0;
        mMp3FileListSize = 0;
        mImgFileListSize = 0;
        mZipFileListSize = 0;
        mApkFileListSize = 0;
        mAllFileList.clear();
        mTxtFileList.clear();
        mVideoFileList.clear();
        mMp3FileList.clear();
        mImgFileList.clear();
        mZipFileList.clear();
        mApkFileList.clear();
    }

    private void searchFile(File file, boolean isLastTask) {
        if (isStopSearch) {
            if (isLastTask) {
                callEnd(true);
            }
            return;
        }
        if (file == null || !file.canRead() || !file.exists()||file.length()<=0) {
            return;
        }
        if (!file.isDirectory()) {
            if (file.length() <= 0) {
                return;
            }
            if (!file.getName().contains(".")) {
                return;
            }
            String name = file.getName();
            String fileType = name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase();
            FileInfo fileInfo = new FileInfo(file, fileType);
            mAllFileList.add(fileInfo);
            mAllFileListSize += file.length();
            if (FileTools.isTxtFile(file)) {
                mTxtFileList.add(fileInfo);
                mTxtFileListSize += file.length();
            } else if (FileTools.isVideoFile(file)) {
                mVideoFileList.add(fileInfo);
                mVideoFileListSize += file.length();
            } else if (FileTools.isMp3File(file)) {
                mMp3FileList.add(fileInfo);
                mMp3FileListSize += file.length();
            } else if (FileTools.isImgFile(file)) {
                mImgFileList.add(fileInfo);
                mImgFileListSize += file.length();
            } else if (FileTools.isZipFile(file)) {
                mZipFileList.add(fileInfo);
                mZipFileListSize += file.length();
            } else if (FileTools.isApkFile(file)) {
                mApkFileList.add(fileInfo);
                mApkFileListSize += file.length();
            }
            callIsSearching(fileType);
            return;
        }
        File[] files = file.listFiles();
        if (files == null || files.length <= 0) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            searchFile(files[i], false);
        }
        if (isLastTask) {
            callEnd(false);
        }
    }

    public void searchAll() {
        if (mAllFileList == null || mAllFileList.size() <= 0) {
            initData();
            searchFile(mInternalDirectory, false);
            searchFile(mSDcardDirectory, true);
        } else {
            callEnd(true);
        }
    }
    public void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; ++i) {
                    deleteFile(files[i]);
            }
            }
        }
        file.delete();
    }
    public void deleteSeletedFile(ArrayList<FileInfo> fileInfos){
        if(fileInfos!=null&&fileInfos.size()>=0){
            for(int i=0;i<fileInfos.size();i++){
                FileInfo info=fileInfos.get(i);
                if(info.mSelected){
                    deleteFile(info.mFile);
                    fileInfos.remove(i);
                }
            }
        }
    }
}
