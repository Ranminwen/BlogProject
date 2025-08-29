package blog.com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

	// blogの追加処理チェック
	// もし、findByBlogTitleがnullだったら
	// 保存処理 true
	// そうではない場合 false
	public boolean creatBlogs(String blogTitle, String categoryName, String blogImage, String article, Long accountId) {
		if (blogDao.findByBlogTitle(blogTitle) == null) {
			blogDao.save(new Blog(blogTitle, categoryName, blogImage, article, accountId));
			return true;
		} else {
			return false;
		}
	}

	// 編集画面を表示する時のチャック
	// もし、blogtId ==null
	// そうでない場合、
	// findByBlogIdの情報をコントロ-ラ-クラスに渡す、
	public Blog BlogEditCheck(Long blogId) {
		if (blogId == null) {
			return null;
		} else {
			return blogDao.findByBlogId(blogId);
		}
	}

	// 更新処理のチエツク
	// もし、blogId==nul1だったら、更新処理はしない
	// false
	// そうでない場合、更新処理をする
	// コントロ-ラ-クラスからもらった、blogIdを使って、編集する前の、デ一タを取得
	// 変更するべきところだけ、セッタ-を使用してデ-タの更新をする。
	// trueを返す

	public boolean blogUpdate(Long blogId, String blogTitle, String categoryName, String blogImage, String article,
			Long accountId) {
		if (blogId == null) {
			return false;
		} else {
			Blog blog = blogDao.findByBlogId(blogId);
			blog.setBlogTitle(blogTitle);
			blog.setCategoryName(categoryName);
			blog.setBlogImage(blogImage);
			blog.setArticle(article);
			blog.setAccountId(accountId);
			blogDao.save(blog);
			return true;
		}
	}

	// 削除処理
	// もし、コントローラーからbologId==nul1だったら、削除処理はしない
	// false
	// そうでない場合、削除処理をする
	// コントロ-ラ-クラスからもらった、deleteByblogIdを使って商品を消す
	// trueを返す
	public boolean deleteBlog(Long blogId) {
		if(blogId == null) {
			return false;
		}else {
			blogDao.deleteById(blogId);
			return true;
		}
	}
	

}
