#!/bin/bash
set -e
rm -rf public || exit 0;
mkdir public

# publicフォルダに開発用ファイルを展開する。
cp -r src/* public

cd public

git config user.email "example@example.com"
git config user.name "Travis-CI"

git init
git add .
git commit -m "Deploy to GitHub Pages. #$TRAVIS_BUILD_NUMBER"
git push --force --quiet "https://${GH_TOKEN}@github.com/JupiterDevelopmentTeam/JupiterDevelopmentTeam.github.io.git" master:master > /dev/null 2>&1
