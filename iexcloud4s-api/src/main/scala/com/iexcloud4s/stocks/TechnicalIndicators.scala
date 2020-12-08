package com.iexcloud4s.stocks

import scala.collection.generic.CanBuildFrom

final case class TechnicalIndicators[T](
  indicator: Array[Array[Option[BigDecimal]]],
  chart: List[T] // TODO Maybe Array
)

object TechnicalIndicators {

  import com.iexcloud4s.http.Parameters

  sealed abstract class Indicator(
    name: String,
    input1: Option[BigDecimal] = None,
    input2: Option[BigDecimal] = None,
    input3: Option[BigDecimal] = None,
    input4: Option[BigDecimal] = None
  ) {
    def path: String = name
    def params: Parameters =
      Parameters(
        "input1" -> input1,
        "input2" -> input2,
        "input3" -> input3,
        "input4" -> input4,
      )
  }
  final case object Abs extends Indicator("abs")
  final case object Acos extends Indicator("acos")
  final case object Ad extends Indicator("ad")
  final case object Add extends Indicator("add")
  final case class  Adosc(short: BigDecimal, long: BigDecimal) extends Indicator("adosc", Some(short), Some(long))
  final case class  Adx(period: BigDecimal) extends Indicator("adx", Some(period))
  final case class  Adxr(period: BigDecimal) extends Indicator("adxr", Some(period))
  final case object Ao extends Indicator("ao")
  final case class  Apo(short: BigDecimal, long: BigDecimal) extends Indicator("apo", Some(short), Some(long))
  final case class  Aroon(period: BigDecimal)
      extends Indicator("aroon", Some(period))
  final case class  Aroonosc(period: BigDecimal)
      extends Indicator("aroonosc", Some(period))
  final case object Asin extends Indicator("asin")
  final case object Atan extends Indicator("atan")
  final case class  Atr(period: BigDecimal)
      extends Indicator("atr", Some(period))
  final case object Avgprice extends Indicator("avgprice")
  final case class  Bbands(period: BigDecimal, stddev: BigDecimal)
      extends Indicator("bbands", Some(period), Some(stddev))
  final case object Bop extends Indicator("bop")
  final case class  Cci(period: BigDecimal) extends Indicator("cci", Some(period))
  final case object Ceil extends Indicator("ceil")
  final case class  Cmo(period: BigDecimal) extends Indicator("cmo", Some(period))
  final case object Cos extends Indicator("cos")
  final case object Cosh extends Indicator("cosh")
  final case object Crossany extends Indicator("crossany")
  final case object Crossover extends Indicator("crossover")
  final case class  Cvi(period: BigDecimal) extends Indicator("cvi", Some(period))
  final case class  Decay(period: BigDecimal) extends Indicator("decay", Some(period))
  final case class  Dema(period: BigDecimal) extends Indicator("dema", Some(period))
  final case class  Di(period: BigDecimal) extends Indicator("di", Some(period))
  final case object Div extends Indicator("div")
  final case class  Dm(period: BigDecimal) extends Indicator("dm", Some(period))
  final case class  Dpo(period: BigDecimal) extends Indicator("dpo", Some(period))
  final case class  Dx(period: BigDecimal) extends Indicator("dx", Some(period))
  final case class  Edecay(period: BigDecimal) extends Indicator("edecay", Some(period))
  final case class  Ema(period: BigDecimal) extends Indicator("ema", Some(period))
  final case object Emv extends Indicator("emv")
  final case object Exp extends Indicator("exp")
  final case class  Fisher(period: BigDecimal) extends Indicator("fisher", Some(period))
  final case object Floor extends Indicator("floor")
  final case class  Fosc(period: BigDecimal) extends Indicator("fosc", Some(period))
  final case class  Hma(period: BigDecimal) extends Indicator("hma", Some(period))
  final case class  Kama(period: BigDecimal) extends Indicator("kama", Some(period))
  final case class  Kvo(short: BigDecimal, long: BigDecimal) extends Indicator("kvo", Some(short), Some(long))
  final case class  Lag(period: BigDecimal) extends Indicator("lag", Some(period))
  final case class  Linreg(period: BigDecimal) extends Indicator("linreg", Some(period))
  final case class  Linregintercept(period: BigDecimal) extends Indicator("linregintercept", Some(period))
  final case class  Linregslope(period: BigDecimal) extends Indicator("linregslope", Some(period))
  final case object Ln extends Indicator("ln")
  final case object Log10 extends Indicator("log10")
  final case class  Macd(short: BigDecimal, long: BigDecimal, signal: BigDecimal)
      extends Indicator("macd", Some(short), Some(long), Some(signal))
  final case object Marketfi extends Indicator("marketfi")
  final case class  Mass(period: BigDecimal) extends Indicator("mass", Some(period))
  final case class  Max(period: BigDecimal) extends Indicator("max", Some(period))
  final case class  Md(period: BigDecimal) extends Indicator("md", Some(period))
  final case object Medprice extends Indicator("medprice")
  final case class  Mfi(period: BigDecimal) extends Indicator("mfi", Some(period))
  final case class  Min(period: BigDecimal) extends Indicator("min", Some(period))
  final case class  Mom(period: BigDecimal) extends Indicator("mom", Some(period))
  final case class  Msw(period: BigDecimal) extends Indicator("msw", Some(period))
  final case object Mul extends Indicator("mul")
  final case class  Natr(period: BigDecimal) extends Indicator("natr", Some(period))
  final case object Nvi extends Indicator("nvi")
  final case object Obv extends Indicator("obv")
  final case class  Ppo(short: BigDecimal, long: BigDecimal)
      extends Indicator("ppo", Some(short), Some(long))
  final case class  Psar(step: BigDecimal, max: BigDecimal)
      extends Indicator("psar", Some(step), Some(max))
  final case object Pvi extends Indicator("pvi")
  final case class  Qstick(period: BigDecimal) extends Indicator("qstick", Some(period))
  final case class  Roc(period: BigDecimal) extends Indicator("roc", Some(period))
  final case class  Rocr(period: BigDecimal) extends Indicator("rocr", Some(period))
  final case object Round extends Indicator("round")
  final case class  Rsi(period: BigDecimal) extends Indicator("rsi", Some(period))
  final case object Sin extends Indicator("sin")
  final case object Sinh extends Indicator("sinh")
  final case class  Sma(period: BigDecimal) extends Indicator("sma", Some(period))
  final case object Sqrt extends Indicator("sqrt")
  final case class  Stddev(period: BigDecimal) extends Indicator("stddev", Some(period))
  final case class  Stderr(period: BigDecimal) extends Indicator("stderr", Some(period))
  final case class  Stoch(k: BigDecimal, kSlowing: BigDecimal, d: BigDecimal)
      extends Indicator("stoch", Some(k), Some(kSlowing), Some(d))
  final case class  Stochrsi(period: BigDecimal)
      extends Indicator("stochrsi", Some(period))
  final case object Sub extends Indicator("sub")
  final case class  Sum(period: BigDecimal) extends Indicator("sum", Some(period))
  final case object Tan extends Indicator("tan")
  final case object Tanh extends Indicator("tanh")
  final case class  Tema(period: BigDecimal) extends Indicator("tema", Some(period))
  final case object Todeg extends Indicator("todeg")
  final case object Torad extends Indicator("torad")
  final case object Tr extends Indicator("tr")
  final case class  Trima(period: BigDecimal) extends Indicator("trima", Some(period))
  final case class  Trix(period: BigDecimal) extends Indicator("trix", Some(period))
  final case object Trunc extends Indicator("trunc")
  final case class  Tsf(period: BigDecimal) extends Indicator("tsf", Some(period))
  final case object Typprice extends Indicator("typprice")
  final case class  Ultosc(short: BigDecimal, med: BigDecimal, long: BigDecimal)
      extends Indicator("ultosc", Some(short), Some(med), Some(long))
  final case class  Var(period: BigDecimal) extends Indicator("var", Some(period))
  final case class  Vhf(period: BigDecimal) extends Indicator("vhf", Some(period))
  final case class  Vidya(short: BigDecimal, long: BigDecimal, alpha: BigDecimal)
      extends Indicator("vidya", Some(short), Some(long), Some(alpha))
  final case class  Volatility(period: BigDecimal)
      extends Indicator("volatility", Some(period))
  final case class  Vosc(short: BigDecimal, long: BigDecimal)
      extends Indicator("vosc", Some(short), Some(long))
  final case class  Vwma(period: BigDecimal) extends Indicator("vwma", Some(period))
  final case object Wad extends Indicator("wad")
  final case object Wcprice extends Indicator("wcprice")
  final case class  Wilders(period: BigDecimal)
      extends Indicator("wilders", Some(period))
  final case class  Willr(period: BigDecimal) extends Indicator("willr", Some(period))
  final case class  Wma(period: BigDecimal) extends Indicator("wma", Some(period))
  final case class  Zlema(period: BigDecimal) extends Indicator("zlema", Some(period))

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit def decoder[T](implicit dec: Decoder[T]): Decoder[TechnicalIndicators[T]] =
    deriveDecoder[TechnicalIndicators[T]]

}
