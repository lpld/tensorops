package com.github.lpld.tensorops

package tensorops

import Nat.*
import v2.*

@main def Main(args: String*): Unit =
  summon[_0 < _3]
  summon[_3 <= _3]

  summon[_3 >= _3]

  val a = summon[_2 + _17]
  summon[a.Result =:= _19]

  val b = summon[_29 - _13]
  summon[b.Result =:= _16]

  val c1 = summon[_7 * _4]
  summon[c1.Result =:= _28]

  val c2 = summon[_3 * _7]
  summon[c2.Result =:= _21]

  val c3 = summon[_8 * _1]
  summon[c3.Result =:= _8]

  val c4 = summon[_1 * _8]
  summon[c4.Result =:= _8]

  val c5 = summon[_0 * _8]
  summon[c5.Result =:= _0]

  val c6 = summon[_8 * _0]
  summon[c6.Result =:= _0]
  
  1 :: Nil

  val l1 = 1 #:: 2 #:: LNil
  summon[l1.Length =:= _2]

  // summon[LList.Concat[LNil, LNil] =:= LNil]
  // summon[LList.Concat[LNil, #::[Int, _12]] =:= #::[Int, _12]]
  // summon[LList.Concat[#::[Int, _1], #::[Int, _1]] =:= #::[Int, _5]]

  val m1: Tensor.Matrix[_4, _9] = ???
  summon[m1.Shape =:= TensorShape.Matrix[_4, _9]]

  val l2 = SCons(4, SCons(2, SNil))
  val l3 = SCons(3, SCons(1, SNil))
  val l4 = SCons(3, SCons(1, SCons(-4, SNil)))


  // summon[_4#Plus[_5] =:= _9]
  // summon[l2.Length == l4.Length]
  // summon[l3.Length =:= _5]
