package com.iexcloud4s

package object types {

  sealed abstract class Period(private val string: String) {
    override def toString(): String = string
  }
  final case object Annual extends Period("annual")
  final case object Quarter extends Period("quarter")

  sealed abstract class SortingOrder(private val string: String) {
    override def toString(): String = string
  }
  final case object Desc extends SortingOrder("desc")
  final case object Asc extends SortingOrder("asc")

}
