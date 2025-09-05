package blog.com.controllerText;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import blog.com.models.entity.Account;
import blog.com.services.AccountService;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountLoginControllerText {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	//ログイン画面のテスト
	@Test
	public void testgetLoginPage() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/account/login");
		mockMvc.perform(request).andExpect(view().name("account_login.html"));

	}

	// データの準備
	@BeforeEach
	public void prepareDate() {
		// loginの情報を作成する（Daoの内容を返すので
		Account account = new Account("test2", "test2@email.com", "abcd");

		// ログインが成功： accountEmail "test2@email.com"、 password "abcd" 場合、
		// このaccountを返す
		when(accountService.loginCheck("test2@email.com", "abcd")).thenReturn(account);

		// ログインが失敗1：accountEmailが間違い
		when(accountService.loginCheck("test@email.com", "abcd")).thenReturn(null);

		// ログインが失敗2：password が間違い
		when(accountService.loginCheck("test2@email.com", "1234")).thenReturn(null);

		// ログインが失敗3：accountEmailとpassword が間違い
		when(accountService.loginCheck("test@email.com", "1234")).thenReturn(null);

	}

	// ログインが成功した場合のテスト
	// ログインが成功したら「/blog/list」に遷移して、入力された値が渡されているかのテスト
	@Test
	public void testLoginSuccess_Succe() throws Exception {
		MockHttpSession session = (MockHttpSession) mockMvc.perform(
				post("/account/login/process").param("accountEmail", "test2@email.com").param("password", "abcd"))
				.andExpect(view().name("redirect:/blog/list")).andReturn().getRequest().getSession();
		// sessionの確認
		Account loginInfo = (Account) session.getAttribute("loginAccountInfo");
		assertThat(loginInfo).isNotNull();
		assertThat(loginInfo.getAccountName()).isEqualTo("test2");
	}

	// ログインが失敗した場合のテスト
	// ログインが失敗1：accountEmailが間違い
	@Test
	public void testLoginSuccess_Fail_AccountEmail() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/account/login/process")
				.param("accountEmail", "test2@email.com").param("password", "1234");

		mockMvc.perform(request).andExpect(view().name("account_login.html"));

		// Verify 指定された引数で1回だけ呼び出されたことを確認
		verify(accountService, times(1)).loginCheck("test2@email.com", "1234");
	}

	// ログインが失敗2：password が間違い
	@Test
	public void testLoginSuccess_Fail_Password() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/account/login/process")
				.param("accountEmail", "test2@email.com").param("password", "1234");

		mockMvc.perform(request).andExpect(view().name("account_login.html"));

		// Verify 指定された引数で1回だけ呼び出されたことを確認
		verify(accountService, times(1)).loginCheck("test2@email.com", "1234");
	}

	// ログインが失敗3：accountEmailとpassword が間違い
	@Test
	public void testLoginSuccess_Fail_AccpuntEmailAndPassword() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/account/login/process")
				.param("accountEmail", "test@email.com").param("password", "1234");

		mockMvc.perform(request).andExpect(view().name("account_login.html"));

		// Verify 指定された引数で1回だけ呼び出されたことを確認
		verify(accountService, times(1)).loginCheck("test@email.com", "1234");
	}

}
