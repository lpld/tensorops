package com.github.lpld.tensorops

import Nat.*

object NatDemo:
  summon[Pred[_5] =:= _4]
  
  summon[_1 + _3 =:= _4]
  summon[_3 + _1 =:= _4]
  summon[_0 + _0 =:= _0]
  summon[_2 * _12 =:= _24]
  summon[_27 - _19 =:= _8]

  def check[A <: Nat, B <: Nat] =
    summon[Succ[A] + B =:= Succ[A + B]]
