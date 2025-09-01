
<div id="top"></div>

# 📘 自作ブログウェブサイト「IndividuaBlog」

<p align="center">
  <img src="https://img.shields.io/badge/-Java-007396.svg?logo=java&style=for-the-badge">
  <img src="https://img.shields.io/badge/-SpringBoot-6DB33F.svg?logo=springboot&style=for-the-badge&logoColor=white">
  <img src="https://img.shields.io/badge/-Thymeleaf-005F0F.svg?style=for-the-badge">
  <img src="https://img.shields.io/badge/-PostgreSQL-4479A1.svg?logo=mysql&style=for-the-badge&logoColor=white">
  <img src="https://img.shields.io/badge/-GitHub-181717.svg?logo=github&style=for-the-badge">
</p>

---


## 📚 目次

1. [プロジェクト概要](#プロジェクト概要)
2. [使用技術](#使用技術)
3. [画面イメージ](#画面イメージ)
4. [ユースケース図](#ユースケース図)
5. [テーブル設計](#テーブル設計)
6. [URL設計](#url設計)
7. [ディレクトリ構成](#ディレクトリ構成)
8. [工夫した点](#工夫した点)
9. [今後の課題](#今後の課題)
10. [作成者情報](#作成者情報)

---
## 🧩 プロジェクト概要

Spring Bootを使用して開発した料理が好き人に向けるブログウェブサイトです。ユーザーはログインして記事を投稿・編集・削除でき、他ユーザーの投稿を見るすことも可能です。

---
## ⚙️ 使用技術

- バックエンド：Java 17 / Spring Boot / JPA
- フロントエンド：HTML / CSS / JavaScript / Thymeleaf
- データベース：PostgreSQL
- ビルド・管理：GitHub

---
## 🖼 画面イメージ

> スクリーンショットを `images/` フォルダに追加し、以下に貼り付けてください

- account登録画面
- <img src="/images/login.png">
- ログイン画面
- <img src="/images/login.png">
- 投稿一覧画面
- <img src="/images/bloglist.png">
- ブログ投稿フォーム
- <img src="/images/blogregister.png">
- ブログ編集・削除フォーム
- <img src="/images/blogedit.png">
---

## 🧭 ユースケース図

- アカウントの登録・ログイン
- ブログ作成・編集・削除
- ブログ一覧
 <img src="/images/usecase.png">
> ※ draw.io や StarUML で作成した図を貼り付けてください

---


## 🗃 テーブル設計

```sql
-- account テーブル
CREATE TABLE account(
    account_id BIGINT PRIMARY KEY,
    account_name VARCHAR(255),
    account_email VARCHAR(255),
    password  VARCHAR(255)
)

-- blog テーブル
CREATE TABLE blog(
    blog_id BIGINT PRIMARY KEY,
    blog_title VARCHAR(255),
    category_name VARCHAR(255),
    blog_image VARCHAR(255),
    article TEXT,
    account_id BIGINT,
    FOREIGN KEY (account_id) REFERENCES account(account_id)
)
```

---

## 🌐 URL設計

- `/account/register`：ユーザー登録画面（GET, POST）
- `/account/login`：ログイン画面（GET, POST）
- `/blog/list`：ブログ一覧（GET）
- `/blog/register/process`：ブログ追加フォーム（GET, POST）
- `/blog/edit/process`：ブログ編集・削除（GET, POST）

---

## 📂 ディレクトリ構成

```
src/main/java
  ├── blog.com
    ├── controller       // 各種コントローラ
  ├── models
    ├── dao              // JPA Dao
    ├── entity           // JPAエンティティ
  ├── service          // 業務ロジック
src/main/resource
  ├── static           // CSSやJSなど静的ファイル
  ├── templates        // Thymeleafテンプレート
```

---

## 💡 工夫した点

- 各画面共通のCSSの設計
- ブログ作者以外の人による編集・削除操作を防ぐこと
- 

---

## 🧪 今後の課題

- ブログへの「コメント」「いいね」機能
- Spring Securityによる認可設定
- ブログの検出機能

---

## 👤 作成者情報

- 氏名：RAN　MINWEN
- 所属：
- GitHub：(https://github.com/Ranminwen)

<p align="right">(<a href="#top">トップへ</a>)</p>
