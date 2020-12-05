package com.iexcloud4s

import io.circe.Decoder
import io.circe.Decoder.decodeString
import io.circe.generic.extras.Configuration
import java.time.format.DateTimeParseException
import java.time.LocalDate

package object utils {

  object Decoding {

    implicit val config: Configuration = Configuration.default.withDefaults

    implicit val decoderOptDate: Decoder[Option[LocalDate]] =
      decodeString.map { string =>
        try Some(LocalDate.parse(string))
        catch {
          case e: DateTimeParseException => None
        }
      }
  }

}
