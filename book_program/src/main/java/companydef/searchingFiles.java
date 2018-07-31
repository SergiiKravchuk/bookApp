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
public class searchingFiles {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        String[] extList = {".txt", ".html"};
        System.out.println("Make auto-search or hand-search? ( type auto/hand )");
        String choice = scanner.next();

        if (choice.equals("auto")){

            System.out.println("Make full scan? ( type yes/no )");
            String scanChoice = scanner.next();
            if (scanChoice.equals("no")){
//                String path = "e:\\Games\\testFor\\";
                String path;
//                String path = "e:\\ ";
                System.out.println("Enter the path like c:\\folder\\nextfolder\\");
                path = scanner.next();
                File str_dir = new File(path);
                listOfFiles(str_dir, extList);


            }else if(scanChoice.equals("yes")){
//                getComputerInfo();
                File[] pathDisc = getDiscList();
                for (File path : pathDisc){
                    System.out.println("From disc" + path);
                    listOfFiles(path, extList);
                }
            }

        }
        else if (choice.equals("hand")){
            fileChoose();
        }


    }

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

    public static void fileChoose(){
        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File selectedFile = chooser.getSelectedFile();
            String name = selectedFile.getName();
            System.out.println("You choose the file with name " + name);
        }
    }

    public static File[] getDiscList() {
        File[] path = File.listRoots();
        return path;
    }
}
