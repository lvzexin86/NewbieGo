package edu.feicui.app.phone.biz;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/**
 * Created by Administrator on 2017/2/9.
 */
public class StorageMent {
    static StatFs sfInternal=new StatFs(Environment.getDataDirectory().getPath());
    static StatFs sfSDcard=new StatFs(Environment.getExternalStorageDirectory().getPath());
    public static long getSDcardTotalStorageSpace(){
        if(getExternalIsMounted()){
            long blockSize=sfSDcard.getBlockSize();
            long blockCount=sfSDcard.getBlockCount();
            long totalSpace=blockSize*blockCount;
            return totalSpace;
        }else {
            Log.i("########################","没有内存卡");
            return 0;
        }
    }
    public static long getInternalTotalStorageSpace(){
        long blockSize=sfInternal.getBlockSize();
        long blockCount=sfInternal.getBlockCount();
        long totalSpace=blockSize*blockCount;
        return totalSpace;
    }
    public static long getSDcardFreeStorageSpace(){
        long blockSize=sfSDcard.getBlockSize();
        long availableBlocks=sfSDcard.getAvailableBlocks();
        long totalSpace=blockSize*availableBlocks;
        Log.i("####################","getSDcardFreeStorageSpace="+totalSpace);
        return totalSpace;
    }
    public static long getInternalFreeStorageSpace(){
        long blockSize=sfSDcard.getBlockSize();
        long availableBlocks=sfSDcard.getAvailableBlocks();
        long totalSpace=blockSize*availableBlocks;
        Log.i("####################","getInternalFreeStorageSpace="+totalSpace);
        return totalSpace;
    }
    public static double getSDandInternalProportion(){
        long SDcardTotal=getSDcardTotalStorageSpace();
        long internalTotal=getInternalTotalStorageSpace();
        if(SDcardTotal==0){
            return 0.003;
        }else {
            long totalSpace=SDcardTotal+internalTotal;
            Log.i("############################","totalSpace="+totalSpace);
            double proportion=((double)internalTotal/totalSpace);
            Log.i("#######################=","getSDandInternalProportion="+proportion);
            return proportion;
        }
    }
    public static double getSDcardFreeProportion(){
        long SDcardFree=getSDcardFreeStorageSpace();
        long SDcardTotal=getSDcardTotalStorageSpace();
        Log.i("###############################","SDcardFree"+SDcardFree);
        Log.i("###############################","SDcardTotal"+SDcardTotal);
        double proportion=(float)SDcardFree/SDcardTotal;
        Log.i("#######################","getSDcardFreeProportion="+proportion);
        return proportion;
    }
    public static double getInternalFreeProportion(){
        long internalFree=getInternalFreeStorageSpace();
        long internalTotal=getInternalTotalStorageSpace();
        Log.i("###############################","internalFree"+internalFree);
        Log.i("###############################","internalTotal"+internalTotal);
        double proportion=(float) internalFree/internalTotal;
        Log.i("#######################","getInternalFreeProportion="+proportion);
        return proportion;
    }
    public static boolean getExternalIsMounted(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
