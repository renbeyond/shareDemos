//
// Created by 童堂 on 16/3/16.
//
#include "com_android_talon_test_JniUtils.h"
/*
 * Class:     com_android_talon_test_JniUtils.h
 * Method:    getString
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_android_talon_test_JniUtils_getString
        (JNIEnv *env, jobject obj){
    return (*env)->NewStringUTF(env,"Hello World!");
}


