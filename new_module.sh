#!/usr/bin/env bash

BASEDIR=$(cd "$(dirname $0)" && pwd)
cd $BASEDIR

MODULE_NAME=""
BASE_PACKAGE="com.cdg"

info() {
  ehco "INFO: $1"
}

warn() {
  echo "WARN: $1"
}

error() {
  echo "ERROR: $1"
  exit 1

setModuleName() {
  while true
  do
    info "모듈 이름을 입력하세요."
    read MODULE_NAME
    if [ "$MODULE_NAME" == "" ]; then
      warn "빈 문자열일 수 없습니다."
    elif [ -d "$MODULE_NAME" ]; then
      warn "이미 존재하는 디렉토리입니다."
    else
      info "모듈 이름: $MODULE_NAME"
      break
    fi
  done
}

createModule() {
  info "모듈 생성 중..."
  DIRECTORY_FOR_BASE_PACKAGE=$(echo $BASE_PACKAGE | sed -e "s/\./\//g")
  mkdir -p $MODULE_NAME
  mkdir -p $MODULE_NAME/src/main/kotlin/$DIRECTORY_FOR_BASE_PACKAGE/$MODULE_NAME
  mkdir -p $MODULE_NAME/src/test/kotlin/$DIRECTORY_FOR_BASE_PACKAGE/$MODULE_NAME
  info "모듈 생성 완료"

  build_gradle_file="$MODULE_NAME/build.gradle.kts"
  touch $build_gradle_file
  echo "plugins {" >> $build_gradle_file
  echo "} " >> $build_gradle_file
  echo "" >> $build_gradle_file

  echo "dependencies {" >> $build_gradle_file
  echo "" >> $build_gradle_file
  echo "} " >> $build_gradle_file

  info "file created $build_gradle_file"

  ESCAPED_NAME=$(echo $MODULE_NAME | sed -e "s/\//\:/g")
  sed -i '' "s/include(/include\(\n    \"${ESCAPED_NAME}\",/" settings.gradle.kts

  info "module added to settings.gradle.kts"
}

setModuleName

createModule