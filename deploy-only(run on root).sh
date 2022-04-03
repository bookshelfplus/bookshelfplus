
git reset HEAD --hard

git checkout deploy
git pull
git merge develop
git push

sudo chown -R www-data:www-data ../bookshelf.plus/
sudo chmod -R 755 ../bookshelf.plus/

pm2 restart bookshelfplus-frontend
java -jar ./bookshelfplus/target/bookshelfplus-1.0-SNAPSHOT.jar &