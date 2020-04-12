package com.zsc.flower.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.flower.service.ProductImageService;
import com.zsc.flower.service.ProductService;
import org.apache.commons.lang.StringUtils;
import model.entity.*;
import model.result.ResponseResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductImageService productImageService;

    @Autowired
    ProductService productService;


    //针对产品来做图片的插入  多文件！！！！！！！！！
    @RequestMapping("/uploadPictures")
//    public ResponseImageUrl uploadPictures(@RequestParam("file") CommonsMultipartFile file) {
    public ResponseResult uploadPictures(@RequestParam("pid") long pid,
                                         @RequestParam("files") MultipartFile[] files) {
        ResponseResult result = new ResponseResult();
        result.setMsg(false);
        int n = 0;
        String fileName = null, fileurlpath = null;

        //判断文件是否为空
        if (files.length != 0) {
            for (MultipartFile file : files) {
                String uuid = UUID.randomUUID().toString().trim();
                String fileN = file.getOriginalFilename();
                int index = fileN.indexOf(".");
                fileName = uuid + fileN.substring(index);
                File fileMkdir;
                try {
//                    fileMkdir = new File("/usr/local/nginx/html/mall-images");
                    fileMkdir = new File("F:\\nginx-1.12.1\\html\\mall-images");
                    if (!fileMkdir.exists()) {
                        fileMkdir.mkdir();
                    }
                    //定义输出流 将文件保存在F盘    file.getOriginalFilename()为获得文件的名字
                    FileOutputStream os = new FileOutputStream(fileMkdir.getPath() + "\\" + fileName);
                    InputStream in = file.getInputStream();
                    int b;
                    while ((b = in.read()) != -1) { //读取文件
                        os.write(b);
                    }
                    os.flush(); //关闭流
                    in.close();
                    os.close();
                    System.out.println(fileMkdir.toString());
                } catch (Exception e) {
                    result.setMsg(false);
                    return result;
                }
                //访问路径为http://localhost:8081/+fileurlpath
                fileurlpath = "mall-images/" + fileName;
                ProductImage productImage = new ProductImage();
                productImage.setPid(pid);
                productImage.setFilename(fileName);
                productImage.setFileurlpath(fileurlpath);
                n = productImageService.findInsertProductImages(productImage);
            }
            if (n == 1) {
                result.setMsg(true);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fileName", fileName);
                jsonObject.put("fileurlpath", fileurlpath);
                result.setData(jsonObject);
                return result;
            } else {
                return result;
            }
        } else {
            return result;
        }
    }

    //新增一个分类
    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public ResponseResult addCategory(String name) {
        ResponseResult result = new ResponseResult();
        result.setMsg(false);
        if (productService.findAddCategory(name) == 1) {
            result.setMsg(true);
            return result;
        } else
            return result;
    }

    //展示所有分类
    @RequestMapping(value = "/listCategory", method = RequestMethod.GET)
    public ResponseResult listCategory() {
        ResponseResult result = new ResponseResult();
        List<Category> categoryList = productService.findListCategory();
        result.setMsg(true);
        result.setData(categoryList);
        return result;
    }

//    //对应分类下的属性，增加
//    @RequestMapping(value = "/addProperty", method = RequestMethod.POST)
//    public ResponseResult addProperty(long cid, String name) {
//        ResponseResult result = new ResponseResult();
//        result.setMsg(false);
//        if (productService.findAddProperty(cid, name) == 1){
//            result.setMsg(true);
//            return result;
//        }
//        else
//            return result;
//    }

    //查询商品的所有信息
    @RequestMapping(value = "/showBaseDetail", method = RequestMethod.GET)
    public ResponseResult showBaseDetail(long id) {
        ResponseResult result = new ResponseResult();
        List<BaseDetail> baseDetailList = productService.findShowBaseDetail(id);
        result.setMsg(true);
        result.setData(baseDetailList);
        return result;
    }

    //查询该商品的具体信息
    @RequestMapping(value = "/showExtendDetail", method = RequestMethod.GET)
    public ResponseResult showExtendDetail(long id) {
        List<ExtendDetail> extendDetailList = productService.findShowExtendDetail(id);
        ResponseResult result = new ResponseResult();
        result.setMsg(true);
        result.setData(extendDetailList);
        return result;
    }

    //后台的商品列表
    @RequestMapping(value = "/listProduct", method = RequestMethod.GET)
    public ResponseResult listProduct(@RequestParam(defaultValue = "1", required = true, value = "pn") Integer pn) {
        int pageSize = 8;
        PageHelper.startPage(pn, pageSize);
        PageInfo<ListProduct> pageInfo = productService.findListProduct(pn, pageSize);
        ResponseResult result = new ResponseResult();
        result.setMsg(true);
        result.setData(pageInfo);
        return result;
    }


    //按分类ID,返回listProduct列表yi
    @RequestMapping("/listProductByCId")
    public ResponseResult listProductByCId(@RequestParam(defaultValue = "1", required = true, value = "pn") Integer pn,
                                           @RequestParam("cid") long cid) {
        ResponseResult result = new ResponseResult();
        int pageSize = 8;
        PageHelper.startPage(pn, pageSize);
        List<ListProduct> listProductList = productService.findProductsByCId(cid);
        PageInfo<ListProduct> pageInfo = new PageInfo<>(listProductList);
        result.setMsg(true);
        result.setData(pageInfo);
        return result;
    }

    //单个商品的展示，返回商品的基本信息加五张图
    @RequestMapping(value = "/showProductDetail", method = RequestMethod.GET)
    public ResponseResult showProductDetail(@RequestParam("pid") long pid) {
        //这个SimpleProWithPic用来打包所有商品信息传递给前端‘
        SimpleProWithPic simpleProWithPic = new SimpleProWithPic();

        List<String> fileurlpathlist = productImageService.findPicListByPID(pid);
        simpleProWithPic.setFileurlpath(fileurlpathlist);

        SimpleDetail simpleDetail = productService.findSimpleDetail(pid);
        simpleProWithPic.setSimpleDetail(simpleDetail);

        ResponseResult result = new ResponseResult();
        result.setMsg(true);
        result.setData(simpleProWithPic);
        return result;
    }

    //按商品名模糊匹配查询出产品列表
    @RequestMapping(value = "/dimSearch", method = RequestMethod.GET)
    public ResponseResult dimSearch(@RequestParam("name") String name) {
        List<ListProduct> listProductList = productService.findListProductByDimSearch(name);
        ResponseResult result = new ResponseResult();
        result.setMsg(true);
        result.setData(listProductList);
        return result;
    }

    //产品的库存、价格、商品名、二级标题修改
    @RequestMapping(value = "/modifyProduct", method = RequestMethod.GET)
    public ResponseResult modifyProduct(Product product) {
        //需要提供商品的ID
        long id = product.getId();
        Product newProduct = productService.findProductById(id);
        newProduct.setStock(product.getStock());
        newProduct.setPromotePrice(product.getPromotePrice());
        newProduct.setName(product.getName());
        newProduct.setSubTitle(product.getSubTitle());
        ResponseResult result = new ResponseResult();
        result.setMsg(false);
        if (productService.findUpdateProduct(product) == 1) {
            result.setMsg(true);
            return result;
        } else
            return result;
    }

    //后台商品下架
    @RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
    public ResponseResult deleteProduct(@Param("id") long id) {
        ResponseResult result = new ResponseResult();
        result.setMsg(false);
        productService.closeforeign();
        if (productService.findDeleteProduct(id) == 1) {
            result.setMsg(true);
            return result;
        } else
            return result;
    }

    //前台按createDate排序返回商品展示列表
    @RequestMapping(value = "/showProductByCreateDate", method = RequestMethod.GET)
    public ResponseResult showProductByCreateDate() {
        List<ListProduct> listProductList = productService.findListProductByCreateDate();
        ResponseResult result = new ResponseResult();
        result.setMsg(true);
        result.setData(listProductList);
        return result;
    }


    @RequestMapping(value = "/showProductBySaleCount", method = RequestMethod.GET)
    public ResponseResult showProductBySaleCount(@RequestParam(defaultValue = "1", required = true, value = "pn") Integer pn) {
        int pageSize = 8;
        PageHelper.startPage(pn, pageSize);
        List<ListProduct> listProductList = productService.findListProductBySaleCount();
        PageInfo<ListProduct> pageInfo = new PageInfo<>(listProductList);
        ResponseResult result = new ResponseResult();
        result.setMsg(true);
        result.setData(pageInfo);
        return result;
    }

    @RequestMapping(value = "/showProductForBanner", method = RequestMethod.GET)
    public ResponseResult showProductForBanner() {
        List<ListProduct> listProductList = productService.findProductForBanner();
        ResponseResult result = new ResponseResult();
        result.setMsg(true);
        result.setData(listProductList);
        return result;
    }

    //单个商品的展示，返回商品的基本信息加单张图
    @RequestMapping(value = "/showProductDetailWithSingle", method = RequestMethod.GET)
    public ResponseResult showProductDetailWithSingle(@RequestParam("pid") long pid) {
        //这个SimpleProWithPic用来打包所有商品信息传递给前端‘
        SimpleProWithSinglePic simpleProWithSinglePic = new SimpleProWithSinglePic();

        List<String> fileurlpathlist = productImageService.findPicListByPID(pid);

        simpleProWithSinglePic.setFileurlpath(fileurlpathlist.get(0));

        SimpleDetail simpleDetail = productService.findSimpleDetail(pid);
        simpleProWithSinglePic.setSimpleDetail(simpleDetail);

        ResponseResult result = new ResponseResult();
        result.setMsg(true);
        result.setData(simpleProWithSinglePic);
        return result;
    }

    //base64转换
    @RequestMapping(value = "/base64", method = RequestMethod.POST)
    public ResponseResult base64(Product product, @RequestParam("dataBase") String arr) {
        product.setSaleCount(15L);
        product.setCreateDate(new Date());
        int n = productService.findAddProduct(product);
        int m = 0;
        long pid = 0;
        JSONArray jsonArray = JSONArray.parseArray(arr);
        Byte mainPic = 1;
        if (n == 1) {
            pid = productService.findPidByTopInsert();
            for (int i = 0; i < jsonArray.size(); i++) {
                String data = (String) jsonArray.getJSONObject(i).get("name");
                ProductImage productImage = parseContents(data);
                productImage.setPid(pid);
                productImage.setType(mainPic);
                productImage.setSort(i);
                m = productImageService.findInsertProductImages(productImage);
            }
        }
        ResponseResult result = new ResponseResult();
        result.setMsg(false);
        if (m == 1) {
            result.setMsg(true);
            result.setData(pid);
            return result;
        } else {
            return result;
        }
    }

    @RequestMapping(value = "/uploadBanners", method = RequestMethod.POST)
    public ResponseResult uploadBanners(@RequestParam("id") long pid, @RequestParam("dataBase") String arr) {
        int m = 0;
        JSONArray jsonArray = JSONArray.parseArray(arr);
        Byte bannerPic = 0;
        for (int i = 0; i < jsonArray.size(); i++) {
            String data = (String) jsonArray.getJSONObject(i).get("name");
            ProductImage productImage = parseContents(data);
            productImage.setPid(pid);
            productImage.setType(bannerPic);
            int topSort = productImageService.findTopSort(pid,bannerPic);
            productImage.setSort(topSort+1+i);
            m = productImageService.findInsertProductImages(productImage);
        }
        ResponseResult result = new ResponseResult();
        result.setMsg(false);
        if (m == 1) {
            result.setMsg(true);
            result.setData(pid);
            return result;
        } else {
            return result;
        }
    }

    public ProductImage parseContents(String contents) {
        String fileNameSuffix = null;
        if (contents.indexOf("data:image/") != -1) {
            int firstIndex = contents.indexOf("data:image/") + 11;

            int index1 = contents.indexOf(";base64,");

            String type = contents.substring(firstIndex, index1);

            fileNameSuffix = UUID.randomUUID().toString() + "." + type;
            String fileName = "F:\\nginx-1.12.1\\html\\mall-images\\" + fileNameSuffix;
            BASE64Decoder decoder = new BASE64Decoder();
            OutputStream os = null;
            try {
                String imgsrc = StringUtils.substringBefore(contents.substring(contents.indexOf(";base64,") + 8), "\"");
                byte[] bytes = decoder.decodeBuffer(imgsrc);
                //替换之前的src中base64数据为servlet请求
                contents = contents.replace("data:image/" + type + ";base64," + imgsrc, "/images.do?src=" + fileName);

                File file = new File(fileName);
                //获取父目录
                File fileParent = file.getParentFile();
                //判断是否存在
                if (!fileParent.exists()) {
                    //创建父目录文件
                    fileParent.mkdirs();
                }
                file.createNewFile();
                os = new FileOutputStream(file);
                os.write(bytes);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        String path = "mall-images/" + fileNameSuffix;
        ProductImage productImage = new ProductImage();
        productImage.setFilename(fileNameSuffix);
        productImage.setFileurlpath(path);
        return productImage;

    }

    @GetMapping("/getSort")
    public ResponseResult getSort(@RequestParam(defaultValue = "1", required = true, value = "pn") Integer pn,
                                  @RequestParam("cid") long cid, @RequestParam("sort") String sort) {

        int pageSize = 10;
        PageHelper.startPage(pn, pageSize);
        ResponseResult result = new ResponseResult();
        result.setMsg(false);
        try {
            PageInfo<ListProduct> pageInfo = productService.findSort(pn, pageSize, cid, sort);
            result.setMsg(true);
            result.setData(pageInfo);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("系统出错");
            return result;
        }
    }
}
