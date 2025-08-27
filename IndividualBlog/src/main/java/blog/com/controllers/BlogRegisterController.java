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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.com.models.entity.Account;
import blog.com.services.BlogService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogRegisterController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private HttpSession session;

	// blog追加画面の表示
	@GetMapping("/blog/register")
	public String getBlogRegisterPage(Model model) {
		// セクションからログインしている人の情報を取得
		Account account = (Account) session.getAttribute("loginAccountInfo");
		// もし account ==nul1 ログイン画面にリダイレクトする
		// そうでない場合は、ログインしている人の名前を画面に渡し、ブログ登録画面を表示させる
		if (account == null) {
			return "redirect:/account/login";
		} else {
			model.addAttribute("accountName", account.getAccountName());
			return "blog_register.html";
		}
	}

	// ブログ追加処理
	@PostMapping("/blog/register/process")
	public String blogBlogRegisterProcess(@RequestParam String blogTitle, @RequestParam String categoryName,
			@RequestParam MultipartFile blogImage, @RequestParam String article) {
		// セッションからログインしている人の情報を取得
		Account account = (Account) session.getAttribute("loginAccountInfo");
		// もし、accountがない（ｎｕｌｌ）だったら、ログイン画面にリダイレクトする
		// そうではない場合，ブログ画像のファイル名を取得
		// 画像のアップロ-ド
		// もし、同じフアイルの名前がなかったら保存
		// ブログリスト画面ににリダイレクトする
		// そうではない場合ブログ追加画面ににとどまります。
		if (account == null) {
			return "redirect:/account/login";
		} else {
			// ファイルの名前を取得
			/*
			 * 現在の日時情報を元に、フアイル名を作成しています。SimpleDateFormatクラスを使用して、日時のフオ-マットを指定している
			 * 具体的には、"yyyy-MM-dd-HH-mm-ss-"の形式でフオ-マットされた文字列を取得している。
			 * その後、blogImageオプジエクトから元のフアイル名を取得し、フオ-マットされた日時文字列と連結して、フアイル名前変数に代入
			 */
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ blogImage.getOriginalFilename();
			// ファイルの保存
			try {
				Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/blog-img/" + fileName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(blogService.creatBlogs(blogTitle,categoryName, fileName, article, account.getAccountId())){
				return "redirect:/blog/list";
			}else {
				return "blog_register.html";
			}
		}
	}

}
