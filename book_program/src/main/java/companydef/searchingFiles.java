package companydef;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 1 on 30.07.2018.
 */
public class SearchingFiles {

    static Scanner scanner = new Scanner(System.in);

    public static void listOfFiles(File str_dir, String[] extList){

        List<String> list = new ArrayList<String>();

        try{
            list = searching(str_dir, extList);
        }catch (Exception e){
            e.printStackTrace();
        }

        for (String str : list){
            System.out.println(str);
        }
    }

    public static void getComputerInfo(){
        File[] pathDisk;
        FileSystemView systemView = FileSystemView.getFileSystemView();

        pathDisk = File.listRoots();

        for (File path : pathDisk){
            System.out.println("Drives " + path);
            System.out.println("Description " + systemView.getSystemTypeDescription(path));

            System.out.println("Adds");
            System.out.println(systemView.getSystemDisplayName(path));
        }
    }

    public static List<String> searching(File searchDir, String[] extList) {
        List<String> result = new ArrayList<String>();

        LinkedList<File> dirList = new LinkedList<File>();
        if (searchDir.isDirectory()) {
            dirList.addLast(searchDir);
        }

        while (dirList.size() > 0) {
            File[] filesList = dirList.getFirst().listFiles();
            if (filesList != null) {
                for (File path : filesList) {
                    if (path.isDirectory()) {
                        dirList.addLast(path);
                    } else {
                        String simpleFileName = path.getName();

                        for (String ext: extList) {

                            if (simpleFileName.endsWith(ext)) {
                                result.add(path.getAbsolutePath().toString());
                            }
                        }
                    }
                }
            }
            dirList.removeFirst();
        }
        return result;
    }


    public static File[] getDiscList() {
        File[] path = File.listRoots();
        return path;
    }
}
