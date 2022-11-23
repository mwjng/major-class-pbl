package com.example.android_sns

class User (
    val image : Int,        // 이미지 -> 구현 해주세요
    val writer: String,     // 글쓴사람 이메일
    val id : String,        // 글마다 id 부여
    val title : String,     // 글 제목
    val name : String,      // 글쓴사람 닉네임
    val content : String,   // 글내용
    val like : Int,         // 좋아요 갯수
    val date : String       // 글 올린 시간
    )