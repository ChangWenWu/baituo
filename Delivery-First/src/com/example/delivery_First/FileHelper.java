package com.example.delivery_First; 
import java.io.DataOutputStream; 
import java.io.File; 
import java.io.FileOutputStream; 
import java.io.FileWriter; 
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import java.io.IOException; 
import android.content.Context; 
import android.os.Environment; 
public class FileHelper { 
private Context context; 
/** SD���Ƿ����**/ 
private boolean hasSD = false; 
/** SD����·��**/ 
public String SDPATH; 
/** ��ǰ�������·��**/ 
private String FILESPATH; 
public FileHelper(Context context) { 
this.context = context; 
hasSD = Environment.getExternalStorageState().equals( 
android.os.Environment.MEDIA_MOUNTED); 
SDPATH = Environment.getExternalStorageDirectory().getPath(); 
FILESPATH = this.context.getFilesDir().getPath(); 
} 
/** 
* ��SD���ϴ����ļ� 
* 
* @throws IOException 
*/ 
public File createSDFile(String fileName) throws IOException { 
File file = new File(SDPATH + "//" + fileName); 
if (!file.exists()) { 
file.createNewFile(); 
} 
return file; 
} 
/** 
* ɾ��SD���ϵ��ļ� 
* 
* @param fileName 
*/ 
public boolean deleteSDFile(String fileName) { 
File file = new File(SDPATH + "//" + fileName); 
if (file == null || !file.exists() || file.isDirectory()) 
return false; 
return file.delete(); 
} 
/** 
* д�����ݵ�SD���е�txt�ı��� 
* strΪ���� 
*/ 
public void writeSDFile(String str,String fileName) 
{ 
try { 
FileWriter fw = new FileWriter(SDPATH + "//" + fileName); 
File f = new File(SDPATH + "//" + fileName); 
fw.write(str); 
FileOutputStream os = new FileOutputStream(f); 
DataOutputStream out = new DataOutputStream(os); 
out.writeShort(2); 
out.writeUTF(""); 
System.out.println(out); 
fw.flush(); 
fw.close(); 
System.out.println(fw); 
} catch (Exception e) { 
} 
} 
/** 
* ��ȡSD�����ı��ļ� 
* 
* @param fileName 
* @return 
*/ 
public String readSDFile(String fileName) { 
StringBuffer sb = new StringBuffer(); 
File file = new File(SDPATH + "//" + fileName); 
try { 
FileInputStream fis = new FileInputStream(file); 
int c; 
while ((c = fis.read()) != -1) { 
sb.append((char) c); 
} 
fis.close(); 
} catch (FileNotFoundException e) { 
e.printStackTrace(); 
} catch (IOException e) { 
e.printStackTrace(); 
} 
return sb.toString(); 
} 
public String getFILESPATH() { 
return FILESPATH; 
} 
public String getSDPATH() { 
return SDPATH; 
} 
public boolean hasSD() { 
return hasSD; 
} 
} 



