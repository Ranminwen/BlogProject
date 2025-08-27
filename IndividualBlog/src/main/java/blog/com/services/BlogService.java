package blog.com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.models.dao.BlogDao;
import blog.com.models.entity.Blog;

@Service
public class BlogService {
	@Autowired
	private BlogDao blogDao;

	// blogのチャック
	// もしaccount == null 戻り値null
	// そうではない場合findall内容をBlogListControllerクラスにわたす
	public List<Blog> selectAllbloglist(Long accountId) {
		if (accountId == null) {
			return null;
		} else {
			return blogDao.findAll();
		}
	}
	
	//blogの追加処理チェック
	// もし、findByBlogTitleがnullだったら
	// 保存処理 true
	// そうではない場合 false
	public boolean creatBlogs(String blogTitle,String categoryName, String blogImage, String article, Long accountId) {
		if(blogDao.findByBlogTitle(blogTitle)==null) {
			blogDao.save(new Blog(blogTitle,categoryName, blogImage, article,accountId));
			return true;
		}else {
			return false;
		}
		
	}
	
	
	

}
