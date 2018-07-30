package com.company;

import javax.swing.*;
import java.io.File;
        import java.io.FilenameFilter;
        import java.io.IOException;
        import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Main {

    static class MyFileFilter implements FilenameFilter {
        private String ext;

        public MyFileFilter(String ext) {
            this.ext = ext;
        }

        public boolean accept(File dir, String name) {
            return name.endsWith(ext);
        }
    }

    private static void findFilesExtList(String srcPath, String[] extlist, ArrayList<String> list) {
        try {
            File dir = new File(srcPath);
            for(int j=0; j<extlist.length;j++){
                String extt = extlist[j];
                File[] files = dir.listFiles(new MyFileFilter(extt));
                for (int i = 0; i < files.length; i++) {
                    list.add(srcPath + files[i].getName());
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
//        String[] extlist ={".txt", ".java",".class",".jar"};
        String[] extlist ={".txt", ".doc"};
        File str_dir = new File("e:\\Games\\testFor\\");
        File str_file = new File("e:\\Games\\testFor\\file viewmodel.txt\\");
        try {
            findFilesExtList("e:\\Games\\testFor\\", extlist, list);
            list2 = searching(str_dir);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String s : list)
            System.out.println(s);

        System.out.println("second part");
        for(String s:list2){
            System.out.println(s);
        }
        System.out.println("New part");
        String path = "e:\\Games\\testFor\\";
        try{

        Runtime.getRuntime().exec("explorer.exe /select," + path);

        }catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("File choose method");
        fileChoose();
        //file open
//        openFile(str_file);
    }
    private static List<String> searching(File rootDir) {
        List<String> result = new ArrayList<>();

        LinkedList<File> dirList = new LinkedList<>();
        if (rootDir.isDirectory()) {
            dirList.addLast(rootDir);
        }

        while (dirList.size() > 0) {
            File[] filesList = dirList.getFirst().listFiles();
            if (filesList != null) {
                for (File path : filesList) {
                    if (path.isDirectory()) {
                        dirList.addLast(path);
                    } else {
                        String simpleFileName = path.getName();

                        if (simpleFileName.endsWith(".txt")) {
                            result.add(path.getAbsolutePath().toString());
                        }
                    }
                }
            }
            dirList.removeFirst();
        }
        return result;
    }

//    file open with winexplorer

    public static void fileChoose(){
        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File selectedFile = chooser.getSelectedFile();
            String name = selectedFile.getName();
            System.out.println(name);
        }
    }

    public static void openFile(File file) {
    try {
        String osName = System.getProperty("os.name");
        String[] cmd = new String[3];

        if (osName.equals("Windows 95")) {
            cmd[0] = "command.com";
        } else {
            cmd[0] = "cmd.exe";
        }
        cmd[1] = "/C";
        cmd[2] = file.getAbsolutePath();
        Runtime rt = Runtime.getRuntime();
        rt.exec(cmd);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
    }

    /**
      * Opens windows directory in explorer
      * @param dir Directory to open
      * @author Cloud
      */
    public static void openDir(File dir) {
    try {
        String[] cmd = new String[2];
        cmd[0] = "explorer.exe";
        cmd[1] = dir.getAbsolutePath();
        Runtime rt = Runtime.getRuntime();
        rt.exec(cmd);

    } catch (IOException ex) {
        ex.printStackTrace();
    }
    }
}
