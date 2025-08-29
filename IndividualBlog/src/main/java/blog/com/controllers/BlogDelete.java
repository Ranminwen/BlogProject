package blog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import blog.com.models.entity.Account;
import blog.com.services.BlogService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogDelete {
	@Autowired
	private BlogService blogService;
	@Autowired
	private HttpSession session;

	@PostMapping("/blog/delete")
	public String blogDelete(Long blogId) {
		// セッションからログインしている人の情報をaccountという変数に格納
		Account account = (Account) session.getAttribute("loginAccountInfo");
		// もしaccount==nul1 ログイン画面にリダイレクトする
		if (account == null) {
			return "redirect:/account/login";
		} else {
			// もし、deleteblogの結果がtrue
			if (blogService.deleteBlog(blogId)) {
				// 商品の一覧ページにダイレクト
				return "redirect:/blog/list";
			} else {
				// そうではない場合
				// 編集画面にリダイレクトする
				return "redirect:/blog/edit" + blogId;
			}
		}
	}
}
