package com.cn.tmall.controller;

import com.cn.tmall.pojo.Category;
import com.cn.tmall.service.CategoryService;
import com.cn.tmall.util.ImageUtil;
import com.cn.tmall.util.Page;
import com.cn.tmall.util.UploadedImageFile;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 分类的控制类
 */
@Controller
//@RequestMapping("")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Category> cs = categoryService.list();
        int total = (int) new PageInfo<>(cs).getTotal();
        //System.out.println(total);
        page.setTotal(total);
        model.addAttribute("cs", cs);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    /**
     * 添加分类
     */
    @RequestMapping("admin_category_add")
    public String add(Category c, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.add(c);
        //System.out.println(c.getId());
        File imageFolder = new File(session.getServletContext().getRealPath("/img/category"));
        File file = new File(imageFolder, c.getId() + ".jpg");

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

//        System.out.println(uploadedImageFile);
//        System.out.println(uploadedImageFile.getImage());
//        System.out.println(file);
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);

        return "redirect:admin_category_list";
    }

    /**
     * 删除分类，连图片一起删除
     * admin_category_delete
     */
    @RequestMapping("admin_category_delete")
    public String delete(Integer id, HttpSession session) {
        categoryService.delete(id);

        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        System.out.println(imageFolder);
        File file = new File(imageFolder, id + ".jpg");
        System.out.println(file);
        file.delete();

        return "redirect:admin_category_list";
    }

    /**
     * 跳转到修改页面
     */
    @RequestMapping("admin_category_edit")
    public String edit(Integer id, Map<String, Object> map) {
        Category c = categoryService.get(id);
        map.put("c", c);
        return "admin/editCategory";
    }

    /**
     * 更新分类
     */
    @RequestMapping("admin_category_update")
    public String update(Category c, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.update(c);

        MultipartFile image = uploadedImageFile.getImage();
        if (null != image && !image.isEmpty()) {
            File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder, c.getId() + ".jpg");
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }

        return "redirect:admin_category_list";
    }
}
