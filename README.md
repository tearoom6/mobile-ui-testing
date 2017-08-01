# サンプルアプリケーション (mobile-ui-testing)

[WEB+DB PRESS Vol.99](http://gihyo.jp/magazine/wdpress/archive/2017/vol99/support) の "［iOS/Android両対応!!］UIテスト自動化" のサンプルアプリケーションで学習。

----------------------------------------------------------

# WEB+DB PRESS Vol.99 特集2「UIテスト自動化」サンプルコード

技術評論社刊「WEB+DB PRESS Vol.99」の特集2「UIテスト自動化」のサンプルコードです。

本サンプルコードには、以下のコードが含まれています。

 - iOSアプリのプロダクトコード / XCTestのテストコード
 - Androidアプリのプロダクトコード / Espressoのテストコード
 - Appiumを用いたRSpecのテストコード

それぞれについての実行方法について以下に記載します。

## 共通の下準備
本アプリは[Firebase](https://firebase.google.com/?hl=ja)を利用しています。以下の手順に従い準備をしておいてください。

 1. Firebaseのアカウントを作成してください。
 2. プロジェクトを1つ作成してください。
 3. 左メニューからAuthenticationを選択してください。
 4. ログイン方法からメール/パスワードのステータスを有効にしてください。

## 注意点
自動テストで利用すテストアセットのユーザーは、アプリから操作して事前に作っておき利用しますが、iOS/Android共に同じユーザーを利用してください。


## Androidアプリ / Espresso(3章)
### 事前に準備するもの
本アプリは[Firebase](https://firebase.google.com/?hl=ja)を利用しています。

 1. 下準備で作成したプロジェクトに、アプリ(Android)を追加してください。
 2. google-services.jsonをダウンロードしてください。
 3. autotest-sample/appディレクトリ以下に置いてください。

### 開発環境
以下の環境で動作確認済みです。

- Java: 1.8.0_121
- Ruby: 2.3.4
- Android Studio: 2.3.2

### コードスタイル
AndroidStudio 2.3.2 におけるデフォルトのフォーマットを利用しています。

### 下準備
Android用のサンプルコード上で以下を行ってください。

1. Bundlerのインストール

```bash
$ gem install bundler
```

2. Gemのインストール（Fastlane）

```bash
$ bundle install --path vendor/bundle
```

ここまでの準備が終われば、Android Studioでプロジェクトを開くことにより、アプリのビルドをおこなうことができます。
次で紹介しているテストアセットの用意が終わったら、テストの実行もおこなうことができます。

### テストアセットの用意
アプリから事前にユーザーを登録し、ノートを1つ以上作成しておいてから、以下のファイルに情報を記入しておきます。

`app/src/androidTest/assets/data.json`


### コマンドからのビルド
以下のコマンドでapkファイルが作成されます。

```bash
$ bundle exec fastlane build
```

`app/build/outputs/apk/`以下に2つのapkファイルが出来ます。

 - autotest-sample-debug.apk
 - autotest-sample-release-unsigned.apk

### コマンドからのテストの実行
以下のコマンドでUIテストが実行されます。

```bash
$ bundle exec fastlane ui_test
```

事前にエミュレーターの起動、または実機を接続しておいてください。


## iOSアプリ / XCTest(4章)
### 事前に準備するもの
本アプリは[Firebase](https://firebase.google.com/?hl=ja)を利用しています。

 1. 下準備で作成したプロジェクトに、アプリ(iOS)を追加してください。
 2. GoogleService-info.plistをダウンロードしてください。
 3. autotest-sampleディレクトリ以下に置いてください。

### 開発環境
以下の環境で動作確認済みです

- XCode 8.3.2
- Ruby: 2.3.4

### 下準備
iOS用のサンプルコード上で以下を行ってください。

1. Bundlerのインストール

```bash
$ gem install bundler
```

2. Gemのインストール（Fastlane、CocoaPodsなど）

```bash
$ bundle install --path vendor/bundle
```

3. CocoaPodsライブラリのインストール

```bash
$ bundle exec pod install
```

4. Carthageのインストール

```bash
$ carthage update --platform iOS
```

ここまでの準備が終われば、Xcodeでプロジェクトを開くことにより、アプリのビルドをおこなうことができます。
次で紹介しているテストアセットの用意が終わったら、テストの実行もおこなうことができます。

### テストアセットの用意
アプリから事前にユーザーを登録し、ノートを1つ以上作成しておいてから、以下のファイルに情報を記入しておきます。

`autotest-sampleUITests/data.plist`


### コマンドからのビルド
ipaファイルの作成は以下のコマンドでおこなえます。

```bash
$ bundle exec fastlane build
```

`apps/autotest-sample.ipa` が生成されます

### コマンドからのテストの実行
以下のコマンドでiOSシミュレータを使ったUIテストが実行されます。

```bash
$ bundle exec fastlane simulator_ui_test
```

実行するiOSシミュレータの予測変換をオフにしておくのが良いです。


## Appium + RSpec(5章)
### ディレクトリの用意
1. テストの失敗時に保存されるスクリーンショット先として`screenshots`ディレクトリを作成してください。

2. アプリケーションファイルの保存先として`apps`ディレクトリを作成してください。

Appiumディレクトリ以下は次のような構成になります。

```
Gemfile
Gemfile.lock
spec/
config/
screenshots/
apps/
```


### 下準備
Appium + RSpecのサンプルコード上で以下を行ってください。

1. Bundlerのインストール

```bash
$ gem install bundler
```

2. Gemのインストール

```bash
$ bundle install --path vendor/bundle
```


### iOS実機で動かすための下準備
iOSの実機で動かす場合は以下のコマンドを実行してください。

```bash
$ brew install libimobiledevice
$ npm install -g ios-deploy
```

さらに、問題が起きた場合は、[公式ドキュメント](https://github.com/appium/appium/blob/v1.6.5/docs/en/appium-setup/real-devices-ios.md)を参考にすると原因が特定しやすいです。


### アプリケーションファイルの用意
iOS/Androidのプロジェクトからアプリケーションファイルを用意します。
用意したファイルは`apps`ディレクトリ以下におきます。

### テストアセットの用意
iOS/Androidアプリで作成したユーザーの情報を、以下のファイルに記入しておきます。

`config/asset.yml`


### テストの実行
以下のコマンド(またはappium-desktopを利用)でAppiumを起動しておきます。

```bash
$ appium
```

次に、以下のコマンドでテストを実行します。
以下の場合は、Android端末でテストが実行されます。

```bash
$ PLATFORM=android bundle exec rspec spec/scenario_spec.rb
```


また、以下のようにオプションを指定して実行するシナリオを絞ることが出来ます。

```bash
$ PLATFORM=android bundle exec rspec --tag type:registered_user spec/scenario_spec.rb

$ PLATFORM=android bundle exec rspec --tag type:new_user spec/scenario_spec.rb
```

#### 実行時の環境変数について
共通

 - PLATFORM(必須)
   - iosまたはandroid
 - DEBUG(オプション)
   - 1の場合は、pryが有効になります
 - APP_PATH(オプション)
   - アプリケーションファイルの場所を指定します

iOS実機のみ

 - UDID(オプション)
   - iOS実機で動かす場合は端末のUDIDを指定します
   - 1端末のみ接続されている場合は、`auto`を指定するだけで動きます
 - TEAM_ID
   - ご自身のアカウントのチームIDを指定してください


デフォルト値などについては、`spec/support/helpers/(android|ios)/config_helper.rb`を確認してください。


## 今後のテストについて
### 既存テストコードの改良
#### iOS / XCTest
現在は、入力フォームの値を空にするという操作はしていません。
本来は、明示的に値を空にしたほうが良いので是非とも挑戦してみてください。

#### Appium + RSpec
現在、新規ユーザーのシナリオでは冗長な操作をしています。
その操作を省くことによりテストの実行時間が短縮化されます。
是非とも、その箇所を見つけて短縮化をしてみてください。

### 新機能
現状は以下の機能がありません。

 - ノートの編集
 - ノートの削除

一般的に、これらは追加される機能だと思います
是非とも自ら実装して頂き、テストコードまで実装してみてください。

