package blog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountLogout {
	// Sessionが使えるように
	@Autowired
	private HttpSession session;

	// ログアウト処理
	@GetMapping("/account/logout")
	public String accountLogout() {
		// セクションの無効化
		session.invalidate();
		// ログイン画面にリダイレクトする
		return "redirect:/account/login";
	}

}
