package blog.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.com.models.entity.Blog;

@Repository
public interface BlogDao extends JpaRepository<Blog, Long> {
	//保存処理を更新処理　insertとupdate
	Blog save(Blog blog);
	
	//SELECT * FROM blog 
	//blogを表示できるように
	List<Blog>findAll();
	
	//SELECT * FROM blog WHERE　blog_id　＝?
	//あるblogを更新できるように blog_idを利用
	Blog findByBlogId(Long blogId);
	
	//DLETE FROM  blog WHERE blog_id = ?
	//あるblogを更新できるように blog_idを利用
	void deleteByBlogId(Long blogId);
	
}
