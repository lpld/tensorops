package com.github.lpld.tensorops

package tensorops

import Nat.*
import TensorShape.*

@main def Main(args: String*): Unit =

  val l1 = "a" #: "b" #: "c" #: LNil
  val l2 = "x" #: "y" #: LNil

  val l3 = l1 concat l2

  summon[l3.Length =:= _5]
  println(l3)

  summon[_0 < _3]
  summon[_3 <= _3]

  summon[_3 >= _3]

  type shape1 = _15 +: _11 +: SNil

  summon[Rank[shape1] =:= _2]
