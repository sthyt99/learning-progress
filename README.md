# 学習進捗管理アプリ - Learning Progress Tracker

このアプリは、ユーザーが自身の学習目標と日々の進捗記録を管理できる Web API です。  
管理者ユーザーは全ユーザーの情報を監視・管理することもできます。
今回は自主学習用のため、バックエンド（JAVA）のみ開発。

---


## 主な機能

- ユーザー登録・ログイン（JWT認証）
- ロール別アクセス制御（USER / ADMIN）
- 学習目標の登録・更新・削除
- 学習進捗記録の登録・確認
- 管理者用：全ユーザー／目標／記録一覧取得
- パスワード変更（マイページ操作）
- エラー共通仕様（バリデーション・業務例外）
- 初期データ自動登録（`DataInitializer`）

---


## 技術スタック

| 技術 | 内容 |
|------|------|
| Java 17 | 言語 |
| Spring Boot | アプリケーションフレームワーク |
| Spring Security + JWT | 認証／認可 |
| MySQL | データベース |
| JPA (Hibernate) | ORM |
| Maven | ビルドツール |
| Postman | テストツール |
| Swagger（オプション）| APIドキュメント（導入予定） |

---


## ディレクトリ構成（一部）

src/

├── config/ # セキュリティ設定

├── controller/ # REST API コントローラ

├── dto/ # リクエスト・レスポンスDTO

├── entity/ # エンティティ

├── exception/ # 共通例外処理

├── repository/ # JPAリポジトリ

├── service/ # ビジネスロジック

└── util/ # JWTユーティリティ など


## 環境構築手順

1. **このリポジトリを clone**
   ```bash
   git clone https://github.com/sthyt99/learning-progress-app.git
   cd learning-progress-app

2. MySQLのセットアップ

    CREATE DATABASE learning_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

3. application.properties を編集 （パスワードは公開しない）

    - spring.datasource.url=jdbc:mysql://localhost:3306/learning_db
    - spring.datasource.username=root
    - spring.datasource.password=********
    - spring.jpa.hibernate.ddl-auto=create
   
4.  Spring Boot アプリ起動

    IDE（Eclipse/IntelliJ）で LearningProgressApplication.java を実行


## 認証・認可（JWT）

1. ログイン → /api/auth/login（username＋password）

  初期データ（開発用）
  - ユーザーアカウント(USER)
    username : alice
    password : password123
  - 管理者アカウント(ADMIN)
    username : admin
    password : adminpass

2. 取得したJWTを各APIリクエストの Authorization ヘッダーに指定


## 主要API一覧（抜粋）

| メソッド   | パス                    | 説明             |
| ------ | --------------------- | -------------- |
| `POST` | `/api/users`          | 新規ユーザー登録       |
| `POST` | `/api/auth/login`     | ログイン（JWT取得）    |
| `GET`  | `/api/users/me`       | 自分の情報取得        |
| `PUT`  | `/api/users/password` | パスワード変更        |
| `GET`  | `/api/goals/me`       | 自分の目標一覧        |
| `POST` | `/api/goals`          | 新規目標登録         |
| `GET`  | `/api/progress/me`    | 自分の記録一覧        |
| `POST` | `/api/progress`       | 記録の追加          |
| `GET`  | `/api/admin/users`    | 全ユーザー一覧（ADMIN） |
| `GET`  | `/api/admin/goals`    | 全目標一覧（ADMIN）   |

