package com.mahdi.actorn.utils


import org.apache.hc.client5.http.classic.methods.HttpGet
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.core5.http.HttpEntity
import org.apache.hc.core5.http.io.entity.EntityUtils
import java.net.URI

class NetworkQueryUtils
{
     fun getResponseFromHttpUrl(url : URI) : String
     {
          var client : CloseableHttpClient = HttpClients.createDefault()
          val request = HttpGet(url)
          val response : CloseableHttpResponse = client.execute(request)
          var response_entity_content = ""
          try
          {
               val entity : HttpEntity = response.getEntity()
               response_entity_content = EntityUtils.toString(entity)
               EntityUtils.consume(entity)
          }
          finally
          {
               response.close()
          }
          return response_entity_content
     }
}