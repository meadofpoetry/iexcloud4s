package com.iexcloud4s

import io.circe.Decoder
import io.circe.Decoder.{decodeString, decodeOption}
import io.circe.generic.extras.Configuration
import java.time.format.DateTimeParseException
import java.time.LocalDate

package object utils {

  object Decoding {

    implicit val config: Configuration = Configuration.default.withDefaults

    implicit val decoderOptDate: Decoder[Option[LocalDate]] =
      decodeOption(decodeString).map { v =>
        try v.flatMap(string => Some(LocalDate.parse(string)))
        catch {
          case e: DateTimeParseException => None
        }
      }
  }

  object FormattingSyntax {

    implicit class ChartDateFormatting(date: LocalDate) {
      def chartFormat: String =
        s"${date.getYear()}${date.getMonth()}${date.getDayOfMonth()}"
    }

  }

}
