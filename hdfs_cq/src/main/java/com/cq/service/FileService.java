package com.cq.service;

import com.cq.dao.*;
import com.cq.entity.*;
import com.cq.util.CommunityUtil;
import okhttp3.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.*;

@Service
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Autowired
    private FilePathMapper filePathMapper;

    @Autowired
    private FileMetaMapper fileMetaMapper;

    @Autowired
    private DataNodeMapper dataNodeMapper;

    @Autowired
    private FileDataInfoMapper fileDataInfoMapper;

    @Autowired
    private DataInfoMapper dataInfoMapper;

    @Autowired
    private CommunityUtil communityUtil;

    @Autowired
    private DataService dataService;

    public boolean checkSameName(String name, int type, int parentId) {
        FilePath res = filePathMapper.selectIdByNameAndType(name, type, parentId);
        return res == null;
    }

    // 上传文件
    public Map<String, Object> uploadFile(MultipartFile multipartFile, int parentId) {
        Map<String, Object> map = new HashMap<>();
        String name = multipartFile.getOriginalFilename();
        File file = transferToFile(multipartFile);
        // 1 将这个文件的信息存储到file_path中, 将获取到的MultipartFile对象转换为File对象
        filePathMapper.insertFile(parentId, name);
        int fileId = filePathMapper.selectIdByParentIdAndName(parentId, name);
        FileMeta fileMeta = new FileMeta();
        fileMeta.setFileId(fileId);
        fileMeta.setSuffix(name.substring(name.lastIndexOf(".")+1));
        int size = (int) (multipartFile.getSize() / 1024);
        fileMeta.setSize(size);
        String md5 = CommunityUtil.md5(file);
        fileMeta.setMd5(md5);
        int res = fileMetaMapper.insertFileMeta(fileMeta);
        if(res <= 0) {
            map.put("uploadFileErrorMsg", "插入元数据信息失败");
        }
        int metaId = fileMetaMapper.selectIdByMD5(md5);
        // 2 对文件进行MD5值的生成
        String s = CommunityUtil.md5(file);
        int a = CommunityUtil.sixteenToTen(s);
        int server1 = communityUtil.selectServer1(a, size);
        int[] server23 = communityUtil.selectServer23(server1, size);
        int server2 = server23[0];
        int server3 = server23[1];
        int[] server123 = new int[]{server1, server2, server3};
        // 3 将File对象分block块存储到硬盘作为过渡区
        try {
            filePart(file, metaId, server123, name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public void uploadFile(String name, int parentId) {
        long start = System.currentTimeMillis();
        File file = new File(name);
        // 1 将这个文件的信息存储到file_path中, 将获取到的MultipartFile对象转换为File对象
        filePathMapper.insertFile(parentId, name);
        int fileId = filePathMapper.selectIdByParentIdAndName(parentId, name);
        FileMeta fileMeta = new FileMeta();
        fileMeta.setFileId(fileId);
        fileMeta.setSuffix(name.substring(name.lastIndexOf(".")+1));
        int size = (int) (FileUtils.sizeOf(file) / 1024);
        fileMeta.setSize(size);
        String md5 = CommunityUtil.md5(file);
        fileMeta.setMd5(md5);
        int res = fileMetaMapper.insertFileMeta(fileMeta);
        int metaId = fileMetaMapper.selectIdByMD5(md5);
        // 2 对文件进行MD5值的生成
        String s = CommunityUtil.md5(file);
        int a = CommunityUtil.sixteenToTen(s);
        int server1 = communityUtil.selectServer1(a, size);
        int[] server23 = communityUtil.selectServer23(server1, size);
        int server2 = server23[0];
        int server3 = server23[1];
        int[] server123 = new int[]{server1, server2, server3};
        // 3 将File对象分block块存储到硬盘作为过渡区
        try {
            filePart(file, metaId, server123, name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("大小: " + size + "KB, 耗时: " + (end - start) + "ms");
    }

    // multipartFile 转 File
    public File transferToFile(MultipartFile multipartFile) {
        StringBuffer pathnameBuffer = new StringBuffer("E:/temp/" + UUID.randomUUID() + multipartFile.getOriginalFilename());
        String pathname = pathnameBuffer.toString();
        File file = new File(pathname);
        System.out.println(file.getName());
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    // 文件切片
    public void filePart(File sourceFile, int metaId, int[] server123, String name) throws IOException {
        // String name = sourceFile.getName();
        String chunkPath = "E:/filePart/";
        File chunkFolder = new File(chunkPath);
        // 如果该文件夹不存在则创建
        if (!chunkFolder.exists()) {
            chunkFolder.mkdirs();
        }
        // 设定文件分块大小,设定为128MB
        long chunkSize = 1024 * 1024 * 128;
        // 分块数量
        long chunkNum = (long) Math.ceil(sourceFile.length() * 1.0 / chunkSize);
        if(chunkNum <= 0) {
            chunkNum = 1;
        }
        // 设置缓冲区
        byte[] bytes = new byte[1024];
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(sourceFile));
        // 开始分块
        for(int i = 1; i <= chunkNum; i++) {
            FileDataInfo fileDataInfo = new FileDataInfo();
            fileDataInfo.setMetaId(metaId);
            StringBuilder builderName = new StringBuilder(chunkPath + name.substring(0, name.lastIndexOf(".")) + "_" + i);
            String partName = builderName.toString();
            fileDataInfo.setName(partName);
            fileDataInfo.setOrder(i);
            // 创建分块文件
            File file = new File(partName);
            if(file.createNewFile()) {
                // 向分块文件中写数据W
                BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(file));
                int len = -1;
                while ((len = fis.read(bytes)) != -1) {
                    fos.write(bytes, 0, len); // 写入数据
                    if(file.length() > chunkSize){
                        break;
                    }
                }
                fos.close();
            }
            int size = (int) (FileUtils.sizeOf(file) / 1024);
            fileDataInfo.setSize(size);
            String md5 = CommunityUtil.md5(file);
            fileDataInfo.setMd5(md5);
            fileDataInfo.setVersion(1);
            fileDataInfo.setData1(server123[0]);
            fileDataInfo.setVersion1(1);
            fileDataInfo.setData2(server123[1]);
            fileDataInfo.setVersion2(1);
            fileDataInfo.setData3(server123[2]);
            fileDataInfo.setVersion3(1);
            fileDataInfoMapper.insert(fileDataInfo);
            // int fileId = fileDataInfoMapper.selectIdByMD5(md5);
            for(int j = 0; j < 3; j++) {
                uploadToServer(partName, server123[j]);
            }
        }
        System.out.println(1);
        fis.close();
    }

    // url: 例如: "http://1.13.181.152:8000"
    // 每个服务器有50GB，即 52428800 KB
    public void uploadToServer(String fileName, int i) {
        DataNode dataNode = dataNodeMapper.selectById(i);
        String url = dataNode.getName();
        int freeSpace = dataNode.getFreeSpace();
        // String fileName1 = id + fileName;
        File file = new File(fileName);
        String md5 = CommunityUtil.md5(file);
        int size = (int) (FileUtils.sizeOf(file) / 1024); // 单位是KB
        int newFreeSpace = freeSpace - size;
        dataNodeMapper.updateFreeSpaceById(i, newFreeSpace);
        double occupy = (double) newFreeSpace / dataNode.getAllSpace();
        if(occupy < 0.2) {
            logger.error("请运维人员及时添加新的服务器数据节点以实现扩容功能！！！\n请运维人员及时添加新的服务器数据节点以实现扩容功能！！！\n请运维人员及时添加新的服务器数据节点以实现扩容功能！！！\n");
        }
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName,
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                file))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataInfo dataInfo = new DataInfo();
        dataInfo.setSize(size);
        dataInfo.setMd5(md5);
        int fileId = fileDataInfoMapper.selectIdByMD5(md5);
        dataInfo.setDataNodeId(i);
        dataInfo.setDataId(fileId);
        dataInfo.setName(fileName);
        dataInfoMapper.insert(dataInfo);
    }

    // 下载文件
    public Map<String, Object> downloadFile(int fileId) throws IOException {
        String allName = filePathMapper.selectNameById(fileId);
        Map<String, Object> map = new HashMap<>();
        Random random = new Random();
        int metaId = fileMetaMapper.getIdByFileId(fileId);
        List<Integer> id = fileDataInfoMapper.getIdByMetaId(metaId);
        for(int i = 0; i < id.size(); i++) {
            String urlName = fileDataInfoMapper.selectNameById(id.get(i));
            String name = urlName.substring(urlName.lastIndexOf("/") + 1);
            String md5 = fileDataInfoMapper.selectMd5ById(id.get(i));
            List<Integer> ids = dataInfoMapper.selectDataNodeIdByMd5(md5);
            int j = random.nextInt(ids.size());
            String dataNodeName = dataNodeMapper.selectNameById(ids.get(j));
            String urlPath = dataNodeName + "/" + name;
            downloadFileFromServer(urlPath, name, "E:/downloadTmp/");
        }
        fileMerge("E:/downloadTmp", "E:/download/" + allName);
        return map;
    }

    // 下载文件
    public File downloadFileFromServer(String urlPath, String filename, String downloadDir) {
        File file = null;
        try {
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            //设置超时
            httpURLConnection.setConnectTimeout(1000 * 5);
            //设置请求方式，默认是GET
            httpURLConnection.setRequestMethod("GET");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();
            // 文件大小
            int fileLength = httpURLConnection.getContentLength();
            // 控制台打印文件大小
            System.out.println("您要下载的文件大小为:" + fileLength / (1024 * 1024) + "MB");
            // 建立链接从请求中获取数据
            URLConnection con = url.openConnection();
            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
            // 指定文件名称(有需求可以自定义)
            // 指定存放位置(有需求可以自定义)
            String path = downloadDir + File.separatorChar + filename;
            file = new File(path);
            // 校验文件夹目录是否存在，不存在就创建一个目录
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[2048];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                // 控制台打印文件下载的百分比情况
                // System.out.println("下载了-------> " + len * 100 / fileLength + "%\n");
            }
            // 关闭资源
            bin.close();
            out.close();
            System.out.println("文件下载成功！");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("文件下载失败！");
        } finally {
            return file;
        }
    }

    // 合并文件
    public static void fileMerge(String chunkFolderName, String mergeFileName) throws IOException {
        // 需要合并的文件所在的文件夹
        // File chunkFolder = new File("E:/testPart");
        File chunkFolder = new File(chunkFolderName);
        // 合并后的文件
        File mergeFile = new File(mergeFileName);
        // 如果该文件夹
        if (mergeFile.exists()) {
            mergeFile.delete();
        }
        // 创建合并后的文件
        mergeFile.createNewFile();
        // 获取分块列表
        File[] fileArray = chunkFolder.listFiles();
        // 把文件转成集合并排序
        ArrayList<File> fileList = new ArrayList<>(Arrays.asList(fileArray));
        // 从小到大进行排序
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                int s1 = Integer.parseInt(o1.getName().substring(o1.getName().lastIndexOf("_") + 1));
                int s2 = Integer.parseInt(o2.getName().substring(o2.getName().lastIndexOf("_") + 1));
                if(s1 < s2) {
                    return -1;
                }
                return 1;
            }
        });
        // 合并文件
        try(FileChannel fosChannel = new FileOutputStream(mergeFile).getChannel()) {
            for (File chunkFile : fileList) {
                try (FileChannel fisChannel =  new FileInputStream(chunkFile).getChannel()) {
                    fisChannel.transferTo(0, fisChannel.size(), fosChannel);
                } catch (IOException ex){
                    System.out.println("IOException: " + ex);
                }
            }

        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        }
    }

    // 删除文件
    public void deleteFile(int id) {
        filePathMapper.updateStatusById(id);
        int metaId = fileMetaMapper.selectIdByFileId(id);
        fileMetaMapper.updateStatusByFileId(id);
        List<Integer> dataInfoId = fileDataInfoMapper.selectIdByMetaId(metaId);
        for(int i = 0; i < dataInfoId.size(); i++) {
            int i_id = dataInfoId.get(i);
            System.out.println(i_id);
            fileDataInfoMapper.updateStatusById(i_id);
            List<DataInfo> dataInfos = dataInfoMapper.selectByDataId(i_id);
            for(DataInfo dataInfo: dataInfos) {
                int dataServerId = dataInfo.getId();
                int dataNodeId = dataInfo.getDataNodeId();
                dataInfoMapper.updateStatusById(dataServerId);
                String name = dataInfo.getName().substring(dataInfo.getName().lastIndexOf("/") + 1);
                String dataNodeName = dataNodeMapper.selectNameById(dataNodeId);
                deleteFileFromServer(dataNodeName, name);
            }
        }
    }

    public void deleteFileFromServer(String url, String name) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                // "http://1.13.181.152:8000/font.zip"
                .url(url + "/" + name)
                .method("DELETE", body)
                .addHeader("Accept", "*/*")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                .addHeader("Authorization", "Basic Y3hrOmN4azEyMw==")
                .addHeader("Connection", "keep-alive")
                .addHeader("Cookie", "_ga=GA1.1.846005249.1661165675; _gid=GA1.1.1187716334.1661165675; _gat=1")
                .addHeader("Origin", url)
                .addHeader("Referer", url + "/")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.5112.102 Safari/537.36 Edg/104.0.1293.63")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}

