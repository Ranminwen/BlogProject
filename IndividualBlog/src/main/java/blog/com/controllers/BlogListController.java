package blog.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import blog.com.models.entity.Account;
import blog.com.models.entity.Blog;
import blog.com.services.BlogService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogListController {
	@Autowired
	private HttpSession session;
	@Autowired
	private BlogService blogService;

	// ブログリスト画面の表示
	@GetMapping("/blog/list")
	public String getBlogList(Model model) {
		// セッションからログインしている人の情報を取得
		Account account = (Account) session.getAttribute("loginAccountInfo");
		// もしaccount == null ログイン画面へ
		// そうではない場合
		// ログインしている人の名前の情報をが画面に渡してブログリストのHTMLを表示
		if (account == null) {
			return "redirect:/account/login";
		} else {
			// ブログリストを取得
			List<Blog> bloglist = blogService.selectAllbloglist(account.getAccountId());
			model.addAttribute("accountName", account.getAccountName());
			model.addAttribute("blogList", bloglist);
			return "blog_list.html";
		}
	}
}
