package edu.feicui.app.phone.biz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 似水流年 on 2017/1/3.
 *
 * @author Nicholas.Lv
 */
public class DataManagement {
    private SQLiteDatabase mysql;
    private static DataManagement dataManagement = null;
    private DataManagement() {
        this.mysql=SQLiteDatabase.openOrCreateDatabase("data"+ File.separator+"data"+File.separator+"edu.feicui.app.phone"
                +File.separator+"files"+File.separator+"mysqlite.db",null);
    }

    public synchronized static DataManagement getInstance() {
        if (dataManagement == null) {
            dataManagement = new DataManagement();
        }
        return dataManagement;
    }

    public ArrayList<String> getTelMgrData() {
        ArrayList<String> telMgrData=new ArrayList<String>();
        int i=0;
        Cursor c = mysql.query("classlist", new String[]{"name"}, null, null, null, null, null);
        while (c.moveToNext()) {
            telMgrData.add(c.getString(0));
        }
        return telMgrData;
    }
    public ArrayList<Map<String,String>> getTelList(int select){
        Cursor c=mysql.rawQuery("select name,number from table"+String.valueOf(select+1)+";",null);
        ArrayList<Map<String,String>> telMgrList=new ArrayList<Map<String, String>>();
        while (c.moveToNext()){
            Map<String,String> map=new HashMap<String, String>();
            String str1=c.getString(0);
            map.put("0",str1);
            String str2=c.getString(1);
            map.put("1",str2);
            telMgrList.add(map);
        }
        return telMgrList;
    }


//    private void fileHandle(final File file){
//    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
//            @Override
//     public void onClick(DialogInterface dialog, int which) {
//               // 打开文件
//                if (which == 0){
//                    openFile(file);
//                    }
//                //修改文件名
//                    else if(which == 1){
//                         LayoutInflater factory = LayoutInflater.from(MainActivity.this);
//                         view = factory.inflate(R.layout.rename_dialog, null);
//                         editText = (EditText)view.findViewById(R.id.editText);
//                         editText.setText(file.getName());
//                         DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
//                              @Override
//                              public void onClick(DialogInterface dialog, int which) {
//                                   // TODO Auto-generated method stub
//                                   String modifyName = editText.getText().toString();
//                                   final String fpath = file.getParentFile().getPath();
//                                   final File newFile = new File(fpath + "/" + modifyName);
//                                   if (newFile.exists()){
//                                        //排除没有修改情况
//                                        if (!modifyName.equals(file.getName())){
//                                             new AlertDialog.Builder(MainActivity.this)
//                                             .setTitle("注意!")
//                                             .setMessage("文件名已存在，是否覆盖？")
//                                             .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                                  @Override
//                                                  public void onClick(DialogInterface dialog, int which) {
//                                                       if (file.renameTo(newFile)){
//                                                            showFileDir(fpath);
//                                                            displayToast("重命名成功！");
//                                                           }
//                                                       else{
//                                                            displayToast("重命名失败！");
//                                                           }
//                                                      }
//                                                 })
//                                             .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                                  @Override
//                                                  public void onClick(DialogInterface dialog, int which) {
//                                                      }
//                                                 })
//                                             .show();
//                                            }
//                                       }
//                                   else{
//                                        if (file.renameTo(newFile)){
//                                             showFileDir(fpath);
//                                             displayToast("重命名成功！");
//                                            }
//                                        else{
//                                             displayToast("重命名失败！");
//                                            }
//                                       }
//                                  }
//                             };
//                         AlertDialog renameDialog = new AlertDialog.Builder(MainActivity.this).create();
//                         renameDialog.setView(view);
//                         renameDialog.setButton("确定", listener2);
//                         renameDialog.setButton2("取消", new DialogInterface.OnClickListener() {
//                              @Override
//                              public void onClick(DialogInterface dialog, int which) {
//                                   // TODO Auto-generated method stub
//                                  }
//                             });
//                         renameDialog.show();
//                        }
//                    //删除文件
//                    else{
//                         new AlertDialog.Builder(MainActivity.this)
//                         .setTitle("注意!")
//                         .setMessage("确定要删除此文件吗？")
//                         .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                              @Override
//                              public void onClick(DialogInterface dialog, int which) {
//                                   if(file.delete()){
//                                        //更新文件列表
//                                        showFileDir(file.getParent());
//                                        displayToast("删除成功！");
//                                       }
//                                   else{
//                                        displayToast("删除失败！");
//                                       }
//                                  }
//                             })
//                         .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                              @Override
//                              public void onClick(DialogInterface dialog, int which) {
//                                  }
//                             }).show();
//                        }
//                   }
//              };
//          //选择文件时，弹出增删该操作选项对话框
//          String[] menu = {"打开文件","重命名","删除文件"};
//          new AlertDialog.Builder(MainActivity.this)
//          .setTitle("请选择要进行的操作!")
//          .setItems(menu, listener)
//          .setPositiveButton("取消", new DialogInterface.OnClickListener() {
//               @Override
//               public void onClick(DialogInterface dialog, int which) {
//                   }
//              }).show();
//         }
}
