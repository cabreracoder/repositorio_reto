package com.example.tutorialreto

object RetrofitClient {
    private const val BASE_URL = "https://dummyjson.com/"

    // Muestra en el Logcat lo que se envía y se recibe (útil para depurar)
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    // 'by lazy' = se crea solo la primera vez que se usa
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}