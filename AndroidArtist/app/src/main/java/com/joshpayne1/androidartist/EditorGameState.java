package com.joshpayne1.androidartist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class EditorGameState {

    private Context context;

    private static EditorGameState instance = new EditorGameState();

    public static EditorGameState getInstance(){
        return instance;
    }



    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<String> getFilesWithExt(String ext){
        File[] files = context.getFilesDir().listFiles();
        ArrayList<String> names = new ArrayList<>();
        for(File f: files){
            if(f.isFile() && f.getName().endsWith("." + ext)){
                names.add(f.getName().substring(0, f.getName().lastIndexOf('.')));
            }
        }
        return names;
    }

    public void deleteFile(String name, String ext) {
        context.deleteFile(name+ "." + ext);
    }

    public Bitmap getBitmapImage(String name) {
        File file = new File(context.getFilesDir() + File.separator + name + ".image");
        String filePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        return bitmap;
    }

    public File getFile(String name) {
        File file = new File(context.getFilesDir() + File.separator + name);
        if (file.canRead()) return file;
        return null;
    }


    public String getJSON(String name) {
        FileInputStream ins = null;
        try {
            ins = context.openFileInput(name + ".game");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String jsonString = convertStreamToString(ins);
        return jsonString;
    }
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
