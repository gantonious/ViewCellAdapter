#!/bin/bash

BASE_API_ENDPOINT=https://api.bintray.com

# Generate release zip
./gradlew clean build generateRelease

# Get version of release
RELEASE_DIR=./library/build
RELEASE_FILE=$(ls $RELEASE_DIR/release*.zip)
VERSION_NUMBER=$(ls $RELEASE_DIR/release*.zip | cut -c 2- | tr -d [a-z/\\-] | rev | cut -c 2- | rev)

echo "Publishing Version $VERSION_NUMBER"

# CREATE VERSION
VERSION_ENDPOINT=$BASE_API_ENDPOINT/packages/$BINTRAY_USERNAME/$REPO_NAME/$PROJECT_NAME/versions
curl -X POST -u"$BINTRAY_USERNAME:$BINTRAY_API_KEY" -H "Content-Type: application/json" $VERSION_ENDPOINT -d "{ \"name\" : \"$VERSION_NUMBER\" }"

# UPLOAD VERSION
UPLOAD_ENDPOINT=$BASE_API_ENDPOINT/content/$BINTRAY_USERNAME/$REPO_NAME/$PROJECT_NAME/$VERSION_NUMBER/$VERSION_NUMBER.zip?explode=1
curl -T $RELEASE_FILE -u"$BINTRAY_USERNAME:$BINTRAY_API_KEY" $UPLOAD_ENDPOINT

# PUBLISH VERSION
PUBLISH_ENDPOINT=$BASE_API_ENDPOINT/content/$BINTRAY_USERNAME/$REPO_NAME/$PROJECT_NAME/$VERSION_NUMBER/publish
curl -X POST -u"$BINTRAY_USERNAME:$BINTRAY_API_KEY" -H "Content-Type: application/json" $PUBLISH_ENDPOINT

echo "Published Version $VERSION_NUMBER!"