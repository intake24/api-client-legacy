package uk.ac.ncl.openlab.intake24

import io.circe.generic.auto._
import org.slf4j.LoggerFactory
import uk.ac.ncl.openlab.intake24.api.client.ApiError.HttpError
import uk.ac.ncl.openlab.intake24.api.client.JsonCodecs
import uk.ac.ncl.openlab.intake24.api.shared.{EmailCredentials, RefreshResult, SigninResult}

/*
class AuthCacheImpl(val apiBaseUrl: String, val credentials: EmailCredentials) extends AuthCache with JsonCodecs {

  val logger = LoggerFactory.getLogger(classOf[AuthCacheImpl])

  var accessToken: Option[String] = None
  var refreshToken: Option[String] = None

  def getRefreshToken(): Unit = {
    val response = Http(s"$apiBaseUrl/signin").timeout(1000, 30000).header("Content-Type", "application/json").postData(toJson(credentials)).asString

    if (response.code == 200) {
      fromJson[SigninResult](response.body) match {
        case Left(e) => throw new RuntimeException("Failed to parse signin result", e)
        case Right(SigninResult(token)) => refreshToken = Some(token)
      }
    } else if (response.code == 401) {
      throw new RuntimeException("Invalid credentials")
    } else {
      throw new RuntimeException("Unexpected API response: " + response.code)
    }
  }

  def refreshAccessToken(): String = refreshToken match {
    case Some(token) =>
      val response = Http(s"$apiBaseUrl/refresh").timeout(1000, 30000).method("POST").header("X-Auth-Token", token).asString

      if (response.code == 200) {
        fromJson[RefreshResult](response.body) match {
          case Left(e) => throw new RuntimeException("Failed to parse refresh result", e)
          case Right(RefreshResult(token)) =>
            accessToken = Some(token)
            token
        }
      } else {
        throw new RuntimeException("Unexpected API response: " + response.code)
      }

    case None =>
      getRefreshToken()
      refreshAccessToken()
  }

  def withAccessToken[T](block: String => Either[ApiError, T]): Either[ApiError, T] = {

    def refreshAndRetry(): Either[ApiError, T] = {
      val newToken = refreshAccessToken()
      block(newToken)
    }

    accessToken match {
      case Some(token) => block(token) match {
        case Left(RequestFailed(401, _, _)) => refreshAndRetry()
        case Left(ErrorParseFailed(401, _)) => refreshAndRetry()
        case r => r
      }
      case None => refreshAndRetry()
    }
  }
}*/