package org.example.api

import io.reactivex.rxjava3.core.Observable
import org.example.model.Post
import retrofit2.Call
import retrofit2.http.GET

interface BlogApi {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts")
    fun getPostsCall(): Call<List<Post>>

    @GET("posts")
    fun getPostsObservable(): Observable<List<Post>>
}
