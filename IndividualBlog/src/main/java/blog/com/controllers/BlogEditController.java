package blog.com.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.com.models.entity.Account;
import blog.com.models.entity.Blog;
import blog.com.services.BlogService;
import jakarta.servlet.http.HttpSession;
//import jakarta.websocket.server.PathParam;

@Controller
public class BlogEditController {
	// blogテーブルを操作できるためのblogServiceクラス
	@Autowired
	private BlogService blogService;

	// Sessionが使えるように
	@Autowired
	private HttpSession session;

	// blog編集画面の表示
	@GetMapping("/blog/edit/{blogId}")
	public String getBlogEditPage(@PathVariable Long blogId, Model model) {
		// セッションからログインしている人の情報をaccountという変数に格納
		Account account = (Account) session.getAttribute("loginAccountInfo");
		// もし account ==nul1 ログイン画面にリダイレクトする
		// そうでない場合は、ログインしている人の名前を画面に渡し、ブログ登録画面を表示させる
		if (account == null) {
			return "redirect:/account/login";
		} else {
			// 編集画面に表示させる情報を変数に格納 blog
			Blog blog = blogService.BlogEditCheck(blogId);
			// もしblog==nu11だったら、ブログペ-ジにこリダイレクトする
			if (blog == null) {
				return "redirect:/blog/list";
			} else {
				model.addAttribute("accountName", account.getAccountName());
				// そうでない場合、編集画面に編集する内容を渡す
				model.addAttribute("blog", blog);
				// 編集画面を表示
				return "blog_edit.html";
			}
		}
	}

	// blogの更新処理
	@PostMapping("/blog/edit/process")
	public String blogUpdate(@RequestParam String blogTitle, @RequestParam String categoryName,
			@RequestParam MultipartFile blogImage, @RequestParam String article, @RequestParam Long blogId) {
		// セッションからログインしている人の情報をAccountという変数に格納
		Account account = (Account) session.getAttribute("loginAccountInfo");
		// もしaccount==nul1 ログイン画面にリダイレクトする
		if (account == null) {
			return "redirect:/account/login";
		} else {
			// blogの作者のチェック
			// もし、blogの作者ではない場合は、bloglistにリダイレクトする
			if (!blogService.isBlogOwner(account, blogId)) {
				// ブログの一覧ページにダイレクト
				return "redirect:/blog/list";
			}
			// もし、blogの作者である場合は、
			/*
			 * 現在の日時情報を元に、フアイル名を作成しています。simpleDateformatクラスを使用して、日時のフオ-マットを指定している
			 * 具体的には、"yyyy-MM-dd-HH-mm-ss-"の形式でフオ-マットされた文字列を取得している。
			 * その後、blogImageオプジエクトから元のフアイル名を取得し、フオ-マットされた日時文字列と連結して、fileName変数に代入
			 **/
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ blogImage.getOriginalFilename();
			// フアイルの保存
			try {
				Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/blog-img/" + fileName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// もし、blogUpdateの結果がtrueの場合は、ブログリストににリダイレクト
			// そうでない場合、ブログ編集画面にこリダイレクトする
			if (blogService.blogUpdate(blogId, blogTitle, categoryName, fileName, article, account.getAccountId())) {
				return "redirect:/blog/list";
			} else {
				return "redirect:/blog/edit" + blogId;
			}
		}
	}
}
