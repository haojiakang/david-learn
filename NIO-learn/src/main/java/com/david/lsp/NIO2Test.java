package com.david.lsp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by haojk on 5/25/16.
 */
public class NIO2Test {

    private void pathTest(){
        Path path = Paths.get(".");
        System.out.println("path里面含有的路径数量："+path.getNameCount());
        System.out.println("path的根路径： "+path.getRoot());

        Path absolutePath = path.toAbsolutePath();
        System.out.println("absolutePath: "+absolutePath);
        //绝对路径的根路径
        System.out.println("absolutePath的根路径："+absolutePath.getRoot());

        System.out.println("absolute路径里面包含的路径数量： "+absolutePath.getNameCount());

        System.out.println("absolutePath.getName(3):"+absolutePath.getName(3));

        Path path2 = Paths.get("g", "publish", "codes");
        System.out.println("以多个String来构建Path对象："+path2);
    }

    private void fileTest() {
        Path filePath = Paths.get("src", "main", "java", "com", "jackie", "lsp", "NIO2Test.java");
        try {
            Files.copy(filePath, new FileOutputStream("b.txt"));
            //判断NIO2Test.java文件是否为隐藏文件
            System.out.println("NIO2Test是否为隐藏文件："+Files.isHidden(filePath));
            List<String> lines = Files.readAllLines(filePath, Charset.forName("utf-8"));
            System.out.println(lines);

            //判断指定文件的大小
            System.out.println("NIO2Test.java文件的大小："+Files.size(filePath));

            List<String> poem = new ArrayList<>();
            poem.add("水晶潭底银鱼跃");
            poem.add("清徐风中碧杆横");

            //直接将多个字符串写入指定文件中
            Files.write(Paths.get("poem.txt"), poem, Charset.forName("utf-8"));
            Files.list(Paths.get(".")).forEach(path -> System.out.println(path));
            Files.lines(filePath, Charset.forName("utf-8")).forEach(line -> System.out.println(line));

            FileStore fileStore = Files.getFileStore(Paths.get("/home/fsdevops/tmp"));
            System.out.println("/tmp共有空间："+fileStore.getTotalSpace());
            System.out.println("/tmp可用空间："+fileStore.getUsableSpace());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void fileVisitorTest(){
        Path filePath = Paths.get("src", "main", "java");
        try {
            Files.walkFileTree(filePath, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
                    System.out.println("正在访问"+file+"文件");
                    if(file.endsWith("NIO2Test.java")){
                        System.out.println("已经找到目标文件");
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    System.out.println("正在访问：" + dir + "路径");
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void watchServiceTest(){
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Paths.get("/tmp").register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
            while(true){
                WatchKey key = watchService.take();
                for(WatchEvent<?> event : key.pollEvents()){
                    System.out.println(event.context()+"文件发生了"+event.kind()+"事件");
                }
                boolean valid = key.reset();
                if(!valid){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void attributeViewTest(){
        Path filePath = Paths.get("src", "main", "java", "com", "jackie", "lsp", "NIO2Test.java");
        BasicFileAttributeView basicFileAttributeView = Files.getFileAttributeView(filePath, BasicFileAttributeView.class);
        try {
            BasicFileAttributes basicFileAttributes = basicFileAttributeView.readAttributes();
            System.out.println("创建时间："+new Date(basicFileAttributes.creationTime().toMillis()));
            System.out.println("最后访问时间："+new Date(basicFileAttributes.lastAccessTime().toMillis()));
            System.out.println("最后修改时间："+new Date(basicFileAttributes.lastModifiedTime().toMillis()));
            System.out.println("文件大小："+basicFileAttributes.size());

            FileOwnerAttributeView ownerAttributeView = Files.getFileAttributeView(filePath, FileOwnerAttributeView.class);
            System.out.println("文件所属用户："+ownerAttributeView.getOwner());
            UserPrincipal user = FileSystems.getDefault().getUserPrincipalLookupService().lookupPrincipalByName("fsdevops");
            ownerAttributeView.setOwner(user);
            UserDefinedFileAttributeView userDefinedFileAttributeView = Files.getFileAttributeView(filePath, UserDefinedFileAttributeView.class);
            List<String> attrNames = userDefinedFileAttributeView.list();
            for(String name : attrNames){
                ByteBuffer buffer = ByteBuffer.allocate(userDefinedFileAttributeView.size(name));
                userDefinedFileAttributeView.read(name, buffer);
                buffer.flip();
                String value = Charset.defaultCharset().decode(buffer).toString();
                System.out.println(name + "---->"+ value);
            }
            userDefinedFileAttributeView.write("发行者", Charset.defaultCharset().encode("疯狂java联盟"));
            DosFileAttributeView dosFileAttributeView = Files.getFileAttributeView(filePath, DosFileAttributeView.class);
            dosFileAttributeView.setHidden(true);
            dosFileAttributeView.setReadOnly(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        NIO2Test test = new NIO2Test();
//        test.pathTest();
//        test.fileTest();
//        test.fileVisitorTest();
//        test.watchServiceTest();
        test.attributeViewTest();
    }

}
