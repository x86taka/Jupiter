#!/bin/sh
DIR="$(cd -P "$( dirname "${BASH_SOURCE[0]}" )" && pwd)"
cd "$DIR"

###############
## 再起動の設定 #
###############

DO_LOOP="false"

###############################
# これより下の編集はお勧めしません! #
###############################

clear

NUKKIT_FILE=""

if [ "$NUKKIT_FILE" == "" ]; then
	if [ -f ./nukkit*.jar ]; then
		NUKKIT_FILE="./nukkit-1.0-SNAPSHOT.jar"
	else
		echo "[ERROR] Nukkit JAR が見つかりませんでした!"
		exit 1
	fi
fi

LOOPS=0

while [ "$LOOPS" -eq 0 ] || [ "$DO_LOOP" == "true" ]; do
	if [ "$DO_LOOP" == "true" ]; then
		java -jar "$NUKKIT_FILE" $@
	else
		exec java -jar "$NUKKIT_FILE" $@
	fi
	((LOOPS++))
done

if [ ${LOOPS} -gt 1 ]; then
	echo "[INFO] $LOOPS 回目の再起動です。"
fi