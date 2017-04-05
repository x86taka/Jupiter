@echo off

rem Jupiter 起動バッチファイル(Windows専用)

rem 作成: Itsu
rem 最終更新: 2017/4/6 0:02

rem 引数で外部プロセスへの出力に対応させています。

java -Djline.terminal=jline.UnsupportedTerminal -jar nukkit-1.0-SNAPSHOT.jar
