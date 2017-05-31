@echo on

rem Jupiter 起動バッチファイル(Windows専用)

rem 作成: Itsu
rem 最終更新: 2017/4/6 0:36

goto Main

:Main
set Jupiter=nukkit-1.0-SNAPSHOT.jar
if not exist %Jupiter% (
	goto Finish
)
cls
chcp 932 > nul


java -Djline.terminal=jline.UnsupportedTerminal -jar %Jupiter%

goto END

:Finish
	echo [ERROR]%Jupiter%が見つかりませんでした。
	echo キーを押して終了してください。
	pause > nul

:END
	timeout /t 1 > nul
