package com.example.news_app_mvvm_sec20.MockWebServic_Test_NewsApiService

import com.example.news_app_mvvm_sec20.data.api.NewsApiService
import com.google.common.truth.Truth.assertThat

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiService_Test {

    private lateinit var newsApiService: NewsApiService
    private lateinit var mockWebServer:MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        newsApiService=Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))// we didnt give a url path it is just testing
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    //MockWebServer Can´t get the json response from our json fake file directly, so we make this fun to read the json file
    //and enqueue it into The MockWebServer
    private fun enqeueMockResponseInToMockWebServer(json_Response_fileName:String){

        val inputStream=javaClass.classLoader!!.getResourceAsStream(json_Response_fileName)
        val source_buffer = inputStream.source().buffer()
        val mockResponse = MockResponse()

        mockResponse.setBody(source_buffer.readString(Charsets.UTF_8))

        mockWebServer.enqueue(mockResponse)
    }

    @Test
    fun getHotHeadLines_sentRequest_recievedExpected(){
        //to run it we need Courotine Scope because it is a suspend function so we use runBlocking Scope
        runBlocking {
            //1- Enter json response to the Mock Server
            enqeueMockResponseInToMockWebServer("news_response.json")

            //2- get the response body
            val responseBody= newsApiService.getTopHeadLines(country = "us", page = 1).body()
            val request_path = mockWebServer.takeRequest()

            //3- Test the response body if it is not null and request path value
            assertThat(responseBody).isNotNull()
            assertThat(request_path.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=fca4d499ce1f48f9aa5b75b2535c9554")
            // we didnt give a url path it is just testing so we begin the link from v2/top.....
        }
    }

    // Test if the quantity of Top Head Lines is the default Number which is :20
    @Test
    fun getTopHeadLines_recieveResponse_correctPageSize(){
        runBlocking {
            //1- Enter json response to the Mock Server
            enqeueMockResponseInToMockWebServer("news_response.json")

            //2- get the response body
            val responseBody= newsApiService.getTopHeadLines(country = "us", page = 1).body()

            val articles_List=responseBody!!.articles
            assertThat(articles_List.size).isEqualTo(20)
        }
    }

    // Test if the Content of an article is correct: author + title +url + publishedAt
    @Test
    fun getTopHeadLines_recieveResponse_correctContent(){
        runBlocking {
            //1- Enter json response to the Mock Server
            enqeueMockResponseInToMockWebServer("news_response.json")

            //2- get the response body
            val responseBody= newsApiService.getTopHeadLines(country = "us", page = 1).body()

            val articles_List=responseBody!!.articles

            val first_Article=articles_List[0]

            assertThat(first_Article.author).isEqualTo("Galen Bacharier")
            assertThat(first_Article.title).isEqualTo("Iowa Caucuses: Donald Trump asks supporters to turn out in frigid cold - Des Moines Register")
            assertThat(first_Article.url).isEqualTo("https://www.desmoinesregister.com/story/news/elections/presidential/caucus/2024/01/14/iowa-caucuses-donald-trump-asks-supporters-to-turn-out-in-frigid-cold/72225013007/")
            assertThat(first_Article.publishedAt).isEqualTo("2024-01-15T00:04:38Z")
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}