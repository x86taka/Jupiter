@echo off

rem Jupiter 起動バッチファイル(Windows専用)

rem 作成: Itsu
rem 最終更新: 2017/5/31 23:10

goto Main

:Main
set Jupiter=nukkit-1.0-SNAPSHOT.jar
cls
chcp 932 > nul
echo %Jupiter%を起動しています...
if not exist %Jupiter% (
	goto Finish
)

java -Djline.terminal=jline.UnsupportedTerminal -jar %Jupiter%

goto END

:Finish
	echo [ERROR]%Jupiter%が見つかりませんでした。
	echo キーを押して終了してください。
	pause > nul

:END
	timeout /t 1 > nul
